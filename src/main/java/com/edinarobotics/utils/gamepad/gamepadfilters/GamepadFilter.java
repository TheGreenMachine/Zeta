package com.edinarobotics.utils.gamepad.gamepadfilters;

import com.edinarobotics.utils.gamepad.GamepadAxisState;

/**
 * This interface defines a GamepadFilter which is allowed to modify the
 * values of the joystick axes of a given Gamepad.
 */
public interface GamepadFilter {
    
    /**
     * Filters the values of the gamepad's joystick axes.
     * @param toFilter The state of the gamepad's joystick axes to be filtered.
     * @return A new GamepadAxisState representing the filtered state of the
     * gamepad's joystick axes.
     */
    public GamepadAxisState filter(GamepadAxisState toFilter);
}
