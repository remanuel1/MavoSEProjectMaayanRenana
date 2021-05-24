package elements;

import primitives.*;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class SpotLight extends PointLight {

    private Vector direction;

    /**
     * constructor for point light
     * @param intensity
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
       Color intensity= super.getIntensity(p);
        double factor = alignZero(Math.max(0, direction.dotProduct(getL(p))));
       return intensity.scale(factor);
    }
}
