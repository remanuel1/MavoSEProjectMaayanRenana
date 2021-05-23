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
        double factor = alignZero(Math.max(0, direction.dotProduct(getL(p))));
        if (!isZero(factor)){
            return super.getIntensity().scale(factor);
        }
        //throw new IllegalArgumentException("the angle is zero");
        return Color.BLACK;
    }

    //@Override
    //public Vector getL(Point3D p) {
    //    return direction.normalized();
    //}
}
