package renderer;
import elements.LightSource;
import geometries.Geometries;
import primitives.*;
import scene.Scene;
import java.util.LinkedList;
import java.util.List;
import static primitives.Util.*;
import geometries.Intersectable.GeoPoint;

/**
 * class BasicRayTracer
 * Create the image
 */
public class BasicRayTracer extends RayTracerBase {

    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double PI = 3.141592653589793;


    /**
     * constructor of ray tracer base
     *
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene); // A call to the father's constructor
    }


    /**
     * @param ray
     * @return color of closest intersection point
     */
    @Override
    public Color traceRay(Ray ray ,double alfa) {

        List<GeoPoint> allIntersections = _scene.geometries.findGeoIntersections(ray); //all intersection points

        if (allIntersections == null) { // if there is no intersection points
            return Color.BLACK; // return black
        }
        GeoPoint closestPoint = ray.getClosestGeoPoint(allIntersections); // find the closest point
        return calcColor(closestPoint, ray, alfa); // return the color of this closest point

    }

    /**
     * calculate the color of point
     * @param
     * @return color of this point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray ,double alfa) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K, alfa) // go to calcColor func
                .add(_scene._ambientLight.get_intensity()); // add to Ambient Light
    }

    /**
     * The function calls the functions that calculate the Local Effects and Global Effects
     * @param geoPoint
     * @param ray
     * @param level
     * @param k
     * @return color of point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k ,double alfa) {
        Color color = geoPoint.geometry.getEmission();
        color = color.add(calcLocalEffects(geoPoint, ray, k, alfa)); // add the Local Effects
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray.getDir(), level, k, alfa)); // add Global Effects
    }

    /**
     * function that calculates the global effects of the point
     * @param geoPoint
     * @param v
     * @param level
     * @param k
     * @param alfa
     * @return color of this point
     */
    private Color calcGlobalEffects(GeoPoint geoPoint, Vector v, int level, double k ,double alfa){
        Color color = Color.BLACK; // restart
        Vector n = geoPoint.geometry.getNormal(geoPoint.point); //normal of the point
        Material material = geoPoint.geometry.getMaterial(); // the material
        double kkr = k * material.kR; // kkr= k* the params kr from material
        if (kkr > MIN_CALC_COLOR_K) // kkr > the minimum
            color = calcGlobalEffect(constructReflectedRay(geoPoint, v,n), level, material.kR, kkr, alfa);
        double kkt = k * material.kT; // again , but now to Kt
        if (kkt > MIN_CALC_COLOR_K) // kkt > the minimum
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(geoPoint, v, n), level, material.kT, kkt, alfa));
        return color;
    }

    /**
     * privat func that help the calcGlobalEffects
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @param alfa
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx, double alfa) {
        GeoPoint gp = ray.getClosestGeoPoint(_scene.geometries.findGeoIntersections(ray));
        return (gp == null ? Color.BLACK.add(_scene._background).scale(kx) : calcColor(gp, ray, level-1, kkx, alfa)).scale(kx);
    }

    /**
     * @param geoPoint
     * @param v
     * @return new ray for the refracted
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Vector v, Vector n) {
        return new Ray(geoPoint.point, v,n); //Calculating refracted vector
    }

    /**
     * @param geoPoint
     * @param v
     * @return new ray for the reflected
     */
    private Ray constructReflectedRay(GeoPoint geoPoint, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n))).normalize(); //Calculating reflectance vector
        return new Ray(geoPoint.point, r,n);
    }


    /**
     * @param intersection
     * @param ray
     * @return color with local effect.
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k, double alfa) {
        Vector v = ray.getDir(); //direction of ray
        Vector n = intersection.geometry.getNormal(intersection.point); //normal of geometry and intersection point
        double nv = alignZero(n.dotProduct(v)); // n*v
        if (isZero(nv)) { // if n*v == null
            return Color.BLACK; // return background
        }
        Material material = intersection.geometry.getMaterial(); //  material of geometry
        int nShininess = material.nShininess;
        double kd = material.kD; //diffuse of material
        double ks = material.kS; //specular of material
        Color color = Color.BLACK; // return background
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point).normalized(); // get vector between light and point
            double nl = alignZero(n.dotProduct(l)); // n*l
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                //if (unshaded(lightSource, l, n, intersection)) {
                double ktr = callTransparency(lightSource, l, n, intersection, alfa);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr); // intensity of the light
                    // add all the local effect to the color
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    /**
     *
     * @param light
     * @param l
     * @param n
     * @param intersection
     * @return
     */
    @Deprecated
    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint intersection) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(intersection.point, lightDirection, n); // ray from point
        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, light.getDistance(intersection.point)); //find Geo Points Intersections
        if (intersections == null) return true; // not Points Intersections
        double lightDistance = light.getDistance(intersection.point); // distance between point and light
        for (GeoPoint gp : intersections) { // over all point
            if (alignZero(gp.point.distance(intersection.point) - lightDistance) <= 0 &&
                    gp.geometry.getMaterial().kT == 0)
                return false;
        }
        return true;
    }

    /**
     *
     * @param light
     * @param l
     * @param n
     * @param geoPoint
     * @return
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Point3D point = geoPoint.point;
        Ray lightRay = new Ray(point, lightDirection, n); // ray from point
        double lightDistance = light.getDistance(point); // distance between point and light
        var intersections = _scene.geometries.findGeoIntersections(lightRay, lightDistance); // find Geo Points Intersections
        if (intersections == null) //  no Points Intersections
            return 1.0;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) { // over all point and calculation that kt
            ktr *= gp.geometry.getMaterial().getKt();
            if (ktr < MIN_CALC_COLOR_K) // if ktr < min
                return 0.0;
        }
        return ktr;
    }

    /**
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return Specular effect
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n)))).normalized(); // Calculating vector
        double minusVr = v.scale(-1).dotProduct(r); // (-V)  dot Product vector that we calculating
        return lightIntensity.scale(ks * Math.pow(Math.max(0, minusVr), nShininess)); // return color with Specular effect
    }

    /**
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return diffuse effect
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor = kd * Math.abs(l.dotProduct(n)); // Calculating factor
        return lightIntensity.scale(factor); // return diffuse effect = lightIntensity * factor

    }

    //////// Soft Shadow////////////////////////////

    /**
     *
     * @param light
     * @param l
     * @param n
     * @param geopoint
     * @param angle
     * @return
     */
    private double callTransparency(LightSource light, Vector l, Vector n, GeoPoint geopoint, double angle) {
        Point3D lightP0 = light.getPosition(); // p0 of the light
        if (lightP0 == null || angle == 0)  // if the light==direction light
            return transparency(light, l, n, geopoint);

        //calculate radius of the circle
        double distance = lightP0.distance(geopoint.point); // distance between light and point
        double radius = distance * Math.tan(Math.toRadians(angle)); // radius around the light
        double sumKtr = 0; // sum of all ktr from random point around the light

        //find points on the circle at the same plane.
        Vector v = new Vector(-l.getHead().getY(), l.getHead().getX(), 0).normalized(); // vector in the plane
        Vector w = l.crossProduct(v); // vector ib the plane

        return AdaptiveSuperamplingcallTransparency(light, n, radius, lightP0, v, w, geopoint, 2 * PI, 1);

        // Improving performance

        //send ray to calculate and sum the ktr
       /* for(int i=0; i<81;i++) { // find 81 point around the light

            double t = 2 * PI * Math.random(); //
            double r = radius * Math.random(); // radius random

            double alpha = r * Math.cos(t); // parameter from random point
            double beta = r * Math.sin(t); // parameter from random point

            Point3D randomPoint = lightP0.add(v.scale(alpha).add(w.scale(beta))); //random point around light
            Vector randomL = geopoint.point.subtract(randomPoint).normalized(); // random vector around light
            sumKtr += transparency(light, randomL, n, geopoint); // sum of all ktr from random point around the light
        }
        return sumKtr/81; // average of ktr*/
    }

    /**
     *  the first func of adaptive super sampling.
     *  Department the circle to 4 part and find if all
     * 	the transparency at all parts are same.
     * 	if not call to recursive adaptive super sampling.
     * @param light
     * @param n
     * @param radius
     * @param positionLight
     * @param v
     * @param w
     * @param geopoint
     * @param angle
     * @param depth
     * @return
     */
    private double AdaptiveSuperamplingcallTransparency(LightSource light, Vector n, double radius,
                                                        Point3D positionLight, Vector v, Vector w, GeoPoint geopoint, double angle, int depth) {
        double ktr = 0; //sum ktr
        List<Point3D> circlePoint = new LinkedList<>(); // List of 4 points in a circle
        for (int i = 0; i < 4; i++) {
            double t = 0.25 * PI + 0.5 * PI * i; // our angel in circle , first PI/4
            double alpha = radius * Math.cos(t); // factor of calculate a point in a circle
            double beta = radius * Math.sin(t); // factor of calculate a point in a circle

            Point3D point = positionLight.add(v.scale(alpha).add(w.scale(beta))); // point in circle
            Vector newL = geopoint.point.subtract(point).normalized(); // ray between our point and the point on our body
            circlePoint.add(i,point); //add to list
            ktr+=transparency(light, newL, n, geopoint); // sum all ktr of all point

        }
        return AdaptiveSquare(circlePoint, light, n, positionLight, geopoint, 3); // recurse AdaptiveSquare
    }

    /**
     *
     *   function recursive that calculate ktr at 4 points in the circle.
     * 	 The function check if the ktr at 4 points are same. if equals return ktr.
     * 	 else  Department the square to 4 piece and send them to adaptive super sampling.
     * @param points
     * @param light
     * @param n
     * @param positionLight
     * @param geopoint
     * @param depth
     * @return
     */

    private double AdaptiveSquare(List<Point3D> points, LightSource light, Vector n,
                                  Point3D positionLight, GeoPoint geopoint, int depth) {

        double ktr = 0; // local
        double sumKtr = 0; // sum ktr

        boolean flag = true; // flag if we need recurse
        Point3D A = points.get(0); // point 1 form points
        Point3D B = points.get(1); // point 2 form points
        Point3D C = points.get(2); // point 3 form points
        Point3D D = points.get(3); // point 4 form points

        for (Point3D point : points) { //over all the list

            Vector SquareL = geopoint.point.subtract(point).normalized(); // ray between our point and the point on our body
            if (!point.equals(A)) { // check if this first point
                double kt = transparency(light, SquareL, n, geopoint); // ktr of point
                sumKtr += kt; // sum
                if (kt != ktr) { // check if all ktr is same
                    flag = false; // set flag = false
                }
            }
            else { // this first point
                ktr = transparency(light, SquareL, n, geopoint); // calculate ktr
                sumKtr += ktr; // sum
            }
        }

        if (!flag && depth > 0) // if not all ktr is same and depth > 0
        {
            Point3D middleAC = new Point3D(0.5 * (A.getX() + C.getX()), 0.5 * (A.getY() + C.getY()),
                    0.5 * (A.getZ() + C.getZ()));  // middle point between A and C
            Point3D middleAB = new Point3D(0.5 * (A.getX() + B.getX()), 0.5 * (A.getY() + B.getY()),
                    0.5 * (A.getZ() + B.getZ()));  // middle point between A and B
            Point3D middleAD = new Point3D(0.5 * (A.getX() + D.getX()), 0.5 * (A.getY() + D.getY()),
                    0.5 * (A.getZ() + D.getZ()));  // middle point between A and D
            Point3D middleDC = new Point3D(0.5 * (D.getX() + C.getX()), 0.5 * (D.getY() + C.getY()),
                    0.5 * (D.getZ() + C.getZ()));  // middle point between D and C
            Point3D middleBC = new Point3D(0.5 * (B.getX() + C.getX()), 0.5 * (B.getY() + C.getY()),
                    0.5 * (B.getZ() + C.getZ()));  // middle point between B and C


            // create 4 lists and insert to them 4 points of a quarter of the square

            List<Point3D> square1 = new LinkedList<>();
            List<Point3D> square2 = new LinkedList<>();
            List<Point3D> square3 = new LinkedList<>();
            List<Point3D> square4 = new LinkedList<>();
            square1.add(0,middleAB);
            square1.add( 1, B);
            square1.add( 2, middleBC);
            square1.add( 3, middleAC);

            square2.add( 0, A);
            square2.add( 1, middleAB);
            square2.add( 2, middleAC);
            square2.add( 3, middleAD);

            square3.add( 0,middleAD);
            square3.add( 1, middleAC);
            square3.add( 2, middleDC);
            square3.add( 3, D);

            square4.add( 0, middleAC);
            square4.add( 1, middleBC);
            square4.add( 2, C);
            square4.add( 3, middleDC);

            //  calculation average from the total ktr of all points
            return (AdaptiveSquare(square1, light, n, positionLight, geopoint, depth - 1)
                    + AdaptiveSquare(square2, light, n, positionLight, geopoint, depth - 1)
                    + AdaptiveSquare(square3, light, n, positionLight, geopoint, depth - 1)
                    + AdaptiveSquare(square4, light, n, positionLight, geopoint, depth - 1)) * 0.25;
        }
        else // not recursive , simple calculation
            return sumKtr * 0.25;


    }}


