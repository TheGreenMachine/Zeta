package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example of a Subsystem.
 */
public class CargoCollector extends Subsystem {

    private TalonSRX intake;
    private double power;

    private Solenoid armPiston;
    private boolean pistonOn;

    private static boolean outputsChanged = false;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public CargoCollector(int intakeId, int pcmId, int solenoidId) {
        super("CargoCollector");

        this.intake = new TalonSRX(intakeId);
        this.armPiston = new Solenoid(pcmId, solenoidId);

        intake.set(ControlMode.PercentOutput, 0.0);
    }

    public void setArmPiston(boolean on) {
        this.pistonOn = on;
        outputsChanged = true;
    }

    public boolean getArmPistonState() {
        return pistonOn;
    }

    public void setCargoCollectorIntake(double intakePower) {
        this.power = intakePower;
        outputsChanged = true;
    }

    public double getCargoCollectorIntake() {
        return this.power;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            this.intake.set(ControlMode.PercentOutput, power);
            this.armPiston.set(pistonOn);
            outputsChanged = false;
        }
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}
