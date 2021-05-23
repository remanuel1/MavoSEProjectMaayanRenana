package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void testFindIntersections() {
        Geometries g = new Geometries();
        assertEquals(g.get_intersectables().size(), 0 ,"the list not empty");
        // =============== Boundary Values Tests ==================
        //TC01  checks if there aren't any shapes (0 points)
        assertNull(g.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(-1,0,0))) , "the list empty so dont have intersections");

        //TC02 none of the shapes have intersection points (0 points)
        g.add(new Triangle(new Point3D(1,0,0), new Point3D(0, 1, 0), new Point3D(0, 0, 1)));
        g.add(new Plane(new Point3D(1,0,0), new Point3D(0, 1, 0), new Point3D(0, 0, 1)));
        assertNull(g.findIntersections(new Ray(new Point3D(-0.1, 0, 0), new Vector(-1,0,0))),"There should be no points of intersection ");

        //TC03 only one shape has intersection points with the ray
        g.add(new Triangle(new Point3D(2,0,0), new Point3D(0, 2, 0), new Point3D(0, 0, 2)));
        assertEquals(g.findIntersections(new Ray(new Point3D(0.1, 0, 0), new Vector(1,0,0))).size(), 1 , "should be only one shape have intersection points");

        //TC04  all the shapes have intersection points with the ray (at least one point)
        assertEquals(g.findIntersections(new Ray(new Point3D(0.1, 0, 0), new Vector(0.5,0.5,0.5).normalize())).size(), 3 ,"should be all shapes have intersection points");

        // ============ Equivalence Partitions Tests ==============

        //TC05  more than one shape has intersection points with the ray (at least one point)
        g.add(new Plane(new Point3D(2,0,0), new Point3D(0, 2, 0), new Point3D(0, 0, 2)));
        int num =g.findIntersections(new Ray(new Point3D(0.1, 0, 0), new Vector(1,0,0))).size();
        assertTrue((num>1&&num<4),"should be more than one shape has intersection and no all" );
    }

}