package frc.team1816.robot.commands;

import com.team254.lib.geometry.Pose2dWithCurvature;
import com.team254.lib.trajectory.TimedView;
import com.team254.lib.trajectory.Trajectory;
import com.team254.lib.trajectory.TrajectoryIterator;
import com.team254.lib.trajectory.timing.TimedState;
import frc.team1816.robot.Components;
import frc.team1816.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTrajectoryCommand extends Command {
  private Drivetrain drivetrain;

  private final TrajectoryIterator<TimedState<Pose2dWithCurvature>> trajectory;
  private final boolean mResetPose;

  public DriveTrajectoryCommand(Trajectory<TimedState<Pose2dWithCurvature>> trajectory, boolean resetPose) {
    super("drivetrajectorycommand");
    this.drivetrain = Components.getInstance().drivetrain;
    this.trajectory = new TrajectoryIterator<>(new TimedView<>(trajectory));
    mResetPose = resetPose;
    requires(drivetrain);
}

  @Override
  protected void initialize() {
    //System.out.println(trajectory.toString());
    System.out.println("Starting Trajectory! (length=" + this.trajectory.getRemainingProgress() + ")");
    //Did not add the reset stuff
    this.drivetrain.setTrajectory(this.trajectory);
  }

  @Override
  protected void execute() {
  }

  @Override
  protected boolean isFinished() {
    //System.out.println("isDone: " + this.drivetrain.isDoneWithTrajectory());
    if (this.drivetrain.isDoneWithTrajectory()) {
        System.out.println("Trajectory finished");
        return true;
    }
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
  }
}
