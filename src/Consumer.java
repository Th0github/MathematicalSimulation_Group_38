import java.util.Random;

public class Consumer extends PoissonProcess implements CustomerServiceAgent  {
    private int consumer_id;

    //Average rate of call per minute
    private final double RATE_EIGHT_am_to_SIX_pm = 1.0;
    private final double RATE_SIX_am_to_EIGHT_am = 0.2;

    /**
     * Initialize consumer
     */
    public Consumer(int consumer_id) {
        this.consumer_id = consumer_id;
    }


    @Override
    public boolean handle() {
        return true;
    }


    @Override
    public double getLambda() {
        return super.getLambda();
    }

    public String toString() {
        return "Consumer ID: " + consumer_id + " was handled at lambda = " + getLambda();
    }


}