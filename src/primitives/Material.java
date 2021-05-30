package primitives;

/**
 * class for material with kD, ks, nShininess
 */
public class Material {

    public double kD = 0; //diffuse
    public double kS = 0; //specular
    public int nShininess = 0;
    public double kR=0; // Reflection
    public double kT=0; // Refraction

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
     * set kR
     * @param kR
     * @return this material
     */
    public Material setKr(double kR) {
        this.kR = kR;
        return this;
    }

    /**
     * set kT
     * @param kT
     * @return this material
     */
    public Material setKt(double kT) {
        this.kT = kT;
        return this;
    }

    /**
     * set the nShininess
     * @param nShininess
     * @return this material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    public double getKt() {
        return kT;
    }

    public double getKs() {
        return kS;
    }
}

