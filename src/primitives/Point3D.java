package primitives;


public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;

    public final static Point3D ZERO = new Point3D(0, 0, 0);

    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = x;
        _y = y;
        _z = z;
    }

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
}
