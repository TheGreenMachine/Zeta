package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorControllerEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;
import com.edinarobotics.utils.hardware.RobotFactory;

@RunTest
public class Drivetrain extends Subsystem implements Checkable {
    private static final String NAME = "drivetrain";

    private static final double SLOW_MOD_THROTTLE = 0.5; // TODO: tune this value
    private static final double SLOW_MOD_ROT = 0.8;

    private PigeonIMU gyro;

    private IMotorControllerEnhanced rightMain;
    private IMotorControllerEnhanced rightSlaveOne;
    private IMotorControllerEnhanced rightSlaveTwo;

    private IMotorControllerEnhanced leftMain;
    private IMotorControllerEnhanced leftSlaveOne;
    private IMotorControllerEnhanced leftSlaveTwo;

    private double leftPower;
    private double rightPower;
    private double rotation;

    private boolean isPercentOut;
    private boolean isSlowMode;
    private boolean isReverseMode;
    private boolean outputsChanged = false;

    public Drivetrain() {
        super(NAME);
        RobotFactory factory = Robot.FACTORY;

        this.leftMain = factory.getTalon(NAME, "leftMain");
        this.leftSlaveOne = factory.getTalon(NAME, "leftSlaveOne", "leftMain");
        this.leftSlaveTwo = factory.getTalon(NAME, "leftSlaveTwo", "leftMain");

        this.rightMain = factory.getTalon(NAME, "rightMain");
        this.rightSlaveOne = factory.getTalon(NAME, "rightSlaveOne", "rightMain");
        this.rightSlaveTwo = factory.getTalon(NAME, "rightSlaveTwo", "rightMain");

        this.gyro = new PigeonIMU((TalonSRX) this.leftSlaveTwo);

        this.leftMain.setInverted(true);
        this.leftSlaveOne.setInverted(true);
        this.leftSlaveTwo.setInverted(true);
    }

    public double getGyroAngle() {
        return gyro.getFusedHeading(); // TODO: check this method
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

    @Override
    public void periodic() {
        if (outputsChanged) {
            if (isSlowMode) {
                leftPower *= SLOW_MOD_THROTTLE;
                rightPower *= SLOW_MOD_THROTTLE;
                rotation *= SLOW_MOD_ROT;
            }

            if (isReverseMode) {
                leftPower *= -1;
                rightPower *= -1;
                rotation *= -1;
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
        setDrivetrainPercent(0.5, 0.5);
        Timer.delay(3);
        setDrivetrainPercent(0, 0);
        return true;
    }
}
