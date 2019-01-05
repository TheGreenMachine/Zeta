package com.edinarobotics.utils.gamepad.gamepadfilters;

import com.edinarobotics.utils.gamepad.JoystickAxisState;
import com.edinarobotics.utils.math.Vector2;

/**
 * SimpleJoystickFilter is an abstract class used to quickly implement a
 * joystick filter that applies a single function to
 * the x-, y- and twist axes of the joystick.
 * To implement a full JoystickFilter from SimpleJoystickFilter it is only
 * necessary to override {@link #applyFilter(double)}.
 */
public abstract class SimpleJoystickFilter implements JoystickFilter {
    
    /**
     * The filter function of SimpleJoystickFilter applies
     * {@link #applyFilter(double)} to the values of the x- and y- axes
     * of the main joystick and the twist axis. This class does <em>not</em>
     * filter the throttle axis.
     * @param toFilter The JoystickAxisState to be filtered.
     * @return A new JoystickAxisState representing the filtered values of the
     * axes.
     */
    public JoystickAxisState filter(JoystickAxisState toFilter){
        double x = applyFilter(toFilter.getX());
        double y = applyFilter(toFilter.getY());
        double twist = applyFilter(toFilter.getTwist());
        double throttle = toFilter.getThrottle();
        Vector2 joystick = new Vector2(x, y);
        return new JoystickAxisState(joystick, twist, throttle);
    }
    
    /**
     * This function is called with the values of each joystick axis in order
     * to produce the new filtered values. This function must be overridden
     * by SimpleJoystickFilter subclasses to produce a full filter.
     * value The value of the joystick axis.
     * @return The filtered value of the joystick axis.
     */
    protected abstract double applyFilter(double toFilter);
}
