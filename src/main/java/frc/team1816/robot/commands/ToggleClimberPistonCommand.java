package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Climber;

public class ToggleClimberPistonCommand extends Command {
    private Climber climber;

    private double initTime;
    private double elapsedDelayMs = 2000;

    public ToggleClimberPistonCommand() {
        super("toggleclimberpistoncommand");
        climber = Components.getInstance().climber;
        requires(climber);
    }

    @Override
    protected void initialize() {
        initTime = System.currentTimeMillis();

        if (climber.getHabPistonState() == DoubleSolenoid.Value.kForward) {
            climber.setHabPiston(DoubleSolenoid.Value.kReverse);
        } else {
            climber.setHabPiston(DoubleSolenoid.Value.kForward);
        }
    }

    @Override
    protected void execute() {
        if (
                (initTime + elapsedDelayMs) < System.currentTimeMillis()
                && climber.getHabPistonState() == DoubleSolenoid.Value.kReverse
        ) {
            climber.setHabPiston(DoubleSolenoid.Value.kOff);
        }
    }

    @Override
    protected boolean isFinished() {
        return ((initTime + elapsedDelayMs) < System.currentTimeMillis())
                || (climber.getHabPistonState() == DoubleSolenoid.Value.kForward);
    }
}
