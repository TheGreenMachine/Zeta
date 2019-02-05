package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetBeakCommand extends Command {
    private Birdbeak birdbeak;
    private boolean isOpen;

    public SetBeakCommand(boolean open) {
        super("setbeakcommand");
        birdbeak = Components.getInstance().birdbeak;
        this.isOpen = open;
    }

    @Override
    protected void execute() {
        birdbeak.setBeak(isOpen);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
