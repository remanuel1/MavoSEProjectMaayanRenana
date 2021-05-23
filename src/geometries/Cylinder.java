package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;


/**
 * Cylinder class extends form tube.
 * there is a params about the height.
 * there are get, toString, getNormal func
 */
public class Cylinder extends Tube{
    private double _height;
    final Plane _base1;
    final Plane _base2;

    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        _height = height;
        _base1 = new Plane(_axisRay.getP0(),_axisRay.getDir());
        _base2= new Plane(_axisRay.getPoint(_height),_axisRay.getDir());
    }

    /**
     * @return height of cylinder
     */
    public double getHeight() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder:" +
                "height=" + _height +
                ", axisRay=" + _axisRay +
                ", radius=" + _radius;
    }

    /**
     * @param p
     * @return normal of point in cylinder
     */
    public Vector getNormal(Point3D p){

        Point3D o = _axisRay.getP0();
        Vector v = _axisRay.getDir();

        // projection of P-O on the ray:
        double t;
        try {
            t = alignZero(p.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { // P = O
            return v;
        }

        // if the point is at a base
        if (t == 0 || isZero(_height - t)) // if it's close to 0, we'll get ZERO vector exception
            return v;

        o = o.add(v.scale(t));
        return p.subtract(o).normalize();
    }


    @Override
    public List<Point3D> findIntersections(Ray ray) {

        Vector vAxis = _axisRay.getDir();
        Vector v = ray.getDir();
        Point3D p0 = ray.getP0();
        Point3D pC = _axisRay.getP0();
        Point3D p1;
        Point3D p2;

        // intersections of the ray with infinite cylinder {without the bases)
        List<Point3D> intersections = super.findIntersections(ray);
        double vAxisV = alignZero(vAxis.dotProduct(v)); // cos(angle between ray directions)

        if (intersections == null) { // no intersections with the infinite cylinder
            try {
                vAxis.crossProduct(v); // if ray and axis are parallel - throw exception
                return null; // they are not parallel - the ray is outside the cylinder
            } catch (Exception e) {}

            // The rays are parallel
            Vector vP0PC;
            try {
                vP0PC = pC.subtract(p0); // vector from P0 to Pc (O1)
            } catch (Exception e) { // the rays start at the same point
                // check whether the ray goes into the cylinder
                return vAxisV > 0 ? //
                        List.of(ray.getPoint(_height)) : null;
            }

            double t1 = alignZero(vP0PC.dotProduct(v)); // project Pc (O1) on the ray
            p1 = ray.getPoint(t1); // intersection point with base1

            // Check the distance between the rays
            if (alignZero(p1.distance(pC) - _radius) >= 0)
                return null;

            // intersection points with base2
            double t2 = alignZero(vAxisV > 0 ? t1 + _height : t1 - _height);
            p2 = ray.getPoint(t2);
            // the ray goes through the bases - test bases vs. ray head and return points
            // accordingly
            if (t1 > 0 && t2 > 0)
                return List.of(p1, p2);
            if (t1 > 0)
                return List.of(p1);
            if (t2 > 0)
                return List.of(p2);
            return null;
        }

        // Ray - infinite cylinder intersection points
        p1 = intersections.get(0);
        p2 = intersections.get(1);

        // get projection of the points on the axis vs. base1 and update the
        // points if both under base1 or they are from different sides of base1
        double p1OnAxis = alignZero(p1.subtract(pC).dotProduct(vAxis));
        double p2OnAxis = alignZero(p2.subtract(pC).dotProduct(vAxis));
        if (p1OnAxis < 0 && p2OnAxis < 0)
            p1 = null;
        else if (p1OnAxis < 0)
            p1 = _base1.findIntersections(ray).get(0);
        else
            /* p2OnAxis < 0 */ p2 = _base1.findIntersections(ray).get(0);

        // get projection of the points on the axis vs. base2 and update the
        // points if both above base2 or they are from different sides of base2
        double p1OnAxisMinusH = alignZero(p1OnAxis - _height);
        double p2OnAxisMinusH = alignZero(p2OnAxis - _height);
        if (p1OnAxisMinusH > 0 && p2OnAxisMinusH > 0)
            p1 = null;
        else if (p1OnAxisMinusH > 0)
            p1 = _base2.findIntersections(ray).get(0);
        else
            /* p2OnAxisMinusH > 0 */ p2 = _base2.findIntersections(ray).get(0);

        // Check the points and return list of points accordingly
        if (p1 != null && p2 != null)
            return List.of(p1, p2);
        if (p1 != null)
            return List.of(p1);
        if (p2 != null)
            return List.of(p2);
        return null;
    }

}
