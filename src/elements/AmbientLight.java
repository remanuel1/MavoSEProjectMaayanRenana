package elements;

import primitives.Color;

public class AmbientLight {
    /**
     * class for ambient light source
     */
    private Color _intensity;

    /**
     * constructor
     * @param Ia - intensity
     * @param Ka
     */
    public AmbientLight(Color Ia, double Ka) {
        _intensity = Ia.scale(Ka);
    }

    /**
     * @return intensity of Ambient Light
     */
    public Color getIntensity() {
        return _intensity;
    }
}
