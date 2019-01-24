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
    private DoubleSolenoid pivot1;
    private DoubleSolenoid pivot2;

  //  private double power;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public CargoCollector(int intakeId , int pivot1CANBusId, int pivot1PCMPort , int pivot2CANbusId, int pivot2PCMPort) {
        super("CargoCollector");
        this.intake = new TalonSRX(intakeId);
        this.pivot1 = new DoubleSolenoid(pivot1CANBusId, pivot1PCMPort);
        this.pivot2 = new DoubleSolenoid(pivot2CANbusId, pivot2PCMPort);


        this.pivot1.set(DoubleSolenoid.Value.kOff);
        this.pivot2.set(DoubleSolenoid.Value.kOff);
    }

    public void setCargoCollectorIntake(double power) {
        this.intake.set(ControlMode.PercentOutput, power);
        periodic();
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}
