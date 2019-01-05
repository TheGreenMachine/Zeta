package com.edinarobotics.utils.log;

/**
 * Represents available logging levels in the logging system.
 * 
 * The logging levels (in order from most severe to least severe) are:
 * {@link #FATAL}, {@link #SEVERE}, {@link #WARNING}, {@link #INFO},
 * {@link #DEBUG}, {@link #TRACE}.
 */
public final class Level {
    
    /**
     * Represents an unrecoverable error that will cause the robot program
     * (or a thread) to exit.
     */
    public static final Level FATAL = new Level((byte)5, "fatal");
    
    /**
     * Represents a severe error that may cause unintended operation. The error
     * is severe, but the program can continue.
     */
    public static final Level SEVERE = new Level((byte)4, "severe");
    
    /**
     * An error in the program that is recoverable. The program can continue.
     */
    public static final Level WARNING = new Level((byte)3, "warning");
    
    /**
     * A non-error informational message. Messages at this level should
     * be interpreted as normal.
     */
    public static final Level INFO = new Level((byte)2, "info");
    
    /**
     * A debugging message. This logging level should be used with less
     * frequency than {@link #TRACE}.
     */
    public static final Level DEBUG = new Level((byte)1, "debug");
    
    /**
     * A debugging message with greater detail than {@link #DEBUG}.
     */
    public static final Level TRACE = new Level((byte)0, "trace");

    private byte value;
    private String name;
    
    /**
     * Private constructor for the Level enum class.
     * 
     * The parameter indicates the ordering of this logging level
     * in severity. Higher values indicate a greater severity.
     * @param value The severity ordering of this Level.
     */
    private Level(byte value, String name){
        //Higher numbers are more severe
        this.value = value;
        this.name = name.toLowerCase();
    }
    
    /**
     * This method allows access to the private byte used to order this Level
     * by severity.
     * @return The value indicating the severity ordering of this Level.
     */
    private byte getValue(){
        return value;
    }
    
    /**
     * Returns a hash code value for this Level as required by
     * {@link Object#hashCode()}.
     * @return A hash code value for this Level.
     */
    public int hashCode(){
        return new Byte(getValue()).hashCode();
    }
    
    /**
     * Retrieves the human-readable name of this logging Level.
     * 
     * The name is returned as a lower-case String.
     * @return The String human-readable name of this logging Level.
     */
    public String getName(){
        return name;
    }
    
    /**
     * Tests another object against this one for equality.
     * 
     * An object is equal to this Level if it is also a Level object and 
     * if it has the same severity value.
     * @param other The object to be tested against this Level for equality.
     * @return {@code true} if the objects are equal as defined above,
     * {@code false} otherwise.
     */
    public boolean equals(Object other){
        if(other instanceof Level){
            return ((Level)other).getValue() == getValue();
        }
        return false;
    }
    
    /**
     * Compares this Level against another Level for severity ordering.
     * 
     * Returns a positive number if this Level is more severe than
     * {@code other}, a negative number if the Level is less severe than
     * {@code other}, or 0 if the levels are equal in severity.
     * @param other The other Level to be compared to this one for severity
     * ordering.
     * @return A positive number, a negative number or zero indicating
     * the ordering of this Level versus {@code other} by severity.
     */
    public byte compareTo(Level other){
        //Negative (less than) means this is less severe than other
        if(this.getValue() > other.getValue()){
            return 1;
        }
        else if(this.getValue() < other.getValue()){
            return -1;
        }
        return 0;
    }
    
    /**
     * Returns a String representation of this Level object.
     * 
     * The returned String is equivalent to the value returned by
     * the {@link #getName()} method.
     * @return A String representation of this Level object.
     */
    public String toString(){
        return getName();
    }
}
