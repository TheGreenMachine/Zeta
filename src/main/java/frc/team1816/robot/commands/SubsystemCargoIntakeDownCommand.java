package frc.team1816.robot.commands;

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

    private boolean chainExecuted = false;

    public SubsystemCargoIntakeDownCommand() {
        super("subsystemcargointakedowncommand");
        collector = Components.getInstance().collector;
        shooter = Components.getInstance().shooter;

        elapsedDelayMs = 700;

        requires(collector);
        requires(shooter);
    }

    @Override
    protected void initialize() {
        System.out.println("SUBSYSTEM Cargo Intake Down");
        initTime = System.currentTimeMillis();
        chainExecuted = false;
    }

    @Override
    protected void execute() {
        collector.setArm(true);

        if ((initTime + elapsedDelayMs) < System.currentTimeMillis() && collector.isArmDown()) {
            shooter.setArmPosition(ArmPosition.DOWN);
            if (shooter.getArmEncoderPosition() > (CargoShooter.ARM_POSITION_MAX - 20)
                    || (initTime + elapsedDelayMs + 1500) < System.currentTimeMillis()) {
                collector.setIntake(-1.0);
                shooter.setIntake(1.0);
                chainExecuted = true;
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return chainExecuted;
    }

    @Override
    protected void end() {
    }

    @Override
    protected void interrupted() {
        end();
    }
}
