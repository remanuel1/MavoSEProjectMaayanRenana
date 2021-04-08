package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void testGetNormal() {
        Point3D p1 = new Point3D(1,0,0);
        Point3D p2 = new Point3D(0,1,0);
        Point3D p3 = new Point3D(0,0,1);

        Triangle triangle = new Triangle(p1, p2, p3);
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        Vector v3 = v1.crossProduct(v2);
        assertEquals(triangle.getNormal(new Point3D(1, -1, 1)), v3.normalize(), "Triangle get normal not work");
    }


}