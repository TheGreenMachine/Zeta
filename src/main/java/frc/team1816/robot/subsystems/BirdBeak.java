package frc.team1816.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

public class BirdBeak extends Subsystem {

    private Solenoid beakOpener;
    private Solenoid hatchEjector;
    private Solenoid pivoter;
    private TalonSRX hatchIntake;

    public BirdBeak(int beakOpenerID, int hatchEjectorID, int pivoterID, int hatchIntakeID) {
        super("BirdBeak");
        this.beakOpener = new Solenoid(beakOpenerID);
        this.hatchEjector = new Solenoid(hatchEjectorID);
        this.pivoter = new Solenoid(pivoterID);
        this.hatchIntake = new TalonSRX(hatchIntakeID);
    }

    public void EjectHatch() {
        pivoter.close();
        beakOpener.close();
        Timer.delay(1);
        hatchEjector.set(true);
    }

    public void PickUpHatch() {
        beakOpener.set(true);
        hatchEjector.close();
        pivoter.set(true);
        hatchIntake.set(ControlMode.Velocity, 0.5);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
