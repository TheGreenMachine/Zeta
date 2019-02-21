package frc.team1816.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

public class DriveToHatchCommand extends Command {

    private Drivetrain drivetrain;

    private NetworkTable table;
    private NetworkTableEntry xEntry;
    private NetworkTableEntry yEntry;
    private NetworkTableEntry widthEntry;
    private NetworkTableEntry heightEntry;
    private NetworkTableEntry distanceEntry;

    private static final double kP = 0.0025; // halve velocity for half throttle as nominal with 100px error
    private static final double ERROR_THRESHOLD = 5;
    private static final double DIST_THRESHOLD = 4;

    private double nominalPower;

    private double width;
    private double height;
    private double xCoord;
    private double yCoord;
    private double deltaDist;
    private double lateralError;

    public DriveToHatchCommand(double power) {
        drivetrain = Components.getInstance().drivetrain;
        nominalPower = power;
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        drivetrain.setDrivetrainVisionNav(true);

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        table = inst.getTable("SmartDashboard");
        setupTableEntries();

        updateCoordData();

        drivetrain.enableBrakeMode();
    }

    @Override
    protected void execute() {
        updateCoordData();
        lateralError = (width / 2) - xCoord;
        double leftPow = nominalPower;
        double rightPow = nominalPower;
        double control = lateralError * kP;

        System.out.println("x: " + xCoord + "\ty: " + yCoord + "\tlatErr: " + lateralError + "\tcontrol: " + control);
        System.out.println("Distance to target: " + deltaDist);

        if (Math.abs(lateralError) >= ERROR_THRESHOLD) {
            if(lateralError < 0) { // target is right of center, so decrease right side vel
                rightPow = rightPow - control;
            } else { // target is left of center, so decrease left side vel
                leftPow = leftPow - control;
            }
        }

        System.out.println("L set pow: " + leftPow + "\tR set pow: " + rightPow);
        drivetrain.setDrivetrainPercent(leftPow, rightPow);
    }

    @Override
    protected boolean isFinished() {
        // return (deltaDist > 0 && deltaDist < DIST_THRESHOLD);
        return false;
    }

    @Override
    protected void end() {
        drivetrain.setDrivetrainVisionNav(false);
        drivetrain.setDrivetrainPercent(0,0);
    }

    @Override
    protected void interrupted() {
        end();
    }

    private void setupTableEntries() {
        xEntry = table.getEntry("center_x");
        yEntry = table.getEntry("center_y");
        widthEntry = table.getEntry("width");
        heightEntry = table.getEntry("height");
        distanceEntry = table.getEntry("distance_esti");

        width = widthEntry.getDouble(640);
        height = widthEntry.getDouble(480);
    }

    private void updateCoordData() {
        xCoord = xEntry.getDouble(320);
        yCoord = yEntry.getDouble(240);
        deltaDist = distanceEntry.getDouble(-1);
    }
}
