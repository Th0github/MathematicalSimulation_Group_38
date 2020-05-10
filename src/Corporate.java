public class Corporate extends PoissonProcess implements CustomerServiceAgent {
    //Average rate of call per minute
    private final double RATE_EIGHT_am_to_SIX_pm = 1.0;
    private final double RATE_SIX_am_to_EIGHT_am = 0.2;

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