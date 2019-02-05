package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.CargoShooter.ArmPosition;

public class SetCargoShooterPositionCommand extends Command {
    private CargoShooter shooter;
    private ArmPosition position;

    public SetCargoShooterPositionCommand(ArmPosition pos) {
        super("setcargoshooterpositioncommand");
        shooter = Components.getInstance().shooter;
        this.position = pos;
    }

    @Override
    protected void execute() {
        shooter.setArmPosition(position);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
