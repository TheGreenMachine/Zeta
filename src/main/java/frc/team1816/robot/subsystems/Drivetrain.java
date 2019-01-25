package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

    private PigeonIMU gyro;

    private TalonSRX rightMain;
    private TalonSRX rightSlaveOne;
    private TalonSRX rightSlaveTwo;

    private TalonSRX leftMain;
    private TalonSRX leftSlaveOne;
    private TalonSRX leftSlaveTwo;

    private double leftPower;
    private double rightPower;

    private boolean isPercentOut;
    private boolean outputsChanged = false;

    public Drivetrain(int pigeonId, int leftMainId, int leftSlaveOneId, int leftSlaveTwoId, int rightMainId,
            int rightSlaveOneId, int rightSlaveTwoId) {
        super("Drivetrain");

        this.gyro = new PigeonIMU(pigeonId);

        this.leftMain = new TalonSRX(leftMainId);
        this.leftSlaveOne = new TalonSRX(leftSlaveOneId);
        this.leftSlaveTwo = new TalonSRX(leftSlaveTwoId);

        this.rightMain = new TalonSRX(rightMainId);
        this.rightSlaveOne = new TalonSRX(rightSlaveOneId);
        this.rightSlaveTwo = new TalonSRX(rightSlaveTwoId);

        this.leftSlaveOne.set(ControlMode.Follower, leftMainId);
        this.leftSlaveTwo.set(ControlMode.Follower, leftMainId);
        this.rightSlaveOne.set(ControlMode.Follower, rightMainId);
        this.rightSlaveTwo.set(ControlMode.Follower, rightMainId);

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
}
