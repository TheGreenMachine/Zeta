package frc.team1816.robot.subsystems;

import badlog.lib.BadLog;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import com.edinarobotics.utils.hardware.RobotFactory;
import com.kauailabs.navx.frc.AHRS;
import com.team254.lib.geometry.Pose2dWithCurvature;
import com.team254.lib.geometry.Rotation2d;
import com.team254.lib.geometry.Twist2d;
import com.team254.lib.trajectory.TrajectoryIterator;
import com.team254.lib.trajectory.timing.TimedState;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Kinematics;
import frc.team1816.robot.Robot;
import frc.team1816.robot.RobotState;
import frc.team1816.robot.planners.DriveMotionPlanner;

@RunTest
public class Drivetrain extends Subsystem implements Checkable {
    public static final String NAME = "drivetrain";

    private static final double SLOW_MOD_THROTTLE = 0.5;
    private static final double SLOW_MOD_ROT = 0.8;

    private static double DRIVE_ENCODER_PPR;
    private static double TICKS_PER_REV;
    private static double TICKS_PER_INCH;
    private static double WHEELBASE;
    private static double MAX_VEL_TICKS_PER_100MS;

    private static double INCHES_PER_REV;

    private double kP;
    private double kI;
    private double kD;
    private double kF;

    private AHRS navX;

    private PigeonIMU gyro;

    private RobotState robotState = RobotState.getInstance();

    private IMotorController rightMain;
    private IMotorController rightSlaveOne;
    private IMotorController rightSlaveTwo;

    private IMotorController leftMain;
    private IMotorController leftSlaveOne;
    private IMotorController leftSlaveTwo;

    private double leftPower;
    private double rightPower;
    private double leftAccel;
    private double rightAccel;
    private double rotation;
    private double leftVel;
    private double rightVel;
    private double leftPos;
    private double rightPos;
    private double leftError;
    private double rightError;
    private double leftFeedForward;
    private double rightFeedForward;
    private double MPOutputLeftVel;
    private double MPOutputRightVel;

    private NetworkTable positions = NetworkTableInstance.getDefault().getTable("positions");

    private Rotation2d gyroAngle;
    private Rotation2d gyroOffset = Rotation2d.identity();

    private final double initTime;
    private double initX;
    private double initY;
    private double xPos;
    private double yPos;
    private double prevInches;
    private double prevX;
    private double prevY;
    private double prevLeftInches;
    private double prevRightInches;
    private Rotation2d initAngle;

    private boolean isPercentOut;
    private boolean isSlowMode;
    private boolean isReverseMode;
    private boolean isVisionMode = false;
    private boolean outputsChanged = false;
    private boolean overrideTrajectory = false;

    private DriveMotionPlanner driveMotionPlanner;

    public Drivetrain() {
        super(NAME);
        RobotFactory factory = Robot.factory;

        DRIVE_ENCODER_PPR = factory.getConstant("driveEncoderPPR");
        TICKS_PER_REV = factory.getConstant("ticksPerRev");
        TICKS_PER_INCH = factory.getConstant("ticksPerIn");
        WHEELBASE = factory.getConstant("wheelbase");
        MAX_VEL_TICKS_PER_100MS = factory.getConstant("maxVel");
        INCHES_PER_REV = TICKS_PER_REV / TICKS_PER_INCH;
        kP = factory.getConstant("kP");
        kI = factory.getConstant("kI");
        kD = factory.getConstant("kD");
        kF = factory.getConstant("kF");

        this.leftMain = factory.getMotor(NAME, "leftMain");
        this.leftSlaveOne = factory.getMotor(NAME, "leftSlaveOne", leftMain);
        this.leftSlaveTwo = factory.getMotor(NAME, "leftSlaveTwo", leftMain);

        this.rightMain = factory.getMotor(NAME, "rightMain");
        this.rightSlaveOne = factory.getMotor(NAME, "rightSlaveOne", rightMain);
        this.rightSlaveTwo = factory.getMotor(NAME, "rightSlaveTwo", rightMain);

        invertTalons(true);
        enableBrakeMode();
        setPID(kP, kI, kD, kF);

        navX = new AHRS(I2C.Port.kMXP);
        System.out.println("NavX Active: " + getGyroStatus());

        this.initTime = System.currentTimeMillis();

        // this.gyro = new PigeonIMU((TalonSRX) this.leftSlaveTwo);

        // initCoordinateTracking();
        // initDrivetrainLog();
        initEstimator();

        this.driveMotionPlanner = new DriveMotionPlanner();
    }

