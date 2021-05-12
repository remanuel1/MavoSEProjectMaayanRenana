package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * class for point light
 */
public class PointLight extends Light implements LightSource{

    private Point3D position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * constructor for point light
     * @param intensity
     * @param position
     */
    public PointLight(Color intensity, Point3D position) {
        super(intensity);
        this.position = position;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double denominator, distance;
        distance = position.distance(p);
        denominator = kC + kL*distance + kQ*distance*distance;
        return intensity.reduce(denominator);
    }

    @Override
    public Vector getL(Point3D p) {
        if(p.equals(position))
            return null;
        return position.subtract(p).normalized();
    }

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
