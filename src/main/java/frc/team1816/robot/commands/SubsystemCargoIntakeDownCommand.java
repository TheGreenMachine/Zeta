package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.CargoShooter.ArmPosition;

public class SubsystemCargoIntakeDownCommand extends Command {
    private CargoCollector collector;
    private CargoShooter shooter;

    private double initTime;
    private double elapsedDelayMs;

    public SubsystemCargoIntakeDownCommand() {
        super("subsystemcargointakedowncommand");
        collector = Components.getInstance().collector;
        shooter = Components.getInstance().shooter;

        elapsedDelayMs = 800;

        requires(collector);
        requires(shooter);
    }

    @Override
    protected void initialize() {
        initTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        collector.setArm(true);
        collector.setIntake(-1.0);
        shooter.setIntake(-1.0);
        // Timer.delay(0.8);
        if ((initTime + elapsedDelayMs) < System.currentTimeMillis()) {
            shooter.setArmPosition(ArmPosition.DOWN);
        }
    }

    @Override
    protected boolean isFinished() {
        return ((initTime + elapsedDelayMs) < System.currentTimeMillis());
    }

    @Override
    protected void end() {
        collector.setIntake(0.0);
        shooter.setIntake(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
