package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SubsystemHatchIntakeDownCommand extends Command {
    private Birdbeak birdbeak;

    public SubsystemHatchIntakeDownCommand() {
        super("subsystemhatchintakedowncommand");
        birdbeak = Components.getInstance().birdbeak;

        requires(birdbeak);
    }

    @Override
    protected void initialize() {
        System.out.println("SUBSYSTEM Hatch Intake Down");
    }

    @Override
    protected void execute() {
        birdbeak.setArm(true);
        birdbeak.setIntake(1);
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
