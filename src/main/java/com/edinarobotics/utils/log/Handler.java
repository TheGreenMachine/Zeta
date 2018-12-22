package com.edinarobotics.utils.log;

/**
 * Handler implementations take actions based on logging events
 * sent through the logging system.
 * 
 * Handler objects are the final step in the logging system are the last
 * elements to receive a log event.
 */
public interface Handler {
    
    /**
     * Handles a LogEvent according to the rules of the specific Handler
     * implementation.
     * @param event The LogEvent object to be handled by this Handler
     * implementation.
     */
    public void handle(LogEvent event);
}
