package frc.team1816.robot;

import frc.team1816.robot.subsystems.Drivetrain;

/**
 * Contains all subsystems of the robot.
 * Follows the singleton pattern.
 */
public class Components {
    private static Components instance;

    public Drivetrain drivetrain;

    private Components() {
        drivetrain = new Drivetrain();
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
