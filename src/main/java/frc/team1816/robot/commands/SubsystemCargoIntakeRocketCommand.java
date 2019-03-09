package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.CargoShooter.ArmPosition;

public class SubsystemCargoIntakeRocketCommand extends Command {
    private CargoCollector collector;
    private CargoShooter shooter;

    private double initTime;
    private double elapsedDelayMs;

    public SubsystemCargoIntakeRocketCommand() {
        super("subsystemcargointakerocketcommand");
        collector = Components.getInstance().collector;
        shooter = Components.getInstance().shooter;

        elapsedDelayMs = 800;

        requires(collector);
        requires(shooter);
    }

    @Override
    protected void initialize() {
        System.out.println("SUBSYSTEM Cargo Intake To Rocket");
        initTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        collector.setIntake(0.0);
        shooter.setIntake(0.0);
        shooter.setArmPosition(ArmPosition.ROCKET);
        if ((initTime + elapsedDelayMs) < System.currentTimeMillis()) {
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
