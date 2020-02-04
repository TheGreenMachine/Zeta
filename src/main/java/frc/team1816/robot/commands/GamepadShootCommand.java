package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.Controls;
import frc.team1816.robot.subsystems.CargoShooter;

public class GamepadShootCommand extends CommandBase {
    private CargoShooter shooter;

    public GamepadShootCommand() {
        this.shooter = Components.getInstance().shooter;
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double armPower = Controls.getInstance().getShooterArmThrottle();

        shooter.setArmPower(armPower);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean isFinished) {
        shooter.setArmPower(0);
    }
}
