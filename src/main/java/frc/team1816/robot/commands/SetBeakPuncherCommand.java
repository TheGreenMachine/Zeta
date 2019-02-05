package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetBeakPuncherCommand extends Command {
    private Birdbeak birdbeak;
    private boolean out;

    public SetBeakPuncherCommand(boolean out) {
        super("setbeakpunchercommand");
        birdbeak = Components.getInstance().birdbeak;
        this.out = out;
    }

    @Override
    protected void execute() {
        birdbeak.setPuncher(out);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
