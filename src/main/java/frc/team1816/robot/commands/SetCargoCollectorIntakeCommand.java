package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoCollector;

public class SetCargoCollectorIntakeCommand extends CommandBase {
    private CargoCollector collector;
    private double power;

    public SetCargoCollectorIntakeCommand(double pow) {
        collector = Components.getInstance().collector;
        this.power = pow;
        addRequirements(collector);
    }

    @Override
    public void execute() {
        StringBuilder sb = new StringBuilder()
                .append("Setting Cargo Intake: ")
                .append(power);
        System.out.println(sb.toString());
        collector.setIntake(power);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
