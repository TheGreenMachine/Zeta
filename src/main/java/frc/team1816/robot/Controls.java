package frc.team1816.robot;

/**
 * Contains all control inputs of the robot.
 * Follows the singleton pattern.
 */
public class Controls {
    private static Controls instance;

    private Controls() {

    }

    /**
     * Returns the current singleton instance of Controls.
     * It will initialize the singleton instance if there is none.
     * @return The current singleton instance of Controls.
     */
    public static Controls getInstance() {
        if (instance == null) {
            instance = new Controls();
        }
        return instance;
    }
}
