package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    Point3D p1 = new Point3D(1, 0, 0);
    Point3D p2 = new Point3D(0, 1, 0);
    Point3D p3 = new Point3D(0, 0, 1);
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            Plane p = new Plane(p1, p2, p3);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }

        // TC02: Wrong vertices order
        try{
            new Plane(p1, p1, p2);
            fail("Constructed a plane with wrong vertices");
        } catch (IllegalArgumentException e) {}

        try{
            new Plane(p1, new Point3D(2, 0, 0), new Point3D(4, 0, 0));
            fail("Constructed a plane with wrong vertices");
        } catch (IllegalArgumentException e) {}

    }

    @Test
    void testGetNormal() {
        try {
            Plane p = new Plane(p1, p2, p3);
            assertEquals(p._vector.length(), 1, "wrong normal");
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }
    }
}