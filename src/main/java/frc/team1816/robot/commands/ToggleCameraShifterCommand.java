package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CameraMount;

public class ToggleCameraShifterCommand extends Command {
    private CameraMount cameraShifter;

    public ToggleCameraShifterCommand() {
        super("togglecamerashiftercommand");
        cameraShifter = Components.getInstance().shifter;
        requires(cameraShifter);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        this.cameraShifter.toggleCameraShifter();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
