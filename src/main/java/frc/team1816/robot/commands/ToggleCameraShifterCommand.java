package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CameraShifter;

public class ToggleCameraShifterCommand extends Command {
  private CameraShifter cameraShifter;
  
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
    return false;
  }
}
