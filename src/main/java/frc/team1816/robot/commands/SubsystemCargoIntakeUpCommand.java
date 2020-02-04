package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.CargoShooter.ArmPosition;

public class SubsystemCargoIntakeUpCommand extends CommandBase {
    private CargoCollector collector;
    private CargoShooter shooter;

    private double initTime;
    private double elapsedDelayMs;

    public SubsystemCargoIntakeUpCommand() {
        collector = Components.getInstance().collector;
        shooter = Components.getInstance().shooter;

        elapsedDelayMs = 800;

        addRequirements(collector);
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        System.out.println("SUBSYSTEM Cargo Intake To Bay");
        initTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        shooter.setIntake(0.0);
        collector.setIntake(0.0);
        shooter.setArmPosition(ArmPosition.UP);
        if ((initTime + elapsedDelayMs) < System.currentTimeMillis()) {
            collector.setArm(false);
        }
    }

    @Override
    public boolean isFinished() {
        return ((initTime + elapsedDelayMs) < System.currentTimeMillis());
    }

    @Override
    public void end(boolean isFinished) {
        collector.setIntake(0.0);
        shooter.setIntake(0.0);
    }
}
