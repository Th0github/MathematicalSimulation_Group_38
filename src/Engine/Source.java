package Engine;

import java.util.Random;

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
	private Random uniformVariate = new Random();
	private PoissonProcess consumerArrival = new PoissonProcess(2, uniformVariate);
	private PoissonProcess consumerArrivalThreeAM = new PoissonProcess(0.2, uniformVariate);
	private PoissonProcess corporateArrivalEightToSix = new PoissonProcess(1, uniformVariate);
	private PoissonProcess corporateArrivalSixToEight = new PoissonProcess(0.2, uniformVariate);
	private DataClass data;
	/**
	*	Constructor, creates objects
	*        Interarrival times are exponentially distributed with mean 33
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*/
	public Source(ProductAcceptor q,CEventList l,String n, DataClass data)
	{
		this.data = data;
		list = l;
		queue = q;
		//TODO n needs to represent the type of events this generates, 'consumer' or 'customer'
		name = n;
		meanArrTime=33;
		// put first event in list for initialization
		list.add(this,0,getArrivalTime(n)); //target,type,time
	}

	private double getArrivalTime(String n) {
		double a = 0;

		//TODO ! Create a getCurrentTime()
		if(n == "consumer")
		{
			a = consumerArrival.timeNextEvent();// + currentTime;

			// IF 3AM -> a = consumerArrivalThreeAM.timeNextEvent();
		}
		else if(n == "corporate")
		{
			a = corporateArrivalEightToSix.timeNextEvent();

			// IF 6PM-8AM -> a = corporateArrivalSixToEight.timeNextEvent();
		}
		else
		{
			// Shouldn't happen, failsafe for now
			a = drawRandomExponential(meanArrTime);
		}

		return a;
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are exponentially distributed with specified mean
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param m	Mean arrival time
	*/
	public Source(ProductAcceptor q,CEventList l,String n,double m, DataClass data)
	{
		this.data = data;
		list = l;
		queue = q;
		name = n;
		meanArrTime=m;
		// put first event in list for initialization
		list.add(this,0,drawRandomExponential(meanArrTime)); //target,type,time
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are prespecified
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param ia	interarrival times
	*/
	public Source(ProductAcceptor q, CEventList l, String n, double[] ia, DataClass data)
	{
		this.data = data;
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
		// show arrival
		System.out.println("Arrival at time = " + tme);
		data.arrivalTimes(tme);
		// give arrived product to queue
		Product p = new Product();
		p.stamp(tme,"Creation",name);
		queue.giveProduct(p);
		// generate duration
		if(meanArrTime>0)
		{
			double duration = drawRandomExponential(meanArrTime);
			// Create a new event in the eventlist
			list.add(this,0,tme+duration); //target,type,time
		}
		else
		{
			interArrCnt++;
			if(interarrivalTimes.length>interArrCnt)
			{
				list.add(this,0,tme+interarrivalTimes[interArrCnt]); //target,type,time
			}
			else
			{
				list.stop();
			}
		}
	}
	
	public static double drawRandomExponential(double mean)
	{
		// draw a [0,1] uniform distributed number
		double u = Math.random();
		// Convert it into a exponentially distributed random variate with mean 33
		double res = -mean*Math.log(u);
		return res;
	}
}