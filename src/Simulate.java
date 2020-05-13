import java.util.ArrayList;
import java.util.Random;

/**
 * @author ohnoyes
 */
public class Simulate {
    public static void main(String[] args) {
        //Uniform Distribution
        Random uniformVariate = new Random();
        //Normal Distribution
        NormalNumberGenerator rng = new NormalNumberGenerator();
        Corporate corporate = new Corporate();
        Consumer consumer = new Consumer();

        //Arrival rates from calls: Poisson Process
        //Using Normal variate causes issues.
        PoissonProcess consumerArrival = new PoissonProcess(consumer.AVERAGE_RATE_PER_MINUTE, uniformVariate);
        PoissonProcess corporateArrival = new PoissonProcess(corporate.RATE_EIGHT_am_to_SIX_pm, uniformVariate);
        for(int i = 0; i <= 1000; i++) {
            System.out.println(consumerArrival.events());
        }
    }
}
