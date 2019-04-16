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

    private boolean gyroCorrection;
    private double initAngle;

    private static final double ROTATION_OFFSET_P = 0.06;
    private final double RAMP_UP_INCHES = 6;
    private double RAMP_DOWN_INCHES = 6;

    public DriveXInchesCommand(double inches, double speed, boolean gyroCorrect) {
        super("drivexinchescommand");
        this.inches = inches;
        this.speed = speed;
        this.endSpeed = 0.2;
        this.startSpeed = 0.2;

        if (inches < RAMP_DOWN_INCHES) {
            RAMP_DOWN_INCHES = inches;
        }

        drivetrain = Components.getInstance().drivetrain;

        if (gyroCorrect && drivetrain.getGyroStatus()) {
            gyroCorrection = true;
        }
        requires(drivetrain);
    }

    public DriveXInchesCommand(double inches, double speed, double startSpeed, double endSpeed, boolean gyroCorrect) {
        super("drivexinchescommand");
        this.inches = inches;
        this.speed = speed;
        this.startSpeed = startSpeed;
        this.endSpeed = endSpeed;
        drivetrain = Components.getInstance().drivetrain;

        if (gyroCorrect && drivetrain.getGyroStatus()) {
            gyroCorrection = true;
        }
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        initPosition = drivetrain.getLeftPosInches();
        if (gyroCorrection) {
            initAngle = drivetrain.getGyroAngle().getDegrees();
        }
    }

    @Override
    protected void execute() {
        double deltaAngle = drivetrain.getGyroAngle().getDegrees() - initAngle;

        double setVelPercent = speed;
        double leftSetVel, rightSetVel;
        
        double currentInches = drivetrain.getLeftPosInches() - initPosition;
        remainingInches = inches - Math.abs(currentInches);

        /*-----RAMP UP RATE-----*/
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

        /*-----RAMP DOWN RATE-----*/
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

        leftSetVel = setVelPercent;
        rightSetVel = setVelPercent;

        /*-----GYRO CORRECTION-----*/
        if (gyroCorrection) {
            if (deltaAngle < 0) {
                /*-----RIGHT GYRO CORRECTION-----*/
                rightSetVel = rightSetVel - Math.abs(deltaAngle * ROTATION_OFFSET_P);

                System.out.println("DriveX Correcting Right\t delta angle: " + deltaAngle);
                System.out.println("L Velocity: " + leftSetVel + " R Velocity: " + rightSetVel);
                System.out.println("---");
            } else if (deltaAngle > 0) {
                /*-----LEFT GYRO CORRECTION-----*/
                leftSetVel = leftSetVel - Math.abs(deltaAngle * ROTATION_OFFSET_P);

                System.out.println("DriveX Correcting Left\t delta angle: " + deltaAngle);
                System.out.println("L Velocity: " + leftSetVel + " R Velocity: " + rightSetVel);
                System.out.println("---");
            } else {
                /*-----NO GYRO CORRECTION-----*/
                System.out.println("DriveX Straight\t delta angle: " + deltaAngle);
                System.out.println("L Velocity: " + leftSetVel + " R Velocity: " + rightSetVel);
                System.out.println("---");
            }
        }

        System.out.println("Remaining Inches: " + remainingInches);
        System.out.println("Inches Traveled: " + currentInches);
        System.out.println("Talon Pos L: " + drivetrain.getLeftPosInches());
        System.out.println("---");

        drivetrain.setDrivetrainPercent(setVelPercent, setVelPercent);
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