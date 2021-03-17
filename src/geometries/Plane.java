package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane class implements Geometry interface
 * there are point in plane and normal vector to plane
 * there are get, toString, getNormal func
 */
public class Plane implements Geometry {
    public Point3D _point3D;
    public Vector _vector;

    // constructor
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _point3D = p1;
        _vector = null;
    }

    // constructor
    public Plane(Point3D point3D, Vector vector){
        _point3D = point3D;
        _vector = getNormal(point3D); //null
    }

    public Point3D getPoint3D() {
        return _point3D;
    }

    public Vector getVector() {
        return _vector;
    }

    @Override
    public String toString() {
        return "Plane-" +
                "point:" + _point3D +
                " vector:" + _vector;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
