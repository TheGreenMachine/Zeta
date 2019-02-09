package frc.team1816.robot;

import com.edinarobotics.utils.gamepad.FilteredGamepad;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.gamepad.gamepadfilters.DeadzoneFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilterSet;
import com.edinarobotics.utils.gamepad.gamepadfilters.PowerFilter;
import frc.team1816.robot.commands.*;

import java.util.ArrayList;
import java.util.List;

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

        gamepadDriver.diamondUp().whenPressed(new ToggleReverseModeCommand());

        gamepadDriver.leftBumper().whenPressed(new SubsystemHatchFireCommand());
        gamepadDriver.leftBumper().whenReleased(new SubsystemHatchUnfireCommand());
        gamepadDriver.leftTrigger().whenPressed(new SetBeakCommand(true));
        gamepadDriver.leftTrigger().whenReleased(new SetBeakCommand(false));

        // gamepadDriver.rightTrigger().whenPressed(new ToggleCameraCommand());
        gamepadDriver.rightTrigger().whenPressed(new SetCargoCollectorIntakeCommand(1.0));
        gamepadDriver.rightTrigger().whenReleased(new SetCargoCollectorIntakeCommand(0));

        gamepadDriver.dPadUp().whenPressed(new SetClimberPowerCommand(1.0));
        gamepadDriver.dPadUp().whenReleased(new SetClimberPowerCommand(0));
        gamepadDriver.dPadDown().whenPressed(new SetClimberPowerCommand(-1.0));
        gamepadDriver.dPadDown().whenReleased(new SetClimberPowerCommand(0));


        gamepadOperator.diamondUp().whenPressed(new SubsystemHatchFireCommand());
        gamepadOperator.diamondUp().whenReleased(new SubsystemHatchUnfireCommand());
        gamepadOperator.diamondDown().whenPressed(new SetBeakCommand(true));
        gamepadOperator.diamondDown().whenReleased(new SetBeakCommand(false));

        gamepadOperator.diamondRight().whenPressed(new SubsystemCargoIntakeDownCommand());
        gamepadOperator.diamondLeft().whenPressed(new SubsystemCargoIntakeUpCommand());
        gamepadOperator.leftTrigger().whenPressed(new SetCargoCollectorArmCommand(true));

        gamepadOperator.dPadRight().whenPressed(new SetBeakCollectorArmCommand(false));
        gamepadOperator.dPadLeft().whenPressed(new SetBeakCollectorArmCommand(true));

        gamepadOperator.dPadUp().whenPressed(new SetCargoCollectorIntakeCommand(0.6));
        gamepadOperator.dPadUp().whenReleased(new SetCargoCollectorIntakeCommand(0));
        gamepadOperator.dPadDown().whenPressed(new SetCargoCollectorIntakeCommand(-0.6));
        gamepadOperator.dPadDown().whenReleased(new SetCargoCollectorIntakeCommand(0));

        gamepadOperator.rightTrigger().whenPressed(new SetCargoShooterIntakeCommand(1.0));
        gamepadOperator.rightTrigger().whenReleased(new SetCargoShooterIntakeCommand(0));
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

    public boolean getDriverClimbUp() {
        return gamepadDriver.dPadUp().get();
    }

    public boolean getDriverClimbDown() {
        return gamepadDriver.dPadDown().get();
    }

    public double getShooterArmThrottle() {
        return gamepadOperator.getRightY();
    }


    public boolean getOperatorLeftTrigger() {
        return gamepadOperator.leftTrigger().get();
    }

    public boolean getOperatorRightTrigger() {
        return gamepadOperator.rightTrigger().get();
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
