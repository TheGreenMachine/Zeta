package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Birdbeak;

public class SetIntakeCommand extends Command {
    private Birdbeak birdbeak;
    private double power;

    public SetIntakeCommand(double power) {
        super("setintakecommand");
        birdbeak = Components.getInstance().birdbeak;
        this.power = power;
    }

    @Override
    protected void execute() {
        if (birdbeak.getIntakePow() != power)
            birdbeak.setIntake(power);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
