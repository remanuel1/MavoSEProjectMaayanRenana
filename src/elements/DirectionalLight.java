package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for directional Light
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * constructor for directional Light
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    @Override
    public Vector getL(Point3D p) {
        return direction.normalized();
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }
}
