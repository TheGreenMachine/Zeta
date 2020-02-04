package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.CargoShooter.ArmPosition;

public class SetCargoShooterPositionCommand extends CommandBase {
    private CargoShooter shooter;
    private ArmPosition position;

    public SetCargoShooterPositionCommand(ArmPosition pos) {
        shooter = Components.getInstance().shooter;
        this.position = pos;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        System.out.println("Setting Shooter Position: " + position.toString());
        shooter.setArmPosition(position);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end(boolean isFinished) {
    }
}
