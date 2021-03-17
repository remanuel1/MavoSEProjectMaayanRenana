package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Sphere class is implements Geometry interface
 * there are point - the center of sphere and radius
 * there are get, toString, getNormal func
 */
public class Sphere implements Geometry{
    public Point3D _point3D;
    public double radius;

    // constructor
    public Sphere(Point3D point3D, double radius) {
        _point3D = point3D;
        this.radius = radius;
    }

    public Point3D getPoint3D() {
        return _point3D;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_point3D=" + _point3D +
                ", radius=" + radius +
                '}';
    }

    public Vector getNormal(Point3D point){
        return null;
    }
}
