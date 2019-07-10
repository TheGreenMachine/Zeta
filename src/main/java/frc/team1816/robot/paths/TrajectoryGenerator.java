package frc.team1816.robot.paths;

import com.team254.lib.geometry.Pose2d;
import com.team254.lib.geometry.Pose2dWithCurvature;
import com.team254.lib.geometry.Rotation2d;
import com.team254.lib.geometry.Translation2d;
import com.team254.lib.trajectory.Trajectory;
import com.team254.lib.trajectory.TrajectoryUtil;
import com.team254.lib.trajectory.timing.CentripetalAccelerationConstraint;
import com.team254.lib.trajectory.timing.TimedState;
import com.team254.lib.trajectory.timing.TimingConstraint;
import frc.team1816.robot.planners.DriveMotionPlanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrajectoryGenerator {
    // velocities are in/sec
    private static final double kMaxVelocity = 25;
    private static final double kMaxAccel = 25;
    private static final double kMaxCentripetalAccelElevatorDown = 110.0;
    private static final double kMaxCentripetalAccel = 100.0;
    private static final double kMaxVoltage = 9.0;
    // private static final double kFirstPathMaxVoltage = 9.0;
    // private static final double kFirstPathMaxAccel = 400.0;
    // private static final double kFirstPathMaxVel = 46.6;

    // private static final double kSimpleSwitchMaxAccel = 100.0;
    // private static final double kSimpleSwitchMaxCentripetalAccel = 80.0;
    // private static final double kSimpleSwitchMaxVelocity = 25.0;

    private static TrajectoryGenerator mInstance = new TrajectoryGenerator();
    private final DriveMotionPlanner mMotionPlanner;
    private TrajectorySet mTrajectorySet = null;

    public static TrajectoryGenerator getInstance() {
        return mInstance;
    }

    private TrajectoryGenerator() {
        mMotionPlanner = new DriveMotionPlanner();
    }

    public void generateTrajectories() {
        if (mTrajectorySet == null) {
            System.out.println("Generating trajectories...");
            mTrajectorySet = new TrajectorySet();
            System.out.println("Finished trajectory generation");
        }
    }

    public TrajectorySet getTrajectorySet() {
        return mTrajectorySet;
    }

    public Trajectory<TimedState<Pose2dWithCurvature>> generateTrajectory(
            boolean reversed,
            final List<Pose2d> waypoints,
            final List<TimingConstraint<Pose2dWithCurvature>> constraints,
            double max_vel,  // inches/s
            double max_accel,  // inches/s^2
            double max_voltage) {
        return mMotionPlanner.generateTrajectory(reversed, waypoints, constraints, max_vel, max_accel, max_voltage);
    }

    public Trajectory<TimedState<Pose2dWithCurvature>> generateTrajectory(
            boolean reversed,
            final List<Pose2d> waypoints,
            final List<TimingConstraint<Pose2dWithCurvature>> constraints,
            double start_vel,  // inches/s
            double end_vel,  // inches/s
            double max_vel,  // inches/s
            double max_accel,  // inches/s^2
            double max_voltage) {
        return mMotionPlanner.generateTrajectory(reversed, waypoints, constraints, start_vel, end_vel, max_vel, max_accel, max_voltage);
    }

    // CRITICAL POSES
    // Origin is the center of the robot when the robot is placed against the middle of the alliance station wall.
    // +x is towards the center of the field.
    // +y is to the left.
    // ALL POSES DEFINED FOR THE CASE THAT ROBOT STARTS ON RIGHT! (mirrored about +x axis for LEFT)

    // shop
    public static final Pose2d kShop1 = new Pose2d(74,-36,Rotation2d.fromDegrees(180-45));
    public static final Pose2d kShop2 = new Pose2d(114,-126,Rotation2d.fromDegrees(180-22));
    public static final Pose2d kVexBox = new Pose2d(198,-150,Rotation2d.fromDegrees(180));

    public static final Pose2d kEndLine = new Pose2d(200,0,Rotation2d.fromDegrees(180));

    public static final Pose2d kMiddleWalkway = new Pose2d(79.5,11.0,Rotation2d.fromDegrees(180+45));
    public static final Pose2d kStraight  = new Pose2d( 60.0,0, Rotation2d.fromDegrees(180));
    public static final Pose2d kStairs = new Pose2d(176,36,Rotation2d.fromDegrees(180));
    public static final Pose2d kSideStartPose = new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(180.0));
   
    // STARTING IN CENTER
    public static final Pose2d kCenterStartPose = new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(180.0));
    public static final Pose2d kSimpleSwitchStartPose = new Pose2d(0.0, -2.0, Rotation2d.fromDegrees(180.0));
    public static final Pose2d kRightSwitchPose = new Pose2d(new Translation2d(100.0, -60.0), Rotation2d.fromDegrees(0.0 + 180.0));
    public static final Pose2d kLeftSwitchPose = new Pose2d(new Translation2d(100.0, 60.0), Rotation2d.fromDegrees(0.0 + 180.0));

    public class TrajectorySet {

        public class MirroredTrajectory {
            public MirroredTrajectory(Trajectory<TimedState<Pose2dWithCurvature>> right) {
                this.right = right;
                this.left = TrajectoryUtil.mirrorTimed(right);
            }

            public Trajectory<TimedState<Pose2dWithCurvature>> get(boolean left) {
                return left ? this.left : this.right;
            }

            public final Trajectory<TimedState<Pose2dWithCurvature>> left;
            public final Trajectory<TimedState<Pose2dWithCurvature>> right;
        }

        public final Trajectory<TimedState<Pose2dWithCurvature>> centerStartToStairs;
        public final Trajectory<TimedState<Pose2dWithCurvature>> centerStartToVex;

        private TrajectorySet() {
            centerStartToStairs = getCenterStartToStairs();
            centerStartToVex = getCenterStartToVex();
        }    

        private Trajectory<TimedState<Pose2dWithCurvature>> getCenterStartToStairs() {
            List<Pose2d> waypoints = new ArrayList<>();
            waypoints.add(kCenterStartPose);
            //waypoints.add(kMiddleWalkway);
            //waypoints.add(kStairs);
            waypoints.add(kEndLine);
            return generateTrajectory(true, waypoints, Arrays.asList(new CentripetalAccelerationConstraint(kMaxCentripetalAccel)),
                    kMaxVelocity, kMaxAccel, kMaxVoltage);
        }


        private Trajectory<TimedState<Pose2dWithCurvature>> getCenterStartToVex() {
            List<Pose2d> waypoints = new ArrayList<>();
            waypoints.add(kCenterStartPose);
            waypoints.add(kShop1);
            waypoints.add(kShop2);
            waypoints.add(kVexBox);
            return generateTrajectory(true, waypoints, Arrays.asList(new CentripetalAccelerationConstraint(kMaxCentripetalAccel)),
                    kMaxVelocity, kMaxAccel, kMaxVoltage);
        }
    }
}

