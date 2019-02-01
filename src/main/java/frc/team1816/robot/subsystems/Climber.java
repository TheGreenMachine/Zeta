package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

public class Climber extends Subsystem {
    private static final String NAME = "climber";

    private IMotorController climbMaster;
    private IMotorController climbSlave;

    private DoubleSolenoid habPiston;
    private DoubleSolenoid.Value habPistonState;

    private double motorPower;

    private boolean outputsChanged = false;

    public Climber() {
        super(NAME);
        RobotFactory factory = Robot.FACTORY;

        this.climbMaster = factory.getMotor(NAME, "climbMaster");
        this.climbSlave = factory.getMotor(NAME, "climbSlave", "climbMaster");
        this.habPiston = factory.getDoubleSolenoid(NAME, "habPiston");

        this.climbMaster.setInverted(true); // TODO: check which motor should be inverted
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
