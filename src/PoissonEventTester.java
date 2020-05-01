import java.util.Random;

public class PoissonEventTester {
    public static void main(String[] args) {
        double lambda = 2;
        double sampleCount = 10;
        PoissonProcess pois = new PoissonProcess(lambda, new Random());

        for(int i = 1; i <= sampleCount; i++) {
            double p = pois.events();
            System.out.println("Time at: " + i + " had " + p + " calls");
        }
    }
}
