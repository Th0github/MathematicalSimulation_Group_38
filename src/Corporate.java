public class Corporate extends PoissonProcess implements CustomerServiceAgent {
    //Average rate of call per minute
    private final double RATE_EIGHT_am_to_SIX_pm = 1.0;
    private final double RATE_SIX_am_to_EIGHT_am = 0.2;

    //Time per hour, minute, etc.
    private final double MINUTE_TO_HOUR = 60;
    private final double SECOND_TO_HOUR = 3600; //this is what we will be using

    //TODO: make this process not equal consumer corporate id.
    private int corporate_id;

    public Corporate(int corporate_id) {this.corporate_id = corporate_id; }

    @Override
    public boolean handle() {
        return true;
    }

    @Override
    public double getLambda () {
        return super.getLambda();
    }

    @Override
    public int getID() {
        return corporate_id;
    }

    public String toString() {
        return "Process time is " + getLambda();
    }

}