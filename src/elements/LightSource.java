package elements;

import primitives.*;

public interface LightSource {
    /**
     * intarface of light source
     */


    /**
     * @param p
     * @return the intensity of light source in point p
     */
    public Color getIntensity(Point3D p);

    /**
     * @param p
     * @return the vector of light, normalized
     */
    public Vector getL(Point3D p);

}
