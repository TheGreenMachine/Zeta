package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetBeakCommand extends Command {
    private Birdbeak birdbeak;
    private boolean open;

    public SetBeakCommand(boolean open) {
        super("setbeakcommand");
        birdbeak = Components.getInstance().birdbeak;
        this.open = open;
    }

    @Override
    protected void execute() {
        birdbeak.setBeak(open);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
