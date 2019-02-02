package frc.team1816.robot;

import com.edinarobotics.utils.checker.Checker;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1816.robot.commands.GamepadDriveCommand;
import frc.team1816.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {

    private Drivetrain drivetrain;

    /*
     * Constants are defined as static final deeply immutable types (e.g. String)
     * Since the factory is not deeply immutable, we use regular variable case.
     * See Google Java Style Guide.
     */
    public static final RobotFactory factory = new RobotFactory(
            System.getenv("ROBOT_NAME") != null
                    ? System.getenv("ROBOT_NAME") : "zeta"
    );

    @Override
    public void robotInit() {
        System.out.println("Initializing robot!");
        System.out.println(System.getenv("ROBOT_NAME"));

        Components.getInstance();
        Controls.getInstance();

        drivetrain = Components.getInstance().drivetrain;
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
        Checker.runTests(factory::isImplemented);
    }

    @Override
    public void disabledPeriodic() {
        periodic();
    }
    
    @Override
    public void autonomousPeriodic() {
        periodic();
    }

    @Override
    public void teleopPeriodic() {
        periodic();
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        periodic();
    }

    private void periodic() {
        // System.out.println("Gyro Angle" + drivetrain.getGyroAngle());
    }
}
