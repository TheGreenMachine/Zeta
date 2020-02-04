package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SubsystemHatchUnfireCommand extends CommandBase {
    private Birdbeak birdbeak;

    public SubsystemHatchUnfireCommand() {
        birdbeak = Components.getInstance().birdbeak;

        addRequirements(birdbeak);
    }

    @Override
    public void initialize() {
        System.out.println("UnFiring Hatch");
    }

    @Override
    public void execute() {
        birdbeak.setBeak(false);
        birdbeak.setPuncher(false);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean isFinished) {
    }
}
