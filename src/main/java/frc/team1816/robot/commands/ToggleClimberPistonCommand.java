package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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
    protected void execute() {
        if (climber.getHabPistonState() == DoubleSolenoid.Value.kForward) {
            climber.setHabPiston(DoubleSolenoid.Value.kReverse);
        } else {
            climber.setHabPiston(DoubleSolenoid.Value.kForward);
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
