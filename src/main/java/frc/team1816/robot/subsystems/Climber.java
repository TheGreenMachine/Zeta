package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

    //TODO: Renaming variables

    private TalonSRX climbMotorOne;
    private TalonSRX climbMotorTwo;

    private DoubleSolenoid habPiston;
    private DoubleSolenoid.Value habPistonState;

    private double motorOnePower;
    private double motorTwoPower;

    private boolean outputsChanged = false;

    public Climber(int cmId1, int cmId2, int hpcId, int hppPortId) {
        super("Climber");

        this.climbMotorOne = new TalonSRX(cmId1);
        this.climbMotorTwo = new TalonSRX(cmId2);
        this.habPiston = new DoubleSolenoid(hpcId, hppPortId);

        this.habPistonState = DoubleSolenoid.Value.kOff;

        this.climbMotorOne.setInverted(true);

        this.habPiston.set(habPistonState);
    }

    //TODO: Check if call to periodic() method is necessary

    public void setClimberPower(double MotorOnePower, double MotorTwoPower) {
        this.motorOnePower = MotorOnePower;
        this.motorTwoPower = MotorTwoPower;
        outputsChanged = true;
        periodic();
    }

    public void setHabPistonState(DoubleSolenoid.Value state) {
        this.habPistonState = state;
        periodic();
    }

    public DoubleSolenoid.Value getHabPistonState() {
        return habPistonState;
    }

    public double getMotorOnePower() {
        return motorOnePower;
    }

    public double getMotorTwoPower() {
        return motorTwoPower;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            this.climbMotorTwo.set(ControlMode.PercentOutput, motorOnePower);
            this.climbMotorOne.set(ControlMode.PercentOutput, motorTwoPower);
            habPiston.set(habPistonState);
            outputsChanged = false;
        }
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}
