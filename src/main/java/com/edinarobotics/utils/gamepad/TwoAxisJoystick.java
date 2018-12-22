package com.edinarobotics.utils.gamepad;

import com.edinarobotics.utils.math.Vector2;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class implements a simple interface for interacting with a 2-axis
 * joystick. This interface was designed with the Logitech Attack 3 in mind.
 * 
 * The buttons have been given names to avoid depending on their printed labels.
 */
public class TwoAxisJoystick extends Joystick {
    private Button hatButtonMiddle, hatButtonLeft, 
            hatButtonRight, hatButtonDown;
    private Button ringLeftUp, ringLeftDown, ringRightUp, ringRightDown;
    private Button auxLeft, auxRight;
    
    /**
     * Constructs a new Two-axis Joystick given its port number.
     * @param port The port number of the joystick to be read.
     */
    public TwoAxisJoystick(int port) {
        super(port);
        //Set up top buttons
        hatButtonMiddle = new JoystickButton(joystick, 3);
        hatButtonLeft = new JoystickButton(joystick, 4);
        hatButtonRight = new JoystickButton(joystick, 5);
        hatButtonDown = new JoystickButton(joystick, 2);
        //Set up ring buttons
        ringLeftUp = new JoystickButton(joystick, 8);
        ringLeftDown = new JoystickButton(joystick, 7);
        ringRightUp = new JoystickButton(joystick, 11);
        ringRightDown = new JoystickButton(joystick, 10);
        //Set up auxiliary buttons
        auxLeft = new JoystickButton(joystick, 8);
        auxRight = new JoystickButton(joystick, 9);
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
        return new JoystickAxisState(getJoystick(), getThrottle());
    }
    
    /**
     * Returns the current value of the throttle axis of the joystick. <br/>
     * A value of {@code 0} indicates that the throttle lever is fully down.
     * A value of {@code 1} indicates that the throttle lever is fully up.<br/>
     * @return The current value of the throttle axis of the joystick.
     */
    public double getThrottle(){
        return (-1.0*joystick.getRawAxis(3)+1.0)*0.5;
    }
    
    /**
     * Returns a Button object representing the left hat button of the
     * joystick. <br/>
     * The hat buttons are on the head of the joystick.
     * @return A Button object for the left hat button.
     */
    public Button hatButtonLeft(){
        return hatButtonLeft;
    }
    
    /**
     * Returns a Button object representing the right hat button of the
     * joystick. <br/>
     * The hat buttons are on the head of the joystick.
     * @return A Button object for the right hat button.
     */
    public Button hatButtonRight(){
        return hatButtonRight;
    }
    
    /**
     * Returns a Button object representing the middle hat button of the
     * joystick. <br/>
     * The hat buttons are on the head of the joystick.
     * @return A Button object for the middle hat button.
     */
    public Button hatButtonMiddle(){
        return hatButtonMiddle;
    }
    
    /**
     * Returns a Button object representing the down hat button of the
     * joystick. <br/>
     * The hat buttons are on the head of the joystick.
     * @return A Button object for the down hat button.
     */
    public Button hatButtonDown(){
        return hatButtonDown;
    }
    
    /**
     * Returns a Button object representing the upper button in the left ring
     * of buttons on the joystick.<br/>
     * The ring buttons are on the sides of the joystick.
     * @return A Button object for upper left ring button.
     */
    public Button leftRingUpper(){
        return ringLeftUp;
    }
    
    /**
     * Returns a Button object representing the lower button in the left ring
     * of buttons on the joystick.<br/>
     * The ring buttons are on the sides of the joystick.
     * @return A Button object for lower left ring button.
     */
    public Button leftRingLower(){
        return ringLeftDown;
    }
    
    /**
     * Returns a Button object representing the upper button in the right ring
     * of buttons on the joystick.<br/>
     * The ring buttons are on the sides of the joystick.
     * @return A Button object for upper right ring button.
     */
    public Button rightRingUpper(){
        return ringRightUp;
    }
    
    /**
     * Returns a Button object representing the lower button in the right ring
     * of buttons on the joystick.<br/>
     * The ring buttons are on the sides of the joystick.
     * @return A Button object for lower right ring button.
     */
    public Button rightRingLower(){
        return ringRightDown;
    }
    
    /**
     * Returns a Button object representing the left auxiliary button on the
     * joystick.<br/>
     * The auxiliary buttons are on the bottom of the joystick.
     * @return A Button object for left auxiliary button.
     */
    public Button auxLeft(){
        return auxLeft;
    }
    
    /**
     * Returns a Button object representing the right auxiliary button on the
     * joystick.<br/>
     * The auxiliary buttons are on the bottom of the joystick.
     * @return A Button object for right auxiliary button.
     */
    public Button auxRight(){
        return auxRight;
    }
}
