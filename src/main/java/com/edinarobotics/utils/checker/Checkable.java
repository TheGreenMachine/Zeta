package com.edinarobotics.utils.checker;

/**
 * An interface to represent any object that can be checked.
 * For example, a subsystem such as a drivetrain or an algorithm.
 */
public interface Checkable {
    /**
     * Checks this Checkable.
     * @return True if the check passed.
     * @throws CheckFailException If the check failed, a CheckFailException is
     * thrown with a reference to the source of the exception.
     */
    boolean check() throws CheckFailException;
}
