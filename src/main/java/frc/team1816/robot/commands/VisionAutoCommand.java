package frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.subsystems.Drivetrain;

public class VisionAutoCommand extends Command {

    private Drivetrain drivetrain;
    
    private AnalogInput ai;
    private double initialPosition, target, ticks;
    private double rampDownValue = 0;
    private double startTime = 0;
    private double bits;
    private int x = 0;
    private int a = 0;

    private boolean tooClose = false;
    private boolean stuckVision = false;
    private int wiggleCounter = 0;

    private final int CAMERA_X_DIMENSION = 640;
    private final int SLOW_RANGE_STRAFE = 30;
    private final int ENDING_TOLERANCE_STRAFE = 3;

    public VisionAutoCommand(double inches) {
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void execute() {
        super.execute();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        super.end();
    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
