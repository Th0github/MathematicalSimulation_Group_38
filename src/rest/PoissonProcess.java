package rest;

import java.util.Random;

public class PoissonProcess {
    private double lambda;
    private Random rand;

    /**
     * @param lambda is rate
     * @param rand util.Random that uses LCG from: assignment 1
     */
    public PoissonProcess(double lambda, Random rand) {
        this.lambda = lambda;
        this.rand = rand;
    }

    /**
     * Default constructor
     */
    public PoissonProcess() {
        this.lambda = 1;
        this.rand = new Random();
    }

    /**
     * getter
     * @return rand
     */
    public Random getRand() {
        return rand;
    }

    /**
     * getter
     * @return lambda
     */
    public double getLambda() {
        return lambda;
    }

    /**
     * Get time for next event
     * @return random inter-arrival time
     */
    public double timeNextEvent() {
        return - Math.log(1.0 - rand.nextDouble()) / lambda;
    }

    /**
     * Helper method: default value of events()
     * @return events(1)
     */
    public int events() {
        return events(1);
    }

    /**
     * count of occurence in time t w.r.t. unit time
     * @return time length of time interval
     */
    public int events(double time) {
        int n = 0;
        double p = Math.exp(-lambda * time);
        double s = p;
        double u = rand.nextDouble();
        while (u > s) {
            n++;
            p*=lambda/n;
            s+=p;
        }
        return n;
    }
}
