package com.edinarobotics.utils.gamepad.gamepadfilters;

import com.edinarobotics.utils.math.Math1816;

public class SquareFilter extends SimpleGamepadJoystickFilter {

    @Override
    protected double applyFilter(double value) {
        return Math1816.signum(value) * (value * value);
    }
}
