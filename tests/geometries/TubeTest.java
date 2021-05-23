package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TubeTest {

    @Test
    void testGetNormal() {
        Tube tube = new Tube(
                new Ray(new Point3D(1,1,1), new Vector(1,0,0)),
                4);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct tube with vertices in correct order
            Vector p = new Vector(0, 3, 4);
            assertEquals(tube.getNormal(new Point3D(3,4,5)), p.normalize(), "get normal not work");


        // =============== Boundary Values Tests ==================
        // TC10: The point "Between in front of the head of the ray"
            double t = new Point3D(1,1, 2).subtract(tube.getAxisRay().getP0()).dotProduct(tube.getAxisRay().getDir());
            if(Util.isZero(t)){
                assertEquals(tube.getNormal(new Point3D(1,1,2)), new Vector(0,0,1), "Tube get normal not work");
            }
            else
                throw new IllegalArgumentException("Tube get normal not work");

    }
}