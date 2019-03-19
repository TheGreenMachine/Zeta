package frc.team1816.robot.subsystems;

import com.edinarobotics.utils.checker.CheckFailException;
import com.edinarobotics.utils.checker.Checkable;
import com.edinarobotics.utils.checker.RunTest;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team1816.robot.Robot;

@RunTest
public class Birdbeak extends Subsystem implements Checkable {
    public static final String NAME = "birdbeak";

    private Solenoid beak;
    private Solenoid hatchPuncher;

    private boolean beakNotGripped;
    private boolean puncherOut;
    private boolean outputsChanged = false;

    public Birdbeak() {
        super(NAME);
        RobotFactory factory = Robot.factory;

        this.beak = factory.getSolenoid(NAME, "beak");
        this.hatchPuncher = factory.getSolenoid(NAME, "puncher");
    }

    public void setBeak(boolean notGripped) {
        beakNotGripped = notGripped;
        outputsChanged = true;
    }

    public void setPuncher(boolean out) {
        puncherOut = out;
        outputsChanged = true;
    }

    public boolean getBeakState() {
        return beak.get();
    }

    public boolean getPuncherState() {
        return hatchPuncher.get();
    }

    @Override
    protected void initDefaultCommand() {
    }

    @Override
    public void periodic() {
        if (outputsChanged) {
            beak.set(beakNotGripped);
            hatchPuncher.set(puncherOut);

            outputsChanged = false;
        }
    }

    @Override
    public boolean check() throws CheckFailException {
        System.out.println("Warning: mechanisms will move!");
        Timer.delay(3);

        setBeak(true);
        setPuncher(true);
        Timer.delay(0.5);
        setBeak(false);
        setPuncher(false);

        return true;
    }
}
