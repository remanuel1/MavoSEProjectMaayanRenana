package elements;

import primitives.*;

abstract class Light {
    /**
     * abstract class for the light
     */
    protected final Color _intensity;

    /**
     * constructor for Light
     * @param intensity
     */
    protected Light(Color intensity) {
        _intensity = intensity;
    }

    /**
     * get the intensity of light
     * @return intensity
     */
    public Color get_intensity() {
        return _intensity;
    }
}
