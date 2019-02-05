package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Climber;

public class SetClimberPowerCommand extends Command {
    private Climber climber;
    private double power;

    public SetClimberPowerCommand(double power) {
        super("setclimberpowercommand");
        climber = Components.getInstance().climber;
        this.power = power;
        requires(climber);
    }

    @Override
    protected void execute() {
        climber.setClimberPower(power);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
