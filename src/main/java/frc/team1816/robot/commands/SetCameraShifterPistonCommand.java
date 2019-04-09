package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CameraShifter;

public class SetCameraShifterPistonCommand extends Command {
  private CameraShifter shifter;
  private boolean isRetracted;
  
  public SetCameraShifterPistonCommand(boolean isRetracted) {
    super("setcamerashifterpistoncommand");
    shifter = Components.getInstance().shifter;
    this.isRetracted = isRetracted;
    requires(shifter);
 
  }

  @Override
  protected void initialize() {
    System.out.println("Retracting the camera: " + this.isRetracted);
  }

  @Override
  protected void execute() {
    shifter.setShifterPistonState(isRetracted);
  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }
}
