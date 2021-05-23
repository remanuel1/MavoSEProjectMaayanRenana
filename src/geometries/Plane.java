package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * Plane class implements Geometry interface
 * there are point in plane and normal vector to plane
 * there are get, toString, getNormal func
 */
public class Plane extends Geometry {
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

    /**
     * @param ray
     * @return Intersections geoPoint between ray and the plane
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {

        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();

        Vector n = _vector;

        if(_point3D.equals(P0)){
            return null;
        }

        Vector P0_Q0 = _point3D.subtract(P0);

        double mechane = alignZero(n.dotProduct(P0_Q0));

        //
        if (isZero(mechane)){
            return null;
        }

        //mone
        double nv = alignZero(n.dotProduct(v));

        // ray is lying in the plane axis
        if(isZero(nv)){
            return null;
        }

        double  t = alignZero(mechane / nv);

        if (t <=0){
            return  null;
        }
        Point3D P = ray.getPoint(t);

        return List.of(new GeoPoint(this, P));
    }
}
