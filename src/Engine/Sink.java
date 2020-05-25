package Engine;

import java.util.ArrayList;
import Products.Corporate;
import Products.Consumer;
/**
 *	A sink
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Sink implements ProductAcceptor
{
	/** All products are kept */
	private ArrayList<Product> products;
	/** All properties of products are kept */
	private ArrayList<Integer> numbers;
	private ArrayList<Double> times;
	private ArrayList<String> events;
	private ArrayList<String> stations;
	private ArrayList<Double> waitingTimeCorp;
	private ArrayList<Double> waitingTimeCons;
	/** Counter to number products */
	private int number;
	/** Name of the sink */
	private String name;
	private Corporate corporate;
	private Consumer consumer;

	
	/**
	*	Constructor, creates objects
	*/
	public Sink(String n)
	{
		name = n;
		products = new ArrayList<>();
		numbers = new ArrayList<>();
		times = new ArrayList<>();
		events = new ArrayList<>();
		stations = new ArrayList<>();
		number = 0;
		corporate = new Corporate();
		consumer = new Consumer();
		waitingTimeCorp = new ArrayList<>();
		waitingTimeCons = new ArrayList<>();
	}
	
        @Override
	public boolean giveProduct(Product p)
	{
		number++;
		products.add(p);
		// store stamps
		ArrayList<Double> t = p.getTimes();
		ArrayList<String> e = p.getEvents();
		ArrayList<String> s = p.getStations();

		for(int i=0;i<t.size();i++)
		{
			numbers.add(number);
			times.add(t.get(i));
			events.add(e.get(i));
			stations.add(s.get(i));
		}

		double[] times = p.getTimesAsArray();
		double waitTime = times[1] - times[0];
		if(p.getType() == corporate.AGENTTYPE){
			waitingTimeCorp.add(waitTime);
		}
		else if (p.getType() == consumer.AGENTTYPE){
			waitingTimeCons.add(waitTime);
		}
		return true;
	}
	
	public int[] getNumbers()
	{
		numbers.trimToSize();
		int[] tmp = new int[numbers.size()];
		for (int i=0; i < numbers.size(); i++)
		{
			tmp[i] = (numbers.get(i)).intValue();
		}
		return tmp;
	}

	public double[] getTimes()
	{
		times.trimToSize();
		double[] tmp = new double[times.size()];
		for (int i=0; i < times.size(); i++)
		{
			tmp[i] = (times.get(i)).doubleValue();
		}
		return tmp;
	}

	public String[] getEvents()
	{
		String[] tmp = new String[events.size()];
		tmp = events.toArray(tmp);
		return tmp;
	}

	public String[] getStations()
	{
		String[] tmp = new String[stations.size()];
		tmp = stations.toArray(tmp);
		return tmp;
	}

	public int getNumber(){
		return number;
	}

	public double[] getWaitingTimeCorp(){
		waitingTimeCorp.trimToSize();
		double[] tmp = new double[waitingTimeCorp.size()];
		for (int i=0; i < waitingTimeCorp.size(); i++)
		{
			tmp[i] = (waitingTimeCorp.get(i)).doubleValue();
		}
		return tmp;
	}

	public double[] getWaitingTimeCons(){
		waitingTimeCons.trimToSize();
		double[] tmp = new double[waitingTimeCons.size()];
		for (int i=0; i < waitingTimeCons.size(); i++)
		{
			tmp[i] = (waitingTimeCons.get(i)).doubleValue();
		}
		return tmp;
	}


}