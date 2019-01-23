package com.edinarobotics.utils.math;

import org.opencv.core.Mat;

/**
 * This class provides common math functions that are not implemented
 * elsewhere in WPILib.
 */
public final class Math1816 {

    private Math1816(){
        //Hide constructor
    }

    /**
     * Implements a signum function.
     *
     * If the parameter is {@code NaN} or {@code 0.0} returns the original value.
     * <br/>
     * If the parameter is positive, returns {@code 1.0}.<br/>
     * If the parameter is negative, returns {@code -1.0}<br/>
     * @param num The number whose signum will be returned.
     * @return The signum of the number as described above.,
     */
    public static double signum(double num){
        if(Double.isNaN(num) || num == 0.0){
            //For NaN or 0, return the same value
            return num;
        }
        if(num > 0.0){
            //If positive, return 1
            return 1;
        }
        //Only case left is negative
        return -1;
    }

    /**
     * Implements a signum function.
     *
     * If the parameter is {@code NaN} or {@code 0.0} returns the original value.
     * <br/>
     * If the parameter is positive, returns {@code 1.0}.<br/>
     * If the parameter is negative, returns {@code -1.0}<br/>
     * @param num The number whose signum will be returned.
     * @return The signum of the number as described above.,
     */
    public static float signum(float num){
        if(Double.isNaN(num) || num == 0.0){
            //For NaN or 0, return the same value
            return num;
        }
        if(num > 0.0){
            //If positive, return 1
            return 1;
        }
        //Only case left is negative
        return -1;
    }

    /**
     * A convenience function that coerces a given number between maximum
     * and minimum bounds.
     * If the number is greater than the maximum value, return the maximum
     * value; if the number is less than the minimum value, return the minimum
     * value; otherwise, return the original number.
     * @param max The maximum allowable value.
     * @param min The minimum allowable value.
     * @param num The number to be coerced.
     * @return The given value coreced between the maximum and minimum
     * limits.
     */
    public static double coerceValue(double max, double min, double num){
        if(num >= max){
            return max;
        }
        else if(num <= min){
            return min;
        }
        return num;
    }

    /**
     * A function that returns an inverse tangent approximation for the slope of the
     * line that goes through the origin and (x, y) coordinate pair
     * The approximation function is in the form ax / (b + sqrt(c + x^2)), where
     * a, b and c are constants tuned to reduce the error.
     * Maximum error that occurs is approximately 0.00209
     * @param y The y coordinate of the coordinate pair
     * @param x The x coordinate of the coordinate pair
     * @return An approximation of the inverse tan of the number as described above.
     */
    public static double atanApprox(double y, double x){
        if ((x == 0) && (y > 0)){
            return Math.PI/2;
        } else if ((x == 0) && (y < 0)) {
            return -1 * Math.PI/2;
        }

        double num = y/x;
        double a = Math.PI / 2;
        double b = (12 - Math.pow(Math.PI, 2))/(4 * (4 - Math.PI));
        double c = Math.pow(((6 - Math.PI) * (2 - Math.PI)/(4 * (4 - Math.PI))), 2);

        double atan = (a * num) / (b + Math.sqrt(c + Math.pow(num, 2)));
        return atan;
    }

    /**
     * A function that returns an inverse sine approximation for the given number.
     * The function uses the identity that arcsin(x) = arctan(x/sqrt(1-x^2))
     * @param num The number whose inverse sine approximation will be returned.
     * @return An approximation of the inverse sin of the number as described above.
     */
    public static double asinApprox(double num) {
        if (num == 1) {
            return Math.PI / 2;
        } else if (num == -1) {
            return -1 * Math.PI / 2;
        } else {
            return atanApprox(num , Math.sqrt(1 - num * num));
        }
    }

}
