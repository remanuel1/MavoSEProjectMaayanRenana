package geometries;

import primitives.Point3D;
import primitives.Ray;
import java.util.List;
import java.util.stream.Collectors;

/**
 * i
 */
public interface Intersectable {

    /**
     *  GeoPoint is inner class
     *  that connects a point of intersection to a shape
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * constructor that get Geometry and Point
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        /**
         * equals between two GeoPoint
         * @param o
         * @return if they equals or not
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

    }

    /**
     * @param ray
     * @return Intersections point between ray and shape
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).collect(Collectors.toList());
    }


    /**
     * @param ray
     * @return Intersections GeoPoint between ray and the plane
     */
    List<GeoPoint> findGeoIntersections (Ray ray, double maxDistance);

    default List<GeoPoint> findGeoIntersections (Ray ray){
        return findGeoIntersections(ray,Double.POSITIVE_INFINITY);
    }

}
