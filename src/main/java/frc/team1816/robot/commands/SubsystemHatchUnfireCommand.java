package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SubsystemHatchUnfireCommand extends Command {
    private Birdbeak birdbeak;

    public SubsystemHatchUnfireCommand() {
        super("subsystemhatchunfirecommand");
        birdbeak = Components.getInstance().birdbeak;

        requires(birdbeak);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        birdbeak.setBeak(true);
        birdbeak.setPuncher(false);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
