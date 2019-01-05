package com.edinarobotics.utils.math;

/**
 * This class implements a coordinate point on a plane, defined by the x
 * and y unit distance from the origin.
 */
public class Point2 {
    private double x;
    private double y;
    
    /**
     * Creates a two-dimensional point based on the x and y values described.
     * @param x The x value of the point
     * @param y The y value of the point
     */
    public Point2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Returns the x coordinate of the point
     * @return The x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Returns an {@code int} hash code value for this Point2.
     * @return An {@code int} hash code value for this Point2.
     */
    public int hashCode() {
        //Auto-generated hashCode method.
        int hash = 7;
        hash = 19 * hash + (int) (Double.doubleToLongBits(getX()) ^ (Double.doubleToLongBits(getX()) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(getY()) ^ (Double.doubleToLongBits(getY()) >>> 32));
        return hash;
    }
    
    /**
     * Determines whether an Object is equal to this Point2.
     * 
     * An object is equal to this Point2 if it is also an instance of Point2
     * and if it contains equal components.
     * @param other The object to be tested for equality against this Point2.
     * @return {@code true} if the objects are equal as defined above
     * {@code false} otherwise.
     */
    public boolean equals(Object other){
        if(other instanceof Point2){
            Point2 otherPoint = (Point2)other;
            return otherPoint.getX() == getX() && otherPoint.getY() == getY();
        }
        return false;
    }
    
    /**
     * Returns a String representation of this Point2.
     * 
     * This representation is designed to be human-readable.
     * @return A human-readable String representation of this Point2.
     */
    public String toString(){
        return "<Point2: ("+getX()+", "+getY()+")>";
    }
    
    /**
     * Returns the y coordinate of the point
     * @return The y coordinate
     */
    public double getY() {
        return y;
    }
}
