package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Climber;

public class SetClimberPowerCommand extends CommandBase {
    private Climber climber;
    private double power;

    public SetClimberPowerCommand(double power) {
        climber = Components.getInstance().climber;
        this.power = power;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
        System.out.println("Setting Climber Power: " + power);
    }

    @Override
    public void execute() {
        climber.setClimberPower(power);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean isFinished) {
        climber.setClimberPower(0);
    }
}
