package frc.team1816.robot;

import frc.team1816.robot.subsystems.Birdbeak;
import frc.team1816.robot.subsystems.CargoCollector;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.Climber;
import frc.team1816.robot.subsystems.Drivetrain;
import frc.team1816.robot.subsystems.LEDManager;

/**
 * Contains all subsystems of the robot.
 * Follows the singleton pattern.
 */
public class Components {
    private static Components instance;

    public Birdbeak birdbeak;
    public LEDManager ledManager;

    // TODO: CANifier ID
    public static final int CANIFIER_ID = 8;
    public Climber climber;
    public CargoCollector collector;
    public Drivetrain drivetrain;
    public CargoShooter shooter;

    private Components() {
        birdbeak = new Birdbeak();
        ledManager = new LEDManager(CANIFIER_ID);
        climber = new Climber();
        collector = new CargoCollector();
        drivetrain = new Drivetrain();
        shooter = new CargoShooter();
    }

    /**
     * Returns the singleton instance of Components.
     * Initializes it if there is no current instance.
     * @return The current singleton instance of Components.
     */
    public static Components getInstance() {
        if (instance == null) {
            instance = new Components();
        }
        return instance;
    }
}
