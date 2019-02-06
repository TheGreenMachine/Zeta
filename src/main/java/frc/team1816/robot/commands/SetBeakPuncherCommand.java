package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetBeakPuncherCommand extends Command {
    private Birdbeak birdbeak;
    private boolean isOut;

    public SetBeakPuncherCommand(boolean out) {
        super("setbeakpunchercommand");
        birdbeak = Components.getInstance().birdbeak;
        this.isOut = out;
        requires(birdbeak);
    }

    @Override
    protected void execute() {
        birdbeak.setPuncher(isOut);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
