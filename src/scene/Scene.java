package scene;

import elements.*;
import geometries.*;
import primitives.*;

public class Scene {
    public String _name;
    public Color _background=Color.BLACK;
    public AmbientLight _ambientLight = new AmbientLight(Color.BLACK, 0);
    public Geometries _geometries;

    /**
     * constructor of scene
     * @param name
     */
    public Scene(String name) {
        _name = name;
        _geometries = new Geometries();
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
        _geometries = geometries;
        return this;
    }

}
