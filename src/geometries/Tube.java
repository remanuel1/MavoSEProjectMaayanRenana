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
}
