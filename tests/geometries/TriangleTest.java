package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct triangle with vertices in correct order
        Point3D p1 = new Point3D(1,0,0);
        Point3D p2 = new Point3D(0,1,0);
        Point3D p3 = new Point3D(0,0,1);

        Triangle triangle = new Triangle(p1, p2, p3);
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        Vector v3 = v1.crossProduct(v2);
        assertEquals(triangle.getNormal(new Point3D(1, -1, 1)), v3.normalize(), "Triangle get normal not work");
    }


    @Test
    void testFindIntersections() {

        Triangle tr = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        Ray ray;

        // ============ Equivalence Partitions Tests ==============

        // TC01: ray inside triangle
        ray = new Ray(new Point3D(1, 1, 1), new Vector(-1, -1, -1));
        assertEquals(List.of(new Point3D(1d / 3, 1d / 3, 1d / 3)), tr.findIntersections(ray),
                "Bad intersection");

        // TC02: ray outside against edge
        ray = new Ray(new Point3D(0, 0, -1), new Vector(1, 1, 0));
        assertEquals(List.of(new Point3D(1, 1, -1)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(tr.findIntersections(ray), "Bad intersection");

        // TC03: ray outside against vertex
        ray = new Ray(new Point3D(0, 0, 2), new Vector(-1, -1, 0));
        assertEquals(List.of(new Point3D(-0.5, -0.5, 2)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(tr.findIntersections(ray), "Bad intersection");

        // =============== Boundary Values Tests ==================

        // TC11: ray in vertex
        ray = new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0));
        assertEquals(List.of(new Point3D(0, 1, 0)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(tr.findIntersections(ray), "Bad intersection");

        // TC12: ray is on edge
        ray = new Ray(new Point3D(-1, -1, 0), new Vector(1, 1, 0));
        assertEquals(List.of(new Point3D(0.5, 0.5, 0)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(tr.findIntersections(ray), "Bad intersection");

        // TC13: ray is on edge continuation
        ray = new Ray(new Point3D(-2, 0, 0), new Vector(1, 1, 0));
        assertEquals(List.of(new Point3D(-0.5, 1.5, 0)), pl.findIntersections(ray),
                "Wrong intersection with plane");
        assertNull(tr.findIntersections(ray), "Bad intersection");
    }
}