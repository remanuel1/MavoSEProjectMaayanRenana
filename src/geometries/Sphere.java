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

    /**
     * @return sphere's center point
     */
    public Point3D getCenter() {
        return _center;
    }

    /**
     * @return sphere's radius
     */
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

    /**
     *
     * @param p
     * @return sphere's normal from point p
     */
    public Vector getNormal(Point3D p){
        Vector O_P = p.subtract(_center); // O_P = point p - center point
        return O_P.normalize(); // O_P normalize
    }
}
