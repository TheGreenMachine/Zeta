package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BirdBeak extends Subsystem {

    //TODO: Tune timer delays

    private Solenoid beakOpener;
    private Solenoid hatchEjector;
    private Solenoid pivoter;
    private TalonSRX hatchIntake;

    private boolean intake;
    private boolean outputsChanged = false;

    public BirdBeak(int beakOpenerID, int hatchEjectorID, int pivoterID, int hatchIntakeID) {
        super("BirdBeak");
        this.beakOpener = new Solenoid(beakOpenerID);
        this.hatchEjector = new Solenoid(hatchEjectorID);
        this.pivoter = new Solenoid(pivoterID);
        this.hatchIntake = new TalonSRX(hatchIntakeID);
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
        pivoter.set(on);
        Timer.delay(0.25);
        hatchEjector.set(!on);
        Timer.delay(0.5);
        beakOpener.set(!on);
        Timer.delay(0.5);
        if(on) {
            hatchIntake.set(ControlMode.Velocity, 0.25);
            Timer.delay(1);
            hatchIntake.setNeutralMode(NeutralMode.Brake);
            beakOpener.set(true);
            Timer.delay(0.5);
            pivoter.set(false);
        } else {
            hatchEjector.set(true);
        }
    }

    public boolean getIntake() {
        return pivoter.get();
    }

    @Override
    protected void initDefaultCommand() { }

    @Override
    public void periodic() {
        if(outputsChanged) {
            setIntake(intake);
        }
    }
}