    private void invertTalons(boolean invertRight) {
        if (invertRight) {
            this.rightMain.setInverted(true);
            this.rightSlaveOne.setInverted(true);
            this.rightSlaveTwo.setInverted(true);
        } else {
            this.leftMain.setInverted(true);
            this.leftSlaveOne.setInverted(true);
            this.leftSlaveTwo.setInverted(true);
        }
    }

    public void enableBrakeMode() {
        this.leftMain.setNeutralMode(NeutralMode.Brake);
        this.leftSlaveOne.setNeutralMode(NeutralMode.Brake);
        this.leftSlaveTwo.setNeutralMode(NeutralMode.Brake);

        this.rightMain.setNeutralMode(NeutralMode.Brake);
        this.rightSlaveOne.setNeutralMode(NeutralMode.Brake);
        this.rightSlaveTwo.setNeutralMode(NeutralMode.Brake);
    }

    public void enableCoastMode() {
        this.leftMain.setNeutralMode(NeutralMode.Coast);
        this.leftSlaveOne.setNeutralMode(NeutralMode.Coast);
        this.leftSlaveTwo.setNeutralMode(NeutralMode.Coast);

        this.rightMain.setNeutralMode(NeutralMode.Coast);
        this.rightSlaveOne.setNeutralMode(NeutralMode.Coast);
        this.rightSlaveTwo.setNeutralMode(NeutralMode.Coast);
    }

    public void setPID(double pValue, double iValue, double dValue, double fValue){
        this.kP = pValue;
        this.kI = iValue;
        this.kD = dValue;
        this.kF = fValue;
        System.out.printf("PID values set: P=%.2f, I=%.2f, D=%.2f, F=%.2f\n", kP, kI, kD, kF);

        this.leftMain.config_kP(0, kP, 20);
        this.leftMain.config_kI(0, kI, 20);
        this.leftMain.config_kD(0, kD, 20);
        this.leftMain.config_kF(0, kF, 20);
        //this.leftMain.config_IntegralZone(0, izone, 20);

        this.rightMain.config_kP(0, kP, 20);
        this.rightMain.config_kI(0, kI, 20);
        this.rightMain.config_kD(0, kD, 20);
        this.rightMain.config_kF(0, kF, 20);
        //this.rightMain.config_IntegralZone(0, izone, 20);
    }

    // private void initDrivetrainLog() { // TODO: do all bad logging in different thread
    //     BadLog.createTopic("Drivetrain/Angle", "deg",
    //             () -> getGyroAngle());
    //     BadLog.createTopic("Drivetrain/Left Meas Velocity", BadLog.UNITLESS,
    //             () -> (double) this.leftMain.getSelectedSensorVelocity(0), "hide", "join:Drivetrain/Velocities");
    //     BadLog.createTopic("Drivetrain/Right Meas Velocity", BadLog.UNITLESS,
    //             () -> (double) this.rightMain.getSelectedSensorVelocity(0), "hide", "join:Drivetrain/Velocities");
    //     BadLog.createTopic("Drivetrain/Left Set Percent", BadLog.UNITLESS,
    //             () -> getLeftPower(), "hide", "join:Drivetrain/Percent Out");
    //     BadLog.createTopic("Drivetrain/Right Set Percent", BadLog.UNITLESS,
    //             () -> getRightPower(), "hide", "join:Drivetrain/Percent Out");
    // }

    // public void initCoordinateTracking() {
    //     this.initX = 0;
    //     this.initY = 0;
    //     this.xPos = 0;
    //     this.yPos = 0;
    //     this.prevRightInches = 0.0;
    //     this.prevLeftInches = 0.0;
    //     this.prevInches = 0;
    //     this.prevX = 0;
    //     this.prevY = 0;
    //     this.initAngle = navX.getAngle();
    //     System.out.println(initAngle);
    // }

