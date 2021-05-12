package renderer;

import elements.LightSource;
import geometries.*;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

import geometries.Intersectable.GeoPoint;

public class BasicRayTracer extends RayTracerBase {

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

        List<GeoPoint> allIntersections = _scene._geometries.findGeoIntersections(ray); //all intersection points

        if (allIntersections == null) { // if there is no intersection points
            return _scene._background; // return the background of scene
        }
        GeoPoint closestPoint = ray.getClosestGeoPoint(allIntersections); // find the closest point
        return calcColor(closestPoint, ray); // return the color of this closest point

    }

    /**
     * calculate the color of point
     *
     * @param point
     * @return color of this point
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return _scene._ambientLight.getIntensity()
                .add(point.geometry.getEmission()
                .add(calcLocalEffects(point, ray)));
    }

    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (isZero(nv)) {
            return Color.BLACK;
        }
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD;
        double ks = material.kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));;
            }
        }
        return color;
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n)))).normalized();
        double minusVr = v.scale(-1).dotProduct(r);
        return lightIntensity.scale(ks*Math.pow(Math.max(0, minusVr), nShininess));

    }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor = kd * Math.abs(l.dotProduct(n));
        return lightIntensity.scale(factor);

    }
}
