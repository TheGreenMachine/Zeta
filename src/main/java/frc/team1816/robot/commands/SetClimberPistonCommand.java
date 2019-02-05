package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Climber;

public class SetClimberPistonCommand extends Command {
    private Climber climber;
    private DoubleSolenoid.Value value;

    public SetClimberPistonCommand(DoubleSolenoid.Value value) {
        super("setclimberpistoncommand");
        climber = Components.getInstance().climber;
        this.value = value;
    }

    @Override
    protected void execute() {
        climber.setHabPiston(value);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
