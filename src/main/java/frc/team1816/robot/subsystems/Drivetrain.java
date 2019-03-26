package frc.team1816.robot.subsystems;

import static frc.team1816.robot.subsystems.LedManager.RobotStatus.DRIVETRAIN_FLIPPED;
import static frc.team1816.robot.subsystems.LedManager.RobotStatus.ENABLED;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import com.edinarobotics.utils.hardware.RobotFactory;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Components;
import frc.team1816.robot.Robot;


@RunTest
public class Drivetrain extends Subsystem implements Checkable {
    public static final String NAME = "drivetrain";

    private static final double SLOW_MOD_THROTTLE = 0.5;
    private static final double SLOW_MOD_ROT = 0.8;

    private static double TICKS_PER_REV;
    private static double TICKS_PER_INCH;
    private static double WHEELBASE;
    private static double MAX_VEL_TICKS_PER_100MS;

    private static double INCHES_PER_REV;

    private AHRS navX;

    private IMotorController rightMain;
    private IMotorController rightSlaveOne;
    private IMotorController rightSlaveTwo;

    private IMotorController leftMain;
    private IMotorController leftSlaveOne;
    private IMotorController leftSlaveTwo;

    private double leftPower;
    private double rightPower;
    private double rotation;
    private double leftVel;
    private double rightVel;
    private double leftPos;
    private double rightPos;

    //   private NetworkTable positions = NetworkTableInstance.getDefault().getTable("positions");

    private double gyroAngle;
    private double leftTalonVelocity;
    private double rightTalonVelocity;
    private double talonPositionLeft;
    private double talonPositionRight;

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
    private double initAngle;

    private boolean isPercentOut;
    private boolean isSlowMode;
    private boolean isReverseMode;
    private boolean isVisionMode = false;
    private boolean outputsChanged = false;

    public Drivetrain() {
        super(NAME);
        RobotFactory factory = Robot.factory;

        TICKS_PER_REV = factory.getConstant("ticksPerRev");
        TICKS_PER_INCH = factory.getConstant("ticksPerIn");
        WHEELBASE = factory.getConstant("wheelbase");
        MAX_VEL_TICKS_PER_100MS = factory.getConstant("maxVel");
        INCHES_PER_REV = TICKS_PER_REV / TICKS_PER_INCH;

        this.leftMain = factory.getMotor(NAME, "leftMain");
        this.leftSlaveOne = factory.getMotor(NAME, "leftSlaveOne", leftMain);
        this.leftSlaveTwo = factory.getMotor(NAME, "leftSlaveTwo", leftMain);

        this.rightMain = factory.getMotor(NAME, "rightMain");
        this.rightSlaveOne = factory.getMotor(NAME, "rightSlaveOne", rightMain);
        this.rightSlaveTwo = factory.getMotor(NAME, "rightSlaveTwo", rightMain);

        invertTalons(true);
        setNeutralMode(NeutralMode.Brake);

        navX = new AHRS(I2C.Port.kMXP);
        System.out.println("NavX Active: " + getGyroStatus());

        this.initTime = System.currentTimeMillis();

        // initCoordinateTracking();
        // initDrivetrainLog();
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

    public double getLeftPower() {
        return leftPower;
    }

    public double getRightPower() {
        return rightPower;
    }

    public double getGyroAngle() {
        return gyroAngle;
    }

    public boolean getGyroStatus() {
        return navX.isConnected();
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
        LedManager ledManager = Components.getInstance().ledManager;
        this.isReverseMode = reverseMode;
        if (isReverseMode) {
            ledManager.indicateStatus(DRIVETRAIN_FLIPPED);
        } else {
            ledManager.clearStatus(DRIVETRAIN_FLIPPED);
        }
        outputsChanged = true;
    }

    public void toggleReverseMode() {
        setReverseMode(!isReverseMode);
    }

    @Override
    public void periodic() {
        this.gyroAngle = navX.getAngle();
        this.leftPos = leftMain.getSelectedSensorPosition(0);
        this.rightPos = rightMain.getSelectedSensorPosition(0);

        this.talonPositionLeft = leftMain.getSelectedSensorPosition(0);
        this.talonPositionRight = rightMain.getSelectedSensorPosition(0);

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
                this.leftMain.set(ControlMode.Velocity, leftVel);
                this.rightMain.set(ControlMode.Velocity, rightVel);
            }

            outputsChanged = false;
        }

        //  coordinateTracking();
    }

//    public void coordinateTracking() {
//        double currLeftInches = getLeftPosInches();
//        double currRightInches = getRightPosInches();
//        double avgDistance =
//            ((currLeftInches - prevLeftInches)
//            + (currRightInches - prevRightInches)) / 2;
//        double theta = (Math.toRadians(initAngle - gyroAngle) + Math.PI / 2);
//
//        xPos = avgDistance * Math.cos(theta) + prevX;
//        yPos = avgDistance * Math.sin(theta) + prevY;
//
//        positions.getEntry("xPos").setDouble(xPos);
//        positions.getEntry("yPos").setDouble(yPos);
//
//        prevX = xPos;
//        prevY = yPos;
//        prevLeftInches = currLeftInches;
//        prevRightInches = currRightInches;
//    }

    @Override
    protected void initDefaultCommand() {
    }

    @Override
    public boolean check() throws CheckFailException {
        // System.out.println("Warning: mechanisms will move!");
        return true;
    }
}
