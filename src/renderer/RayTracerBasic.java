package renderer;

import geometries.*;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

public class RayTracerBasic extends RayTracerBase {

    /**
     * constructor of ray tracer base
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    /**
     *
     * @param ray
     * @return color of closest intersection point
     */
    @Override
    public Color traceRay(Ray ray) {

        List<Point3D> allIntersections = _scene._geometries.findIntersections(ray); //all intersection points

        if (allIntersections == null) { // if there is no intersection points
            return _scene._background; // return the background of scene
        }
        Point3D closestPoint = ray.findClosestPoint(allIntersections); // find the closest point
        return calcColor(closestPoint); // return the color of this closest point

    }

    /**
     * calculate the color of point
     * @param point
     * @return color of this point
     */
    private Color calcColor(Point3D point){
        return _scene._ambientLight.getIntensity();
    }
}
