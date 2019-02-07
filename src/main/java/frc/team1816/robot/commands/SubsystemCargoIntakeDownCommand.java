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

    public SubsystemCargoIntakeDownCommand() {
        super("subsystemcargointakedowncommand");
        collector = Components.getInstance().collector;
        shooter = Components.getInstance().shooter;

        requires(collector);
        requires(shooter);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        collector.setArm(true);
        collector.setIntake(0.6);
        Timer.delay(0.5); // TODO: tune delay timing
        shooter.setArmPosition(ArmPosition.DOWN);
        shooter.setIntake(0.6);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
    }
}
