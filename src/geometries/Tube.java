package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * tube class is implements Geometry interface
 * there are ray and radius
 * there are get, toString, getNormal func
 */
public class Tube extends Geometry{
    protected Ray _axisRay;
    protected double _radius;

    //constructor
    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    /**
     *
     * @return Tube's ray
     */
    public Ray getAxisRay() {
        return _axisRay;
    }

    /**
     *
     * @return Tube's radius
     */
    public double getRadius() {
        return _radius;
    }

    @Override
    public String toString() {
        return "Tube:" +
                "axisRay=" + _axisRay +
                ", radius=" + _radius;
    }

    /**
     * @param p
     * @return Tube's normal from point p
     */
    public Vector getNormal(Point3D p){
        Point3D P0 = _axisRay.getP0(); // P0 is start point of ray
        Vector v = _axisRay.getDir(); // v is direction of ray
        Vector P0_P = p.subtract(P0); // P0_P = point p - P0

        double t = v.dotProduct(P0_P); // t = v*P0_P (dot product)

        if(isZero(t)){
            // if t=0, P0_P is the normal and need to normalize
            return P0_P.normalize();
        }

        Point3D O =  P0.add(v.scale(t)); // O = P0 + v*t
        if(O.equals(p)){
            throw new IllegalArgumentException("point p cannot be on the tube's axis");
        }
        return p.subtract(O).normalize(); // normal = (p-O). normalize
    }

    /**
     * @param ray
     * @return list of intersectables points with ray
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        Vector vAxis = _axisRay.getDir();
        Vector v = ray.getDir();
        Point3D p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(_axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0) // the ray is orthogonal to Axis
                return List.of(ray.getPoint(_radius));

            double t = alignZero(Math.sqrt(_radius * _radius / vMinusVVaVa.lengthSquared()));
            return t == 0 ? null : List.of(ray.getPoint(t));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(_radius * _radius / a));
                return t == 0 ? null : List.of(ray.getPoint(t));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - _radius * _radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0)
            return List.of(ray.getPoint(t1), ray.getPoint(t2));
        else // t2 is behind the head
            return List.of(ray.getPoint(t1));

    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        Vector vAxis = _axisRay.getDir();
        Vector v = ray.getDir();
        Point3D p0 = ray.getP0();

        // At^2+Bt+C=0
        double a = 0;
        double b = 0;
        double c = 0;

        double vVa = alignZero(v.dotProduct(vAxis));
        Vector vVaVa;
        Vector vMinusVVaVa;
        if (vVa == 0) // the ray is orthogonal to the axis
            vMinusVVaVa = v;
        else {
            vVaVa = vAxis.scale(vVa);
            try {
                vMinusVVaVa = v.subtract(vVaVa);
            } catch (IllegalArgumentException e1) { // the rays is parallel to axis
                return null;
            }
        }
        // A = (v-(v*va)*va)^2
        a = vMinusVVaVa.lengthSquared();

        Vector deltaP = null;
        try {
            deltaP = p0.subtract(_axisRay.getP0());
        } catch (IllegalArgumentException e1) { // the ray begins at axis P0
            if (vVa == 0) // the ray is orthogonal to Axis
                return List.of(new GeoPoint(this, ray.getPoint(_radius)));

            double t = alignZero(Math.sqrt(_radius * _radius / vMinusVVaVa.lengthSquared()));
            return t == 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
        }

        double dPVAxis = alignZero(deltaP.dotProduct(vAxis));
        Vector dPVaVa;
        Vector dPMinusdPVaVa;
        if (dPVAxis == 0)
            dPMinusdPVaVa = deltaP;
        else {
            dPVaVa = vAxis.scale(dPVAxis);
            try {
                dPMinusdPVaVa = deltaP.subtract(dPVaVa);
            } catch (IllegalArgumentException e1) {
                double t = alignZero(Math.sqrt(_radius * _radius / a));
                return t == 0 ? null : List.of(new GeoPoint(this, ray.getPoint(t)));
            }
        }

        // B = 2(v - (v*va)*va) * (dp - (dp*va)*va))
        b = 2 * alignZero(vMinusVVaVa.dotProduct(dPMinusdPVaVa));
        c = dPMinusdPVaVa.lengthSquared() - _radius * _radius;

        // A*t^2 + B*t + C = 0 - lets resolve it
        double discr = alignZero(b * b - 4 * a * c);
        if (discr <= 0) return null; // the ray is outside or tangent to the tube

        double doubleA = 2 * a;
        double tm = alignZero(-b / doubleA);
        double th = Math.sqrt(discr) / doubleA;
        if (isZero(th)) return null; // the ray is tangent to the tube

        double t1 = alignZero(tm + th);
        if (t1 <= 0) // t1 is behind the head
            return null; // since th must be positive (sqrt), t2 must be non-positive as t1

        double t2 = alignZero(tm - th);

        // if both t1 and t2 are positive
        if (t2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
        else // t2 is behind the head
            return List.of(new GeoPoint(this, ray.getPoint(t1)));
    }
}
