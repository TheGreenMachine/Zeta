package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.CargoShooter.ArmPosition;

public class SubsystemCargoIntakeUpCommand extends Command {
    private CargoCollector collector;
    private CargoShooter shooter;

    private double initTime;
    private double elapsedDelayMs;

    public SubsystemCargoIntakeUpCommand() {
        super("subsystemcargointakeupcommand");
        collector = Components.getInstance().collector;
        shooter = Components.getInstance().shooter;

        elapsedDelayMs = 1000;

        requires(collector);
        requires(shooter);
    }

    @Override
    protected void initialize() {
        initTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        shooter.setIntake(0);
        collector.setIntake(0);
        shooter.setArmPosition(ArmPosition.UP);
        // Timer.delay(0.8);
        if ((initTime + elapsedDelayMs) < System.currentTimeMillis()) { // TODO: test
            collector.setArm(false);
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
