package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BirdBeak extends Subsystem {

    //TODO: Tune timer delays

    private Solenoid beak;
    private Solenoid hatchEjector;
    private Solenoid hatchArm;
    private TalonSRX hatchIntake;

    private boolean intake;
    private boolean outputsChanged = false;

    public BirdBeak(int beakOpenerId, int hatchEjectorId, int hatchArmId, int hatchIntakeId) {
        super("BirdBeak");
        this.beak = new Solenoid(beakOpenerId);
        this.hatchEjector = new Solenoid(hatchEjectorId);
        this.hatchArm = new Solenoid(hatchArmId);
        this.hatchIntake = new TalonSRX(hatchIntakeId);
        hatchIntake.setNeutralMode(NeutralMode.Brake);
    }

    public void ejectHatch() {
        intake = false;
        outputsChanged = true;
    }

    public void pickUpHatch() {
        intake = true;
        outputsChanged = true;
    }

    private void setIntake(boolean on) {
        hatchArm.set(on);
        Timer.delay(0.25);
        hatchEjector.set(!on);
        Timer.delay(0.5);
        beak.set(!on);
        Timer.delay(0.5);
        if(on) {
            hatchIntake.set(ControlMode.PercentOutput, 0.25);
            Timer.delay(1);
            hatchIntake.set(ControlMode.PercentOutput, 0);
            beak.set(true);
            Timer.delay(0.5);
            hatchArm.set(false);
        } else {
            hatchEjector.set(true);
        }
    }

    public boolean getIntake() {
        return hatchArm.get();
    }

    @Override
    protected void initDefaultCommand() { }

    @Override
    public void periodic() {
        if(outputsChanged) {
            setIntake(intake);
        }
        outputsChanged = false;
    }
}
