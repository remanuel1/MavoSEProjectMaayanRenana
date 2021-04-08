package primitives;

import java.util.Objects;

/**
 * Ray class.
 * there are point and vector
 */
public class Ray {
    final Point3D _p0;
    final Vector _dir;

    //constructor
    public Ray(Point3D p0, Vector dir) {
        _dir = dir.normalize();
        _p0 = p0;
        //double toNormal = Math.sqrt(p0._x._coord * p0._x._coord + p0._y._coord * p0._y._coord + p0._z._coord * p0._z._coord);
        //if (toNormal!=1) {

        //    double pXTemp = p0._x._coord * (1 / toNormal);
        //    double pYTemp = p0._y._coord * (1 / toNormal);
        //    double pZTemp = p0._z._coord * (1 / toNormal);
       //     _p0 = new Point3D(pXTemp, pYTemp, pZTemp);
       // }
       // else


    }

    public Point3D getP0() {
        return _p0;
    }

    public Vector getDir() {
        return new Vector(_dir._head);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }

    @Override
    public String toString() {
        return "Ray:" +
                "p0=" + _p0 +
                ", dir=" + _dir;
    }
}
