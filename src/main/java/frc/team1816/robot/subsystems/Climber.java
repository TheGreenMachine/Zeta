package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Climber extends Subsystem {

    private TalonSRX climbMaster;
    private TalonSRX climbSlave;

    private DoubleSolenoid habPiston;
    private DoubleSolenoid.Value habPistonState;

    private double motorPower;

    private boolean outputsChanged = false;

    public Climber(int climbMasterId, int climbSlaveId, int pcmId, int pistonFwdId, int pistonOutId) {
        super("climber");

        this.climbMaster = new TalonSRX(climbMasterId);
        this.climbSlave = new TalonSRX(climbSlaveId);
        this.habPiston = new DoubleSolenoid(pcmId, pistonFwdId, pistonOutId);

        this.climbMaster.setInverted(true); // TODO: check which motor should be inverted
        this.climbSlave.set(ControlMode.Follower, climbMasterId);
    }

    public void setClimberPower(double motorPow) {
        this.motorPower = motorPow;
        outputsChanged = true;
        periodic();
    }

    public void setHabPiston(DoubleSolenoid.Value state) {
        this.habPistonState = state;
        periodic();
    }

    public String getHabPistonState() {
        return habPistonState.toString();
    }

    public double getMotorPower() {
        return motorPower;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            climbMaster.set(ControlMode.PercentOutput, motorPower);
            habPiston.set(habPistonState);
            outputsChanged = false;
        }
    }

    public void initDefaultCommand() { }
}
