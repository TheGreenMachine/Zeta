package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.CargoShooter;

public class SetCargoShooterIntakeCommand extends Command {
    private CargoShooter shooter;
    private double power;

    public SetCargoShooterIntakeCommand(double power) {
        super("setcargoshooterintakecommand");
        shooter = Components.getInstance().shooter;
        this.power = power;
        requires(shooter);
    }

    @Override
    protected void initialize() {
        StringBuilder sb = new StringBuilder()
                .append("Setting Shooter Intake")
                .append(power);
        System.out.println(sb.toString());
    }

    @Override
    protected void execute() {
        shooter.setIntake(power);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
