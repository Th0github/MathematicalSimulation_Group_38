/**
 * @author ohnoyes
 */
public class NormalNumberGenerator {
    /**
     * Uses the Box and Muller method for generating Normal random numbers.
     */
    public NormalNumberGenerator() {
        double r, x, y;

        // find a uniform random point (x, y) inside unit circle
        do {
            x = 2.0 * Math.random() - 1.0;
            y = 2.0 * Math.random() - 1.0;
            r = x*x + y*y;
        } while (r > 1 || r == 0);    // loop executed 4 / pi = 1.273.. times on average
        // http://en.wikipedia.org/wiki/Box-Muller_transform


        // apply the Box-Muller formula to get standard Gaussian z
        double z = x * Math.sqrt(-2.0 * Math.log(r) / r);

        // print it to standard output
        System.out.println(z);
    }
}
