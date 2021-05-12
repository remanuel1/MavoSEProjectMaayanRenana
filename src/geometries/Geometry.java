package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface for all the geometries that have a normal from them
 */
public abstract class Geometry implements Intersectable{

    protected Color emission = Color.BLACK;
    private Material _material = new Material();
    /**
     * @param point should be null for flat geometries
     * @return the normal to the geometry
     */
    abstract public Vector getNormal(Point3D point);

    /**
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * @param emission
     * @return set the emission and return all this geometry - bilder
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * @param material
     * @return set the material and return all this geometry - bilder
     */
    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    public Material getMaterial() {
        return _material;
    }
}
