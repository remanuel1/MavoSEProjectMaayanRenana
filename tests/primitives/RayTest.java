package primitives;

import geometries.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import geometries.Intersectable.GeoPoint;


class RayTest {

    @Test
    void testFindClosestPoint() {

        List<Point3D> point = new LinkedList<>();
        point.add(new Point3D(10,10,10));
        point.add(new Point3D(2,2,2));
        point.add(new Point3D(9,9,9));

        // ============ Equivalence Partitions Tests ==============
        // TC01: closest point is found amidst the list of point
        Ray ray1 = new Ray(new Point3D(1,1,1), new Vector(1,1,1));
        assertEquals(ray1.findClosestPoint(point), new Point3D(2,2,2), "FindClosestPoint not work");

        // =============== Boundary Values Tests ==================
        // TC11: closest point is the first point
        Ray ray2 = new Ray(new Point3D(11,11,11), new Vector(1,1,1));
        assertEquals(ray2.findClosestPoint(point), new Point3D(10,10,10), "FindClosestPoint not work");// TC11: closest point is the first point

        // TC12: closest point is the last point
        Ray ray3 = new Ray(new Point3D(8,8,8), new Vector(1,1,1));
        assertEquals(ray3.findClosestPoint(point), new Point3D(9,9,9), "FindClosestPoint not work");

        // TC13: list is empty and there is no closest point
        List<Point3D> point1 = new LinkedList<>();
        assertEquals(ray3.findClosestPoint(point1), null, "FindClosestPoint not work");

    }

    @Test
//    void testGetClosestGeoPoint() {
//        List<GeoPoint> geoPoint = new LinkedList<>();
//        Ray ray = new Ray(new Point3D(0,0,0), new Vector(1,1,1));
//        GeoPoint g3 = new GeoPoint (new Triangle(new Point3D(2,2,3), new Point3D(3,0,0), new Point3D(0, 3, 0)), new Point3D(1.8,1.8,1.8));
//        GeoPoint g1 = new GeoPoint(new Sphere(2, new Point3D(2,2,3)), new Point3D(3.39, 3.39, 3.39));
//        GeoPoint g2 = new GeoPoint(new Sphere(2, new Point3D(2,2,3)), new Point3D(1.3, 1.3, 1.3));
//        geoPoint.add(g1);
//        geoPoint.add(g2);
//        geoPoint.add(g3);
//
//
//        // ============ Equivalence Partitions Tests ==============
//        // TC01: closest point is found amidst the list of point
//        assertEquals(ray.getClosestGeoPoint(geoPoint), g2, "getClosestGeoPoint not work");
//
//        // =============== Boundary Values Tests ==================
//        // TC11: closest point is the first point
//        geoPoint.clear();
//        geoPoint.add(g2);
//        geoPoint.add(g1);
//        geoPoint.add(g3);
//        assertEquals(ray.getClosestGeoPoint(geoPoint), g2, "getClosestGeoPoint not work");
//
//        // TC12: closest point is the last point
//        geoPoint.clear();
//        geoPoint.add(g3);
//        geoPoint.add(g1);
//        geoPoint.add(g2);
//        assertEquals(ray.getClosestGeoPoint(geoPoint), g2, "getClosestGeoPoint not work");
//
//        // TC13: list is empty and there is no closest point
//        geoPoint.clear();
//        assertEquals(ray.getClosestGeoPoint(geoPoint), null, "getClosestGeoPoint not work");
//
//    }

    public void testgetClosestGeoPoint() {
        Ray ray1 = new Ray(new Point3D(0, 0, 10), new Vector(1, 10, -100));


        List<GeoPoint> list = new LinkedList<GeoPoint>();
        list.add(new GeoPoint(new Sphere(2, new Point3D(1,1,-100)),new Point3D(1, 1, -100)));
        list.add(new GeoPoint( new Sphere(2, new Point3D(-1,1,-99)),new Point3D(-1, 1, -99)));
        list.add(new GeoPoint(new Sphere(2, new Point3D(0,2,-10)),new Point3D(0, 2, -10)));
        list.add(new GeoPoint(new Sphere(2, new Point3D(0.5,0,-100)),new Point3D(0.5, 0, -100)));

        // ============ Equivalence Partitions Tests ==============
        //T01: A point in the middle of the list is closest to the beginning of the fund
        assertEquals(new Point3D(0, 2, -10) ,ray1.getClosestGeoPoint(list).point, "point in the middle of the list");

        // =============== Boundary Values Tests ==================
        //T02: empty point list
        List<GeoPoint> list2 = null;
        assertNull(ray1.getClosestGeoPoint(list2), "empty point list");

        //T03: The first point is closest to the beginning of the foundation
        Ray ray3 = new Ray(new Point3D(1, 1.5, -100), new Vector(1, 10, -100));
        assertEquals(list.get(0) ,ray3.getClosestGeoPoint(list), "first point is closest");

        //T04: The last point is closest to the beginning of the foundation
        Ray ray4 = new Ray(new Point3D(0, 0, -100), new Vector(1, 10, -100));
        assertEquals(list.get(3) ,ray4.getClosestGeoPoint(list), "last point is closest");


    }
}