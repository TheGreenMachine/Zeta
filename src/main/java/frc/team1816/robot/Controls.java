package frc.team1816.robot;

import com.edinarobotics.utils.gamepad.FilteredGamepad;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.gamepad.gamepadfilters.DeadzoneFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilterSet;
import com.edinarobotics.utils.gamepad.gamepadfilters.SquareFilter;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.team1816.robot.commands.*;
import frc.team1816.robot.subsystems.Birdbeak;
import frc.team1816.robot.subsystems.CargoCollector;
import frc.team1816.robot.subsystems.CargoShooter;
import frc.team1816.robot.subsystems.Climber;

import java.util.ArrayList;
import java.util.List;

import static frc.team1816.robot.Robot.factory;

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
        gamepadFilter.add(new SquareFilter());
        GamepadFilterSet filterSet = new GamepadFilterSet(gamepadFilter);

        if (factory.getConstant("cheezyDrive") == 0) {
            gamepadDriver = new FilteredGamepad(0, filterSet);
        } else {
            System.out.println("*** Using CheezyDrive ***");
            gamepadDriver = new Gamepad(0);
        }
        gamepadOperator = new FilteredGamepad(1, filterSet);

        if (factory.isImplemented(Birdbeak.NAME)) {
            gamepadDriver.leftTrigger().whenPressed(new SetBeakCommand(true));
            gamepadDriver.leftTrigger().whenReleased(new SetBeakCommand(false));
            gamepadDriver.leftBumper().whenPressed(new SubsystemHatchFireCommand());
            gamepadDriver.leftBumper().whenReleased(new SubsystemHatchUnfireCommand());

            gamepadOperator.diamondUp().whenPressed(new SetBeakCommand(false));
            gamepadOperator.diamondDown().whenPressed(new SetBeakCommand(true));
        }

        if (factory.isImplemented(CargoCollector.NAME)) {
            gamepadOperator.leftBumper().whenPressed(new SubsystemCargoIntakeDownCommand());
            gamepadOperator.diamondLeft().whenPressed(new SubsystemCargoIntakeRocketCommand());
            gamepadOperator.diamondRight().whenPressed(new SubsystemCargoIntakeUpCommand());
        }

        if (factory.isImplemented(CargoShooter.NAME)) {
            gamepadDriver.rightTrigger().whenPressed(new SetCargoCollectorIntakeCommand(1.0));
            gamepadDriver.rightTrigger().whenReleased(new SetCargoCollectorIntakeCommand(0));

            gamepadOperator.rightTrigger().whenPressed(new SetCargoShooterIntakeCommand(-0.85));
            gamepadOperator.rightTrigger().whenReleased(new SetCargoShooterIntakeCommand(0.0));
            gamepadOperator.rightBumper().whenPressed(new SetCargoShooterIntakeCommand(1.0));
            gamepadOperator.rightBumper().whenReleased(new SetCargoShooterIntakeCommand(0.0));
        }

        if (factory.isImplemented(Climber.NAME)) {
            gamepadDriver.diamondRight().whenPressed(new ToggleClimberPistonCommand());
            gamepadDriver.middleLeft().whenPressed(new SetClimberPistonCommand(Value.kReverse));
            gamepadDriver.middleRight().whenPressed(new SetClimberPistonCommand(Value.kForward));

        }

        gamepadDriver.diamondUp().whenPressed(new ToggleReverseModeCommand());
        gamepadDriver.rightBumper().whenPressed(new SetSlowModeCommand(true));
        gamepadDriver.rightBumper().whenReleased(new SetSlowModeCommand(false));

        gamepadDriver.diamondDown().whileHeld(new DriveToHatchCommand(0.30));
        gamepadDriver.diamondLeft().whileHeld(new DriveToLoadHatchCommand(0.35));
    }

    /**
     * Returns the current singleton instance of Controls.
     * It will initialize the singleton instance if there is none.
     *
     * @return The current singleton instance of Controls.
     */
    public static Controls getInstance() {
        if (instance == null) {
            instance = new Controls();
        }
        return instance;
    }

    public double getDriveThrottle() {
        return gamepadDriver.getLeftY();
    }

    public double getDriveTurn() {
        return gamepadDriver.getRightX();
    }

    public boolean getQuickTurn() {
        return gamepadDriver.dPadLeft().get();
    }

    public double getClimbThrottle() {
        return gamepadOperator.getLeftY();
    }

    public double getShooterArmThrottle() {
        return gamepadOperator.getRightY();
    }

    public boolean getOperatorRightTrigger() {
        return gamepadOperator.rightTrigger().get();
    }
}
