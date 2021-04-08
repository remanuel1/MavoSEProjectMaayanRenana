package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static primitives.Util.isZero;
/**
 * Unit tests for primitives.Vector class
 *
 */

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    @Test
    void testAdd() {
        Vector vr = v1.add(v2);
        assertEquals(new Vector(-1, -2, -3), vr, "add() wrong result for coord");

        try{
            v1.add(new Vector(-1, -2, -3));
            throw new IllegalArgumentException("the operator subtract didn't work");
        }
        catch(Exception e){
        }


    }

    @Test
    void testSubtract() {
        Vector vr = v1.subtract(v2);
        assertEquals(new Vector(3, 6, 9), vr, "subtract() wrong result for coord");

        try{
            v1.subtract(new Vector(1, 2, 3));
            throw new IllegalArgumentException("the operator subtract didn't work");
        }
        catch(Exception e){
        }
    }

    @Test
    void testScale() {
        Vector vr = v1.scale(4);
        assertEquals(new Vector(4, 8, 12), vr, "scale() wrong result for coord");

        try{
            v1.scale(0);
            throw new IllegalArgumentException("the operator scale didn't work");
        }
        catch(Exception e){
        }
    }

    @Test
    void testDotProduct() {

        assertTrue(isZero(v1.dotProduct(v3)), "dotProduct() for orthogonal vectors is not zero");
        assertTrue(isZero(v1.dotProduct(v2) + 28), "dotProduct() wrong value");

    }

    @Test
    void testVectorZero(){
        assertThrows(IllegalArgumentException.class, ()-> new Vector(0,0,0), "nununu!");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {

        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(),
                vr.length(),
                0.00001,
                "crossProduct() wrong result length");


        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        assertThrows(IllegalArgumentException.class,
                    () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
        // try {
        //     v1.crossProduct(v2);
        //     fail("crossProduct() for parallel vectors does not throw an exception");
        // } catch (Exception e) {}
    }


    @Test
    void testLengthSquared() {
        assertTrue (isZero(v1.lengthSquared()-14), "lengthSquared() wrong value");

    }

    @Test
    void testLength() {
        Vector vCopy = new Vector(v1.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertTrue (isZero(vCopyNormalize.length() - 1), "ERROR: normalize() result is not a unit vector");
    }

    @Test
    void testNormalize() {
        // test vector normalization vs vector length and cross-product

        Vector vCopy = new Vector(v1.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals (vCopy, vCopyNormalize, "ERROR: normalize() function creates a new vector");

    }

    @Test
    void testNormalized() {
        Vector u = v1.normalized();
        assertEquals(u, new Vector(1/(Math.sqrt(14)),2/(Math.sqrt(14)),3/(Math.sqrt(14))), "ERROR: normalizated() function does not create a new vector");
    }
}