package com.edinarobotics.utils.gamepad.gamepadfilters;

/**
 * This filter applies a deadzone of a given radius to the joysticks of a gamepad.
 * All values whose absolute value is less than the deadzone value are converted
 * to be exactly zero.
 */
public class DeadzoneFilter extends SimpleGamepadJoystickFilter {
    private final double radius;
    
    /**
     * Constructs a new DeadzoneFilter that applies a deadzone of a given
     * radius.
     * @param radius The size of the deadzone to apply in this filter.
     */
    public DeadzoneFilter(double radius){
        this.radius = radius;
    }

    /**
     * Internal function that applies the deadzone to the gamepad axis value.
     * @param value The value to which the deadzone is to be applied.
     * @return The new deadzoned value.
     */
    protected double applyFilter(double value) {
        if(Math.abs(value) < radius){
            return 0.0;
        }
        return value;
    }
}
