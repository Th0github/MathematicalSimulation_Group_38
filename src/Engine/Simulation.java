package Engine;

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
    static final int CORPCUSTOMER = 1;
    static final int CONSUMER = 2;

	

        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Create an eventlist
	CEventList l = new CEventList();


	// A queue for the machine
	Queue q1 = new Queue();
	Queue q2 = new Queue();

	// A source
	Source s1 = new Source(q1,l,"rest.Corporate customers.Source", CORPCUSTOMER);
	Source s2 = new Source(q2,l, "rest.Corporate consumers.Source", CONSUMER);

	// A sink
    Sink si2 = new Sink("Consumers.Sink 1");
    Sink si1 = new Sink("Customers.Sink 2");

    // A machine
	Machine CSAcorp = new Machine(q1,si1,l,"CSACorp station");
	Machine CSAcons = new Machine(q1,si2,l, "CSACons station");

	// start the eventlist
	l.start(2000); // 2000 is maximum time

    }
    
}
