import java.util.ArrayList;
import java.util.Random;

public class Simulate {
    public static void main(String[] args) {
        //Uniform Distribution
        Random uniformVariate = new Random();
        //Normal Distribution
        NormalNumberGenerator rng = new NormalNumberGenerator();
        Corporate corporate = new Corporate();
        Consumer consumer = new Consumer();
        int sampleCount = 100;
        //Three shifts, for now we just record 10 events, either increase row count for more data or use Arraylist
        double[][] timeShift = new double[3][sampleCount];

        //Arrival rates from calls: Poisson Process
        //Using Normal variate causes issues.
        PoissonProcess consumerArrival = new PoissonProcess(consumer.AVERAGE_RATE_PER_MINUTE, uniformVariate);
        PoissonProcess consumerArrivalThreeAM = new PoissonProcess(consumer.AVERAGE_RATE_PER_MINUTE_3_AM, uniformVariate);
        PoissonProcess corporateArrivalEightToSix = new PoissonProcess(corporate.RATE_EIGHT_am_to_SIX_pm, uniformVariate);
        PoissonProcess corporateArrivalSixToEight = new PoissonProcess(corporate.RATE_SIX_am_to_EIGHT_am, uniformVariate);

        System.out.println("Call recieved at first run: " + consumer.events());

        //TODO: simulate service time with TruncatedNormalDistribution.
    }
}
