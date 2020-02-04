package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.*;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class DriveToLoadHatchCommand extends ParallelCommandGroup {
    private Birdbeak birdbeak;

    public DriveToLoadHatchCommand(double power) {
        birdbeak = Components.getInstance().birdbeak;

        addCommands(
            new SetBeakCommand(true),
            new DriveToHatchCommand(power)
        );    
    }

    @Override
    public void end(boolean isInterrupted) {
        if (birdbeak != null) {
            birdbeak.setBeak(false);
        }
    }
}
