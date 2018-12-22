package com.edinarobotics.utils.math;

import java.lang.Math;

/**
 * This class implements a 2-dimensional vector and associated mathematical
 * operations.
 */
public class Vector2 extends Point2 {
    
    /**
     * Constructs a new Vector2 with the specified x and y components.
     * @param x The x component of the new vector.
     * @param y The y component of the new vector.
     */
    public Vector2(double x, double y){
        super(x, y);
    }
    
    /**
     * Returns a String representation of this Vector2.
     * 
     * This representation is designed to be human-readable.
     * @return A human-readable String representation of this Vector2.
     */
    public String toString(){
        return "<Vector2: ("+getX()+", "+getY()+")>";
    }
    
    /**
     * Computes the element-wise dot product of this Vector2 and another
     * Vector2.
     * @param other The other Vector2 which is to be dotted with this Vector2.
     * @return A double that is the equal to the dot product of this
     * Vector2 the specified other Vector2.
     */
    public double dot(Vector2 other){
        double n_x = getX() * other.getX();
        double n_y = getY() * other.getY();
        return n_x + n_y;
    }
    
    /**
     * Computes the element-wise sum of this Vector2 and another Vector2. The
     * result is a new Vector2.
     * @param other The other Vector2 which is to be summed with this Vector2.
     * @return A new Vector2 that is equal to the element-wise sum of this
     * Vector2 and the specified Vector2.
     */
    public Vector2 add(Vector2 other){
        double n_x = getX() + other.getX();
        double n_y = getY() + other.getY();
        return new Vector2(n_x, n_y);
    }
    
    /**
     * Computes the scalar multiple of this Vector2. The distance of the new
     * Vector2 will be equal to the distance of the original Vector2 times the
     * given scalar.
     * @param scalar The scalar value by which this Vector2 is to be multiplied.
     * @return A new Vector2 which is the result of multiplying this Vector2
     * by the given scalar.
     */
    public Vector2 scalarMult(double scalar){
        double n_x = scalar*getX();
        double n_y = scalar*getY();
        return new Vector2(n_x, n_y);
    }
    
    /**
     * Computes the unit normalized vector for this Vector2.
     * The result is a Vector2 that points in the same direction as this
     * Vector2, but whose euclidian magnitude is {@code 1.0}.
     * @return The unit normalized Vector2 pointing in the same direction
     * as this Vector2.
     */
    public Vector2 unitNormalize(){
        return this.scalarMult(1.0/this.magnitude());
    }
    
    /**
     * Rotates this Vector2 counterclockwise through a radian angle theta.
     * @param theta The angle by which to rotate this Vector2 <i>in radians</i>.
     * @return A new Vector2 that is the result of rotating this Vector2 through
     * the angle theta.
     */
    public Vector2 rotate(double theta){
        double n_x = getX() * Math.cos(theta) - getY() * Math.sin(theta);
        double n_y = getX() * Math.sin(theta) + getY() * Math.cos(theta);
        return new Vector2(n_x, n_y);
    }
    
    /**
     * Computes the radian angle between this Vector2 and another Vector2.
     * This angle is computed using the dot product. The result is in the range
     * {@code 0.0} and pi, inclusive.
     * @param other The Vector2 whose angle with this Vector2 is to be computed.
     * @return The angle between this Vector2 and the other Vector2 in radians.
     */
    public double angle(Vector2 other){
        if(this.magnitude() == 0.0 || other.magnitude() == 0.0){
            return 0.0;
        }
        double dot = this.dot(other);
        double cosVal = dot/this.magnitude()/other.magnitude();
        return Math.acos(cosVal);
    }
    
    /**
     * Produces the two possible normal (perpendicular) vectors to this Vector2.
     * The two normal vectors both have unit length (magnitude of {@code 1.0})
     * and are stored in a 2-element array. The first Vector2 in this array
     * is the vectors which has the maximum y-component (i.e. points "most
     * upward").
     * @return A 2-element array containing the 2 possible unit Vector2 objects
     * which are normal to this Vector2.
     */
    public Vector2[] normalVectors(){
        Vector2 unit = unitNormalize();
        Vector2[] returnArr = new Vector2[2];
        returnArr[0] = unit.rotate(Math.PI / 2.0);
        returnArr[1] = unit.rotate((3.0 * Math.PI) / 2.0);
        //Swap vectors to get upwards vector first, if needed
        if(returnArr[1].getY() > returnArr[0].getY()){
            Vector2 swapTemp = returnArr[0];
            returnArr[0] = returnArr[1];
            returnArr[1] = swapTemp;
        }
        return returnArr;
    }
    
    /**
     * Computes the euclidian magnitude of this Vector2 (i.e. it's length).
     * @return The euclidian magnitude (length) of this Vector2.
     */
    public double magnitude(){
        return Math.sqrt(Math.pow(getX(), 2.0) + Math.pow(getY(), 2.0));
    }
}
