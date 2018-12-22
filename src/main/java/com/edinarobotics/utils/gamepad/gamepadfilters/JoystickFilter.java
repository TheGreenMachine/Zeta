package com.edinarobotics.utils.gamepad.gamepadfilters;

import com.edinarobotics.utils.gamepad.JoystickAxisState;

/**
 * This interface defines a JoystickFilter which is allowed to modify the
 * values of the axes of a given Joystick.
 */
public interface JoystickFilter {
    
    /**
     * Filters the values of the joystick's axes.
     * @param toFilter The state of the joystick's axes to be filtered.
     * @return A new GamepadAxisState representing the filtered state of the
     * joystick's axes.
     */
    public JoystickAxisState filter(JoystickAxisState toFilter);
}
