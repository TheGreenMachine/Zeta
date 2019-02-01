package frc.team1816.robot.subsystems;

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

    private double gyroAngle;

    private boolean isPercentOut;
    private boolean isSlowMode;
    private boolean isReverseMode;
    private boolean outputsChanged = false;

    public Drivetrain() {
        super(NAME);
        RobotFactory factory = Robot.FACTORY;

        this.leftMain = factory.getMotor(NAME, "leftMain");
        this.leftSlaveOne = factory.getMotor(NAME, "leftSlaveOne", "leftMain");
        this.leftSlaveTwo = factory.getMotor(NAME, "leftSlaveTwo", "leftMain");

        this.rightMain = factory.getMotor(NAME, "rightMain");
        this.rightSlaveOne = factory.getMotor(NAME, "rightSlaveOne", "rightMain");
        this.rightSlaveTwo = factory.getMotor(NAME, "rightSlaveTwo", "rightMain");

        navX = new AHRS(I2C.Port.kMXP);

        this.rightMain.setInverted(true);
        this.rightSlaveOne.setInverted(true);
        this.rightSlaveTwo.setInverted(true);

        this.leftMain.setNeutralMode(NeutralMode.Brake);
        this.leftSlaveOne.setNeutralMode(NeutralMode.Brake);
        this.leftSlaveTwo.setNeutralMode(NeutralMode.Brake);

        this.rightMain.setNeutralMode(NeutralMode.Brake);
        this.rightSlaveOne.setNeutralMode(NeutralMode.Brake);
        this.rightSlaveTwo.setNeutralMode(NeutralMode.Brake);

        System.out.println("NavX Active: " + getGyroStatus());
    }

    public double getGyroAngle() {
        return gyroAngle;
    }

    public boolean getGyroStatus() {
        return navX.isConnected();
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

            double leftVel = leftPower;
            double rightVel = rightPower; // TODO: Add velocity conversion factors.

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
