package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {
    Point3D p = new Point3D(1, 2, 3);

    /**
     * test of subtract point3D - point3D
     */
    @Test
    void testSubtract() {

        assertEquals(p.subtract(new Point3D(2,3,4)), new Vector(-1,-1,-1), "Point - Point does not work correctly");
        try{
            p.subtract(new Point3D(1, 2, 3));
            throw new IllegalArgumentException("the operator subtract didn't work");
        }
        catch(Exception e){
        }

    }

    /**
     * test of add point3D + point3D
     */
    @Test
    void testAdd() {

        assertEquals(p.add(new Vector(2,3,4)), new Point3D(3,5,7), "Point + vector does not work correctly");
        try{
            p.add(new Vector(-1, -2, -3));
            throw new IllegalArgumentException("the operator add didn't work");
        }
        catch(Exception e){
        }
    }

}