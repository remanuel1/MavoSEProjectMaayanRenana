package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * class for Geometries
 */
public class Geometries implements Intersectable {

    private List<Intersectable> _intersectables = new LinkedList<>();

    public Geometries(Intersectable... geometries) {
        add(geometries);

    }

    /**
     * add Intersectable that get in param to list of Intersectable
     *
     * @param geometries
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(_intersectables, geometries);
    }


    /**
     * @param ray
     * @return Intersections GeoPoint between ray and the plane
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;

        for (Intersectable geometry : _intersectables) {
            List<GeoPoint> geoIntersections = geometry.findGeoIntersections(ray,maxDistance);
            //if there are elements in geoIntersections – add them to intersections
            if (geoIntersections != null) {
                //first time initialize result to new LinkedList
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                //add all item points to the resulting list
                intersections.addAll(geoIntersections);
            }

        }
        return intersections;

    }

        /**
         * @return get list of intersectables point
         */
        public List<Intersectable> get_intersectables () {
            return _intersectables;

        }




}
