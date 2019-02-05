package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoShooter;

public class SetCargoShooterCommand extends Command {
    private CargoShooter shooter;
    private double power;

    public SetCargoShooterCommand(double power) {
        super("setcargoshootercommand");
        shooter = Components.getInstance().shooter;
        this.power = power;
    }

    @Override
    protected void execute() {
        shooter.setIntakePower(power);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
