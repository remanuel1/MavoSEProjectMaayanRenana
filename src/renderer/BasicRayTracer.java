package renderer;

import elements.LightSource;
import geometries.Geometries;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.*;

import geometries.Intersectable.GeoPoint;

public class BasicRayTracer extends RayTracerBase {

    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;


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
    public Color traceRay(Ray ray) {

        List<GeoPoint> allIntersections = _scene.geometries.findGeoIntersections(ray); //all intersection points

        if (allIntersections == null) { // if there is no intersection points
            return Color.BLACK; // return black
        }
        GeoPoint closestPoint = ray.getClosestGeoPoint(allIntersections); // find the closest point
        return calcColor(closestPoint, ray); // return the color of this closest point

    }

    /**
     * calculate the color of point
     * @param
     * @return color of this point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene._ambientLight.get_intensity());
    }

    /**
     * @param geoPoint
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = geoPoint.geometry.getEmission();
        color = color.add(calcLocalEffects(geoPoint, ray));
        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray.getDir(), level, k));
    }

    /*private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, double k) {
        Color color = Color.BLACK;
        Material material = geoPoint.geometry.getMaterial();
        double kkr = k * material.kR;


        if (kkr > MIN_CALC_COLOR_K) {
            Ray reflectedRay = constructReflectedRay(geoPoint, ray);
            List<GeoPoint> geoPointList1 = _scene.geometries.findGeoIntersections(reflectedRay);
            GeoPoint reflectedPoint = reflectedRay.getClosestGeoPoint(_scene.geometries.findGeoIntersections(ray));
            if (reflectedPoint != null) {
                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(material.kR));
            }
        }
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedRay = constructRefractedRay(geoPoint, ray);
            List<GeoPoint> geoPointList2 = _scene.geometries.findGeoIntersections(refractedRay);
            GeoPoint refractedPoint = refractedRay.getClosestGeoPoint(_scene.geometries.findGeoIntersections(ray));
            if (refractedPoint != null) {
                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(material.kT));
            }
        }
        return color;
    }*/

    private Color calcGlobalEffects(GeoPoint geoPoint, Vector v, int level, double k){
        Color color = Color.BLACK;
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Material material = geoPoint.geometry.getMaterial();
        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(geoPoint, v), level, material.kR, kkr);
        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(geoPoint, v), level, material.kT, kkt));
        return color;
    }

    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = ray.getClosestGeoPoint(_scene.geometries.findGeoIntersections(ray));
        return (gp == null ? Color.BLACK.add(_scene._background).scale(kx) : calcColor(gp, ray, level-1, kkx)).scale(kx);
    }

    /**
     * @param geoPoint
     * @param v
     * @return new ray for the refracted
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Vector v) {
        return new Ray(geoPoint.point, v);
    }

    /**
     * @param geoPoint
     * @param v
     * @return new ray for the reflected
     */
    private Ray constructReflectedRay(GeoPoint geoPoint, Vector v) {
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(geoPoint.point, r.normalize());
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
    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
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
                if (unshaded(lightSource, l, n, intersection)) {

                    Color lightIntensity = lightSource.getIntensity(intersection.point); // intensity of the light
                    // add all the local effect to the color
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint intersection) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(intersection.point, n, lightDirection);
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
}
