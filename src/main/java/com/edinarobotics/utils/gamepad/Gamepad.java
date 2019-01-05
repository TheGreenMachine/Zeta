package com.edinarobotics.utils.gamepad;

import com.edinarobotics.utils.gamepad.buttons.DPadButton;
import com.edinarobotics.utils.math.Vector2;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Joystick;


public class Gamepad {
	
	private Joystick joystick;
	private Button leftBumper, rightBumper;
	private Button leftTrigger, rightTrigger;
    private Button diamondLeft, diamondDown, diamondRight, diamondUp;
    private Button middleLeft, middleRight;
    private Button leftJoystickButton, rightJoystickButton;
    private Button dPadLeft, dPadDown, dPadRight, dPadUp;
	
	public Gamepad(int port) {
		joystick = new Joystick(port);

		leftBumper = new JoystickButton(joystick, 5);
		rightBumper = new JoystickButton(joystick, 6);
        leftTrigger = new JoystickButton(joystick, 7);
        rightTrigger = new JoystickButton(joystick, 8);
        diamondLeft = new JoystickButton(joystick, 1);
        diamondDown = new JoystickButton(joystick, 2);
        diamondRight = new JoystickButton(joystick, 3);
        diamondUp = new JoystickButton(joystick, 4);
        middleLeft = new JoystickButton(joystick, 9);
        middleRight = new JoystickButton(joystick, 10);
        leftJoystickButton = new JoystickButton(joystick, 11);
        rightJoystickButton = new JoystickButton(joystick, 12);
		
        dPadLeft = new DPadButton(this, DPadButton.DPadButtonType.LEFT);
        dPadRight = new DPadButton(this, DPadButton.DPadButtonType.RIGHT);
        dPadUp = new DPadButton(this, DPadButton.DPadButtonType.UP);
        dPadDown = new DPadButton(this, DPadButton.DPadButtonType.DOWN);

	} 
	
	/**
     * Returns a Button object representing the left bumper of the gamepad. <br/>
     * The bumper is the button that is on the front face of the gamepad
     * above the trigger.
     * @return A Button object for the left bumper.
     */
    public Button leftBumper(){
        return leftBumper;
    }
    
    /**
     * Returns a Button object representing the right bumper of the gamepad. <br/>
     * The bumper is the button that is on the front face of the gamepad
     * above the trigger.
     * @return A Button object for the right bumper.
     */
    public Button rightBumper(){
        return rightBumper;
    }
    
    /**
     * Returns a Button object representing the left trigger of the gamepad. <br/>
     * The trigger is the larger button on the bottom of the front face of
     * the gamepad.
     * @return A Button object for the left trigger.
     */
    public Button leftTrigger(){
        return leftTrigger;
    }
    
    /**
     * Returns a Button object representing the right trigger of the gamepad. <br/>
     * The trigger is the larger button on the bottom of the front face of
     * the gamepad.
     * @return A Button object for the right trigger.
     */
    public Button rightTrigger(){
        return rightTrigger;
    }
    
    /**
     * Returns a Button object for the left button in the diamond group of
     * buttons on the gamepad. <br/>
     * The diamond group of buttons is the set of four buttons on the right
     * side of the gamepad either labeled 1,2,3,4 or X,Y,B,A.
     * @return A Button object for the left diamond button.
     */
    public Button diamondLeft(){
        return diamondLeft;
    }
    
    /**
     * Returns a Button object for the down button in the diamond group of
     * buttons on the gamepad. <br/>
     * The diamond group of buttons is the set of four buttons on the right
     * side of the gamepad either labeled 1,2,3,4 or X,Y,B,A.
     * @return A Button object for the down diamond button.
     */
    public Button diamondDown(){
        return diamondDown;
    }
    
    /**
     * Returns a Button object for the right button in the diamond group of
     * buttons on the gamepad. <br/>
     * The diamond group of buttons is the set of four buttons on the right
     * side of the gamepad either labeled 1,2,3,4 or X,Y,B,A.
     * @return A Button object for the right diamond button.
     */
    public Button diamondRight(){
        return diamondRight;
    }
    
    /**
     * Returns a Button object for the up button in the diamond group of
     * buttons on the gamepad. <br/>
     * The diamond group of buttons is the set of four buttons on the right
     * side of the gamepad either labeled 1,2,3,4 or X,Y,B,A.
     * @return A Button object for the up diamond button.
     */
    public Button diamondUp(){
        return diamondUp;
    }
    
    /**
     * Returns a Button object for the left button in the center of the gamepad. <br/>
     * This button is often labeled "back."
     * @return A Button object for the left button in the center of the gamepad.
     */
    public Button middleLeft(){
        return middleLeft;
    }
    
