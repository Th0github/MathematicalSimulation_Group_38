import java.util.Random;

/**
 * @author ohnoyes
 */
public class Consumer extends PoissonProcess implements CustomerServiceAgent  {
    private int consumer_id;

    //Average rate of call per minute
    private final double AVERAGE_RATE_PER_MINUTE = 2;
    private final double AVERAGE_RATE_PER_MINUTE_3_AM = 0.2;

    //Time per hour, minute, etc.
    private final double MINUTE_TO_HOUR = 60;
    private final double SECOND_TO_HOUR = 3600;

    private boolean isIdle;

    //service times
    private final double AVERAGE_SERVICE_TIME = 1.2;
    private final double STANDARD_DEV_SERVICE_TIME = 35.0/60;
    private final double MIN_CALL_TIME = 25.0/60;

    /**
     * Initialize consumer
     */
    public Consumer(int consumer_id) {
        this.consumer_id = consumer_id;
    }


    /**
     * Coll is only handled if consumer service representative is idle, i.e. not taking another call
     * @return the value of isIdle
     */
    @Override
    public boolean handle() {
        return isIdle;
    }

    /**
     * getter
     * @return
     */
    @Override
    public double getLambda() {
        return super.getLambda();
    }

    @Override
    public int getID() {
        return consumer_id;
    }

    public String toString() {
        return "Consumer ID: " + consumer_id + " was handled at lambda = " + getLambda();
    }


}