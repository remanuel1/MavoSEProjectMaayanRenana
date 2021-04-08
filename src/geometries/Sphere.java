package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Sphere class is implements Geometry interface
 * there are point - the center of sphere and radius
 * there are get, toString, getNormal func
 */
public class Sphere implements Geometry{
    final  Point3D _center;
    final  double _radius;

    // constructor
    public Sphere(Point3D point3D, double radius) {
        _center = point3D;
        _radius = radius;
    }

    public Point3D getCenter() {
        return _center;
    }

    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_point3D=" + _center +
                ", radius=" + _radius +
                '}';
    }

    public Vector getNormal(Point3D p){
        Vector O_P = p.subtract(_center);
        return O_P.normalize();
    }
}
