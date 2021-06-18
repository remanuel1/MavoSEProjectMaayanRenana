package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * triangle class is extends Polygon
 * there is toString func
 */

public class Triangle extends Polygon{

    /**
     * constructor
     * @param vertices
     */
    public Triangle(Point3D... vertices) {
        super(vertices);
    }


    /**
     * toString
     * @return
     */
    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
