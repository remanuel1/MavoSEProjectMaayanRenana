package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Sphere class is implements Geometry interface
 * there are point - the center of sphere and radius
 * there are get, toString, getNormal func
 */
public class Sphere extends Geometry {
    private final double _radius;
    private final Point3D _center;

    /**
     * constructor
     * @param radius
     * @param point3D
     */
    public Sphere(double radius, Point3D point3D) {
        _radius = radius;
        _center = point3D;
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
     * @param p
     * @return sphere's normal from point p
     */
    public Vector getNormal(Point3D p) {
        Vector O_P = p.subtract(_center); // O_P = point p - center point
        return O_P.normalize(); // O_P normalize
    }

    /**
     * @param ray
     * @return Intersections geoPoint between ray and the sphere
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();
        // P0 is equal to center point
        if (P0.equals(_center) && alignZero(_radius-maxDistance)<=0) {
            return List.of(new GeoPoint(this, _center.add(v.scale(_radius))));
        }
        Vector U = _center.subtract(P0); // U = center-P0
        double tm = alignZero(v.dotProduct(U)); // tm = v*U
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm)); // d- distance

        // no intersections : the ray direction is above the sphere
        if (d >= _radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(_radius * _radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        // take only t1, t2 > 0
        // both bigger than 0
        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0){
            Point3D p1 = P0.add(v.scale(t1));
            Point3D p2 = P0.add(v.scale(t2));
            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
        }

        // t1 >0
        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
            Point3D p1 = P0.add(v.scale(t1));
            return List.of(new GeoPoint(this, p1));
        }

        // t2>0
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
            Point3D p2 = P0.add(v.scale(t2));
            return List.of(new GeoPoint(this, p2));
        }

        return null;
    }
}
