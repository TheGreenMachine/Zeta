package com.edinarobotics.utils.math;

public class PPPoint {
    double x, y;

    public PPPoint(double x, double y) {
        this.x = x; this.y = y;
    }

    public String toString() { return String.format("[%.2f, %.2f]", x, y); }
}