import java.util.ArrayList;
import java.util.Random;

public class Simulate {
    public static void main(String[] args) {
        Corporate corporate = new Corporate();
        Consumer consumer = new Consumer();
        //Uniform Distribution
        Random uniformVariate = new Random();
        //Truncated Normal distribution
        TruncatedNormalDistribution servTimeCons = new TruncatedNormalDistribution(consumer.AVERAGE_SERVICE_TIME, consumer.STANDARD_DEV_SERVICE_TIME, consumer.MIN_CALL_TIME);
        TruncatedNormalDistribution servTimeCorp = new TruncatedNormalDistribution(corporate.AVERAGE_SERVICE_TIME, corporate.STANDARD_DEV_SERVICE_TIME, corporate.MIN_CALL_TIME);


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

        //TODO: we need to link everything together, get the service linked with the serviceTime
        System.out.println("duration of call if consumer = " + servTimeCons.generate());
        System.out.println("duration of call if corporate = " + servTimeCorp.generate());

    }
}
