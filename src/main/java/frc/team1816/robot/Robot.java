package frc.team1816.robot;

import com.edinarobotics.utils.checker.Checker;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1816.robot.commands.BlinkLedCommand;
import frc.team1816.robot.commands.GamepadClimbCommand;
import frc.team1816.robot.commands.GamepadDriveCommand;
import frc.team1816.robot.subsystems.*;
import frc.team1816.robot.subsystems.LedManager.RobotStatus;

public class Robot extends TimedRobot {

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
        super(.02);
    }

    @Override
    public void robotInit() {
        System.out.print("Initializing robot! ");
        System.out.println(0f);
        System.out.println(System.getenv("ROBOT_NAME"));

        // LogThread logThread = new LogThread();
        // logThread.initLog();
        NetworkTableInstance.getDefault(); // First initializing Network tables

        Components.getInstance();
        Controls.getInstance();

        birdbeak = Components.getInstance().birdbeak;
        climber = Components.getInstance().climber;
        collector = Components.getInstance().collector;
        drivetrain = Components.getInstance().drivetrain;
        leds = Components.getInstance().ledManager;
        shooter = Components.getInstance().shooter;

        // logThread.finishInitialization();
        // logThread.start();

        if (leds != null) {
            leds.setDefaultCommand(new BlinkLedCommand(0.5));
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
            drivetrain.setSlowMode(true);
        }
    }

    @Override
    public void teleopInit() {
        if (climber != null) {
            climber.setDefaultCommand(new GamepadClimbCommand());
        }
        if (drivetrain != null) {
            drivetrain.setDefaultCommand(new GamepadDriveCommand());
            drivetrain.setSlowMode(false);
        }
    }

    @Override
    public void testInit() {
        Checker.runTests(factory::isImplemented);
    }

    @Override
    public void disabledPeriodic() {
        if (shooter != null && leds != null) {
            // Check Shooter arm position
            if (shooter.getArmEncoderPosition() > CargoShooter.ARM_POSITION_MID + 100) {
                leds.blinkStatus(RobotStatus.ERROR);
            } else {
                leds.indicateStatus(RobotStatus.DISABLED);
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
        if (
                drivetrain != null
                        && leds != null
                        && drivetrain.getCurrentCommandName().equals(GamepadDriveCommand.NAME)
        ) {
            if (DriverStation.getInstance().getMatchTime() <= 45
                    && DriverStation.getInstance().getMatchTime() > 0) {
                leds.blinkStatus(RobotStatus.ENDGAME);
            } else if (leds != null) {
                leds.indicateStatus(RobotStatus.ENABLED);
            }
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
    }
}
