package frc.team1816.robot.commands;

import com.team254.lib.util.CheesyDriveHelper;
import edu.wpi.first.wpilibj.command.Command;
import frc.team1816.robot.Components;
import frc.team1816.robot.Controls;
import frc.team1816.robot.subsystems.Drivetrain;

import static frc.team1816.robot.Robot.factory;

public class GamepadDriveCommand extends Command {
    private static final String NAME = "gamepaddrivecommand";

    private Drivetrain drivetrain;
    CheesyDriveHelper mCheesyDriveHelper = new CheesyDriveHelper();

    private double prevPowLeft = 0;
    private double prevPowRight = 0;

    public static final double SET_SPEED_DIFF_MAX = 0.08;

    public GamepadDriveCommand() {
        super(NAME);
        this.drivetrain = Components.getInstance().drivetrain;
        requires(drivetrain);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected void execute() {
        double leftPow = Controls.getInstance().getDriveThrottle();
        double rightPow = leftPow;
        double rotation = Controls.getInstance().getDriveTurn();

        if (factory.getConstant("cheezyDrive") == 0) { // Arcade Drive
            if (rotation == 0 || leftPow != 0) {
                leftPow = limitAcceleration(leftPow, prevPowLeft);
                rightPow = limitAcceleration(rightPow, prevPowRight);
            }

            prevPowLeft = leftPow;
            prevPowRight = rightPow;
        } else { // Cheesy Drive
            boolean quickTurn = Controls.getInstance().getQuickTurn();
            var signal = mCheesyDriveHelper.cheesyDrive(leftPow, rotation, quickTurn, false);
            leftPow = signal.getLeft();
            rightPow = signal.getRight();
            rotation = 0; // CheezyDrive takes care of rotation so set to 0 to keep or code from adjusting
        }

        drivetrain.setDrivetrainPercent(leftPow, rightPow, rotation); // TODO: possibly check vision status tag, if necessary
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        drivetrain.setDrivetrainPercent(0, 0);
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
