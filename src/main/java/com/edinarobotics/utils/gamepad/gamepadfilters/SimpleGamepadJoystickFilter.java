package com.edinarobotics.utils.gamepad.gamepadfilters;

import com.edinarobotics.utils.gamepad.GamepadAxisState;
import com.edinarobotics.utils.gamepad.JoystickAxisState;
import com.edinarobotics.utils.math.Vector2;

/**
 * SimpleGamepadJoystickFilter makes it possible to quickly create filters
 * which act on both joysticks and gamepads.
 * The filters created by subclassing this class act on all axes of gamepads
 * and on the x-, y- and twist axes of joysticks.
 */
public abstract class SimpleGamepadJoystickFilter implements JoystickFilter, GamepadFilter {
    
    /**
     * The filter function of SimpleGamepadJoystickFilter for gamepads applies
     * {@link #applyFilter(double)} to the values of each axis of the gamepad.
     * @param toFilter The GamepadAxisState to be filtered.
     * @return A new GamepadAxisState representing the filtered values of the
     * axes.
     */
    public GamepadAxisState filter(GamepadAxisState toFilter){
        double leftX = applyFilter(toFilter.getLeftJoystick().getX());
        double leftY = applyFilter(toFilter.getLeftJoystick().getY());
        double rightX = applyFilter(toFilter.getRightJoystick().getX());
        double rightY = applyFilter(toFilter.getRightJoystick().getY());
        Vector2 left = new Vector2(leftX, leftY);
        Vector2 right = new Vector2(rightX, rightY);
        return new GamepadAxisState(left, right);
    }
    
    /**
     * The filter function of SimpleGamepadJoystickFilter for joysticks applies
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
     * This function is called with the values of each gamepad or joystick axis
     * in order to produce the new filtered values. This function must be
     * overridden by SimpleGamepadJoystickFilter subclasses to produce a full
     * filter.
     * @param value The value of the axis.
     * @return The filtered value of the axis.
     */
    protected abstract double applyFilter(double toFilter);
}
