package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * An example of a Subsystem.
 */
public class Climber extends Subsystem {
    private TalonSRX climb_moto1, climb_moto2;
    private DoubleSolenoid HAB_piston;

    public Climber(int climb_moto1, int climb_moto2, int HAB_piston ) {
        super();
        this.climb_moto1 = new TalonSRX(climb_moto1);
        this.climb_moto2 = new TalonSRX(climb_moto2);
        this.HAB_piston = new DoubleSolenoid(HAB_piston);

        this.climb_moto1.setInverted(true);
        this.climb_moto2.setInverted(true);

        this.climb_moto1.set(ControlMode.Follower, climb_moto1);
        this.climb_moto2.set(ControlMode.Follower, climb_moto1);
    }

    public void initClimber(double power1, double power2, boolean piston) {
        this.climb_moto2.set(ControlMode.PercentOutput, power1);
        this.climb_moto1.set(ControlMode.PercentOutput, power2);
        this.HAB_piston.set(HAB_piston);

    }
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}
