package frc.team1816.robot.commands;

import com.edinarobotics.utils.math.Math1816;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;
import frc.team1816.robot.subsystems.LedManager;
import frc.team1816.robot.subsystems.LedManager.RobotStatus;

public class DriveToHatchCommand extends Command {

    private Drivetrain drivetrain;

    private NetworkTable table;
    private NetworkTableEntry xEntry;
    private NetworkTableEntry yEntry;
    private NetworkTableEntry widthEntry;
    private NetworkTableEntry heightEntry;
    private NetworkTableEntry distanceEntry;

    private static final double kP = 0.0015;
    private static final double ERROR_THRESHOLD = 5;
    private static final double DIST_THRESHOLD = 4;

    private boolean prevReverseState;

    private double nominalPower;
    private double targetCenterX = 300.0; // define x that corresponds to bot center

    private double width;
    private double height;
    private double xCoord;
    private double yCoord;
    private double deltaDist;
    private double lateralError;

    private LedManager leds;

    public DriveToHatchCommand(double power) {
        drivetrain = Components.getInstance().drivetrain;
        leds = Components.getInstance().ledManager;
        nominalPower = power;
        requires(drivetrain);
    }

    @Override
    protected void initialize() { // TODO: ungrip while driving, grip when finished
        drivetrain.setDrivetrainVisionNav(true);

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        table = inst.getTable("SmartDashboard");
        setupTableEntries();

        updateCoordData();

        drivetrain.enableBrakeMode();

        prevReverseState = drivetrain.isReverseMode();
        drivetrain.setReverseMode(true);
    }

    @Override
    protected void execute() {
        updateCoordData();
        lateralError = targetCenterX - xCoord;
        double leftPow = nominalPower;
        double rightPow = nominalPower;
        double control = Math.abs(lateralError * kP);

        System.out.println("cam: (" + width + "x" + height + ")\tcenter: (" + xCoord + "," + yCoord +
                ")\tlatErr: " + lateralError + "\tcontrol: " + control);
        System.out.println("In range?: " + (deltaDist < DIST_THRESHOLD) + "\tDistance to target: " + deltaDist);

        if (xCoord == -1.0 || yCoord == -1.0) {
            control = 0;
        } else {
            leds.indicateStatus(RobotStatus.TARGET_SEEN);
        }

        if (Math.abs(lateralError) >= ERROR_THRESHOLD) {
            if (lateralError < 0) { // target is right of center, so decrease right side (wrt cargo) vel
                leftPow = leftPow - control; // drivetrain reversed, so apply control to other side
                Math1816.coerceValue(1.0, 0.0, leftPow);
            } else { // target is left of center, so decrease left side (wrt cargo) vel
                rightPow = rightPow - control; // drivetrain reversed, so apply control to other side
                Math1816.coerceValue(1.0, 0.0, rightPow);
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
        drivetrain.setDrivetrainPercent(0, 0);
        drivetrain.setReverseMode(prevReverseState);
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
        xCoord = xEntry.getDouble(-1.0);
        yCoord = yEntry.getDouble(-1.0);
        deltaDist = distanceEntry.getDouble(-1);
    }
}
