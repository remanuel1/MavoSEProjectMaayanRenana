package scene;

import elements.*;
import geometries.*;
import primitives.*;
import java.util.*;



public class Scene {
    public String _name;
    public Color _background=Color.BLACK;
    public AmbientLight _ambientLight = new AmbientLight(Color.BLACK, 0);
    public Geometries geometries;
    public List<LightSource> lights;

    /**
     * constructor of scene
     * @param name
     */
    public Scene(String name) {
        _name = name;
        geometries = new Geometries();
        lights = new LinkedList<LightSource>();
    }

    /**
     * set the background
     * @param background
     * @return this scene
     */
    public Scene setBackground(Color background) {
        _background = background;
        return this;
    }

    /**
     * set the ambientLight
     * @param ambientLight
     * @return this scene
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        _ambientLight = ambientLight;
        return this;
    }

    /**
     * set the geometries
     * @param geometries
     * @return this scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * set the light
     * @param lights
     * @return this scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
