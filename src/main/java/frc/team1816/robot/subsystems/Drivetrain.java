package frc.team1816.robot.subsystems;

import badlog.lib.BadLog;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import com.edinarobotics.utils.hardware.RobotFactory;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

@RunTest
public class Drivetrain extends Subsystem implements Checkable {
    private static final String NAME = "drivetrain";

    private static final double SLOW_MOD_THROTTLE = 0.5; // TODO: tune this value
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

    private double gyroAngle;

    private boolean isPercentOut;
    private boolean isSlowMode;
    private boolean isReverseMode;
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
        this.leftSlaveOne = factory.getMotor(NAME, "leftSlaveOne", "leftMain");
        this.leftSlaveTwo = factory.getMotor(NAME, "leftSlaveTwo", "leftMain");

        this.rightMain = factory.getMotor(NAME, "rightMain");
        this.rightSlaveOne = factory.getMotor(NAME, "rightSlaveOne", "rightMain");
        this.rightSlaveTwo = factory.getMotor(NAME, "rightSlaveTwo", "rightMain");

        invertTalons(true);
        setBrakeMode();

        navX = new AHRS(I2C.Port.kMXP);
        System.out.println("NavX Active: " + getGyroStatus());

        initDrivetrainLog();
    }

    private void invertTalons(boolean invertRight) {
        if(invertRight) {
            this.rightMain.setInverted(true);
            this.rightSlaveOne.setInverted(true);
            this.rightSlaveTwo.setInverted(true);
        } else {
            this.leftMain.setInverted(true);
            this.leftSlaveOne.setInverted(true);
            this.leftSlaveTwo.setInverted(true);
        }
    }

    public void setBrakeMode() {
        this.leftMain.setNeutralMode(NeutralMode.Brake);
        this.leftSlaveOne.setNeutralMode(NeutralMode.Brake);
        this.leftSlaveTwo.setNeutralMode(NeutralMode.Brake);

        this.rightMain.setNeutralMode(NeutralMode.Brake);
        this.rightSlaveOne.setNeutralMode(NeutralMode.Brake);
        this.rightSlaveTwo.setNeutralMode(NeutralMode.Brake);
    }

    public void setCoastMode() {
        this.leftMain.setNeutralMode(NeutralMode.Coast);
        this.leftSlaveOne.setNeutralMode(NeutralMode.Coast);
        this.leftSlaveTwo.setNeutralMode(NeutralMode.Coast);

        this.rightMain.setNeutralMode(NeutralMode.Coast);
        this.rightSlaveOne.setNeutralMode(NeutralMode.Coast);
        this.rightSlaveTwo.setNeutralMode(NeutralMode.Coast);
    }

    private void initDrivetrainLog() {
        BadLog.createTopic("Drivetrain/Left Output Percent", BadLog.UNITLESS, () -> this.leftMain.getMotorOutputPercent(), 
            "hide", "join:Drivetrain/Output Percents");
        BadLog.createTopic("Drivetrain/Right Output Percent", BadLog.UNITLESS, () -> this.rightMain.getMotorOutputPercent(), 
            "hide", "join:Drivetrain/Output Percents");
        BadLog.createTopic("Drivetrain/Left Output Velocity", BadLog.UNITLESS, () -> (double) this.leftMain.getSelectedSensorVelocity(0), 
            "hide", "join:Drivetrain/Output Velocities");
        BadLog.createTopic("Drivetrain/Right Output Velocity", BadLog.UNITLESS, () -> (double) this.rightMain.getSelectedSensorVelocity(0), 
            "hide", "join:Drivetrain/Output Velocities");

        BadLog.createTopic("Drivetrain/Angle", "deg", () -> getGyroAngle());
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

    public double getLeftPosInches() {
        return ticksToInches(leftPos);
    }

    public double getRightPosInches() {
        return ticksToInches(rightPos);
    }

    public double ticksToInches(double ticks) {
        return ticks * (1 / TICKS_PER_REV) * INCHES_PER_REV;
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
        periodic();
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
        periodic();
    }

    public void setSlowMode(boolean slowMode) {
        this.isSlowMode = slowMode;
        outputsChanged = true;
        periodic();
    }

    public boolean isReverseMode() {
        return isReverseMode;
    }

    public void setReverseMode(boolean reverseMode) {
        this.isReverseMode = reverseMode;
        outputsChanged = true;
        periodic();
    }

    public void toggleReverseMode() {
        this.isReverseMode = !this.isReverseMode;
        outputsChanged = true;
        periodic();
    }

    @Override
    public void periodic() {
        gyroAngle = navX.getAngle();
        leftPos = leftMain.getSelectedSensorPosition(0);
        rightPos = rightMain.getSelectedSensorPosition(0);

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
    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public boolean check() throws CheckFailException {
        System.out.println("Warning: mechanisms will move!");
        Timer.delay(3);
        setDrivetrainPercent(0.5, 0.5);
        Timer.delay(3);
        setDrivetrainPercent(0, 0);
        Timer.delay(0.5);
        setDrivetrainPercent(-0.5, -0.5);
        Timer.delay(3);
        setDrivetrainPercent(0, 0);
        return true;
    }
}
