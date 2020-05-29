package Engine;

import Distribution.PoissonProcess;
import Products.Consumer;
import Products.Corporate;
/**
 *	A source of products
 *	This class implements Engine.CProcess so that it can execute events.
 *	By continuously creating new events, the source keeps busy.
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Source implements CProcess
{
	/** Eventlist that will be requested to construct events */
	private CEventList list;
	/** Engine.Queue that buffers products for the machine */
	private ProductAcceptor queue;
	/** Name of the source */
	private String name;
	/** Mean interarrival time */
	private double meanArrTime;
	/** Interarrival times (in case pre-specified) */
	private double[] interarrivalTimes;
	/** Interarrival time iterator */
	private int interArrCnt;
	/** Type of source (1 for corp and 2 for consumer */
	private String agentType;
	/** Poisson process according to the source type*/
	private PoissonProcess pois, pois2;
	private Consumer consumer = new Consumer();
	private Corporate corporate = new Corporate();
	private int totalProducts;
	/**
	*	Constructor, creates objects that follow a nonstationarty Poisson process that is sinusoid with
	 *	an average rate of 2 per minute.
	*
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param t 	type of souce (1 for corporate and 2 for consumer
	 *
	*/
	public Source(ProductAcceptor q,CEventList l,String n, String t)
	{
		list = l;
		queue = q;
		name = n;
		this.agentType = t;
		this.totalProducts = 0;

		if(t == corporate.AGENTTYPE)
		{
			this.pois = new PoissonProcess(corporate.RATE_EIGHT_am_to_SIX_pm);
			this.pois2 = new PoissonProcess(corporate.RATE_SIX_am_to_EIGHT_am);
		}
		else if(t == consumer.AGENTTYPE)
		{
			this.pois = new PoissonProcess(consumer.AVERAGE_RATE_PER_MINUTE);
			this.pois2 = new PoissonProcess(consumer.AVERAGE_RATE_PER_MINUTE_3_AM);
		}

		// put first event in list for initialization
		list.add(this,0, pois.timeNextEvent());
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are prespecified
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param ia	interarrival times
	*/
	public Source(ProductAcceptor q, CEventList l, String n, double[] ia)
	{
		list = l;
		queue = q;
		name = n;
		meanArrTime=-1;
		interarrivalTimes=ia;
		interArrCnt=0;
		// put first event in list for initialization
		list.add(this,0,interarrivalTimes[0]); //target,type,time
	}
	
        @Override
	public void execute(int type, double tme)
	{
		double arrival = 0;
		// show arrival
		System.out.println("Arrival at time = " + tme + "\t\t Source: " + this.name);
		// give arrived product to queue
		Product p = new Product(this.agentType);
		p.stamp(tme,"Creation",name);
		queue.giveProduct(p);
		// add to the total products list
		totalProducts++;

		// generate time of next event keeping in mind the different in rates correlated with defined time instances
		if(this.agentType == corporate.AGENTTYPE)
		{
			if(list.getDayTime() < 120 || list.getDayTime() > 1080){
				arrival = pois2.timeNextEvent();
			}
			else arrival = pois.timeNextEvent();
		}

		else if(this.agentType == consumer.AGENTTYPE)
		{
			if (list.getDayTime() > 1260  && list.getDayTime() < 1320) {
				arrival = pois2.timeNextEvent();
			}
			else arrival = pois.timeNextEvent();
		}

		// Create a new event in the eventlist
		list.add(this,0, arrival+tme); //target,type,time


	}

	public int getTotalProducts(){
		return this.totalProducts;
	}
}