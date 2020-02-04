package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SubsystemHatchFireCommand extends CommandBase {
    private Birdbeak birdbeak;

    private double initTime;
    private double elapsedDelayMs;

    public SubsystemHatchFireCommand() {
        birdbeak = Components.getInstance().birdbeak;

        elapsedDelayMs = 100;

        addRequirements(birdbeak);
    }

    @Override
    public void initialize() {
        System.out.println("Firing Hatch");
        initTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        birdbeak.setPuncher(true);
        if ((initTime + elapsedDelayMs) < System.currentTimeMillis()) {
            birdbeak.setBeak(true);
        }
    }

    @Override
    public boolean isFinished() {
        return ((initTime + elapsedDelayMs) < System.currentTimeMillis());
    }

    @Override
    public void end(boolean isFinished) {
    }
}
