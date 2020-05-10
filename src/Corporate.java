public class Corporate extends PoissonProcess implements CustomerServiceAgent {


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