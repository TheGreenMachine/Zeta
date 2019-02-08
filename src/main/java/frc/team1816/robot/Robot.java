package frc.team1816.robot;

import badlog.lib.BadLog;
import com.edinarobotics.utils.checker.Checker;
import com.edinarobotics.utils.hardware.RobotFactory;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1816.robot.commands.GamepadClimbCommand;
import frc.team1816.robot.commands.GamepadDriveCommand;
import frc.team1816.robot.commands.GamepadShootCommand;
import frc.team1816.robot.subsystems.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Robot extends TimedRobot {
    BadLog logger;

    public Birdbeak birdbeak;
    public Climber climber;
    public CargoCollector collector;
    public Drivetrain drivetrain;
    public CargoShooter shooter;

    public static final RobotFactory factory = new RobotFactory(
            System.getenv("ROBOT_NAME") != null ? System.getenv("ROBOT_NAME") : "zeta");

    @Override
    public void robotInit() {
        System.out.println("Initializing robot!");
        System.out.println(System.getenv("ROBOT_NAME"));

        initLog();

        Components.getInstance();
        Controls.getInstance();

        birdbeak = Components.getInstance().birdbeak;
        climber = Components.getInstance().climber;
        collector = Components.getInstance().collector;
        drivetrain = Components.getInstance().drivetrain;
        shooter = Components.getInstance().shooter;

        logger.finishInitialization();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void teleopInit() {
        if (climber != null) {
            climber.setDefaultCommand(new GamepadClimbCommand());
        }
        if (drivetrain != null) {
            drivetrain.setDefaultCommand(new GamepadDriveCommand());
        }
        if (shooter != null) {
            shooter.setDefaultCommand(new GamepadShootCommand());
        }
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

        logger.updateTopics();
        if (!DriverStation.getInstance().isDisabled()) {
            logger.log();
        }
    }

    private void initLog() {
        var timestamp = new SimpleDateFormat("DDD.HH.mm").format(new Date());
        String path = "/home/lvuser/";
        logger = BadLog.init(path + System.getenv("ROBOT_NAME") + "_" + timestamp + ".bag");

        BadLog.createValue("Match Type", DriverStation.getInstance().getMatchType().toString());
        BadLog.createValue("Match Number", "" + DriverStation.getInstance().getMatchNumber());
        BadLog.createTopic("Match Time", "s", () -> DriverStation.getInstance().getMatchTime());
    }
}
