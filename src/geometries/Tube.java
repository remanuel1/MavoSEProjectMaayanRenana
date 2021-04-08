package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

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

    public Vector getNormal(Point3D p){
        Point3D P0 = _axisRay.getP0();
        Vector v = _axisRay.getDir();
        Vector P0_P = p.subtract(P0);

        double t = v.dotProduct(P0_P);

        //TODO explain here what's happens
        if(isZero(t)){
            return P0_P.normalize();
        }

        Point3D O =  P0.add(v.scale(t));
        if(O.equals(p)){
            throw new IllegalArgumentException("point p cannot be on the tube's axis");
        }
        return p.subtract(O).normalize();
    }
}
