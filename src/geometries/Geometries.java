package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{

    private List<Intersectable> _intersectables = new LinkedList<>();

    public Geometries(){ }

    public Geometries(Intersectable... geometries){
        add(geometries);

    }

    public void add(Intersectable... geometries){
        Collections.addAll(_intersectables, geometries);
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {

        List<Point3D> result = null;
        for (Intersectable item : _intersectables) {
            //get intersections points of a particular item from _intersectables
            List<Point3D> itempoints = item.findIntersections(ray);
            if(itempoints!= null){
                //first time initialize result to new LinkedList
                if(result== null){
                    result= new LinkedList<>();
                }
                //add all item points to the resulting list
                result.addAll(itempoints);
            }
        }
        return result;
    }

    public List<Intersectable> get_intersectables() {
        return _intersectables;
    }
}
