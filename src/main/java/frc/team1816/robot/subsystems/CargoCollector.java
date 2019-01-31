package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoCollector extends Subsystem {

    private Solenoid armPiston;
    private TalonSRX intake;

    private double intakePow;

    private boolean armDown;
    private static boolean outputsChanged = false;

    public CargoCollector(int pcmId, int solenoidId, int intakeId) {
        super("cargocollector");

        this.intake = new TalonSRX(intakeId);
        this.armPiston = new Solenoid(pcmId, solenoidId);
    }

    public void setArmPiston(boolean down) {
        this.armDown = down;
        outputsChanged = true;
    }

    public void setIntake(double intakePower) {
        this.intakePow = intakePower;
        outputsChanged = true;
    }

    public boolean getArmPistonState() {
        return armDown;
    }

    public double getIntakePow() {
        return this.intakePow;
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            this.intake.set(ControlMode.PercentOutput, intakePow);
            this.armPiston.set(armDown);
            outputsChanged = false;
        }
    }

    public void initDefaultCommand() { }
}
