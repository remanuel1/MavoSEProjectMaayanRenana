package elements;

import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CameraRayIntersectionsIntegrationTests {
    /**
     * help func - to check if there are sum intersection like expected
     * @param camera
     * @param geo
     * @param expected
     */
    private void assertCountIntersections(Camera camera, Intersectable geo, int expected) {

        int count = 0;
        camera.setViewPlaneSize(3, 3).setDistance(1);
        //view plane 3X3 (WxH 3X3 & nx,ny =3 => Rx,Ry =1)
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                var intersections = geo.findIntersections(camera.constructRayThroughPixel(3, 3, j, i));
                count += intersections == null ? 0 : intersections.size();
            }
        }
        assertEquals(expected, count, "Wrong amount of intersections");
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Sphere intersections
     */
    @Test
    public void cameraRaySphereIntegration() {
        Camera camera1 = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0));
        Camera camera2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Small Sphere 2 points
        assertCountIntersections(camera1, new Sphere(1, new Point3D(0, 0, -3)), 2);

        // TC02: Big Sphere 18 points
        assertCountIntersections(camera2, new Sphere(2.5, new Point3D(0, 0, -2.5)), 18);

        // TC03: Medium Sphere 10 points
        assertCountIntersections(camera2, new Sphere(2, new Point3D(0, 0, -2)), 10);

        // TC04: Inside Sphere 9 points
        assertCountIntersections(camera2, new Sphere(4, new Point3D(0, 0, -1)), 9);

        // TC05: Beyond Sphere 0 points
        assertCountIntersections(camera1, new Sphere(0.5, new Point3D(0, 0, 1)), 0);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Plane intersections
     */
    @Test
    public void cameraRayPlaneIntegration() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Plane intersection camera 9 points
        assertCountIntersections(camera, new Plane(new Point3D(0, 0, -10), new Vector(0, 0, 1)), 9);

        // TC02: Plane with small angle - intersection 9 points
        assertCountIntersections(camera, new Plane(new Point3D(0, 0, -10), new Vector(0, 1, 2)), 9);

        // TC03: Plane parallel to lower rays 6 points
        assertCountIntersections(camera, new Plane(new Point3D(0, 0, -10), new Vector(0, 1, 1)), 6);
    }

    /**
     * Integration tests of Camera Ray construction with Ray-Triangle intersections
     */
    @Test
    public void cameraRayTriangleIntegration() {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0));

        // TC01: Small triangle 1 point
        assertCountIntersections(camera, new Triangle(new Point3D(1, -1, -2), new Point3D(-1, -1, -2), new Point3D(0, 1, -2)), 1);

        // TC02: Medium triangle 2 points
        assertCountIntersections(camera, new Triangle(new Point3D(1, -1, -2), new Point3D(-1, -1, -2), new Point3D(0, 20, -2)), 2);

    }
}
