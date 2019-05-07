package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Climber;

public class ToggleClimberPistonCommand extends Command {
    private Climber climber;

    public ToggleClimberPistonCommand() {
        super("toggleclimberpistoncommand");
        climber = Components.getInstance().climber;
        requires(climber);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        climber.toggleClimberPiston();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
