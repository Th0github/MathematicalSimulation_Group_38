package Engine;

import Products.Consumer;
import Products.Corporate;

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
    Sink si2 = new Sink("Corporate Consumers Sink");
    Sink si1 = new Sink("Corporate Customers Sink");

    // A machine
	Machine CSAcorp = new Machine(q1,si1,l,"CSACorp station", corporate.AGENTTYPE);
	Machine CSAcons = new Machine(q1,si2,l, "CSACons station", consumer.AGENTTYPE);

	// start the eventlist
	l.start(2000); // 2000 is maximum time

    }
    
}
