package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.CargoShooter.ArmPosition;

public class SetCargoShooterPositionCommand extends Command {
    private CargoShooter shooter;
    private ArmPosition position;

    public SetCargoShooterPositionCommand(ArmPosition pos) {
        super("setcargoshooterpositioncommand");
        shooter = Components.getInstance().shooter;
        this.position = pos;
        requires(shooter);
    }

    @Override
    protected void execute() {
        System.out.println("Setting Shooter Position: " + position.toString());
            shooter.setArmPosition(position);
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
        end();
    }
}
