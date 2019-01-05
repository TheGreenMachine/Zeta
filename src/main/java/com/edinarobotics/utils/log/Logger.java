package com.edinarobotics.utils.log;

import java.util.Hashtable;

/**
 * The Logger class implements the client's handle to the logging system.
 * 
 * Users of the logging system can use the Logger to submit events and to
 * process events as they travel up the logger hierarchy.
 */
public final class Logger {
    private String name;
    private Logger parent;
    private Hashtable<String, Logger> children;
    private Filter filter;
    private Handler handler;
    private static boolean enabled = true;
    
    /**
     * Constructs a new Logger with the given name and the given parent.
     * @param name The name of this logger.
     * @param parent The parent Logger of this Logger.
     */
    protected Logger(String name, Logger parent){
        this.name = name;
        this.parent = parent;
        this.children = new Hashtable<String, Logger>();
    }
    
    /**
     * Returns the parent Logger of this logger, it it exists.
     * 
     * The root Logger has no parent and will return {@code null}.
     * @return The parent Logger of the Logger in the hierarchy.
     */
    public Logger getParent(){
        return parent;
    }
    
    /**
     * Returns a child Logger of this Logger by name.
     * 
     * If the specified child logger does not exist, it will be created.
     * The name given to this method is <em>not</em> fully-qualified
     * it represents the name of the child logger only and may not contain
     * periods or spaces.
     * @param name The name of the child Logger to return.
     * @return The named child Logger of this Logger.
     */
    public synchronized Logger getChild(String name){
        if(name.indexOf(".") >= 0){
            //Disallow "." in logger names.
            throw new IllegalArgumentException("Given name of child logger must not contain \".\" (received: "+name+")");
        }
        if(name.indexOf(" ") >= 0){
            //Disallow " " in logger names.
            throw new IllegalArgumentException("Given name of child logger must not contain \" \" (received: "+name+" )");
        }
        if(!children.containsKey(name)){
            Logger newLogger = new Logger(name, this);
            children.put(name, newLogger);
            return newLogger;
        }
        return (Logger)children.get(name);
    }
    
    /**
     * Sets this Logger to use the specified Filter to filter log events.
     * @param newFilter The new Filter to be used to filter log events.
     */
    public void setFilter(Filter newFilter){
        this.filter = newFilter;
    }
    
    /**
     * Sets this Logger to use the specified Handler to handle log events.
     * @param newHandler The new Handler to be used to handle log events.
     */
    public void setHandler(Handler newHandler){
        this.handler = newHandler;
    }
    
    /**
     * Returns the name of this Logger.
     * 
     * This name is <em>not</em> fully-qualified.
     * @return The name of this Logger.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Returns the fully-qualified name of this Logger.
     * 
     * This name includes the names of this Logger's parent loggers
     * <em>and</em> the name of the implicit root logger.
     * @return The fully-qualified name of this Logger, including
     * the root Logger.
     */
    public String getFullName(){
        if(getParent() == null){
            return getName();
        }
        return getParent().getFullName()+"."+getName();
    }
    
    /**
     * Returns the fully-qualified name of this Logger, omitting the
     * implicit root Logger.
     * 
     * This name includes the names of this Logger's parent loggers, but omits
     * the root Logger at the top of the hierarchy.
     * @return The fully-qualified name of the Logger without the root Logger.
     */
    public String getFullNameWithoutRoot(){
        if(getParent() == null){
            //We are the root Logger
            return "";
        }
        String parentName = getParent().getFullNameWithoutRoot();
        if(parentName.equals("")){
            return getName();
        }
        return parentName+"."+getName();
    }
    
    /**
     * Internal method that determines whether this Logger should handle
     * a given LogEvent.
     * @param event The LogEvent to test.
     * @return {@code true} if this Logger should handle the event,
     * {@code false} otherwise.
     */
    private boolean shouldHandle(LogEvent event){
        return shouldHandle(event.getLevel(), event.getMessage(), event.getThrowable());
    }
    
    /**
     * Internal method that determines whether this Logger should handle
     * a given log event.
     * @param level The severity level associated with the event.
     * @param message The String message associated with the event.
     * @param thrown The optional Throwable associated with the event.
     * @return {@code true} if this Logger should handle the event,
     * {@code false} otherwise.
     */
    private boolean shouldHandle(Level level, String message, Throwable thrown){
        if(!enabled) {
            return false;
        }
        if(filter == null){
            return true;
        }
        return filter.filter(level, message, thrown);
    }
    
    /**
     * Internal method that handles the event by passing it to this Logger's
     * Handler, if one is defined.
     * @param event The event to be handled.
     */
    private void handle(LogEvent event){
        if(handler == null){
            return;
        }
        handler.handle(event);
    }
    
    /**
     * Submits an event to the logging system.
     * 
     * This event will be logged as dispatched from this Logger.
     * This event will propagate up the Logger hierarchy through each logger
     * that approves the event.
     * @param level The severity level of the submitted event.
     * @param message The message associated with the submitted event.
     */
    public void log(Level level, String message){
        log(level, message, null);
    }
    
    /**
     * Submits an event to the logging system.
     * 
     * This event will be logged as dispatched from this Logger.
     * This event will propagate up the Logger hierarchy through each logger
     * @param level The severity level of the submitted event.
     * @param message The message associated with the submitted event.
     * @param thrown The optional Throwable associated with this event.
     */
    public void log(Level level, String message, Throwable thrown){
        if(!shouldHandle(level, message, thrown)){
            return;
        }
        LogEvent event = new LogEvent(level, message, thrown, this);
        log(event);
    }
    
    /**
     * Internal method used to propagate log events up the Logger hierarchy.
     * 
     * This method handles the log event and passes it up the hierarchy
     * if this Logger approves the event.
     * @param event The LogEvent to be logged.
     */
    protected void log(LogEvent event){
        if(!shouldHandle(event)){
            return;
        }
        handle(event);
        if(getParent() != null){
            getParent().log(event);
        }
    }
}
