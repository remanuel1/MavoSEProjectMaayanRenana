package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    Point3D p1 = new Point3D(1, 0, 0);
    Point3D p2 = new Point3D(0, 1, 0);
    Point3D p3 = new Point3D(0, 0, 1);
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct plane with vertices in correct order
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
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct plane with vertices in correct order
        try {
            Plane p = new Plane(p1, p2, p3);
            assertEquals(p._vector.length(), 1, "wrong normal");
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }
    }

    @Test
    void testFindIntersections() {
        Plane pl = new Plane(new Point3D(0, 0, 1), new Vector(1, 1, 1));
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray intersects the plane
        assertEquals(List.of(new Point3D(1, 0, 0)),
                pl.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0))),
                "Bad plane intersection");

        // TC02: Ray does not intersect the plane
        assertNull(pl.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))),
                "Must not be plane intersection");

        // =============== Boundary Values Tests ==================

        // TC11: Ray is parallel to the plane
        assertNull(pl.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(0, 1, -1))),
                "Must not be plane intersection");

        // TC12: the ray included in the plane
        assertNull(pl.findIntersections(new Ray(new Point3D(0, 0.5, .5), new Vector(0, 1, -1))),
                "Must not be plane intersection");

        // TC13: Ray is orthogonal to the plane - in the plane
        assertEquals(List.of(new Point3D(1d / 3, 1d / 3, 1d / 3)),
                pl.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(-1, -1, -1))),
                "Bad plane intersection");

        // TC14: Ray is orthogonal to the plane - after the plane
        assertNull(pl.findIntersections(new Ray(new Point3D(1, 1, 1), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC15: Ray is orthogonal to the plane - before the plane
        assertNull(pl.findIntersections(new Ray(new Point3D(0, 0.5, 0.5), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC16: p0 is in the plane, but not the ray
        assertNull(pl.findIntersections(new Ray(new Point3D(0, 0.5, 0.5), new Vector(1, 1, 0))),
                "Must not be plane intersection");

        // TC17: p0 begins in the same point in the plane (=_point3D)
        assertNull(pl.findIntersections(new Ray(new Point3D(0, 0, 1), new Vector(1, 1, 0))),
                "Must not be plane intersection");

    }
}