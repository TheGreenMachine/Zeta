package com.edinarobotics.utils.gamepad.buttons;

import com.edinarobotics.utils.gamepad.ThreeAxisJoystick;

import edu.wpi.first.wpilibj.buttons.Button;

public class HatSwitchButton extends Button {

	private final ThreeAxisJoystick joystick;
	private final HatSwitchButtonType buttonType;

	/**
	 * Constructs a new HatSwitchButton attached to the given ThreeAxisJoystick,
	 * monitoring the given direction on the hat switch.
	 * 
	 * @param joystick
	 *            The ThreeAxisJoystick object to which this button is bound.
	 * @param buttonType
	 *            The direction on the hat switch that will be monitored by this
	 *            HatSwitchButton.
	 */
	public HatSwitchButton(ThreeAxisJoystick joystick, HatSwitchButtonType buttonType) {
		this.joystick = joystick;
		this.buttonType = buttonType;
	}

	/**
	 * Returns the current state of this HatSwitchButton.
	 * 
	 * @return The current state of this HatSwitchButton.
	 */
	@Override
	public boolean get() {
		if (buttonType == HatSwitchButtonType.UP) {
			return joystick.getHatSwitchY() == 1;
		} else if (buttonType == HatSwitchButtonType.DOWN) {
			return joystick.getHatSwitchY() == -1;
		} else if (buttonType == HatSwitchButtonType.LEFT) {
			return joystick.getHatSwitchX() == -1;
		} else if (buttonType == HatSwitchButtonType.RIGHT) {
			return joystick.getHatSwitchX() == 1;
		}

		return false;
	}

	/**
	 * Indicates the type of this HatSwitchButton.
	 * 
	 * @return The direction on the hat switch that is monitored by this button.
	 */
	public HatSwitchButtonType getButtonType() {
		return buttonType;
	}

	public enum HatSwitchButtonType {
		UP((byte) 0, "up"), 
		DOWN((byte) 1, "down"), 
		LEFT((byte) 2, "left"), 
		RIGHT((byte) 3, "right");

		private final byte value;
		private final String name;

		HatSwitchButtonType(byte value, String name) {
			this.value = value;
			this.name = name;
		}

		private byte getValue() {
			return value;
		}

		/**
		 * Gives the name of this HatSwitchButtonType for debugging purposes.
		 * 
		 * @return The name of this HatSwitchButtonType in lower case.
		 */
		public String getName() {
			return name.toLowerCase();
		}

		/**
		 * Provides a human-readable String representation of this object.
		 * 
		 * @return A human-readable String representation of this object.
		 */
		public String toString() {
			return "Hat Switch " + getName();
		}
	}

}
