package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetBeakCommand extends CommandBase {
    private Birdbeak birdbeak;
    private boolean beakNotGripped;

    public SetBeakCommand(boolean notGripped) {
        birdbeak = Components.getInstance().birdbeak;
        this.beakNotGripped = notGripped;
        if (birdbeak != null) {
            addRequirements(birdbeak);
        }
    }

    @Override
    public void initialize() {
        System.out.println("Setting Beak (not gripped): " + beakNotGripped);
    }

    @Override
    public void execute() {
        if (birdbeak != null) {
            birdbeak.setBeak(beakNotGripped);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
