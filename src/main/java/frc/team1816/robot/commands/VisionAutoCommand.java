package frc.team1816.robot.commands;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

public class VisionAutoCommand extends Command {

    private Drivetrain drivetrain;
    private NetworkTable table;
    private AnalogInput ai;

    private double initialPosition, target, ticks;
    private double rampDownValue = 0;
    private double startTime = 0;
    private double bits;

    private int wiggleCounter = 0;

    private boolean tooClose = false;
    private boolean stuckVision = false;

    private final double RAMP_DOWN_START = 24;
    private final double DISTANCE_THRESHOLD = 900;
    private final double END_DISTANCE_THRESHOLD = 13000;

    private final int CAMERA_X_DIMENSION = 640;
    private final int SLOW_RANGE_STRAFE = 30;
    private final int ENDING_TOLERANCE_STRAFE = 3;

    public VisionAutoCommand(double inches) {
        super("visionautocommand");
        drivetrain = Components.getInstance().drivetrain;
        ticks = -Math.round(((inches * 38 * 1.018) * 10) / 13);
        rampDownValue = RAMP_DOWN_START * 31.6923;
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
        table = NetworkTableInstance.getDefault().getTable("SmartDashboard");
//        initialPosition = drivetrain
        target = initialPosition - ticks;
//        drivetrain.setBreakMode(true);

        startTime = System.currentTimeMillis();
        ai = new AnalogInput(3);
        ai.setAverageBits(2);
        bits = ai.getAverageBits();
        AnalogInput.setGlobalSampleRate(62500);
    }

    @Override
    protected void execute() {
        int x = (int) table.getEntry("averagedCenterX").getDouble(-1);
        int y = (int) table.getEntry("averagedCenterY").getDouble(-1);

        double deltaVision = 320 - x;
        double velocityLeft = 0.33;
        double velocityRight = 0.33;

        if (Math.abs(deltaVision) > 20.0) {
            if (ai.getAverageVoltage() > DISTANCE_THRESHOLD) {
                velocityLeft /= 2;
                velocityRight /= 2;
            }
        } else {
            if (deltaVision > 0)
                velocityLeft += 0.5;
            else
                velocityRight += 0.5;
        }

        drivetrain.setDrivetrainPercent(velocityLeft, velocityRight);
    }

    @Override
    protected boolean isFinished() {
        return ai.getAverageVoltage() >= END_DISTANCE_THRESHOLD;
    }

    @Override
    protected void end() {
        drivetrain.setDrivetrainPercent(0, 0);
        //TODO call the hatch drop command
    }

    @Override
    protected void interrupted() {
        drivetrain.setDrivetrainPercent(0, 0);
    }
}
