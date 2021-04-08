package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    @Test
    void testGetNormal() {
        Sphere s = new Sphere(new Point3D(1,1,1), 5);
        assertEquals(s.getNormal(new Point3D(6,6,6)),
                new Vector((Math.sqrt(3)/3), (Math.sqrt(3)/3), (Math.sqrt(3)/3)),
                "Sphere - get normal not work");

    }
}