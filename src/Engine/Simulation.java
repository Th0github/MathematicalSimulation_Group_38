package Engine;

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
    public static DataClass data = new DataClass();
    public static double maxTime = 2000;



    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    // Create an eventlist
	CEventList l = new CEventList(data);
	// A queue for the machine
	Queue q = new Queue();
	// A source
    // Source s = new Source(q,l,"Engine.Source 1");
    createSources(q, l);
	// A sink
	Sink si = new Sink("Engine.Sink 1");
	// A machine
	// Machine m = new Machine(q,si,l,"Engine.Machine 1");
    createMachines(q, si, l);
	// start the eventlist
	l.start(maxTime); // 2000 is maximum time

    data.print();
    }

    public static void createSources(Queue q, CEventList l)
    {
        Source cons = new Source(q,l,"consumer", data);
//        Source corp = new Source(q,l,"corporate", data);

    }

    public static void createMachines(Queue q, Sink si, CEventList l)
    {
        //TODO getRoster(), for each shift create the appropriate machines.
        Machine m = new Machine(q,si,l,"consumer", data);
//        Machine n = new Machine(q,si,l,"corporate", data);
    }


}
