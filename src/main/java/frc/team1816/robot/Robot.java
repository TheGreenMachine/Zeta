package frc.team1816.robot;

import static frc.team1816.robot.Robot.RobotState.height;
import static frc.team1816.robot.Robot.RobotState.width;
import static frc.team1816.robot.Robot.RobotState.xCoord;

import com.edinarobotics.utils.checker.Checker;
import com.edinarobotics.utils.hardware.RobotFactory;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
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

    private NetworkTableInstance inst;
    private NetworkTable table;
    private NetworkTableEntry widthEntry;
    private NetworkTableEntry heightEntry;

    public static RobotState stateInstance = new RobotState();

    public static class RobotState {
        public static double width;
        public static double height;
        public static double xCoord;

        public double getVisionXCoord() {
            return xCoord;
        }

        public double getVisionWidth() {
            return width;
        }

        public double getVisionHeight() {
            return height;
        }
    }

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

        if (leds != null) {
            leds.setDefaultCommand(new BlinkLedCommand(0.5));
        }

        inst = NetworkTableInstance.getDefault();
        table = inst.getTable("SmartDashboard");
        widthEntry = table.getEntry("width");
        heightEntry = table.getEntry("height");

        width = widthEntry.getDouble(640);
        height = heightEntry.getDouble(480);

        table.addEntryListener("center_x", (table, key, entry, value, flags) -> {xCoord = value.getDouble();}, 
                EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }

    @Override
    public void disabledInit() {
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
            if (drivetrain.isReverseMode()) {
                leds.indicateStatus(RobotStatus.DRIVETRAIN_FLIPPED);
            } else {
                leds.indicateStatus(RobotStatus.ENABLED);
            }
        }
        periodic();
    }

    @Override
    public void teleopPeriodic() {
        if (drivetrain != null && leds != null && drivetrain.getCurrentCommandName().equals(GamepadDriveCommand.NAME)) {
            if (DriverStation.getInstance().getMatchTime() <= 45 && DriverStation.getInstance().getMatchTime() > 0) {
                leds.blinkStatus(RobotStatus.ENDGAME);
            } else if (drivetrain.isReverseMode()) {
                leds.indicateStatus(RobotStatus.DRIVETRAIN_FLIPPED);
            } else {
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
