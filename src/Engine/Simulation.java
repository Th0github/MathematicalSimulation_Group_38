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
    private static final int NUMBEROFDAYS = 1;
	

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
        int cost = createRoster(1,1,1,2,2,1,q1,si1,l);

        // start the eventlist
        l.start(1440*NUMBEROFDAYS); //maximum time

        int totalCustomers = s1.getTotalProducts() + s2.getTotalProducts();
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("total customers: " + totalCustomers);
        System.out.println("--------------------------");
        showStats(si1, totalCustomers);




    }

    public static int createRoster(int consumerShift1, int consumerShift2, int consumerShift3, int corporateShift1, int corporateShift2, int corporateShift3, Queue q1, Sink si1, CEventList l)
    {
        int shiftDuration = 8;
        int consumerCost = 35;
        int corporateCost = 60;

        shift1 = new ArrayList<>();
        shift2 = new ArrayList<>();
        shift3 = new ArrayList<>();

        for( int i = 0; i<consumerShift1; i++)
        {
            shift1.add(new Machine(q1,si1,l, "CSACons station shift1-"+(i+1), consumer.AGENTTYPE));
        }
        for( int i = 0; i<consumerShift2; i++)
        {
            shift2.add(new Machine(q1,si1,l, "CSACons station shift2-"+(i+1), consumer.AGENTTYPE));
        }
        for( int i = 0; i<consumerShift3; i++)
        {
            shift3.add(new Machine(q1,si1,l, "CSACons station shift3-"+(i+1), consumer.AGENTTYPE));
        }
        for( int i = 0; i<corporateShift1; i++)
        {
            shift1.add(new Machine(q1,si1,l,"CSACorp station shift1-"+(i+1), corporate.AGENTTYPE));
        }
        for( int i = 0; i<corporateShift2; i++)
        {
            shift2.add(new Machine(q1,si1,l,"CSACorp station shift2-"+(i+1), corporate.AGENTTYPE));
        }
        for( int i = 0; i<corporateShift3; i++)
        {
            shift3.add(new Machine(q1,si1,l,"CSACorp station shift3-"+(i+1), corporate.AGENTTYPE));
        }

        int cost = consumerCost*(consumerShift1+consumerShift2+consumerShift3)*shiftDuration + corporateCost*(corporateShift1+corporateShift2+corporateShift3)*shiftDuration;

        return cost;
    }

    public static void showStats(Sink si1, int totalCustomers) {

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

        double percentageCorp3 = (1 - (double) waitTimeCorp3 / totalCustomers) * 100;
        double percentageCorp7 = (1 - (double) waitTimeCorp7 / totalCustomers) * 100;
        double percentageCons5 = (1 - (double) waitTimeCons5 / totalCustomers) * 100;
        double percentageCons10 = (1 - (double) waitTimeCons10 / totalCustomers) * 100;

        System.out.println("");
        System.out.println();
        System.out.println("percentages");
        System.out.println("--------------------------------------");
        System.out.println("percentage under 3 Corp = " + percentageCorp3);
        System.out.println("percentage under 7 Corp = " + percentageCorp7);
        System.out.println("percentage under 5 Cons = " + percentageCons5);
        System.out.println("percentage under 10 Cons = " + percentageCons10);
        System.out.println("--------------------------------------");

        if (percentageCons5 >= 90 && percentageCons10 >= 95 && percentageCorp3 >= 95 && percentageCorp7 >= 99){
            System.out.println("VALID STRATEGY FOR THE SIMULATION");
        }
        else System.out.println("INVALID STRATEGY FOR THE SIMULATION");

    }
}
