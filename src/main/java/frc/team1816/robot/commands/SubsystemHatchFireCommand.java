package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SubsystemHatchFireCommand extends Command {
    private Birdbeak birdbeak;

    public SubsystemHatchFireCommand() {
        super("subsystemhatchfirecommand");
        birdbeak = Components.getInstance().birdbeak;

        requires(birdbeak);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        birdbeak.setPuncher(true);
        Timer.delay(0.1); // TODO: tune delay timing, don't delay, use elapsed time
        birdbeak.setBeak(true);
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
