package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.*;

import geometries.Intersectable.GeoPoint;

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
        super(scene);
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
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K, alfa)
                .add(_scene._ambientLight.get_intensity());
    }

    /**
     * @param geoPoint
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k ,double alfa) {
        Color color = geoPoint.geometry.getEmission();
        color = color.add(calcLocalEffects(geoPoint, ray, k, alfa));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray.getDir(), level, k, alfa));
    }

    private Color calcGlobalEffects(GeoPoint geoPoint, Vector v, int level, double k ,double alfa){
        Color color = Color.BLACK;
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Material material = geoPoint.geometry.getMaterial();
        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(geoPoint, v,n), level, material.kR, kkr, alfa);
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(geoPoint, v, n), level, material.kT, kkt, alfa));
        return color;
    }

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
        return new Ray(geoPoint.point, v,n);
    }

    /**
     * @param geoPoint
     * @param v
     * @return new ray for the reflected
     */
    private Ray constructReflectedRay(GeoPoint geoPoint, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n))).normalize();
        return new Ray(geoPoint.point, r,n);
    }


//    private Color calcColor(GeoPoint point, Ray ray) {
//        return _scene._ambientLight.get_intensity()
//                .add(point.geometry.getEmission()
//                .add(calcLocalEffects(point, ray)));
//    }

    /**
     * @param intersection
     * @param ray
     * @return color with local effect.
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, double k, double alfa) {
        Vector v = ray.getDir(); //direction of ray
        Vector n = intersection.geometry.getNormal(intersection.point); //normal of geometry and intersection point
        double nv = alignZero(n.dotProduct(v)); // n*v
        if (isZero(nv)) {
            return Color.BLACK;
        }
        Material material = intersection.geometry.getMaterial(); //  material of geometry
        int nShininess = material.nShininess;
        double kd = material.kD; //diffuse of material
        double ks = material.kS; //specular of material
        Color color = Color.BLACK;
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
        Ray lightRay = new Ray(intersection.point, lightDirection, n);
        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, light.getDistance(intersection.point));
        if (intersections == null) return true;
        double lightDistance = light.getDistance(intersection.point);
        for (GeoPoint gp : intersections) {
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
        Ray lightRay = new Ray(point, lightDirection, n);
        double lightDistance = light.getDistance(point);
        var intersections = _scene.geometries.findGeoIntersections(lightRay, lightDistance);
        if (intersections == null)
            return 1.0;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            ktr *= gp.geometry.getMaterial().getKt();
            if (ktr < MIN_CALC_COLOR_K)
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
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n)))).normalized();
        double minusVr = v.scale(-1).dotProduct(r);
        return lightIntensity.scale(ks * Math.pow(Math.max(0, minusVr), nShininess));

    }

    /**
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return diffuse effect
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor = kd * Math.abs(l.dotProduct(n));
        return lightIntensity.scale(factor);

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
    private double callTransparency(LightSource light, Vector l, Vector n, GeoPoint geopoint, double angle){
        Point3D lightP0 = light.getPosition(); // p0 of the light
        if(lightP0 == null || angle==0)  // if the light==direction light
            return transparency(light, l, n, geopoint);

        //calculate radius of the circle
        double distance = lightP0.distance(geopoint.point); // distance between light and point
        double radius = distance * Math.tan(Math.toRadians(angle)); // radius around the light
        double sumKtr = 0; // sum of all ktr from random point around the light

        //find points on the circle at the same plane.
        Vector v = new Vector(-l.getHead().getY(),l.getHead().getX(),0).normalized(); // vector in the plane
        Vector w=l.crossProduct(v); // vector ib the plane

        //send ray to calculate and sum the ktr
        for(int i=0; i<81;i++) { // find 81 point around the light

            double t = 2 * PI * Math.random(); //
            double r = radius * Math.random(); // radius random

            double alpha = r * Math.cos(t); // parameter from random point
            double beta = r * Math.sin(t); // parameter from random point

            Point3D randomPoint = lightP0.add(v.scale(alpha).add(w.scale(beta))); //random point around light
            Vector randomL = geopoint.point.subtract(randomPoint).normalized(); // random vector around light
            sumKtr += transparency(light, randomL, n, geopoint); // sum of all ktr from random point around the light
        }
        return sumKtr/81; // average of ktr
    }
}
