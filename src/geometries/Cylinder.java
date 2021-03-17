package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Cylinder class extends form tube.
 * there is a params about the height.
 * there are get, toString, getNormal func
 */
public class Cylinder extends Tube{
    private double _height;

    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        _height = height;
    }

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

    public Vector getNormal(Point3D point){
        return null;
    }
}
