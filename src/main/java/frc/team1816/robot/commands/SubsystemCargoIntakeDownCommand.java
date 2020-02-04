package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.CargoShooter.ArmPosition;

public class SubsystemCargoIntakeDownCommand extends CommandBase {
    private CargoCollector collector;
    private CargoShooter shooter;

    private double initTime;
    private double elapsedDelayMs;

    private boolean chainExecuted = false;

    public SubsystemCargoIntakeDownCommand() {
        collector = Components.getInstance().collector;
        shooter = Components.getInstance().shooter;

        elapsedDelayMs = 700;

        addRequirements(collector);
        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        System.out.println("SUBSYSTEM Cargo Intake Down");
        initTime = System.currentTimeMillis();
        chainExecuted = false;
    }

    @Override
    public void execute() {
        collector.setArm(true);

        if ((initTime + elapsedDelayMs) < System.currentTimeMillis() && collector.isArmDown()) {
            shooter.setArmPosition(ArmPosition.DOWN);
            if (shooter.getArmEncoderPosition() > (CargoShooter.ARM_POSITION_MAX - 150) // check arm pos below threshold
                    || (initTime + elapsedDelayMs + 1000) < System.currentTimeMillis()) { // wait 1000ms max before override
                collector.setIntake(-1.0); // TODO: GITCH change math operation regarding 150 to addition
                shooter.setIntake(1.0);
                chainExecuted = true;
            }
        }
    }

    @Override
    public boolean isFinished() {
        return chainExecuted;
    }

    @Override
    public void end(boolean isFinished) {
    }
}
