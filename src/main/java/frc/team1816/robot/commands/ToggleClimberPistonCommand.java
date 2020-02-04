package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Climber;

public class ToggleClimberPistonCommand extends CommandBase {
    private Climber climber;

    public ToggleClimberPistonCommand() {
        climber = Components.getInstance().climber;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        climber.toggleClimberPiston();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
