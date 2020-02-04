package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

public class SetSlowModeCommand extends CommandBase {

    private Drivetrain drivetrain;
    private boolean slowMode;

    public SetSlowModeCommand(boolean slowMode) {
        drivetrain = Components.getInstance().drivetrain;
        this.slowMode = slowMode;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        System.out.println("Setting Slow Mode: " + slowMode);
        drivetrain.setSlowMode(slowMode);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean isFinished) {
    }
}
