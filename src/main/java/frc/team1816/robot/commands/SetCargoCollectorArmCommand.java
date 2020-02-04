package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;

public class SetCargoCollectorArmCommand extends CommandBase {
    private CargoCollector collector;
    private boolean isDown;

    public SetCargoCollectorArmCommand(boolean down) {
        collector = Components.getInstance().collector;
        this.isDown = down;
        addRequirements(collector);
    }

    @Override
    public void execute() {
        System.out.println("Setting Cargo Arm\t Down: " + isDown);

        collector.setArm(isDown);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
