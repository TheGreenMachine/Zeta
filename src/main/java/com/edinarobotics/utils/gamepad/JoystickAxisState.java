package com.edinarobotics.utils.gamepad;

import com.edinarobotics.utils.math.Vector2;
import java.lang.Math;

/**
 * This class represents the state of all axis on a Joystick.
 * These axes are: x- and y- axes of the main joystick, the twist axis
 * and the throttle lever.
 */
public class JoystickAxisState {
    private Vector2 joystick;
    private double throttle, twist;
    
    /**
     * Creates a new JoystickAxisState representing the given axis states.
     * @param joystick The current state of the main joystick as a Vector2.
     */
    public JoystickAxisState(Vector2 joystick){
        this.joystick = joystick;
    }
    
    /**
     * Creates a new JoystickAxisState representing the given axis states.
     * @param joystick The current state of the main joystick as a Vector2.
     * @param throttle The current state of the joystick's throttle.
     */
    public JoystickAxisState(Vector2 joystick, double throttle){
        this.joystick = joystick;
        this.throttle = throttle;
    }
    
    /**
     * Creates a new JoystickAxisState representing the given axis states.
     * @param joystick The current state of the main joystick as a Vector2.
     * @param twist The current state of the joystick's twist axis.
     * @param throttle The current state of the joystick's throttle.
     */
    public JoystickAxisState(Vector2 joystick, double twist, double throttle){
        this.joystick = joystick;
        this.twist = twist;
        this.throttle = throttle;
    }
    
    /**
     * Returns the Vector2 representing the state of the main joystick.
     * @return A Vector2 representing the state of the main joystick.
     */
    public Vector2 getJoystick(){
        return joystick;
    }
    
    /**
     * Returns the state of the x-axis of the main joystick.
     * @return The state of the x-axis of the main joystick.
     */
    public double getX(){
        return joystick.getX();
    }
    
    /**
     * Returns the state of the y-axis of the main joystick.
     * @return The state of the y-axis of the main joystick.
     */
    public double getY(){
        return joystick.getY();
    }
    
    /**
     * Returns the state of the throttle axis lever on the joystick.
     * @return The state of the throttle axis lever on the joystick.
     */
    public double getThrottle(){
        return throttle;
    }
    
    /**
     * Returns the state of the twist axis on the joystick.
     * @return The state of the twist axis on the joystick.
     */
    public double getTwist(){
        return twist;
    }
    
    /**
     * Returns the magnitude of the vector representing the main joystick.
     * @return The magnitude of the vector representing the main joystick.
     */
    public double getMagnitude(){
        return joystick.magnitude();
    }
    
    /**
     * Returns the current direction of the Vector2 representing the state of
     * the joystick in degrees (-180 to 180). This return value is suitable to
     * be passed into RobotDrive.
     * @return the current direction of the Vector2 representing the state of
     * the joystick in degrees (-180 to 180).
     */
    public double getDirection(){
        return Math.toDegrees(Math.atan2(getY(), getX()));
    }
}
