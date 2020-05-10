import java.util.Random;

public class Consumer extends PoissonProcess implements CustomerServiceAgent  {
    private int consumer_id;


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