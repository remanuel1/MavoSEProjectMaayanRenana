package renderer;

import primitives.*;
import scene.Scene;

/**
 * abstract class for ray tracer - base
 */
public abstract class RayTracerBase {
    protected Scene _scene;

    /**
     * constructor of ray tracer base
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        _scene = scene;
    }

    /**
     * @param ray
     * @return
     */
    public abstract Color traceRay(Ray ray , double alpha);
}
