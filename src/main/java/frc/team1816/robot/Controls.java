package frc.team1816.robot;

import java.util.ArrayList;
import java.util.List;

import com.edinarobotics.utils.gamepad.FilteredGamepad;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.gamepad.gamepadfilters.DeadzoneFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilterSet;
import com.edinarobotics.utils.gamepad.gamepadfilters.PowerFilter;
import frc.team1816.robot.commands.ToggleReverseModeCommand;
import frc.team1816.robot.commands.ToggleSlowModeCommand;

/**
 * Contains all control inputs of the robot.
 * Follows the singleton pattern.
 */
public class Controls {
    private static Controls instance;

    private Gamepad gamepadDriver;
    private Gamepad gamepadOperator;

    private Controls() {
        List<GamepadFilter> gamepadFilter = new ArrayList<>();
        gamepadFilter.add(new DeadzoneFilter(0.05));
        gamepadFilter.add(new PowerFilter(2));
        GamepadFilterSet filterSet = new GamepadFilterSet(gamepadFilter);

        gamepadDriver = new FilteredGamepad(0, filterSet);
        gamepadOperator = new FilteredGamepad(1, filterSet);

        gamepadDriver.leftBumper().whenPressed(new ToggleSlowModeCommand(true));
        gamepadDriver.leftBumper().whenReleased(new ToggleSlowModeCommand(false));

        gamepadDriver.rightBumper().whenPressed(new ToggleReverseModeCommand());
    }

    public double getDriveThrottle() {
        return gamepadDriver.getLeftY();
    }

    public double getDriveTurn() {
        return gamepadDriver.getRightX();
    }

    public double getClimbThrottle() {
        return gamepadOperator.getLeftY();
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
