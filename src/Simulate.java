/**
 * @author ohnoyes
 */
public class Simulate {
    public static void main(String[] args) {
        NormalNumberGenerator rng = new NormalNumberGenerator();

        for (int i = 0; i <= 10; i++) {
            double normal = rng.generate();
            System.out.println(normal);
        }
    }
}
