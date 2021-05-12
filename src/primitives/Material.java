package primitives;

/**
 * class for material with kD, ks, nShininess
 */
public class Material {

    public double kD = 0; //diffuse
    public double kS = 0; //specular
    public int nShininess = 0;

    /**
     * set the kD
     * @param kD
     * @return this material
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * set the kS
     * @param kS
     * @return this material
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * set the nShininess
     * @param nShininess
     * @return this material
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}

