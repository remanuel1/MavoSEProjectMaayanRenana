package geometries;

import primitives.Point3D;

/**
 * triangle class is extends Polygon
 * there is toString func
 */

public class Triangle extends Polygon{

    //constructor
    public Triangle(Point3D... vertices) {
        super(vertices);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
