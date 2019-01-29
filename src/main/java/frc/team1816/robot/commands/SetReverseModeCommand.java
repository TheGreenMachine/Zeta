package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

public class SetReverseModeCommand extends Command {

    private Drivetrain drivetrain;
    boolean reverseMode;

    public SetReverseModeCommand(boolean reverseMode) {
        super("setreversemodecommand");
        this.reverseMode = reverseMode;
        drivetrain = Components.getInstance().drivetrain;
    }

    @Override
    protected void initialize() {
        drivetrain.setReverseMode(reverseMode);
    }

    @Override
    protected void execute() { }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
