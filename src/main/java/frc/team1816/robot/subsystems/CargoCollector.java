package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * An example of a Subsystem.
 */
public class CargoCollector extends Subsystem {
    private TalonSRX intake;
    private Solenoid pivot1;
    private Solenoid pivot2;

    private double power;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public CargoCollector(int intakeId , int pivot1CANBusId, int pivot2CANbusId) {
        super("CargoCollector");
        this.intake = new TalonSRX(intakeId);
        this.pivot1 = new Solenoid(pivot1CANBusId);
        this.pivot2 = new Solenoid(pivot2CANbusId);

        intake.set(ControlMode.PercentOutput, 0.0);
        pivot1.set(false);
        pivot2.set(false);
    }

    public void setCargoCollectorIntake(double power) {
        this.power = power;
        this.intake.set(ControlMode.PercentOutput, power);

        periodic();
    }

    public void getCargoCollectorIntake() {
        System.out.println("Intake power:" + power);

        periodic();
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}