    public void initEstimator() {
        xPos = 0;
        yPos = 0;
        prevRightInches = 0.0;
        prevLeftInches = 0.0;
        prevX = 0;
        prevY = 0;
        initAngle = getGyroAngle();
        System.out.println("Initial Angle: " + initAngle);
    }

    public double getLeftPower() {
        return leftPower;
    }

    public double getRightPower() {
        return rightPower;
    }

    public double getLeftVelocity(){
        return leftVel;
    }

    public double getRightVelocity(){
        return rightVel;
    }

    public double getLeftPosTicks() {
        return leftPos;
    }

    public double getRightPosTicks() {
        return rightPos;
    }

    public double ticksToInches(double ticks) {
        return ticks * (1 / TICKS_PER_REV) * INCHES_PER_REV;
    }

    public double getLeftPosInches() {
        return ticksToInches(this.leftPos);
    }

    public double getRightPosInches() {
        return ticksToInches(this.rightPos);
    }

    private static double radiansPerSecondToTicksPer100ms(double rad_s) {
        return rad_s / (Math.PI * 2.0) * DRIVE_ENCODER_PPR / 10.0;
    }

    public Rotation2d getGyroAngle() {
        return gyroAngle;
    }

    public double getHeadingDegrees(){
        return getGyroAngle().rotateBy(gyroOffset).getDegrees();
    }

    public void setHeading(Rotation2d heading){
        gyroOffset = heading.rotateBy(Rotation2d.fromDegrees(this.gyro.getFusedHeading()).inverse());
        gyroAngle = heading;
    }

    public boolean getGyroStatus() {
        return navX.isConnected();
    }

    public void setDrivetrainVelocity(double leftPower, double rightPower, double leftFeedForward, double rightFeedForward){
        this.leftFeedForward = leftFeedForward;
        this.rightFeedForward = rightFeedForward;
        setDrivetrainVelocity(leftPower, rightPower, 0);
    }

    public void setDrivetrainVelocity(double leftPower, double rightPower) {
        setDrivetrainVelocity(leftPower, rightPower, 0);
    }

