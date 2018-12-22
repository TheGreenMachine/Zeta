package com.edinarobotics.utils.log;

/**
 * Filter implementations have the ability to determine whether or not a
 * give log event should be passed to a handler.
 */
public interface Filter {
    
    /**
     * Determines whether a loggable event meets this Filter instance's
     * criteria.
     * 
     * @param level The severity level of the log event.
     * @param message The message associated with the log event.
     * @param thrown The optional Throwable associated with the log event. Note
     * that this parameter may be {@code null}.
     * @return {@code true} if this message passes the filter,
     * {@code false} otherwise.
     */
    public boolean filter(Level level, String message, Throwable thrown);
}
