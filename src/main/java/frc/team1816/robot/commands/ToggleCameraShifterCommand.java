package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CameraMount;

public class ToggleCameraShifterCommand extends CommandBase {
    private CameraMount cameraShifter;

    public ToggleCameraShifterCommand() {
        cameraShifter = Components.getInstance().shifter;
        addRequirements(cameraShifter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        this.cameraShifter.toggleCameraShifter();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
