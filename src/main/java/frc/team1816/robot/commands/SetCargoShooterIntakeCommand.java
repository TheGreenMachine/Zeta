package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoShooter;

public class SetCargoShooterIntakeCommand extends CommandBase {
    private CargoShooter shooter;
    private double power;

    public SetCargoShooterIntakeCommand(double power) {
        shooter = Components.getInstance().shooter;
        this.power = power;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        StringBuilder sb = new StringBuilder()
                .append("Setting Shooter Intake")
                .append(power);
        System.out.println(sb.toString());
    }

    @Override
    public void execute() {
        shooter.setIntake(power);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
