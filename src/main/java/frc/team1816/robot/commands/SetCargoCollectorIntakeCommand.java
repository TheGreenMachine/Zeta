package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;

public class SetCargoCollectorIntakeCommand extends Command {
    private CargoCollector collector;
    private double power;

    public SetCargoCollectorIntakeCommand(double pow) {
        super("setcargocollectorintakecommand");
        collector = Components.getInstance().collector;
        this.power = pow;
        requires(collector);
    }

    @Override
    protected void execute() {
        collector.setIntake(power);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
