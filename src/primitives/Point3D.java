package primitives;

/**
 * point 3D class
 */
public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    public final static Point3D ZERO = new Point3D(0, 0, 0);

    //constructor
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = x;
        _y = y;
        _z = z;
    }

    //constructor
    public Point3D(double x, double y, double z) {
        //this(new Coordinate(x), new Coordinate(y), new Coordinate(z));
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "(" + _x + "," + ", " + _z + ")";
    }

    //subtract func. return new vector this-pt2
    public Vector subtract(Point3D pt2) {
        Point3D head = new Point3D(
                _x._coord-pt2._x._coord,
                _y._coord-pt2._y._coord,
                _z._coord-pt2._z._coord

        );
        if(ZERO.equals(head)) {
            throw new IllegalArgumentException("vector head caanot be point (0,0,0)");
        }
        return new Vector(head);
    }

    //add func. return new point this+v
    public Point3D add (Vector v){
        Point3D p = new Point3D(
                _x._coord+v._head._x._coord,
                _y._coord+v._head._y._coord,
                _z._coord+v._head._z._coord
        );
        return p;
    }

    //distance Squared func. return x^2 + Y^2 + Z^2
    public double distanceSquared (Point3D p){
        return  (_x._coord-p._x._coord)*(_x._coord-p._x._coord) +
                (_y._coord-p._y._coord)*(_y._coord-p._y._coord) +
                (_z._coord-p._z._coord)*(_z._coord-p._z._coord);

    }

    // distance func. return sqrt of distanceSquared
    public double distance (Point3D p) {
        return Math.sqrt(distanceSquared(p));
    }
}
