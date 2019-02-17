package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SubsystemHatchFireCommand extends Command {
    private Birdbeak birdbeak;

    private double initTime;
    private double elapsedDelayMs;

    public SubsystemHatchFireCommand() {
        super("subsystemhatchfirecommand");
        birdbeak = Components.getInstance().birdbeak;

        elapsedDelayMs = 100;

        requires(birdbeak);
    }

    @Override
    protected void initialize() {
        System.out.println("Firing Hatch");
        initTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        birdbeak.setPuncher(true);
        if ((initTime + elapsedDelayMs) < System.currentTimeMillis()) {
            birdbeak.setBeak(true);
        }
    } 

    @Override
    protected boolean isFinished() {
        return ((initTime + elapsedDelayMs) < System.currentTimeMillis());
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
