package elements;

import primitives.Color;

/**
 * class for ambient light source
 */
public class AmbientLight extends Light {

    /**
     * constructor
     * @param Ia - color of light
     * @param Ka - intensity
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }




}



