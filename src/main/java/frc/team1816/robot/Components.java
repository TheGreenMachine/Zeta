package frc.team1816.robot;

/**
 * Contains all subsystems of the robot.
 * Follows the singleton pattern.
 */
public class Components {
    private static Components instance;

    private Components() {
        
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
