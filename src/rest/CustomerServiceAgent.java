package rest;

public interface CustomerServiceAgent {
    boolean handle(); //takes the call
    double getLambda();
    int getID();
    String toString();

}
