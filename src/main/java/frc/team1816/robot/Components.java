package frc.team1816.robot;

import frc.team1816.robot.subsystems.Birdbeak;
import frc.team1816.robot.subsystems.Climber;
import frc.team1816.robot.subsystems.Drivetrain;

/**
 * Contains all subsystems of the robot.
 * Follows the singleton pattern.
 */
public class Components {
    private static Components instance;

    public Drivetrain drivetrain;
    public Climber climber;
    public Birdbeak birdbeak;

    // TODO: CANifier ID
    public static final int CANIFIER_ID = 8;

    private Components() {
        drivetrain = new Drivetrain();
        climber = new Climber();
        birdbeak = new Birdbeak();
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
