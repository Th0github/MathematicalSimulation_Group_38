package Engine;

import Distribution.TruncatedNormalDistribution;
import Products.Consumer;
import Products.Corporate;

/**
 *	Engine.Machine in a factory
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Machine implements CProcess, ProductAcceptor
{
	/** Product that is being handled  */
	private Product product;
	/** Eventlist that will manage events */
	private final CEventList eventlist;
	/** Engine.Queue from which the machine has to take products */
	private Queue queue;
	/** Engine.Sink to dump products */
	private ProductAcceptor sink;
	/** Status of the machine (b=busy, i=idle) */
	private char status;
	/** Engine.Machine name */
	private final String name;
	/** Processing times (in case pre-specified) */
	private double[] processingTimes;
	/** Processing time iterator */
	private int procCnt;
	public String agentType;
	private static Corporate corporate = new Corporate();
	private static Consumer consumer = new Consumer();
	

	/**
	*	Constructor
	*        service times are normally distributed and handled in the start production method
	*	@param q	Engine.Queue from which the machine has to take products
	*	@param s	Where to send the completed products
	*	@param e	Eventlist that will manage events
	*	@param n	The name of the machine
	*/
	public Machine(Queue q, ProductAcceptor s, CEventList e, String n, String agentType)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		this.agentType = agentType;
		queue.askProduct(this);
	}


	/**
	*	Constructor
	*        Service times are pre-specified
	*	@param q	Engine.Queue from which the machine has to take products
	*	@param s	Where to send the completed products
	*	@param e	Eventlist that will manage events
	*	@param n	The name of the machine
	*        @param st	service times
	*/
	public Machine(Queue q, ProductAcceptor s, CEventList e, String n, double[] st)
	{
		status='i';
		queue=q;
		sink=s;
		eventlist=e;
		name=n;
		processingTimes=st;
		procCnt=0;
		queue.askProduct(this);
	}

	/**
	*	Method to have this object execute an event
	*	@param type	The type of the event that has to be executed
	*	@param tme	The current time
	*/
	public void execute(int type, double tme)
	{
		// show arrival
		System.out.println("Product finished at time = " + tme);
		// Remove product from system
		product.stamp(tme,"Production complete",name);
		sink.giveProduct(product);
		product=null;
		// set machine status to idle
		status='i';
		// Ask the queue for products
		queue.askProduct(this);
	}
	
	/**
	*	Let the machine accept a product and let it start handling it
	*	@param p	The product that is offered
	*	@return	true if the product is accepted and started, false in all other cases
	*/
        @Override
	public boolean giveProduct(Product p)
	{
		// Only accept something if the machine is idle
		if(status=='i')
		{
			// accept the product
			product=p;
			// mark starting time
			product.stamp(eventlist.getTime(),"Production started",name);
			// start production
			startProduction(p);
			// Flag that the product has arrived
			return true;
		}
		// Flag that the product has been rejected
		else return false;
	}
	
	/**
	*	Starting routine for the production
	*	Start the handling of the current product with an exponentionally distributed processingtime with average 30
	*	This time is placed in the eventlist
	*/
	private void startProduction(Product p)
	{
		// generate duration
		double duration = drawNormallyDistributedServTime(p.getType());
		// Create a new event in the eventlist
		double tme = eventlist.getTime();
		eventlist.add(this,1,tme+duration); //target,type,time
		// set status to busy
		status='b';
	}

	/*
		type 1 for corporate and type = 2 for consumer:
		gives a truncate normal distributed service time.

	 */
	public double drawNormallyDistributedServTime(String type)
	{
		double callTime = 0;

		if (type == corporate.AGENTTYPE){
			TruncatedNormalDistribution servTimeCorp = new TruncatedNormalDistribution(corporate.AVERAGE_SERVICE_TIME, corporate.STANDARD_DEV_SERVICE_TIME, corporate.MIN_CALL_TIME);
			callTime = servTimeCorp.generate();
		}

		else if (type == consumer.AGENTTYPE){
			TruncatedNormalDistribution servTimeCons = new TruncatedNormalDistribution(consumer.AVERAGE_SERVICE_TIME, consumer.STANDARD_DEV_SERVICE_TIME, consumer.MIN_CALL_TIME);
			callTime = servTimeCons.generate();
		}

		else System.out.println("no type specified");

		return callTime;
	}
}