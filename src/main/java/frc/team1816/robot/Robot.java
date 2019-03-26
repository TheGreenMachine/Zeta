package frc.team1816.robot;

import com.edinarobotics.utils.checker.Checker;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    private boolean autoInitialized;

    public static final RobotFactory factory = new RobotFactory(
            System.getenv("ROBOT_NAME") != null ? System.getenv("ROBOT_NAME") : "zeta");
    private SendableChooser<Boolean> drivetrainReverseChooser;

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

        drivetrainReverseChooser = new SendableChooser<>();
        drivetrainReverseChooser.addOption("Cargo Forward", false);
        drivetrainReverseChooser.setDefaultOption("Hatch Forward", true);
        SmartDashboard.putData("InitDriveDir", drivetrainReverseChooser);
        autoInitialized = false;

        // logThread.finishInitialization();
        // logThread.start();

        if (leds != null) {
            leds.setDefaultCommand(new BlinkLedCommand(0.5));
        }
    }

    @Override
    public void disabledInit() {
        leds.clearStatus(RobotStatus.ENABLED);
        autoInitialized = false;
    }

    @Override
    public void autonomousInit() {
        if (climber != null) {
            climber.setDefaultCommand(new GamepadClimbCommand());
        }
        if (drivetrain != null) {
            drivetrain.setDefaultCommand(new GamepadDriveCommand());
            drivetrain.setReverseMode(drivetrainReverseChooser.getSelected());
            // drivetrain.setSlowMode(true);
        }
        if (leds != null) {
            leds.indicateStatus(RobotStatus.ENABLED);
        }
        autoInitialized = true;
    }

    @Override
    public void teleopInit() {
        if (climber != null) {
            climber.setDefaultCommand(new GamepadClimbCommand());
        }
        if (drivetrain != null) {
            drivetrain.setDefaultCommand(new GamepadDriveCommand());
            drivetrain.setSlowMode(false);
            if (!autoInitialized) {
                drivetrain.setReverseMode(drivetrainReverseChooser.getSelected());
            }
        }
        if (leds != null) {
            leds.indicateStatus(RobotStatus.ENABLED);
        }
    }

    @Override
    public void testInit() {
        Checker.runTests(factory::isImplemented);
        if (leds != null) {
            leds.indicateStatus(RobotStatus.ENABLED);
        }
    }

    @Override
    public void disabledPeriodic() {
        if (shooter != null && leds != null) {
            // Check Shooter arm position
            if (shooter.getArmEncoderPosition() > CargoShooter.ARM_POSITION_MID + 100) {
                leds.indicateStatus(RobotStatus.ERROR);
            } else {
                leds.clearStatus(RobotStatus.ERROR);
            }
        }
        periodic();
    }

    @Override
    public void autonomousPeriodic() {
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
                leds.indicateStatus(RobotStatus.ENDGAME);
            }
        }
        periodic();
    }

    @Override
    public void testPeriodic() {
        periodic();
    }

    private void periodic() {
        Scheduler.getInstance().run();
    }
}
