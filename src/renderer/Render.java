package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingFormatArgumentException;
import java.util.MissingResourceException;

/**
 * class that take scene and create the picture
 */
public class Render {
    ImageWriter _imageWriter;
    //Scene _scene;
    Camera _camera;
    RayTracerBase _rayTracerBase;

    /**
     *
     * @param imageWriter
     * @return render with new value for ImageWriter
     */
    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }


//    /**
//     * @param scene
//     * @return render with new value for scene
//     */
//    public Render setScene(Scene scene) {
//        _scene = scene;
//        return this;
//    }


    /**
     *
     * @param camera
     * @return render with new value for camera
     */
    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    /**
     *
     * @param rayTracerBase
     * @return render with new value for rayTracerBase
     */
    public Render setRayTracer(RayTracerBase rayTracerBase) {
        _rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * to check if all params have a value
     */
    public void renderImage(double alpha){
        if(_camera == null || _imageWriter == null || _rayTracerBase== null){
            throw new IllegalArgumentException("need to put values in all fields");
        }
        for (int i=0; i<_imageWriter.getNy(); i++){
            for(int j=0; j<_imageWriter.getNx(); j++){
                Ray ray = _camera.constructRayThroughPixel(_imageWriter.getNx(), _imageWriter.getNy(), j, i);
                Color colorPixel = _rayTracerBase.traceRay(ray, alpha);
                _imageWriter.writePixel(j,i, colorPixel);
            }
        }

    }

    /**
     * create a grid
     * @param interval // size
     * @param color
     */
    public void printGrid(int interval, Color color){
        if(_imageWriter==null){
            throw new IllegalArgumentException("image writer is null");
        }
        int nX = _imageWriter.getNx(); // column
        int nY = _imageWriter.getNy(); // row
        for (int i = 0; i < nY; i++) { // over all row
            for (int j = 0; j < nX; j++) { // over all column
                if (i % interval == 0 || j % interval == 0) { // check the border of pixel according interval
                    _imageWriter.writePixel(j, i, color); // write the pixel at color that get
                }
            }
        }
    }

    /**
     * write the image
     */
    public void writeToImage(){
        if(_imageWriter==null){
            throw new IllegalArgumentException("image writer is null");
        }
        _imageWriter.writeToImage();
    }

}
