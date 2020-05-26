package Engine;

import Products.Consumer;
import Products.Corporate;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;

/**
 *	Example program for using eventlists
 *	@author Joel Karel
 *	@version %I%, %G%
 */

public class Simulation {

    public CEventList list;
    public Queue queue;
    public Source source;
    public Sink sink;
    public Machine mach;
    private static Corporate corporate = new Corporate();
    private static Consumer consumer = new Consumer();
    private static ArrayList<Machine> shift1, shift2, shift3;
    private static final int NUMBEROFDAYS = 7;
	

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {



        // Create an eventlist
        CEventList l = new CEventList();


        // A queue for the machine
        Queue q1 = new Queue();


        // A source
        Source s1 = new Source(q1,l,"Corporate Customer.Source", corporate.AGENTTYPE);
        Source s2 = new Source(q1,l, "Consumer Customer.Source", consumer.AGENTTYPE);

        // A sink
        Sink si1 = new Sink("Corporate Customers Sink");

        // create the roster and the respective machines
        Roster roster = new Roster(q1, si1, l);
        //find the best strategy by trial and error for the lowest people possible
        int cost = roster.createRoster(5,4,2,4,4,4);
        l.setRoster(roster);

        // start the eventlist
        l.start(1440*1); //maximum time

        int totalCustomers = s1.getTotalProducts() + s2.getTotalProducts();
        int totalHandled = si1.getNumber();
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("total customers: " + totalCustomers);
        System.out.println("total handled: " + totalHandled);
        System.out.println("difference = " + (totalCustomers-totalHandled));
        System.out.println("--------------------------");
        showStats(si1,totalCustomers);

    }


    public static void showStats(Sink si1, int totalCust) {

        double[] waitingTimesCorp = si1.getWaitingTimeCorp();
        double[] waitingTimesCons = si1.getWaitingTimeCons();

        int waitTimeCorp3 = 0;
        int waitTimeCorp7 = 0;
        int waitTimeCons5 = 0;
        int waitTimeCons10 = 0;

        for (int i = 0; i < waitingTimesCorp.length; i++) {
            if (waitingTimesCorp[i] >= 3) {
                waitTimeCorp3++;
            }
            if (waitingTimesCorp[i] >= 7) {
                waitTimeCorp7++;
            }
        }

        for (int i = 0; i < waitingTimesCons.length; i++) {
            if (waitingTimesCons[i] >= 5) {
                waitTimeCons5++;
            }
            if (waitingTimesCons[i] >= 10) {
                waitTimeCons10++;
            }
        }

        double percentageCorp3 = (1 - (double)waitTimeCorp3 / waitingTimesCorp.length) * 100 ;
        double percentageCorp7 = (1 - (double) waitTimeCorp7 / waitingTimesCorp.length) * 100;
        double percentageCons5 = (1 - (double) waitTimeCons5 / waitingTimesCons.length) * 100;
        double percentageCons10 = (1 - (double) waitTimeCons10 / waitingTimesCons.length) * 100;
        double percentageUnanswered = (1 - (double) (waitingTimesCons.length + waitingTimesCorp.length)/totalCust) * 100;

        System.out.println("");
        System.out.println();
        System.out.println("percentages");
        System.out.println("--------------------------------------");
        System.out.println("percentage under 3 Corp = " + percentageCorp3);
        System.out.println("percentage under 7 Corp = " + percentageCorp7);
        System.out.println("percentage under 5 Cons = " + percentageCons5);
        System.out.println("percentage under 10 Cons = " + percentageCons10);
        System.out.println("percentage unanswered = " + percentageUnanswered);
        System.out.println("--------------------------------------");

        if (percentageCons5 >= 90 && percentageCons10 >= 95 && percentageCorp3 >= 95 && percentageCorp7 >= 99 ){
            if (percentageUnanswered < 0.2) {
                System.out.println("VALID STRATEGY FOR THE SIMULATION");
            }
        }
        else System.out.println("INVALID STRATEGY FOR THE SIMULATION");

    }

}
