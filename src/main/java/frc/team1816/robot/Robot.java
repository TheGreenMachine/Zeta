package frc.team1816.robot;

import com.edinarobotics.utils.checker.Checker;
import com.edinarobotics.utils.hardware.RobotFactory;

import badlog.lib.BadLog;
import badlog.lib.DataInferMode;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1816.robot.commands.GamepadDriveCommand;
import frc.team1816.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {
    BadLog logger;
    private long startTime;

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

        startTime = System.currentTimeMillis();
        initLog();

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
    }

    @Override
    public void testPeriodic() {
        periodic();
    }

    private void periodic() {
        // System.out.println("Gyro Angle" + drivetrain.getGyroAngle());
        Scheduler.getInstance().run();

        logTime();
        logger.updateTopics();
        if (!DriverStation.getInstance().isDisabled()) {
            logger.log();
        }
    }

    private void logTime() {
        double currentTime = ((double) (System.currentTimeMillis() - startTime)) / 1_000d;
        BadLog.publish("Time", currentTime);
    }

    private void initLog() {
        logger = BadLog.init("Zeta.bag");
        BadLog.createValue("Match Type", DriverStation.getInstance().getMatchType().toString());
        BadLog.createValue("Match Number", "" + DriverStation.getInstance().getMatchNumber());
        BadLog.createTopic("Match Time", "s", () -> DriverStation.getInstance().getMatchTime());
        BadLog.createTopicSubscriber("Time", "s", DataInferMode.DEFAULT, "hide", "delta", "xaxis");
    }
}
