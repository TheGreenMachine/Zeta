package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetBeakIntakeCommand extends Command {
    private Birdbeak birdbeak;
    private double power;

    public SetBeakIntakeCommand(double power) {
        super("setbeakintakecommand");
        birdbeak = Components.getInstance().birdbeak;
        this.power = power;
    }

    @Override
    protected void execute() {
        birdbeak.setIntake(power);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
