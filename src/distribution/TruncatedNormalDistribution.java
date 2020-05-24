package distribution;
import java.util.Random;


/**
 * @author ohnoyes
 *
 * designed to create random values following a truncated normal distribution
 */


public class TruncatedNormalDistribution {
    private double averageTime;
    private double standardDeviation;
    private double truncMin;
    private Random rand = new Random();

    // insert the correct mean, standard deviation and minimum
    public TruncatedNormalDistribution(double avTime, double stDev, double minimum){
        this.averageTime = avTime;
        this.standardDeviation = stDev;
        this.truncMin = minimum;
    }

    public double generate(){
        double callTime;
        double avT = this.averageTime;
        double std = this.standardDeviation;

        callTime = rand.nextGaussian() * std + avT;

        if(callTime >= this.truncMin){
            return callTime;
        }

        return 0;
    }
}
