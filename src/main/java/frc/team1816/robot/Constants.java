package frc.team1816.robot;

/**
 * A list of constants used by the rest of the robot code. This include physics constants as well as constants
 * determined through calibrations.
 */
public class Constants {
    public static final double kLooperDt = 0.01;

    /* ROBOT PHYSICAL CONSTANTS */

    // Wheels
    public static final double kDriveWheelTrackWidthInches = 21.75;
    public static final double kDriveWheelDiameterInches = 7.25;
    public static final double kDriveWheelRadiusInches = kDriveWheelDiameterInches / 2.0;
    public static final double kTrackScrubFactor = 1.0;  // Tune me!

    // Tuned dynamics
    public static final double kRobotLinearInertia = 60.0;  // kg TODO tune
    public static final double kRobotAngularInertia = 10.0;  // kg m^2 TODO tune
    public static final double kRobotAngularDrag = 12.0;  // N*m / (rad/sec) TODO tune
    public static final double kDriveVIntercept = 1.055;  // V
    public static final double kDriveKv = 0.135;  // V per rad/s
    public static final double kDriveKa = 0.012;  // V per rad/s^2

    /* CONTROL LOOP GAINS */

    public static final double kPathKX = 4.0;  // units/s per unit of error
    public static final double kPathLookaheadTime = 0.4;  // seconds to look ahead along the path for steering
    public static final double kPathMinLookaheadDistance = 24.0;  // inches

    // PID gains for drive velocity loop (LOW GEAR)
    // Units: setpoint, error, and output are in ticks per second.
    public static final double kDriveLowGearVelocityKp = 2;
    public static final double kDriveLowGearVelocityKi = .001;
    public static final double kDriveLowGearVelocityKd = 10;
    public static final double kDriveLowGearVelocityKf = 1.85;
    public static final int kDriveLowGearVelocityIZone = 10;
    public static final double kDriveVoltageRampRate = 0.0;


    // // Do not change anything after this line unless you rewire the robot and
    // // update the spreadsheet!
    // // Port assignments should match up with the spreadsheet here:
    // // https://docs.google.com/spreadsheets/d/179YszqnEWPWInuHUrYJnYL48LUL7LUhZrnvmNu1kujE/edit#gid=0

    // /* I/O */
    // // (Note that if multiple talons are dedicated to a mechanism, any sensors
    // // are attached to the master)

    // public static final int kCANTimeoutMs = 10; //use for on the fly updates
    // public static final int kLongCANTimeoutMs = 100; //use for constructors

    // // Drive
    // public static final int kLeftDriveMasterId = 1;
    // public static final int kLeftDriveSlaveAId = -1;
    // public static final int kLeftDriveSlaveBId = -1;
    // public static final int kRightDriveMasterId = 2;
    // public static final int kRightDriveSlaveAId = -1;
    // public static final int kRightDriveSlaveBId = -1;

    // // Intake
    // public static final int kCanifierId = 12;
    // public static final int kPigeon = 13;


    // // Control Board
    // static final boolean kUseGamepadForDriving = false;
    // static final boolean kUseGamepadForButtons = true;

    // public static final int kDriveGamepadPort = 0;
    // public static final int kButtonGamepadPort = 2;

}

