package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

public class DriveXInchesCommand extends Command {
    // FIXME: UNSTABLE - UNTESTED CODE, EMERGENCY USE ONLY

    private Drivetrain drivetrain;
    private double initPosition;
    private double inches;
    private double speed;
    private double remainingInches;

    private double startSpeed;
    private double endSpeed;

    private final double RAMP_UP_INCHES = 6;
    private double RAMP_DOWN_INCHES = 6;

    public DriveXInchesCommand(double inches, double speed) {
        super("drivexinchescommand");
        this.inches = inches;
        this.speed = speed;

        this.endSpeed = 0.2;
        this.startSpeed = 0.2;

        if (inches < RAMP_DOWN_INCHES) {
            RAMP_DOWN_INCHES = inches;
        }

        drivetrain = Components.getInstance().drivetrain;
        requires(drivetrain);
    }

    public DriveXInchesCommand(double inches, double speed, double startSpeed, double endSpeed) {
        super("drivexinchescommand");
        this.inches = inches;
        this.speed = speed;
        this.startSpeed = startSpeed;
        this.endSpeed = endSpeed;
        drivetrain = Components.getInstance().drivetrain;

        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        initPosition = drivetrain.getLeftPosInches();
        System.out.println("DriveX Init\tPos:" + initPosition);
    }

    @Override
    protected void execute() {
        double setVelPercent;
        double currentInches = drivetrain.getLeftPosInches() - initPosition;
        remainingInches = inches - Math.abs(currentInches);

        setVelPercent = speed;

        // RAMP UP RATE
        if (currentInches < RAMP_UP_INCHES) {
            if (speed > 0) {
                if (setVelPercent * (currentInches / RAMP_UP_INCHES) < startSpeed) {
                    setVelPercent = startSpeed;
                } else {
                    setVelPercent = setVelPercent * (currentInches / RAMP_UP_INCHES);
                }
            } else {
                if (setVelPercent * (currentInches / RAMP_UP_INCHES) > -startSpeed) {
                    setVelPercent = -startSpeed;
                } else {
                    setVelPercent = setVelPercent * (currentInches / RAMP_UP_INCHES);
                }
            }
        }

        // RAMP DOWN RATE
        if (remainingInches < RAMP_DOWN_INCHES) {
            if (speed > 0) {
                if ((setVelPercent * (remainingInches / RAMP_DOWN_INCHES)) > endSpeed) {
                    setVelPercent = setVelPercent * (remainingInches / RAMP_DOWN_INCHES);
                } else {
                    setVelPercent = endSpeed;
                }
            } else {
                if ((setVelPercent * (remainingInches / RAMP_DOWN_INCHES)) < -endSpeed) {

                    setVelPercent = setVelPercent * (remainingInches / RAMP_DOWN_INCHES);
                } else {
                    setVelPercent = -endSpeed;
                }
            }
        }

        drivetrain.setDrivetrainPercent(setVelPercent, setVelPercent);

        System.out.println("Remaining Inches: " + remainingInches);
        System.out.println("Inches Traveled: " + currentInches);
        System.out.println("Talon Pos L: " + drivetrain.getLeftPosInches());
    }

    @Override
    protected void end() {
        drivetrain.setDrivetrainPercent(0, 0);
    }

    @Override
    protected void interrupted() {
        end();
    }

    @Override
    protected boolean isFinished() {
        if (remainingInches <= 0) {
            System.out.println("DriveX Finished");
            return true;
        } else {
            return false;
        }
    }
}