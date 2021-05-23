package elements;

import primitives.Color;

/**
 * class for ambient light source
 */
public class AmbientLight extends Light {

    /**
     * constructor
     * @param Ia - intensity
     * @param Ka
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }




}



