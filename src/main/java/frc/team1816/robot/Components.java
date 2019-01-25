package frc.team1816.robot;

import frc.team1816.robot.subsystems.Drivetrain;

/**
 * Contains all subsystems of the robot.
 * Follows the singleton pattern.
 */
public class Components {
    private static Components instance;

    public Drivetrain drivetrain;

    //TODO: Talon IDs
    private static final int LEFT_MAIN = 1;
    private static final int LEFT_SLAVE_ONE = 2;
    private static final int LEFT_SLAVE_TWO = 3;
    private static final int RIGHT_MAIN = 4;
    private static final int RIGHT_SLAVE_ONE = 5;
    private static final int RIGHT_SLAVE_TWO = 6;

    //TODO: Pigeon IMU ID
    public static final int GYRO_ID = 7;

    private Components() {
        drivetrain = new Drivetrain(GYRO_ID, LEFT_MAIN, LEFT_SLAVE_ONE, LEFT_SLAVE_TWO,
            RIGHT_MAIN, RIGHT_SLAVE_ONE, RIGHT_SLAVE_TWO);
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
