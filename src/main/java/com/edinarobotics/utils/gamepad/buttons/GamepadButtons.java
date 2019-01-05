package com.edinarobotics.utils.gamepad.buttons;

public enum GamepadButtons {

	LEFT_BUMPER((byte) 0, "left bumper"), 
	RIGHT_BUMPER((byte) 1, "right bumper"), 
	LEFT_TRIGGER((byte) 2, "left trigger"), 
	RIGHT_TRIGGER((byte) 3, "right trigger"), 
	DIAMOND_DOWN((byte) 4, "diamond down"), 
	DIAMOND_UP((byte) 5, "diamond up"), 
	DIAMOND_LEFT((byte) 6, "diamond left"), 
	DIAMOND_RIGHT((byte) 7, "diamond right"), 
	MIDDLE_LEFT((byte) 8, "middle left"), 
	MIDDLE_RIGHT((byte) 9, "middle right"), 
	LEFT_JOYSTICK_BUTTON((byte) 10, "left joystick button"), 
	RIGHT_JOYSTICK_BUTTON((byte) 11, "right joystick button"), 
	DPAD_DOWN((byte) 12, "dpad down"), 
	DPAD_UP((byte) 13, "dpad up"), 
	DPAD_LEFT((byte) 14, "dpad left"), 
	DPAD_RIGHT((byte) 15, "dpad right");

	private final byte value;
	private final String name;

	/**
	 * Internal constructor used to create a GamepadButtons value.
	 * 
	 * @param value
	 *            The internal value used for equality comparisons.
	 * @param name
	 *            The name of this GamepadButtons value.
	 */
	GamepadButtons(byte value, String name) {
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
	 * Produces a human-readable representation of this GamepadButtons object.
	 * 
	 * @return A human-readable representation of this GamepadButtons object.
	 */
	public String toString() {
		return getName();
	}

}
