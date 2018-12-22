package com.edinarobotics.utils.gamepad.gamepadfilters;

import com.edinarobotics.utils.math.Math1816;
import java.lang.Math;

/**
 * This filter raises gamepad axis values to a given power while preserving
 * their sign. Regardless of the power, a negative value will remain negative
 * after being passed through this filter. Use this filter to produce a
 * "flatter" zone near the origin.
 */
public class PowerFilter extends SimpleGamepadJoystickFilter{
    private final int power;
    
    /**
     * Constructs a new PowerFilter that raises the gamepad axis values
     * to the given power.
     * @param power The power to which each gamepad axis value is to be raised.
     */
    public PowerFilter(int power){
        this.power = power;
    }

    /**
     * Internal method used to raise the value of each gamepad axis to the
     * given power while preserving their signs.
     * @param value The current value of the gamepad axis.
     * @return The new, filtered value of the gamepad axis.
     */
    protected double applyFilter(double value) {
        return Math1816.signum(value)*Math.abs(Math.pow(value, this.power));
    }
}
