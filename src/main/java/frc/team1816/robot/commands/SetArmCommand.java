package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetArmCommand extends Command {
    private Birdbeak birdbeak;
    private boolean down;

    public SetArmCommand(boolean down) {
        super("setarmcommand");
        birdbeak = Components.getInstance().birdbeak;
        this.down = down;
    }

    @Override
    protected void execute() {
        if (birdbeak.getArmState() != down)
            birdbeak.setArm(down);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
