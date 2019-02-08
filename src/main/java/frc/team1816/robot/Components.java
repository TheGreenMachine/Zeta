package frc.team1816.robot;

import com.edinarobotics.utils.hardware.RobotFactory;

import frc.team1816.robot.subsystems.Birdbeak;
import frc.team1816.robot.subsystems.CargoCollector;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.Climber;
import frc.team1816.robot.subsystems.Drivetrain;

/**
 * Contains all subsystems of the robot. Follows the singleton pattern.
 */
public class Components {
    private static Components instance;

    public Birdbeak birdbeak;
    public Climber climber;
    public CargoCollector collector;
    public Drivetrain drivetrain;
    public CargoShooter shooter;

    private Components() {
        RobotFactory factory = Robot.factory;

        if (factory.isImplemented(Birdbeak.NAME)) {
            birdbeak = new Birdbeak();
        }
        if (factory.isImplemented(Climber.NAME)) {
            climber = new Climber();
        }
        if (factory.isImplemented(CargoCollector.NAME)) {
            collector = new CargoCollector();
        }
        if (factory.isImplemented(Drivetrain.NAME)) {
            drivetrain = new Drivetrain();
        }
        if (factory.isImplemented(CargoShooter.NAME)) {
            shooter = new CargoShooter();
        }
    }

    /**
     * Returns the singleton instance of Components. Initializes it if there is no
     * current instance.
     * 
     * @return The current singleton instance of Components.
     */
    public static Components getInstance() {
        if (instance == null) {
            instance = new Components();
        }
        return instance;
    }
}
