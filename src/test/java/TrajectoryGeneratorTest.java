import com.team254.lib.geometry.Pose2d;
import com.team254.lib.geometry.Pose2dWithCurvature;
import com.team254.lib.geometry.Twist2d;
import com.team254.lib.trajectory.TimedView;
import com.team254.lib.trajectory.Trajectory;
import com.team254.lib.trajectory.TrajectoryIterator;
import com.team254.lib.trajectory.timing.TimedState;
import com.team254.lib.util.Util;
import frc.team1816.robot.paths.TrajectoryGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrajectoryGeneratorTest {

    public static final double kTestEpsilon = 1e-5;

    @Test
    public void test() {
        TrajectoryGenerator.getInstance().generateTrajectories();
        var ts = TrajectoryGenerator.getInstance().getTrajectorySet();
        System.out.println(ts.centerStartToStairs.toString());
        verifyTrajectory(ts.centerStartToStairs, true);
    }

    private void verifyTrajectory(Trajectory<TimedState<Pose2dWithCurvature>> trajectory, boolean shouldBeReversed) {

        var iterator = new TrajectoryIterator<>(new TimedView<>(trajectory));

        final double dt = 0.05; //changing dt causes test to fail
        TimedState<Pose2dWithCurvature> prev_left = null;
        while (!iterator.isDone()) {
            TimedState<Pose2dWithCurvature> left_state = iterator.getState();


            assertTrue((shouldBeReversed ? -1.0 : 1.0) * left_state.velocity() >= -kTestEpsilon);

            if (prev_left != null) {
                // Check there are no angle discontinuities.
                final double kMaxReasonableChangeInAngle = 0.3;  // rad
                Twist2d left_change = Pose2d.log(prev_left.state().getPose().inverse().transformBy(left_state.state()
                        .getPose()));

                assertTrue(Math.abs(left_change.dtheta) < kMaxReasonableChangeInAngle);

                if (!Util.epsilonEquals(left_change.dtheta, 0.0)) {
                    // Could be a curvature sign change between prev and now, so just check that either matches our
                    // expected sign.
                    final boolean left_curvature_positive = left_state.state().getCurvature() > kTestEpsilon ||
                            prev_left.state().getCurvature() > kTestEpsilon;
                    final boolean left_curvature_negative = left_state.state().getCurvature() < -kTestEpsilon ||
                            prev_left.state().getCurvature() < -kTestEpsilon;

                    final double actual_left_curvature = left_change.dtheta / left_change.dx;

                    if (actual_left_curvature < -kTestEpsilon) {
                        assertTrue(left_curvature_negative);
                    } else if (actual_left_curvature > kTestEpsilon) {
                        assertTrue(left_curvature_positive);
                    }

                }
            }

            iterator.advance(dt);
            prev_left = left_state;
        }

    }
}