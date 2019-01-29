package frc.team1816.robot.commands;

import com.edinarobotics.utils.gamepad.Gamepad;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Controls;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

public class GamepadDriveCommand extends Command {
    private static final String NAME = "gamepaddrivecommand";

    private Drivetrain drivetrain;
    private Gamepad gamepad;

    private double prevPowLeft = 0;
    private double prevPowRight = 0;

    public static final double SET_SPEED_DIFF_MAX = 0.04; // TODO: tune max percent velocity increase per 20ms

    public GamepadDriveCommand() {
        super(NAME);
        this.drivetrain = Components.getInstance().drivetrain;
        this.gamepad = Controls.getInstance().gamepadDriver;
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double leftPow = Controls.getInstance().getDriveThrottle();
        double rightPow = Controls.getInstance().getDriveThrottle();
        double rotation = Controls.getInstance().getDriveTurn();

        if(rotation == 0 || leftPow != 0) {
            leftPow = limitAcceleration(leftPow, prevPowLeft);
            rightPow = limitAcceleration(rightPow, prevPowRight);
        }

        prevPowLeft = leftPow;
        prevPowRight = rightPow;

        drivetrain.setDrivetrainPercent(leftPow, rightPow, rotation);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        drivetrain.setDrivetrainPercent(0,0);
    }

    @Override
    protected void interrupted() {
        end();
    }

    private double limitAcceleration(double setPow, double prevPow) {
        if (Math.abs(setPow - prevPow) > SET_SPEED_DIFF_MAX) {
            if (setPow > prevPow) {
                return prevPow + SET_SPEED_DIFF_MAX;
            } else if (setPow < prevPow) {
                return prevPow - SET_SPEED_DIFF_MAX;
            } else {
                return prevPow;
            }
        } else {
            return prevPow;
        }
    }
}
