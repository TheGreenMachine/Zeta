package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetBeakPuncherCommand extends CommandBase {
    private Birdbeak birdbeak;
    private boolean isOut;

    public SetBeakPuncherCommand(boolean out) {
        birdbeak = Components.getInstance().birdbeak;
        this.isOut = out;
        addRequirements(birdbeak);
    }

    @Override
    public void execute() {
        System.out.println("Setting Puncher: " + isOut);
        birdbeak.setPuncher(isOut);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
