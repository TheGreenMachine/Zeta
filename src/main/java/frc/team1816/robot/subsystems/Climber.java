package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {
    private TalonSRX climbMotorOne, climbMotorTwo;
    private DoubleSolenoid HABpiston;

    private double MotorOnePower, MotorTwoPower;

    public Climber(int climbMotor1, int climbMotor2, int HABpistonCANId, int HABpistonPCMPort) {
        super("Climber");
        this.climbMotorOne = new TalonSRX(climbMotor1);
        this.climbMotorTwo = new TalonSRX(climbMotor2);
        this.HABpiston = new DoubleSolenoid(HABpistonCANId, HABpistonPCMPort);

        this.HABpiston.set(DoubleSolenoid.Value.kOff);

        this.climbMotorOne.setInverted(true);

        this.climbMotorOne.set(ControlMode.Follower, climbMotor1);
        this.climbMotorTwo.set(ControlMode.Follower, climbMotor1);
    }

    public void setClimberPower(double MotorOnePower, double MotorTwoPower) {
        this.climbMotorTwo.set(ControlMode.PercentOutput, MotorOnePower);
        this.climbMotorOne.set(ControlMode.PercentOutput, MotorTwoPower);

        periodic();
    }

    public void setHABPiston(boolean status) {
        if(status) {
            HABpiston.set(DoubleSolenoid.Value.kOff);
        } else {
            HABpiston.close();
        }

        periodic();
    }
    public void getHABPiston() {

    }

    public void getClimberPower() {
        System.out.println("MotorOne power:" + MotorOnePower);
        System.out.println("MotorTwo power:" + MotorTwoPower);

        periodic();
    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}
