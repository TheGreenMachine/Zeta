package com.edinarobotics.utils.gamepad;

import com.edinarobotics.utils.math.Vector2;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class implements a simple interface for interacting with a typical
 * joystick.
 * 
 * The buttons have been given names to avoid depending on their printed labels.
 */
public class Joystick {
    protected final edu.wpi.first.wpilibj.Joystick joystick;
    protected final int port;
    private Button trigger;
    
    /**
     * Constructs a new Joystick given its port number.
     * @param port The port number of the joystick to be read.
     */
    public Joystick(int port){
        this.port = port;
        this.joystick = new edu.wpi.first.wpilibj.Joystick(port);
        //Set up trigger
        trigger = new JoystickButton(joystick, 1);
    }
    
    /**
     * Returns the current value of the x-axis of the joystick. <br/>
     * A value of {@code -1} indicates that the joystick is fully left.<br/>
     * A value of {@code 1} indicates that the joystick is fully right.
     * @return The current value of the x-axis of the joystick.
     */
    public double getX(){
        return joystick.getRawAxis(1);
    }
    
    /**
     * Returns the current value of the y-axis of the joystick. <br/>
     * A value of {@code -1} indicates that the joystick is fully down.<br/>
     * A value of {@code 1} indicates that the joystick is fully up.
     * @return The current value of the y-axis of the joystick.
     */
    public double getY(){
        return -1.0*joystick.getRawAxis(2);
    }
    
    /**
     * Returns a new Vector2 representing the state of the joystick.
     * 
     * The Vector2 contains the state of the x- and y- axes of the joystick.
     * @return A Vector2 representing the state of the joystick.
     */
    public Vector2 getJoystick(){
        return new Vector2(getX(), getY());
    }
    
    /**
     * Returns the state of the joystick axes together in a JoystickAxisState.
     * @return A JoystickAxisState object containing the states of all the
     * axes on this Joystick.
     */
    public JoystickAxisState getJoystickAxisState(){
        return new JoystickAxisState(getJoystick());
    }
    
    /**
     * Returns a Button object representing the trigger of the joystick. <br/>
     * The trigger is the large button on the front of the joystick. It is
     * shaped like a trigger.
     * @return A Button object for the trigger.
     */
    public Button trigger(){
        return trigger;
    }
    
    /**
     * Returns a human-readable String form of this Joystick object.
     * @return A human-readable String representing this Joystick.
     */
    public String toString(){
        return "Joystick "+this.port;
    }
}
