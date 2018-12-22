package com.edinarobotics.utils.gamepad.buttons;

import com.edinarobotics.utils.gamepad.Gamepad;

import edu.wpi.first.wpilibj.buttons.Button;

public class DPadButton extends Button {

	private final Gamepad gamepad;
	private final DPadButtonType buttonType;

	public DPadButton(Gamepad gamepad, DPadButtonType buttonType) {
		this.gamepad = gamepad;
		this.buttonType = buttonType;
	}

	@Override
	public boolean get() {
		if (buttonType == DPadButtonType.UP) {
			return gamepad.getDPadY() == 1;
		} else if (buttonType == DPadButtonType.DOWN) {
			return gamepad.getDPadY() == -1;
		} else if (buttonType == DPadButtonType.LEFT) {
			return gamepad.getDPadX() == -1;
		} else if (buttonType == DPadButtonType.RIGHT) {
			return gamepad.getDPadX() == 1;
		}

		return false;
	}

	public enum DPadButtonType {
		UP((byte) 1, "up"), 
		DOWN((byte) 2, "down"), 
		LEFT((byte) 3, "left"), 
		RIGHT((byte) 4, "right");

		private final byte value;
		private final String name;

		DPadButtonType(byte value, String name) {
			this.value = value;
			this.name = name;
		}

		private byte getValue() {
			return value;
		}

		/**
		 * Gives the name of this DPadButtonType for debugging purposes.
		 * 
		 * @return The name of this DPadButtonType in lower case.
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
			return "D-Pad " + getName();
		}
	}

}
