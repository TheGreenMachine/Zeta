package frc.team1816.robot;

import com.edinarobotics.utils.checker.Checker;
import com.edinarobotics.utils.hardware.RobotFactory;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.team1816.robot.commands.DriveToHatchCommand;
import frc.team1816.robot.commands.GamepadClimbCommand;
import frc.team1816.robot.commands.GamepadDriveCommand;
import frc.team1816.robot.subsystems.*;

public class Robot extends TimedRobot {

    public Birdbeak birdbeak;
    public Climber climber;
    public CargoCollector collector;
    public Drivetrain drivetrain;
    public CargoShooter shooter;

    public static final RobotFactory factory = new RobotFactory(
            System.getenv("ROBOT_NAME") != null ? System.getenv("ROBOT_NAME") : "zeta");

    public Robot() {
        super(.04); // set loop timeout (s)
    }
    
    @Override
    public void robotInit() {
        System.out.println("Initializing robot!");
        System.out.println(System.getenv("ROBOT_NAME"));

        LogThread logThread = new LogThread();
        logThread.initLog();

        Components.getInstance();
        Controls.getInstance();

        birdbeak = Components.getInstance().birdbeak;
        climber = Components.getInstance().climber;
        collector = Components.getInstance().collector;
        drivetrain = Components.getInstance().drivetrain;
        shooter = Components.getInstance().shooter;

        logThread.finishInitialization();
        logThread.start();
    }

    @Override
    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
        drivetrain.setDefaultCommand(new DriveToHatchCommand(0.5));
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
            // shooter.setDefaultCommand(new GamepadShootCommand());
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
        //   System.out.println("Gyro Angle" + drivetrain.getGyroAngle());
        Scheduler.getInstance().run();
    }
}
