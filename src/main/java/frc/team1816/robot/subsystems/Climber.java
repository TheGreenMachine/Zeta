package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

    //TODO: Renaming variables

    private TalonSRX climbMotorMaster;
    private TalonSRX climbMotorSlave;

    private DoubleSolenoid habPiston;
    private DoubleSolenoid.Value habPistonState;

    private double motorPower;

    private boolean outputsChanged = false;

    public Climber(int cmIdMaster, int cmIdSlave, int hpcId, int hppPortId) {
        super("Climber");

        this.climbMotorMaster = new TalonSRX(cmIdMaster);
        this.climbMotorSlave = new TalonSRX(cmIdSlave);
        this.habPiston = new DoubleSolenoid(hpcId, hppPortId);

        this.habPistonState = DoubleSolenoid.Value.kOff;

        this.climbMotorMaster.setInverted(true);
        this.climbMotorSlave.set(ControlMode.Follower, cmIdMaster);

        this.habPiston.set(habPistonState);
    }

    //TODO: Check if call to periodic() method is necessary

    public void setClimberPower(double MotorPower) {
        this.motorPower = MotorPower;
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

    public double getMotorPower() {
        return motorPower;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            this.climbMotorMaster.set(ControlMode.PercentOutput, motorPower);
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
