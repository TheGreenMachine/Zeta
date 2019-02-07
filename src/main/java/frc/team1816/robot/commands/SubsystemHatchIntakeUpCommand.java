package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SubsystemHatchIntakeUpCommand extends Command {
    private Birdbeak birdbeak;

    public SubsystemHatchIntakeUpCommand() {
        super("subsystemhatchintakeupcommand");
        birdbeak = Components.getInstance().birdbeak;

        requires(birdbeak);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        birdbeak.setIntake(0);
        birdbeak.setArm(false);
        Timer.delay(0.5); // TODO: tune delay timing OR use sensor feedback
        birdbeak.setBeak(false);
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
