package com.edinarobotics.utils.math;

/**
 * This class provides an implementation of Point2 that allows its components
 * to be modified.
 */
public class VariablePoint2 extends Point2 {
    private double x, y;
    
    /**
     * Constructs a new VariablePoint2 with its components initially set to
     * the given values.
     * @param x The initial value of x.
     * @param y The initial value of y.
     */
    public VariablePoint2(double x, double y){
        super(x, y);
        this.x = x;
        this.y = y;
    }
    
    /**
     * Constructs a new VariablePoint2 with both components defaulted to zero.
     */
    public VariablePoint2(){
        super(0, 0);
        x = 0;
        y = 0;
    }
    
    /**
     * Returns the x coordinate of the point
     * @return The x coordinate
     */
    public double getX(){
        return x;
    }
    
    /**
     * Returns the y coordinate of the point
     * @return The y coordinate
     */
    public double getY(){
        return y;
    }
    
    /**
     * Sets the x component of the point to the given value.
     * @param x The new value of the x component of this point.
     */
    public void setX(double x){
        this.x = x;
    }
    
    /**
     * Sets the y component of the point to the given value.
     * @param y The new value of the y component of this point.
     */
    public void setY(double y){
        this.y = y;
    }
    
    /**
     * Returns a String representation of this VariablePoint2.
     * 
     * This representation is designed to be human-readable.
     * @return A human-readable String representation of this VariablePoint2.
     */
    public String toString() {
        return "<VariablePoint2: ("+getX()+", "+getY()+")>";
    }
}
