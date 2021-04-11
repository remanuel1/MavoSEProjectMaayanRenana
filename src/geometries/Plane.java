package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Plane class implements Geometry interface
 * there are point in plane and normal vector to plane
 * there are get, toString, getNormal func
 */
public class Plane implements Geometry {
    final Point3D _point3D;
    final Vector _vector;

    // constructor
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _point3D = p1;

        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);

        Vector n = v1.crossProduct(v2);

        _vector = n.normalize();
    }

    // constructor
    public Plane(Point3D point3D, Vector vector){
        _point3D = point3D;
        _vector = vector.normalized(); //null
    }

    /**
     * @return plane's point
     */
    public Point3D getPoint3D() {
        return _point3D;
    }

    /**
     * @return plane's vector
     */
    public Vector getVector() {
        return _vector;
    }

    @Override
    public String toString() {
        return "Plane-" +
                "point:" + _point3D +
                " vector:" + _vector;
    }

    /**
     * @return plane's normal
     */
    @Override
    public Vector getNormal(Point3D point) {
        return _vector;
    }
}
