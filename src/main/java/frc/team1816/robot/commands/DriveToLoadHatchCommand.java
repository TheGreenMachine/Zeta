package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class DriveToLoadHatchCommand extends CommandGroup {
    private Birdbeak birdbeak;

    public DriveToLoadHatchCommand(double power) {
        birdbeak = Components.getInstance().birdbeak;

        addParallel(new SetBeakCommand(true));
        if (birdbeak != null) {
            addParallel(new DriveToHatchCommand(power));
        }
    }

    @Override
    protected void end() {
        if (birdbeak != null) {
            birdbeak.setBeak(false);
        }
    }

    @Override
    protected void interrupted() {
        end();
    }
}
