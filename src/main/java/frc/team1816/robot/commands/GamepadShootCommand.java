package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.Controls;
import frc.team1816.robot.subsystems.CargoShooter;

public class GamepadShootCommand extends Command {
    private CargoShooter shooter;

    public GamepadShootCommand() {
        super("gamepadshootcommand");
        this.shooter = Components.getInstance().shooter;
        requires(shooter);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double armPower = Controls.getInstance().getShooterArmThrottle();

        shooter.setArmPower(armPower);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        shooter.setArmPower(0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
