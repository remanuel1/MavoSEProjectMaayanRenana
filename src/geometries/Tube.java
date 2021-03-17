package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * tube class is implements Geometry interface
 * there are ray and radius
 * there are get, toString, getNormal func
 */
public class Tube implements Geometry{
    protected Ray _axisRay;
    protected double _radius;

    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    public Ray getAxisRay() {
        return _axisRay;
    }

    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "Tube:" +
                "axisRay=" + _axisRay +
                ", radius=" + _radius;
    }

    public Vector getNormal(Point3D point){
        return null;
    }
}
