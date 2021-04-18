package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * triangle class is extends Polygon
 * there is toString func
 */

public class Triangle extends Polygon{

    //constructor
    public Triangle(Point3D... vertices) {
        super(vertices);
    }

    /**
     * @param ray
     * @return list of intersectables point with ray
     */
    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return super.findIntersections(ray);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
