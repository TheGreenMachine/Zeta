package frc.team1816.robot;

import com.edinarobotics.utils.checker.Checker;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1816.robot.commands.GamepadDriveCommand;

/**
 * The main robot class.
 *
 * <p>This class contains procedures for initialization as well as
 * methods to be called once and repeatedly for the autonomous,
 * teleop, and testing states of the robot.</p>
 */
public class Robot extends TimedRobot {

    public static final RobotFactory FACTORY = new RobotFactory(
            System.getenv("ROBOT_NAME") != null
                    ? System.getenv("ROBOT_NAME") : "zeta"
    );

    @Override
    public void robotInit() {
        System.out.println("Initializing robot!");
        System.out.println(System.getenv("ROBOT_NAME"));

        Components.getInstance();
        Controls.getInstance();
    }

    @Override
    public void disabledInit() { }

    @Override
    public void autonomousInit() { }

    @Override
    public void teleopInit() {
        Components.getInstance().drivetrain.setDefaultCommand(new GamepadDriveCommand());
    }

    @Override
    public void testInit() {
        Checker.runTests();
    }

    @Override
    public void disabledPeriodic() { }
    
    @Override
    public void autonomousPeriodic() { }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() { }
}
