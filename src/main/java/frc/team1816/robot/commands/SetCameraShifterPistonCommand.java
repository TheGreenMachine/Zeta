package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CameraMount;

public class SetCameraShifterPistonCommand extends Command {
    private CameraMount shifter;
    private Value camState;

    public SetCameraShifterPistonCommand(Value value) {
        super("setcamerashifterpistoncommand");
        shifter = Components.getInstance().shifter;
        this.camState = value;
        requires(shifter);

    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        System.out.println("Setting cam piston: " + this.camState.toString());
        shifter.setCameraPistonState(camState);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }
}
