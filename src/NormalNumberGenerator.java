/**
 * @author ohnoyes
 */
public class NormalNumberGenerator {
    public double r, x, y, z;
    /**
     * Uses the Box and Muller method for generating Normal random numbers.
     */
    public NormalNumberGenerator() {

        // find a uniform random point (x, y) inside unit circle
        do {
            x = 2.0 * Math.random() - 1.0;
            y = 2.0 * Math.random() - 1.0;
            r = x*x + y*y;
        } while (r > 1 || r == 0);


        // formula for Normal Distribution z
        z = x * Math.sqrt(-2.0 * Math.log(r) / r);

        // print it
        System.out.println(z);
    }

    /**
     * generates the random variate on the normal distribution
     * @return the generated random variate
     */
    public double generate(){
        return this.z;
    }
}
