package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.Controls;
import frc.team1816.robot.subsystems.Climber;

public class GamepadClimbCommand extends CommandBase {
    private Climber climber;

    public GamepadClimbCommand() {
        this.climber = Components.getInstance().climber;
        addRequirements(climber);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double climberPow = Controls.getInstance().getClimbThrottle();
        climber.setClimberPower(climberPow);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isFinished) {
        climber.setClimberPower(0);
    }
}
