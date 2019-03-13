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
public abstract class SimpleGamepadJoystickFilter extends SimpleGamepadFilter implements JoystickFilter, GamepadFilter {
    private Vector2 filteredJoystick;
    private JoystickAxisState filteredJoystickState;

    public SimpleGamepadJoystickFilter() {
        super();
        this.filteredJoystick = new Vector2(0, 0);
        this.filteredJoystickState = new JoystickAxisState(filteredJoystick, 0, 0);
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
        filteredJoystick.set(x, y);
        filteredJoystickState.setTwist(twist);
        filteredJoystickState.setThrottle(throttle);
        return filteredJoystickState;
    }
}
