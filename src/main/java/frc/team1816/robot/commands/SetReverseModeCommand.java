package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

public class SetReverseModeCommand extends CommandBase {

    private Drivetrain drivetrain;
    boolean reverseMode;

    public SetReverseModeCommand(boolean reverseMode) {
        this.reverseMode = reverseMode;
        drivetrain = Components.getInstance().drivetrain;
    }

    @Override
    public void initialize() {
        drivetrain.setReverseMode(reverseMode);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean isFinished) {
    }
}
