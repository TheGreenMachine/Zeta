package com.edinarobotics.utils.log;

/**
 * Formatter implementations handle formatting log events into
 * Strings.
 */
public interface Formatter {
    
    /**
     * Formats the given LogEvent into a String.
     * @param event The LogEvent to be formatted.
     * @return A String representing a formatted version of the given
     * LogEvent.
     */
    public String format(LogEvent event);
}
