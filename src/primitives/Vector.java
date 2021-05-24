package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;

/**
 * vector class. there is point for the head
 */
public class Vector {
    Point3D _head;

    public Point3D getHead() {
        return _head;
    }

    //constructor
    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("vector head caanot be point (0,0,0)");
        }
        _head = head;
    }

    //constructor
    public Vector(double x, double y, double z) {
        /*Point3D head = new Point3D(x,y,z);
        if(ZERO.equals(head)) {
            throw new IllegalArgumentException("vector head caanot be point (0,0,0)")
        }
        _head = head;*/
        this(new Point3D(x, y, z));
    }

    //add func. return new vector this+v
    public Vector add(Vector v) {
        return new Vector(v._head._x._coord + _head._x._coord,
                v._head._y._coord + _head._y._coord,
                v._head._z._coord + _head._z._coord);
    }

    //subtract func. return new vector this-v
    public Vector subtract(Vector v) {
        return new Vector(_head._x._coord - v._head._x._coord,
                _head._y._coord - v._head._y._coord,
                _head._z._coord - v._head._z._coord);
    }

    //scale func. return new vector Scalar multiplication
    public Vector scale(double num) {
        return new Vector(
                _head._x._coord * num,
                _head._y._coord * num,
                _head._z._coord * num);
    }

    //dot Product func. return dot Product of this*v
    public double dotProduct(Vector v) {
        return _head._x._coord * v._head._x._coord +
                _head._y._coord * v._head._y._coord +
                _head._z._coord * v._head._z._coord;
    }

    //cross Product func. return cross Product of this*v
    public Vector crossProduct(Vector v) {
        double u1 = _head._x._coord;
        double u2 = _head._y._coord;
        double u3 = _head._z._coord;

        double v1 = v._head._x._coord;
        double v2 = v._head._y._coord;
        double v3 = v._head._z._coord;

        return new Vector(
                u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1);

    }

    //lengthSquared func. return x^2 + Y^2 + Z^2
    public double lengthSquared() {
        return _head._x._coord * _head._x._coord +
                _head._y._coord * _head._y._coord +
                _head._z._coord * _head._z._coord;
    }

    //length func. return sqrt lengthSquared func
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    // normalize func. return a normal vector
    public Vector normalize() {
        double len = 1d / length();
        if (len != 1) {
            double x = _head._x._coord * len;
            double y = _head._y._coord * len;
            double z = _head._z._coord * len;
            _head = new Point3D(x, y, z);
        }
        return this;
    }

    // normalized func. return a new normal vector
    public Vector normalized() {
        Vector v = new Vector(_head);
        return v.normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(vector._head);
    }

}
