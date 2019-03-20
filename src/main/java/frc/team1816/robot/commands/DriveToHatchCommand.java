package frc.team1816.robot.commands;

import com.ctre.phoenix.motorcontrol.NeutralMode;
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
    private NetworkTableInstance inst;

    private NetworkTable table;
    private NetworkTableEntry widthEntry;
    private NetworkTableEntry heightEntry;
    private NetworkTableEntry xEntry;
    private NetworkTableEntry yEntry;
    private NetworkTableEntry distanceEntry;
    private NetworkTableEntry yawEntry;

    private static final double kP = 0.0015; // stable @ 20% - 0.0015
    private static final double ERROR_THRESHOLD = 0;
    private static final double ON_TARGET_THRESHOLD = 20;
    private static final double DIST_THRESHOLD = 4;

    private boolean prevReverseState;

    private double nominalPower;
    private double targetCenterX = 320.0; // define x that corresponds to bot center

    private double width;
    private double height;
    private double xCoord;
    private double yCoord;
    private double yawOffset;
    private double deltaDist;
    private double lateralError;

    private LedManager leds;

    public DriveToHatchCommand(double power) {
        drivetrain = Components.getInstance().drivetrain;
        leds = Components.getInstance().ledManager;
        inst = NetworkTableInstance.getDefault();
        nominalPower = power;
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        drivetrain.setDrivetrainVisionNav(true);

        table = inst.getTable("SmartDashboard");
        setupTableEntries();

        updateCoordData();

        drivetrain.setNeutralMode(NeutralMode.Brake);

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

        StringBuilder sb = new StringBuilder("cam: (");
        sb.append(width).append("x").append(height)
            .append(")\tcenter: (").append(xCoord).append(",").append(yCoord)
            .append(")\tlatErr: ").append(lateralError)
            .append("\tcontrol: ").append(control)
            .append("\nIn range?: ").append(deltaDist < DIST_THRESHOLD)
            .append("\tDistance to target: ").append(deltaDist);

        System.out.println(sb.toString());

        if (xCoord == -1.0 && yCoord == -1.0) {
            control = 0;
            leds.indicateStatus(RobotStatus.OFF);
        } else {
            if (Math.abs(lateralError) <= ON_TARGET_THRESHOLD) {
                leds.indicateStatus(RobotStatus.ON_TARGET);
            } else {
                leds.indicateStatus(RobotStatus.SEEN_TARGET);
            }
        }

        if (Math.abs(lateralError) >= ERROR_THRESHOLD) {
            if (lateralError < 0) { // target is right of center, so decrease right side (wrt cargo) vel
                leftPow = leftPow - control; // drivetrain reversed, so apply control to other side
                leftPow = Math1816.coerceValue(1.0, 0.0, leftPow);
            } else { // target is left of center, so decrease left side (wrt cargo) vel
                rightPow = rightPow - control; // drivetrain reversed, so apply control to other side
                rightPow = Math1816.coerceValue(1.0, 0.0, rightPow);
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
        yawEntry = table.getEntry("yaw");

        width = widthEntry.getDouble(640);
        height = heightEntry.getDouble(480);
    }

    private void updateCoordData() {
        xCoord = xEntry.getDouble(-1.0);
        yCoord = yEntry.getDouble(-1.0);
        deltaDist = distanceEntry.getDouble(-1);
        yawOffset = yawEntry.getDouble(0);
    }
}
