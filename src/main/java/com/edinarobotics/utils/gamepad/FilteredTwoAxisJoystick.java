package com.edinarobotics.utils.gamepad;

import com.edinarobotics.utils.gamepad.gamepadfilters.JoystickFilterSet;
import com.edinarobotics.utils.math.Vector2;

/**
 * Implements a Joystick that filters all of its axis values through a given
 * JoystickFilterSet.
 */
public class FilteredTwoAxisJoystick extends TwoAxisJoystick {
    private JoystickFilterSet filters;
    
    /**
     * Constructs a new FilteredTwoAxisJoystick that will send the axis values of
     * the joystick on the given port through the given JoystickFilterSet.
     * @param port The port of the joystick that is to be wrapped by this
     * FilteredJoystick.
     * @param filters The JoystickFilterSet through which all axis values are
     * to be sent.
     */
    public FilteredTwoAxisJoystick(int port, JoystickFilterSet filters){
        super(port);
        this.filters = filters;
    }
    
    /**
     * Returns the state of the joystick's axes together in a JoystickAxisState.
     * The values in this object have been filtered by the given GamepadFilterSet.
     * @return A GamepadAxisState object containing the states of all the
     * joystick axes on this Gamepad.
     */
    public JoystickAxisState getJoystickAxisState(){
        double x = super.getX();
        double y = super.getY();
        double throttle = super.getThrottle();
        Vector2 joystick = new Vector2(x, y);
        return filters.filter(new JoystickAxisState(joystick, throttle));
    }
    
    /**
     * Returns the filtered value of the joystick's x-axis.
     * @return The filtered value of the joystick's x-axis.
     */
    public double getX(){
        return getJoystickAxisState().getX();
    }
    
    /**
     * Returns the filtered value of the joystick's y-axis.
     * @return The filtered value of the joystick's y-axis.
     */
    public double getY(){
        return getJoystickAxisState().getY();
    }
    
    /**
     * Returns the filtered value of the joystick's throttle axis.
     * Some filters will not filter the value of the throttle.
     * @return The filtered value of the joystick's throttle axis.
     */
    public double getThrottle(){
        return getJoystickAxisState().getThrottle();
    }
    
    /**
     * Returns the filtered state of the axes of the main joystick as a Vector2.
     * @return The filtered state of the axes of the main joystick as a Vector2.
     */
    public Vector2 getJoystick(){
        return getJoystickAxisState().getJoystick();
    }
}