    public void setDrivetrainVelocity(double leftPower, double rightPower, double rotation) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;
        this.rotation = rotation;
        isPercentOut = false;
        outputsChanged = true;
    }

    public void setDrivetrainPercent(double leftPower, double rightPower) {
        setDrivetrainPercent(leftPower, rightPower, 0);
    }

    public void setDrivetrainPercent(double leftPower, double rightPower, double rotation) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;
        this.rotation = rotation;
        isPercentOut = true;
        outputsChanged = true;
    }

    public void setDrivetrainVisionNav(boolean visionOn) {
        isVisionMode = visionOn;
    }

    public boolean getVisionStatus() {
        return isVisionMode;
    }

    public void setSlowMode(boolean slowMode) {
        this.isSlowMode = slowMode;
        outputsChanged = true;
    }

    public boolean isReverseMode() {
        return isReverseMode;
    }

    public void setReverseMode(boolean reverseMode) {
        this.isReverseMode = reverseMode;
        outputsChanged = true;
    }

    public void toggleReverseMode() {
        this.isReverseMode = !this.isReverseMode;
        outputsChanged = true;
    }

    public synchronized void setTrajectory(TrajectoryIterator<TimedState<Pose2dWithCurvature>> trajectory){
        if (driveMotionPlanner != null){
            overrideTrajectory = false;
            driveMotionPlanner.reset();
            driveMotionPlanner.setTrajectory(trajectory);
        }
    }

    public boolean isDoneWithTrajectory(){
        return driveMotionPlanner.isDone() || overrideTrajectory; 
    }

    private void updatePathFollower() {
        final double now = Timer.getFPGATimestamp();

        DriveMotionPlanner.Output output = driveMotionPlanner.update(now, RobotState.getInstance().getFieldToVehicle(now));

        this.MPOutputLeftVel = radiansPerSecondToTicksPer100ms(output.left_velocity);
        this.MPOutputRightVel = radiansPerSecondToTicksPer100ms(output.right_velocity);

        if (!overrideTrajectory) {
            setDrivetrainVelocity(radiansPerSecondToTicksPer100ms(output.left_velocity), 
                                    radiansPerSecondToTicksPer100ms(output.right_velocity),
                                    output.left_feedforward_voltage / 12.0,
                                    output.right_feedforward_voltage / 12.0);
                System.out.println("Left Velocity: " + output.left_velocity);
                System.out.println("Right Velocity: " + output.right_velocity);

            this.leftAccel = radiansPerSecondToTicksPer100ms(output.left_accel) / 1000.0;
            this.rightAccel = radiansPerSecondToTicksPer100ms(output.right_accel) / 1000.0;
        } else {
            setDrivetrainVelocity(0, 0, 0, 0);
            this.leftAccel = this.rightAccel = 0.0;
        }
    }

    public double getMPOutputLeftVelocity(){
        return this.MPOutputLeftVel;
    }

    public double getMPOutputRightVelocity(){
        return this.MPOutputRightVel;
    }

    public void updateEstimator(){
        double timestamp = Timer.getFPGATimestamp();
        final double currLeftInches = getLeftPosInches();
        final double currRightInches = getRightPosInches();
        final double deltaLeft = currLeftInches - prevLeftInches;
        final double deltaRight = currRightInches - prevRightInches;
        final Rotation2d gyroAngle = getGyroAngle();
        final Twist2d measuredVelocity = robotState.generateOdometryFromSensors(deltaLeft, deltaRight, gyroAngle);
        final Twist2d predictedVelocity = Kinematics.forwardKinematics(getLeftVelocity(), getLeftPosInches());
        robotState.addObservations(timestamp, measuredVelocity, predictedVelocity);
        prevLeftInches = currLeftInches;
        prevRightInches = currRightInches;
    }

    @Override
    public void periodic() {
        //this.gyroAngle = navX.getAngle();
        this.gyroAngle = Rotation2d.fromDegrees(this.gyro.getFusedHeading()).rotateBy(gyroOffset);
        this.leftPos = leftMain.getSelectedSensorPosition(0);
        this.rightPos = rightMain.getSelectedSensorPosition(0);
        this.leftVel = leftMain.getSelectedSensorVelocity(0);
        this.rightVel = rightMain.getSelectedSensorVelocity(0);
        
        if (outputsChanged) {
            if (isSlowMode) {
                leftPower *= SLOW_MOD_THROTTLE;
                rightPower *= SLOW_MOD_THROTTLE;
                rotation *= SLOW_MOD_ROT;
            }

            if (isReverseMode) {
                leftPower *= -1;
                rightPower *= -1;
            }

            leftPower += rotation * .55;
            rightPower -= rotation * .55;

            leftVel = leftPower * MAX_VEL_TICKS_PER_100MS;
            rightVel = rightPower * MAX_VEL_TICKS_PER_100MS;

            if (isPercentOut) {
                this.leftMain.set(ControlMode.PercentOutput, leftPower);
                this.rightMain.set(ControlMode.PercentOutput, rightPower);
            } else {
                this.leftMain.set(ControlMode.Velocity, leftVel, DemandType.ArbitraryFeedForward,
                                leftFeedForward + kD * leftAccel / 1023);
                this.rightMain.set(ControlMode.Velocity, rightVel, DemandType.ArbitraryFeedForward,
                                rightFeedForward + kD * rightAccel / 1023);
            }

            outputsChanged = false;
        }

        //  coordinateTracking();
        updateEstimator();
        updatePathFollower();
    }

    // public void coordinateTracking() {
    //     double currLeftInches = getLeftPosInches();
    //     double currRightInches = getRightPosInches();
    //     double avgDistance = ((currLeftInches - prevLeftInches) + (currRightInches - prevRightInches)) / 2;
    //     double theta = (Math.toRadians(initAngle - gyroAngle) + Math.PI / 2);

    //     xPos = avgDistance * Math.cos(theta) + prevX;
    //     yPos = avgDistance * Math.sin(theta) + prevY;

    //     positions.getEntry("xPos").setDouble(xPos);
    //     positions.getEntry("yPos").setDouble(yPos);

    //     prevX = xPos;
    //     prevY = yPos;
    //     prevLeftInches = currLeftInches;
    //     prevRightInches = currRightInches;
    // }

    @Override
    protected void initDefaultCommand() {
    }

    @Override
    public boolean check() throws CheckFailException {
        // System.out.println("Warning: mechanisms will move!");
        return true;
    }
}
