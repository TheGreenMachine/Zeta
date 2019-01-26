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

    private PigeonIMU gyro;

    private IMotorControllerEnhanced rightMain;
    private IMotorControllerEnhanced rightSlaveOne;
    private IMotorControllerEnhanced rightSlaveTwo;

    private IMotorControllerEnhanced leftMain;
    private IMotorControllerEnhanced leftSlaveOne;
    private IMotorControllerEnhanced leftSlaveTwo;

    private double leftPower;
    private double rightPower;

    private boolean isPercentOut;
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
        this.leftPower = leftPower;
        this.rightPower = rightPower;
        isPercentOut = false;
        outputsChanged = true;
        periodic();
    }

    public void setDrivetrainPercent(double leftPower, double rightPower) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;
        isPercentOut = true;
        outputsChanged = true;
        periodic();
    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void periodic() {
        if (outputsChanged) {
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
    public boolean check() throws CheckFailException {
        setDrivetrainPercent(0.5, 0.5);
        Timer.delay(3);
        setDrivetrainPercent(0, 0);
        return true;
    }
}
