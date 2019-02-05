package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;

public class SetCargoCollectorCommand extends Command {
    private CargoCollector collector;
    private boolean isDown;

    public SetCargoCollectorCommand(boolean down) {
        super("setcargocollectorcommand");
        collector = Components.getInstance().collector;
        this.isDown = down;
    }

    @Override
    protected void execute() {
        collector.setArmPiston(isDown);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
