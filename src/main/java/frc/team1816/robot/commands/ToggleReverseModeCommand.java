package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

public class ToggleReverseModeCommand extends CommandBase {

    private Drivetrain drivetrain;

    public ToggleReverseModeCommand() {
        drivetrain = Components.getInstance().drivetrain;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        System.out.println("Reversing Drivetrain");
        drivetrain.toggleReverseMode();

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean isFinished) {
    }
}
