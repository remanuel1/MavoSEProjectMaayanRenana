package renderer; /**
 *
 */


import elements.*;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {

    private double alpha = 10.0; // params of the improvement of soft shadow
    private Scene scene = new Scene("Test scene"); // the scene of tests

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))
                .setViewPlaneSize(150, 150).setDistance(1000);

        scene.geometries.add(
                new Sphere(50, new Point3D(0, 0, -50)) // the big sphere
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25, new Point3D(0, 0, -50)) // the small sphere
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) // spot light
                        .setKl(0.0004).setKq(0.0000006));

        Render render = new Render()
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) // size of view plane
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage(alpha);
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(2500, 2500).setDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add(
                new Sphere(400, new Point3D(-950, -900, -1000)) // big sphere
                        .setEmission(new Color(0, 0, 100))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
                new Sphere(200, new Point3D(-950, -900, -1000)) // small sphere
                        .setEmission(new Color(100, 20, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500), // the background
                        new Point3D(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500), // the background
                        new Point3D(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKr(0.5)));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) // spot light
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage(alpha);
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(200, 200).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //Triangle
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //Triangle
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(30, new Point3D(60, 50, -50)) //Sphere
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) // spot light
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage(alpha);
        render.writeToImage();
    }


    /**
     * Produce a picture of a sphere lighted by a spot light
     * Creates a bear
     */
    @Test
    public void ourPicture() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //

                new Sphere(50, new Point3D(0, 0, -50)) // head
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(20, new Point3D(-40, 40, 10)) // left ear
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
                new Sphere(20, new Point3D(40, 40, 10)) // right ear
                        .setEmission(new Color(java.awt.Color.ORANGE))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
                new Sphere(10, new Point3D(-40, 40, 10)) // left ear
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Sphere(10, new Point3D(40, 40, 10)) // right ear
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Sphere(5, new Point3D(10, 10, 0)) // right eye
                        .setEmission(new Color(java.awt.Color.BLACK))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
                new Sphere(5, new Point3D(-10, 10, 0)) // left eye
                        .setEmission(new Color(java.awt.Color.BLACK))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
                new Triangle(new Point3D(-5, 0, 50), new Point3D(5, 0, 50), new Point3D(2.5, -5, 50)) // background
                        .setEmission(new Color(java.awt.Color.BLACK))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)),
                new Triangle(new Point3D(-2000, -2000, -1800), new Point3D(-1400, 1400, -1900), new Point3D(1500, 1500, -2000)) // background
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)));


        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) // spot light
                        .setKl(0.0004).setKq(0.0000006));


        Render render = new Render()
                .setImageWriter(new ImageWriter("refractionOurPicture", 500, 500)) // size of view plane
                .setCamera(camera)
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage(alpha);
        render.writeToImage();
    }

    /**
     * Produce a picture of room with varied shapes and lights of different types
     */
    @Test
    public void ourFinalPicture() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(1000);


        scene.geometries.add(

                // Creating the room by 5 planes intersecting with each other
                new Plane(new Point3D(0, 0, 0), new Point3D(45, 45, 0), new Point3D(45, -45, 0)) // The back wall
                        .setEmission(new Color(221, 221, 221))
                        .setMaterial(new Material().setKd(0.01).setKs(0.1).setShininess(20)),
                new Plane(new Point3D(45, -45, 0), new Point3D(100, -45, 0), new Point3D(100, -70, 100)) // The floor
                        .setEmission(new Color(221, 221, 221))
                        .setMaterial(new Material().setShininess(60)),
                new Plane(new Point3D(45, 45, 0), new Point3D(100, 45, 0), new Point3D(100, 70, 100)) // The ceiling (up)
                        .setEmission(new Color(221, 221, 221))
                        .setMaterial(new Material().setKs(0.2).setShininess(10)),
                new Plane(new Point3D(45, -45, 0), new Point3D(45, -100, 0), new Point3D(70, -100, 100)) // right wall
                        .setEmission(new Color(96, 53, 181))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                new Plane(new Point3D(-45, 45, 0), new Point3D(-45, 100, 0), new Point3D(-70, 100, 100)) // left wall
                        .setEmission(new Color(170, 36, 26))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),


                // Spheres
                new Sphere(12, new Point3D(30, -40, 200)) // Transparent sphere in front of the mirror
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(40).setKt(0.8)),

                new Sphere(8, new Point3D(-30, -20, 150)) // A small transparent sphere
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(40).setKt(0.8)),

                new Sphere(10, new Point3D(25, -30, 20)) //  A red sphere
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(60).setKt(0.6).setKr(0.1)),


                // The pyramid, made up of 4 triangles (polygons)
                new Polygon(new Point3D(10, -62, 115), new Point3D(12, -50, 113), new Point3D(-11, -62, 112)) // ABC
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(40).setKt(0.5)),
                new Polygon(new Point3D(3, -40, 123), new Point3D(-11, -62, 112), new Point3D(12, -50, 113)) // HCB
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(40).setKt(0.5)),
                new Polygon(new Point3D(10, -62, 115), new Point3D(12, -50, 113), new Point3D(3, -40, 123)) // ABH
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(40).setKt(0.5)),
                new Polygon(new Point3D(10, -62, 115), new Point3D(3, -40, 123), new Point3D(-11, -62, 112)) // AHC
                        .setEmission(new Color(java.awt.Color.RED))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(40).setKt(0.5)),


                // The cube, made up of 6 polygons
                new Polygon(new Point3D(-30, 0, 100), new Point3D(-30, -40, 100), new Point3D(0, -40, 100), new Point3D(0, 0, 100))//adcb
                        .setEmission(new Color(204, 102, 255))
                        .setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(40)),
                new Polygon(new Point3D(-20, -10, 110), new Point3D(-20, -50, 110), new Point3D(10, -50, 110), new Point3D(10, -10, 110))//ehgf
                        .setEmission(new Color(204, 102, 255))
                        .setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(40)),
                new Polygon(new Point3D(-30, 0, 100), new Point3D(0, 0, 100), new Point3D(10, -10, 110), new Point3D(-20, -10, 110))//abfe
                        .setEmission(new Color(204, 102, 255))
                        .setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(300)),
                new Polygon(new Point3D(-30, 0, 100), new Point3D(-30, -40, 100), new Point3D(-20, -50, 110), new Point3D(-20, -10, 110))//adhe
                        .setEmission(new Color(204, 102, 255))
                        .setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(300)),
                new Polygon(new Point3D(0, -40, 100), new Point3D(0, 0, 100), new Point3D(10, -10, 110), new Point3D(10, -50, 110))//cbfg
                        .setEmission(new Color(204, 102, 255))
                        .setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(300)),
                new Polygon(new Point3D(-20, -50, 110), new Point3D(-30, -40, 100), new Point3D(0, -40, 100), new Point3D(10, -50, 110))//hdcg
                        .setEmission(new Color(204, 102, 255))
                        .setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(300)),


                // The mirror
                new Polygon(new Point3D(47, -10, 11.53846154), new Point3D(65, -18, 80.76923077),
                        new Point3D(65, -58, 80.76923077), new Point3D(47, -40, 11.53846154))
                        .setMaterial(new Material().setKt(0).setKr(0.95).setKs(0.95).setShininess(500)));


                /*// The light from the ceiling
                new Polygon(new Point3D(20, 51.25, 35), new Point3D(-20, 51.25, 35),
                        new Point3D(-25, 56.25, 55), new Point3D(25, 56.25, 55))
                        .setEmission(new Color(java.awt.Color.white))
                        .setMaterial(new Material()));*/


                // All lights (spot,point and direction)
                scene.lights.add(
                       new SpotLight(new Color(1000, 600, 0), new Point3D(-43, 43, 0), new Vector(63, -93, 17))
                             .setKl(0.0004).setKq(0.000000006));

                scene.lights.add(
                       new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2))
                              .setKl(0.0004).setKq(0.0000006));

                scene.lights.add(
                         new DirectionalLight(new Color(175, 255, 255), new Vector(1, 5, -3)));

                scene.lights.add(
                        new PointLight(new Color(java.awt.Color.white), new Point3D(0,40,50))
                            .setKl(0.00005).setKq(0.00005));


                Render render = new Render()
                    .setImageWriter(new ImageWriter("refractionOurFinalPicture", 500, 500)) // size of view plane
                    .setCamera(camera)
                    .setMultithreading(3) // Multithreading
                    .setRayTracer(new BasicRayTracer(scene));
                render.renderImage(5); // alpha of the improvement of soft shadow
                render.writeToImage();
    }

}