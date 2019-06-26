package frc.team1816.robot.subsystems;

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

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
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

    // private double kP;
    // private double kI;
    // private double kD;
    // private double kF;

    private AHRS navX;

    private PigeonIMU gyro;

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
    private double leftError;
    private double rightError;
    private double leftFeedForward;
    private double rightFeedForward;
    private double MPOutputLeftVel;
    private double MPOutputRightVel;

    private double leftSetVel;
    private double rightSetVel;

    private double leftMeasPos;
    private double rightMeasPos;
    private Rotation2d gyroAngle;
    private Rotation2d gyroOffset = Rotation2d.identity();

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
        // kP = factory.getConstant("kP");
        // kI = factory.getConstant("kI");
        // kD = factory.getConstant("kD");
        // kF = factory.getConstant("kF");

        this.leftMain = factory.getMotor(NAME, "leftMain");
        this.leftSlaveOne = factory.getMotor(NAME, "leftSlaveOne", leftMain);
        this.leftSlaveTwo = factory.getMotor(NAME, "leftSlaveTwo", leftMain);

        this.rightMain = factory.getMotor(NAME, "rightMain");
        this.rightSlaveOne = factory.getMotor(NAME, "rightSlaveOne", rightMain);
        this.rightSlaveTwo = factory.getMotor(NAME, "rightSlaveTwo", rightMain);

        invertTalons(true);
        setNeutralMode(NeutralMode.Brake);

        try {
            navX = new AHRS(SPI.Port.kMXP);
            System.out.println("NavX Instantiated");
        } catch (RuntimeException e) {
            DriverStation.reportError("Error instantiating navX-MXP:  " + e.getMessage(), true);
        }

        driveMotionPlanner = new DriveMotionPlanner();
        initEstimator();
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

    public void setNeutralMode(NeutralMode mode) {
        this.leftMain.setNeutralMode(mode);
        this.leftSlaveOne.setNeutralMode(mode);
        this.leftSlaveTwo.setNeutralMode(mode);

        this.rightMain.setNeutralMode(mode);
        this.rightSlaveOne.setNeutralMode(mode);
        this.rightSlaveTwo.setNeutralMode(mode);
    }

    public void initEstimator(){
        prevLeftInches = 0;
        prevRightInches = 0;
        initAngle = getGyroAngle();
    }

    public double getLeftPower() {
        return leftPower;
    }

    public double getRightPower() {
        return rightPower;
    }

    public double getLeftVelocity(){
        return leftSetVel;
    }

    public double getRightVelocity(){
        return rightSetVel;
    }

    public double getLeftPosTicks() {
        return leftMeasPos;
    }

    public double getRightPosTicks() {
        return rightMeasPos;
    }

    public double ticksToInches(double ticks) {
        return ticks * (1 / TICKS_PER_REV) * INCHES_PER_REV;
    }

    public double getLeftPosInches() {
        return ticksToInches(this.leftMeasPos);
    }

    public double getRightPosInches() {
        return ticksToInches(this.rightMeasPos);
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
        gyroOffset = heading.rotateBy(Rotation2d.fromDegrees(this.navX.getAngle()).inverse());
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
        // System.out.println("Robot State Instance:\t" + RobotState.getInstance());
        // System.out.println("Time Stamp:\t" + now);
        // System.out.println("DriveMotionPlanner:\t" + driveMotionPlanner);
        DriveMotionPlanner.Output output = driveMotionPlanner.update(now, RobotState.getInstance().getFieldToVehicle(now));

        this.MPOutputLeftVel = radiansPerSecondToTicksPer100ms(output.left_velocity);
        this.MPOutputRightVel = radiansPerSecondToTicksPer100ms(output.right_velocity);

        if (!overrideTrajectory) {
            setDrivetrainVelocity(radiansPerSecondToTicksPer100ms(output.left_velocity), 
                                    radiansPerSecondToTicksPer100ms(output.right_velocity),
                                    output.left_feedforward_voltage / 12.0,
                                    output.right_feedforward_voltage / 12.0);
                System.out.print("Left Velocity: " + output.left_velocity);
                System.out.print("\tRight Velocity: " + output.right_velocity);
                System.out.println("\tGyro Angle:" + gyroAngle);

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
        final Twist2d measuredVelocity = RobotState.getInstance().generateOdometryFromSensors(deltaLeft, deltaRight, gyroAngle);
        final Twist2d predictedVelocity = Kinematics.forwardKinematics(getLeftVelocity(), getLeftPosInches());
        RobotState.getInstance().addObservations(timestamp, measuredVelocity, predictedVelocity);
        prevLeftInches = currLeftInches;
        prevRightInches = currRightInches;
    }

    @Override
    public void periodic() {
        this.gyroAngle = Rotation2d.fromDegrees(this.navX.getAngle()).rotateBy(gyroOffset);
        this.leftMeasPos = leftMain.getSelectedSensorPosition(0);
        this.rightMeasPos = rightMain.getSelectedSensorPosition(0);

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

            leftSetVel = leftPower * MAX_VEL_TICKS_PER_100MS;
            rightSetVel = rightPower * MAX_VEL_TICKS_PER_100MS;

            if (isPercentOut) {
                this.leftMain.set(ControlMode.PercentOutput, leftPower);
                this.rightMain.set(ControlMode.PercentOutput, rightPower);
            } else {
                this.leftMain.set(ControlMode.Velocity, leftSetVel);
                this.rightMain.set(ControlMode.Velocity, rightSetVel);
            }

            outputsChanged = false;
        }
        updateEstimator();
        updatePathFollower();
    }

    @Override
    protected void initDefaultCommand() {
    }

    @Override
    public boolean check() throws CheckFailException {
        // no-op
        return true;
    }
}
