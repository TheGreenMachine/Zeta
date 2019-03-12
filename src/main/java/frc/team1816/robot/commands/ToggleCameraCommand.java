package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;

public class ToggleCameraCommand extends Command {

    public ToggleCameraCommand() {
        super("togglecameracommand");
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        System.out.println("Toggling Camera");
        Components.getInstance().toggleCamera();
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
