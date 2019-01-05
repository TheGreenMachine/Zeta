package com.edinarobotics.utils.gamepad.buttons;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class implements a fake button that can internally forward method calls
 * to a real button.
 * 
 * This class does not inherit from Button to avoid registering it with the
 * scheduler and causing unnecessary overhead.
 */
public class ButtonShim {
	private final Button internalButton;
	private boolean active;

	/**
	 * Constructs a new active ButtonShim that uses the given internal button.
	 * 
	 * @param internalButton
	 *            The button to be used internally by the ButtonShim.
	 */
	public ButtonShim(Button internalButton) {
		this.internalButton = internalButton;
		active = true;
		if (internalButton == null) {
			active = false;
		}
	}

	/**
	 * Constructs a new inactive ButtonShim that discards all method calls.
	 */
	public ButtonShim() {
		this.internalButton = null;
		active = false;
	}

	/**
	 * Indicates whether this button shim is active. If this button shim is
	 * inactive, it will discard all method calls.
	 * 
	 * @return {@code true} if this button shim is active, {@code false}
	 *         otherwise.
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Returns the raw state of the internal button. If this button shim is
	 * inactive, returns {@code false}.
	 * 
	 * @return The raw state of the internal button used by this shim.
	 */
	public boolean get() {
		if (isActive()) {
			return internalButton.get();
		}
		return false;
	}

	/**
	 * Forwards this call to the button used internally by this button shim.
	 * 
	 * @param command
	 *            The command to be forwarded to the internal button of this
	 *            button shim.
	 * @see Button#cancelWhenPressed(edu.wpi.first.wpilibj.command.Command)
	 */
	public void cancelWhenPressed(Command command) {
		if (isActive()) {
			internalButton.cancelWhenPressed(command);
		}
	}

	/**
	 * Forwards this call to the button used internally by this button shim.
	 * 
	 * @param command
	 *            The command to be forwarded to the internal button of this
	 *            button shim.
	 * @see Button#toggleWhenPressed(edu.wpi.first.wpilibj.command.Command)
	 */
	public void toggleWhenPressed(Command command) {
		if (isActive()) {
			internalButton.toggleWhenPressed(command);
		}
	}

	/**
	 * Forwards this call to the button used internally by this button shim.
	 * 
	 * @param command
	 *            The command to be forwarded to the internal button of this
	 *            button shim.
	 * @see Button#whenPressed(edu.wpi.first.wpilibj.command.Command)
	 */
	public void whenPressed(Command command) {
		if (isActive()) {
			internalButton.whenPressed(command);
		}
	}

	/**
	 * Forwards this call to the button used internally by this button shim.
	 * 
	 * @param command
	 *            The command to be forwarded to the internal button of this
	 *            button shim.
	 * @see Button#whenReleased(edu.wpi.first.wpilibj.command.Command)
	 */
	public void whenReleased(Command command) {
		if (isActive()) {
			internalButton.whenReleased(command);
		}
	}

	/**
	 * Forwards this call to the button used internally by this button shim.
	 * 
	 * @param command
	 *            The command to be forwarded to the internal button of this
	 *            button shim.
	 * @see Button#whileHeld(edu.wpi.first.wpilibj.command.Command)
	 */
	public void whileHeld(Command command) {
		if (isActive()) {
			internalButton.whileHeld(command);
		}
	}
}
