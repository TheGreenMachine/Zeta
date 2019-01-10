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

    public Drivetrain(int pigeonId, int leftMain, int leftSlaveOne, int leftSlaveTwo, int rightMain, int rightSlaveOne, int rightSlaveTwo) {
        super();

        this.gyro = new PigeonIMU(pigeonId);

        this.leftMain = new TalonSRX(leftMain);
        this.leftSlaveOne = new TalonSRX(leftSlaveOne);
        this.leftSlaveTwo = new TalonSRX(leftSlaveTwo);

        this.rightMain = new TalonSRX(rightMain);
        this.rightSlaveOne = new TalonSRX(rightSlaveOne);
        this.rightSlaveTwo = new TalonSRX(rightSlaveTwo);

        this.leftSlaveOne.set(ControlMode.Follower, leftMain);
        this.leftSlaveTwo.set(ControlMode.Follower, leftMain);
        this.rightSlaveOne.set(ControlMode.Follower, rightMain);
        this.rightSlaveTwo.set(ControlMode.Follower, rightMain);

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
        double leftVel, rightVel;
        leftVel = leftPower;
        rightVel = rightPower; //TODO: Add velocity conversion factors.

        if (isPercentOut) {
            this.leftMain.set(ControlMode.PercentOutput, leftPower);
            this.rightMain.set(ControlMode.PercentOutput, rightPower);
        } else {
            this.leftMain.set(ControlMode.Velocity, leftVel);
            this.rightMain.set(ControlMode.Velocity, rightVel);
        }
    }
}
