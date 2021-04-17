package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/**
 * i
 */
public interface Intersectable {
    List<Point3D> findIntersections(Ray ray);
}
