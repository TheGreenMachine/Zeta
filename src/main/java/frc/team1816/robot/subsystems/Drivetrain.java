package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drivetrain extends Subsystem {

    private TalonSRX rightMain, rightSlaveOne, rightSlaveTwo;
    private TalonSRX leftMain, leftSlaveOne, leftSlaveTwo;

    public Drivetrain(int rightMain, int rightSlaveOne, int rightSlaveTwo, int leftMain, int leftSlaveOne, int leftSlaveTwo) {
        super();

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

    public void setDrivetrain(double leftPower, double rightPower) {
        this.leftMain.set(ControlMode.PercentOutput, leftPower);
        this.rightMain.set(ControlMode.PercentOutput, rightPower);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
