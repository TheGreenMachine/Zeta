package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Climber;

public class SetClimberPistonCommand extends CommandBase {
    private Climber climber;
    private DoubleSolenoid.Value value;

    public SetClimberPistonCommand(Value value) {
        climber = Components.getInstance().climber;
        this.value = value;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        System.out.println("Setting Climber Piston: " + value.toString());
    }

    @Override
    public void execute() {
        climber.setHabPiston(value);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
