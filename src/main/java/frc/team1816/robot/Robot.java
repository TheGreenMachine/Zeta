package frc.team1816.robot;

import com.edinarobotics.utils.checker.Checker;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The main robot class.
 *
 * <p>This class contains procedures for initialization as well as
 * methods to be called once and repeatedly for the autonomous,
 * teleop, and testing states of the robot.</p>
 */
public class Robot extends TimedRobot {

    public static final RobotFactory FACTORY = new RobotFactory("zenith");

    @Override
    public void robotInit() { }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() { }

    @Override
    public void teleopInit() { }

    @Override
    public void testInit() {
        Checker.runTests();
    }

    @Override
    public void disabledPeriodic() { }
    
    @Override
    public void autonomousPeriodic() { }

    @Override
    public void teleopPeriodic() { }

    @Override
    public void testPeriodic() { }
}
