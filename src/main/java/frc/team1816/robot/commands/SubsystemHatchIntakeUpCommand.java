package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SubsystemHatchIntakeUpCommand extends Command {
    private Birdbeak birdbeak;

    private double initTime;
    private double elapsedDelayMs;

    public SubsystemHatchIntakeUpCommand() {
        super("subsystemhatchintakeupcommand");
        birdbeak = Components.getInstance().birdbeak;

        elapsedDelayMs = 1500;

        requires(birdbeak);
    }

    @Override
    protected void initialize() {
        initTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        birdbeak.setIntake(0);
        birdbeak.setArm(false);
        if ((initTime + elapsedDelayMs) < System.currentTimeMillis()) {
            birdbeak.setBeak(false);
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
