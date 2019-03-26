package com.edinarobotics.utils.gamepad.gamepadfilters;

import com.edinarobotics.utils.gamepad.GamepadAxisState;
import com.edinarobotics.utils.math.Vector2;

/**
 * SimpleGamepadFilter is an abstract class used to quickly implement a gamepad
 * filter that applies a single function to each axis of the gamepad.
 * To implement a full GamepadFilter from SimpleGamepadFilter it is only
 * necessary to override {@link #applyFilter(double)}.
 */
public abstract class SimpleGamepadFilter implements GamepadFilter {
    private Vector2 filteredLeft;
    private Vector2 filteredRight;
    private GamepadAxisState filteredState;

    public SimpleGamepadFilter() {
        this.filteredLeft = new Vector2(0, 0);
        this.filteredRight = new Vector2(0, 0);
        this.filteredState = new GamepadAxisState(filteredLeft, filteredRight);
    }
    
    /**
     * The filter function of SimpleGamepadFilter applies
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
        filteredLeft.set(leftX, leftY);
        filteredRight.set(rightX, rightY);
        return filteredState;
    }
    
    /**
     * This function is called with the values of each gamepad axis in order
     * to produce the new filtered values. This function must be overridden
     * by SimpleGamepadFilter subclasses to produce a full filter.
     * @param value The value of the gamepad axis.
     * @return The filtered value of the gamepad axis.
     */
    protected abstract double applyFilter(double value);
}
