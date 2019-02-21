package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;

public class SetCargoCollectorArmCommand extends Command {
    private CargoCollector collector;
    private boolean isDown;

    public SetCargoCollectorArmCommand(boolean down) {
        super("setcargocollectorcommand");
        collector = Components.getInstance().collector;
        this.isDown = down;
        requires(collector);
    }

    @Override
    protected void execute() {
        System.out.println("Setting Cargo Arm\t Down: " + isDown);

        collector.setArm(isDown);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
