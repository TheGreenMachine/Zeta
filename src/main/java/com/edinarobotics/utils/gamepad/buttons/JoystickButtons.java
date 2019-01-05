package com.edinarobotics.utils.gamepad.buttons;

public enum JoystickButtons {

	TRIGGER((byte) 0, "trigger"), 
	HAT_BUTTON_LEFT_TOP((byte) 1, "hat button left top"), 
	HAT_BUTTON_LEFT_BOTTOM((byte) 2, "hat button left bottom"), 
	HAT_BUTTON_RIGHT_TOP((byte) 3, "hat button right top"), 
	HAT_BUTTON_RIGHT_BOTTOM((byte) 4, "hat button right bottom"), 
	SHOULDER_BUTTON((byte) 5, "shoulder button"), 
	HAT_SWITCH_UP((byte) 6, "hat switch up"), 
	HAT_SWITCH_DOWN((byte) 7, "hat switch down"), 
	HAT_SWITCH_LEFT((byte) 8, "hat switch left"), 
	HAT_SWITCH_RIGHT((byte) 9, "hat switch right"), 
	OUTER_RING_TOP((byte) 10, "outer ring top"), 
	OUTER_RING_MIDDLE((byte) 11, "outer ring middle"), 
	OUTER_RING_BOTTOM((byte) 12, "outer ring bottom"), 
	INNER_RING_TOP((byte) 13, "inner ring top"), 
	INNER_RING_MIDDLE((byte) 14, "inner ring middle"), 
	INNER_RING_BOTTOM((byte) 15, "inner ring bottom");

	private final byte value;
	private final String name;

	/**
	 * Internal constructor used to create a JoystickButtons value.
	 * 
	 * @param value
	 *            The internal value used for equality comparisons.
	 * @param name
	 *            The name of this JoystickButtons value.
	 */
	JoystickButtons(byte value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * Returns the name of this button in lower case.
	 * 
	 * @return The name of this button in lower case.
	 */
	public String getName() {
		return name.toLowerCase();
	}

	/**
	 * Returns the internal byte value used for equality testing.
	 * 
	 * @return The byte value used for equality testing.
	 */
	private byte getValue() {
		return value;
	}

	/**
	 * Produces a human-readable representation of this JoystickButtons object.
	 * 
	 * @return A human-readable representation of this JoystickButtons object.
	 */
	public String toString() {
		return getName();
	}

}
