package Products;

/**
 * @author ohnoyes
 */
public class Corporate extends PoissonProcess implements CustomerServiceAgent {
    //cost per hour
    public final double COST = 60;
    public final String AGENTTYPE = "corp";

    //Average rate of call per minute
    public final double RATE_EIGHT_am_to_SIX_pm = 1.0;
    public final double RATE_SIX_am_to_EIGHT_am = 0.2;

    //Time per hour, minute, etc.
    private final double MINUTE_TO_HOUR = 60;
    private final double SECOND_TO_HOUR = 3600; //this is what we will be using

    //service times
    public final double AVERAGE_SERVICE_TIME = 3.6;
    public final double STANDARD_DEV_SERVICE_TIME = 1.2;
    public final double MIN_CALL_TIME = 45.0/60;

    public Corporate(){}

    @Override
    public boolean handle() {
        return true;
    }

    @Override
    public double getLambda () {
        return super.getLambda();
    }

    public String toString() {
        return "Process time is " + getLambda();
    }

}