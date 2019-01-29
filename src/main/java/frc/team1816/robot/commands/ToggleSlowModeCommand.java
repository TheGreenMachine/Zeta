package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

public class ToggleSlowModeCommand extends Command {

    private Drivetrain drivetrain;
    private boolean slowMode;

    public ToggleSlowModeCommand(boolean slowMode) {
        super("toggleslowmodecommand");
        drivetrain = Components.getInstance().drivetrain;
        this.slowMode = slowMode;
    }


    @Override
    protected void initialize() {
        drivetrain.setSlowMode(slowMode);
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
