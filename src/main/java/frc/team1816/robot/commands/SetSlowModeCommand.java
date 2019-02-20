package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

public class SetSlowModeCommand extends Command {

    private Drivetrain drivetrain;
    private boolean slowMode;

    public SetSlowModeCommand(boolean slowMode) {
        super("setslowmodecommand");
        drivetrain = Components.getInstance().drivetrain;
        this.slowMode = slowMode;
    }


    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        System.out.println("Setting Slow Mode: " + slowMode);
        drivetrain.setSlowMode(slowMode);
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
        super.interrupted();
    }
}
