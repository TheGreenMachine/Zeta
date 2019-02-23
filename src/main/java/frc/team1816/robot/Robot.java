package frc.team1816.robot;

import badlog.lib.BadLog;
import com.edinarobotics.utils.checker.Checker;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1816.robot.commands.BlinkLedCommand;
import frc.team1816.robot.commands.DriveToHatchCommand;
import frc.team1816.robot.commands.GamepadClimbCommand;
import frc.team1816.robot.commands.GamepadDriveCommand;
import frc.team1816.robot.subsystems.*;
import frc.team1816.robot.subsystems.LedManager.RobotStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Robot extends TimedRobot {
    BadLog logger;

    public Birdbeak birdbeak;
    public Climber climber;
    public CargoCollector collector;
    public Drivetrain drivetrain;
    public LedManager leds;
    public CargoShooter shooter;

    public static final RobotFactory factory = new RobotFactory(
            System.getenv("ROBOT_NAME") != null ? System.getenv("ROBOT_NAME") : "zeta");

    public Robot() {
        // set the loop timeout in seconds
        super(.04); // TODO: change back to default
    }

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
        leds = Components.getInstance().ledManager;
        shooter = Components.getInstance().shooter;

        logger.finishInitialization();

        if (leds != null) {
            leds.setDefaultCommand(new BlinkLedCommand(2));
        }
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
        if (climber != null) {
            climber.setDefaultCommand(new GamepadClimbCommand());
        }
        if (drivetrain != null) {
            drivetrain.setDefaultCommand(new GamepadDriveCommand());
        }
    }

    @Override
    public void teleopInit() {
        if (climber != null) {
            climber.setDefaultCommand(new GamepadClimbCommand());
        }
        if (drivetrain != null) {
            drivetrain.setDefaultCommand(new DriveToHatchCommand(0.1));
        }
    }

    @Override
    public void testInit() {
        Checker.runTests(factory::isImplemented);
    }

    @Override
    public void disabledPeriodic() {
        if (shooter != null && leds != null) {
            if (shooter.getArmEncoderPosition() > CargoShooter.ARM_POSITION_MID) {
                leds.blinkStatus(RobotStatus.ERROR);
            } else {
                leds.blinkStatus(RobotStatus.DISABLED);
            }
        }
        periodic();
    }

    @Override
    public void autonomousPeriodic() {
        if (leds != null) {
            leds.indicateStatus(RobotStatus.ENABLED);
        }
        periodic();
    }

    @Override
    public void teleopPeriodic() {
        if (leds != null) {
            leds.indicateStatus(RobotStatus.ENABLED);
        }
        periodic();
    }

    @Override
    public void testPeriodic() {
        if (leds != null) {
            leds.indicateStatus(RobotStatus.ENABLED);
        }
        periodic();
    }

    private void periodic() {
        Scheduler.getInstance().run();

        if (!DriverStation.getInstance().isDisabled()) {
            logger.updateTopics();
            logger.log();
        }
    }

    private void initLog() {
        var timestamp = new SimpleDateFormat("DDD.HH.mm").format(new Date());
        String path = "/home/lvuser/";
        logger = BadLog.init(path + System.getenv("ROBOT_NAME") + "_" + timestamp + ".bag");

        BadLog.createValue("Match Type", DriverStation.getInstance().getMatchType().toString());
        BadLog.createValue("Match Number", "" + DriverStation.getInstance().getMatchNumber());
        BadLog.createTopic("Match Time", "s", DriverStation.getInstance()::getMatchTime);
    }
}