    /**
     * Returns a Button object for the right button in the center of the gamepad. <br/>
     * This button is often labeled "start."
     * @return A Button object for the right button in the center of the gamepad.
     */
    public Button middleRight(){
        return middleRight;
    }
    
    /**
     * Returns a Button object representing a press of the left joystick. <br/>
     * This button is bound to the left joystick being pressed straight down.
     * @return A Button object representing a press of the left joystick.
     */
    public Button leftJoystickButton(){
        return leftJoystickButton;
    }
    
    /**
     * Returns a Button object representing a press of the right joystick. <br/>
     * This button is bound to the right joystick being pressed straight down.
     * @return A Button object representing a press of the right joystick.
     */
    public Button rightJoystickButton(){
        return rightJoystickButton;
    }
    
    /**
     * Returns a Button object representing the d-pad being pressed left.
     * @return A Button object representing the d-pad being pressed left.
     */
    public Button dPadLeft(){
        return dPadLeft;
    }
    
    /**
     * Returns a Button object representing the d-pad being pressed down.
     * @return A Button object representing the d-pad being pressed down.
     */
    public Button dPadDown(){
        return dPadDown;
    }
    
    /**
     * Returns a Button object representing the d-pad being pressed right.
     * @return A Button object representing the d-pad being pressed right.
     */
    public Button dPadRight(){
        return dPadRight;
    }
    
    /**
     * Returns a Button object representing the d-pad being pressed up.
     * @return A Button object representing the d-pad being pressed up.
     */
    public Button dPadUp(){
        return dPadUp;
    }
    
    /**
     * Returns the current value of the x-axis of the left joystick. <br/>
     * A value of {@code -1} indicates that the joystick is fully left.<br/>
     * A value of {@code 1} indicates that the joystick is fully right.
     * @return The current value of the x-axis of the left joystick.
     */
    public double getLeftX(){
        return joystick.getRawAxis(0);
    }
    
    /**
     * Returns the current value of the y-axis of the left joystick. <br/>
     * A value of {@code -1} indicates that the joystick is fully down.<br/>
     * A value of {@code 1} indicates that the joystick is fully up.
     * @return The current value of the y-axis of the left joystick.
     */
    public double getLeftY(){
        return -joystick.getRawAxis(1);
    }
    
    /**
     * Returns the current value of the x-axis of the right joystick. <br/>
     * A value of {@code -1} indicates that the joystick is fully left.<br/>
     * A value of {@code 1} indicates that the joystick is fully right.
     * @return The current value of the x-axis of the right joystick.
     */
    public double getRightX(){
        return joystick.getRawAxis(2);
    }
    
    /**
     * Returns the current value of the y-axis of the right joystick. <br/>
     * A value of {@code -1} indicates that the joystick is fully down.<br/>
     * A value of {@code 1} indicates that the joystick is fully up.
     * @return The current value of the y-axis of the right joystick.
     */
    public double getRightY(){
        return -joystick.getRawAxis(3);
    }
    
    /**
     * Returns the state of the left joystick as a Vector2.
     * This vector 2 contains the state of the x- and y- axis of the joystick.
     * @return A Vector2 representing the state of the left joystick.
     */
    public Vector2 getLeftJoystick(){
        return new Vector2(getLeftX(), getLeftY());
    }
    
    /**
     * Returns the state of the right joystick as a Vector2.
     * This vector 2 contains the state of the x- and y- axis of the joystick.
     * @return A Vector2 representing the state of the right joystick.
     */
    public Vector2 getRightJoystick(){
        return new Vector2(getRightX(), getRightY());
    }
    
    /**
     * Returns the state of the gamepad's joysticks together in a
     * GamepadAxisState.
     * @return A GamepadAxisState object containing the states of all the
     * joystick axes on this Gamepad.
     */
    public GamepadAxisState getGamepadAxisState(){
        Vector2 left = new Vector2(getLeftX(), getLeftY());
        Vector2 right = new Vector2(getRightX(), getRightY());
        return new GamepadAxisState(left, right);
    }

	public int getDPadY() {
		int povValue = joystick.getPOV();
		
		if (povValue == 0 || povValue == 45 || povValue == 315){
			return 1;
		} else if (povValue == 90 || povValue == 270 || povValue == -1){
			return 0;
		} else {
			return -1;
		}
	}
	
	public int getDPadX() {
		int povValue = joystick.getPOV();
		
		if (povValue == 0 || povValue == 180 || povValue == -1){
			return 0;
		} else if (povValue == 270 || povValue == 315 || povValue == 225){
			return -1;
		} else {
			return 1;
		}
	}

}
