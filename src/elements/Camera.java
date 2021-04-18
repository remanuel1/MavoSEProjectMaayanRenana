package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;


public class Camera {
    final Point3D _p0;
    final Vector _vT0;
    final Vector _vUp;
    final Vector _vRight;
    private double _distance;
    private double _width;
    private double _height;


    public Camera(Point3D p0, Vector vT0, Vector vUp) {
        _p0 = p0;
        _vT0 = vT0.normalized();
        _vUp = vUp.normalized();
        if(!isZero(_vT0.dotProduct(_vUp))) {
            throw new IllegalArgumentException("vTo and vUp not orthogonal");
        }
        _vRight = _vT0.crossProduct(_vUp);
    }

    public Point3D getP0() {
        return _p0;
    }

    public Vector getvT0() {
        return _vT0;
    }

    public Vector getvUp() { return _vUp; }

    public Vector getvRight() {
        return _vRight;
    }

    /**
     *
     * @param width
     * @param height
     * @return camera with new size of view plane - Builder pattern
     */
    public Camera setViewPlaneSize(double width, double height) {

        _width = width;
        _height = height;
        return this;
    }

    /**
     *
     * @param distance
     * @return camera with new distance - Builder pattern
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    /**
     *
     * @param nX - wight
     * @param nY - height
     * @param j - num pixels in column
     * @param i -  num pixels in row
     * @return ray that Through in pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
        Point3D Pc = _p0.add(_vT0.scale(_distance));
        double Ry = _height / nY;
        double Rx = _width / nX;
        double Yi = -(i-(nY-1)/2d)*Ry;
        double Xj = (j-(nX-1)/2d)*Rx;

        Point3D Pij = Pc;

        if(isZero(Yi) && isZero(Xj)) {
            // Pij = Pc
            return new Ray(_p0, Pij.subtract(_p0));
        }

        if(isZero(Xj)) {
            Pij = Pc.add(_vUp.scale(Yi));
            return new Ray(_p0, Pij.subtract(_p0));
        }

        if(isZero(Yi)) {
            Pij = Pc.add(_vRight.scale(Xj));
            return new Ray(_p0, Pij.subtract(_p0));
        }

        Pij = Pc.add(_vRight.scale(Xj).add(_vUp.scale(Yi)));
        return new Ray(_p0, Pij.subtract(_p0));
    }
}


