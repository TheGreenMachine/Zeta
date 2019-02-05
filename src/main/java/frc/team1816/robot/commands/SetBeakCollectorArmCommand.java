package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetBeakCollectorArmCommand extends Command {
    private Birdbeak birdbeak;
    private boolean isDown;

    public SetBeakCollectorArmCommand(boolean down) {
        super("setbeakcollectorarmcommand");
        birdbeak = Components.getInstance().birdbeak;
        this.isDown = down;
        requires(birdbeak);
    }

    @Override
    protected void execute() {
        birdbeak.setArm(isDown);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
