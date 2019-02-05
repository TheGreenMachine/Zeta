package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetBeakCollectorCommand extends Command {
    private Birdbeak birdbeak;
    private boolean down;

    public SetBeakCollectorCommand(boolean down) {
        super("setbeakcollectorcommand");
        birdbeak = Components.getInstance().birdbeak;
        this.down = down;
    }

    @Override
    protected void execute() {
        birdbeak.setArm(down);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
