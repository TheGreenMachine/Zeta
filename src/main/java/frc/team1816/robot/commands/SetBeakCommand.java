package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetBeakCommand extends Command {
    private Birdbeak birdbeak;
    private boolean beakNotGripped;

    public SetBeakCommand(boolean notGripped) {
        super("setbeakcommand");
        birdbeak = Components.getInstance().birdbeak;
        this.beakNotGripped = notGripped;
        requires(birdbeak);
    }

    @Override
    protected void execute() {
        System.out.println("Setting Beak: " + beakNotGripped);
        birdbeak.setBeak(beakNotGripped);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
