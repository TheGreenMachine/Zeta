package frc.team1816.robot.commands;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

public class NewVisionAutoCommand extends Command {

    private Drivetrain drivetrain;

    double initialPosition;
    double kP = 0.03; // Some P constant
    double gyroAngle;
    double targetAngle;


    public NewVisionAutoCommand() {
        drivetrain = Components.getInstance().drivetrain;
        gyroAngle = drivetrain.getGyroAngle();
    }

    @Override
    protected void initialize() {
        targetAngle = 0;

    }

    @Override
    protected void execute() {
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

    }
}
