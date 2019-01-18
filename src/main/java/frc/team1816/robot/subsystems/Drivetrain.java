package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

    private PigeonIMU gyro;

    private TalonSRX rightMain, rightSlaveOne, rightSlaveTwo;
    private TalonSRX leftMain, leftSlaveOne, leftSlaveTwo;

    private double leftPower, rightPower;

    private boolean isPercentOut;

    public Drivetrain(
            int pigeonID,
            int leftMainID, int leftSlaveOneID, int leftSlaveTwoID,
            int rightMainID, int rightSlaveOneID, int rightSlaveTwoID
    ) {
        super("Drivetrain");

        this.gyro = new PigeonIMU(pigeonID);

        this.leftMain = new TalonSRX(leftMainID);
        this.leftSlaveOne = new TalonSRX(leftSlaveOneID);
        this.leftSlaveTwo = new TalonSRX(leftSlaveTwoID);

        this.rightMain = new TalonSRX(rightMainID);
        this.rightSlaveOne = new TalonSRX(rightSlaveOneID);
        this.rightSlaveTwo = new TalonSRX(rightSlaveTwoID);

        this.leftSlaveOne.set(ControlMode.Follower, leftMainID);
        this.leftSlaveTwo.set(ControlMode.Follower, leftMainID);
        this.rightSlaveOne.set(ControlMode.Follower, rightMainID);
        this.rightSlaveTwo.set(ControlMode.Follower, rightMainID);

        this.leftMain.setInverted(true);
        this.leftSlaveOne.setInverted(true);
        this.leftSlaveTwo.setInverted(true);

        this.leftMain.setNeutralMode(NeutralMode.Brake);
        this.leftSlaveOne.setNeutralMode(NeutralMode.Brake);
        this.leftSlaveTwo.setNeutralMode(NeutralMode.Brake);
        this.rightMain.setNeutralMode(NeutralMode.Brake);
        this.rightSlaveOne.setNeutralMode(NeutralMode.Brake);
        this.rightSlaveTwo.setNeutralMode(NeutralMode.Brake);
    }

    public double getGyroAngle() {
        return gyro.getFusedHeading(); //TODO: check this method
    }

    public void setDrivetrainVelocity(double leftPower, double rightPower) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;
        isPercentOut = false;
        periodic();
    }

    public void setDrivetrainPercent(double leftPower, double rightPower) {
        this.leftPower = leftPower;
        this.rightPower = rightPower;
        isPercentOut = true;
        periodic();
    }

    @Override
    protected void initDefaultCommand() {

    }

    @Override
    public void periodic() {
        double leftVel = leftPower;
        double rightVel = rightPower; //TODO: Add velocity conversion factors.

        if (isPercentOut) {
            this.leftMain.set(ControlMode.PercentOutput, leftPower);
            this.rightMain.set(ControlMode.PercentOutput, rightPower);
        } else {
            this.leftMain.set(ControlMode.Velocity, leftVel);
            this.rightMain.set(ControlMode.Velocity, rightVel);
        }
    }
}
