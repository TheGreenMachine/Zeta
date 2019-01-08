package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/*
   Base subsystem
 */


public class Drivetrain extends Subsystem {

    private TalonSRX rightMain, rightSlave, leftMain, leftSlave;

    public Drivetrain(int rightMain, int rightSlave, int leftMain, int leftSlave) {
        super();
        this.rightMain = new TalonSRX(rightMain);
        this.rightSlave= new TalonSRX(rightSlave);
        this.leftMain = new TalonSRX(leftMain);
        this.leftSlave= new TalonSRX(leftSlave);

        this.rightSlave.set(ControlMode.Follower, rightMain);
        this.leftSlave.set(ControlMode.Follower, leftMain);

        this.leftMain.setInverted(true);
        this.leftSlave.setInverted(true);

        this.rightMain.setNeutralMode(NeutralMode.Brake);
        this.rightSlave.setNeutralMode(NeutralMode.Brake);
        this.leftMain.setNeutralMode(NeutralMode.Brake);
        this.leftSlave.setNeutralMode(NeutralMode.Brake);

    }

    public void setDrivetrain(double leftPower, double rightPower) {
        this.rightMain.set(ControlMode.PercentOutput, rightPower);
        this.leftMain.set(ControlMode.PercentOutput, leftPower);


    }




    @Override
    protected void initDefaultCommand() {

    }
}
