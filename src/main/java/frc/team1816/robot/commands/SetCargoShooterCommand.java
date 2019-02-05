package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;

public class SetCargoShooterCommand extends Command {
    private CargoCollector collector;
    private double power;

    public SetCargoShooterCommand(double power) {
        super("setcargoshootercommand");
        collector = Components.getInstance().collector;
        this.power = power;
    }

    @Override
    protected void execute() {
        collector.setIntake(power);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
