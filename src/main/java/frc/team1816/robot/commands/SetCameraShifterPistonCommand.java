package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CameraMount;

public class SetCameraShifterPistonCommand extends CommandBase {
    private CameraMount shifter;
    private Value camState;

    public SetCameraShifterPistonCommand(Value value) {
        shifter = Components.getInstance().shifter;
        this.camState = value;
        addRequirements(shifter);

    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        System.out.println("Setting cam piston: " + this.camState.toString());
        shifter.setCameraPistonState(camState);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean isFinished) {
    }
}
