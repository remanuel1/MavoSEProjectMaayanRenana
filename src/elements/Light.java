package elements;

import primitives.*;

abstract class Light {
    /**
     * abstract class for the light
     */
    protected Color intensity;

    /**
     * constructor for Light
     * @param intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * get the intensity of light
     * @return intensity
     */
    public Color getIntensity() {
        return intensity;
    }
}
