package Engine;

import java.util.ArrayList;
import Products.Corporate;
import Products.Consumer;
/**
 *	Engine.Queue that stores products until they can be handled on a machine machine
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Queue implements ProductAcceptor
{
	/** List in which the products are kept */
	private ArrayList<Product> rowCorp;
	private ArrayList<Product> rowCons;
	/** Requests from machine that will be handling the products */
	private ArrayList<Machine> requests;
	private Consumer consumer = new Consumer();
	private Corporate corporate = new Corporate();
	/**
	*	Initializes the queue and introduces a dummy machine
	*	the machine has to be specified later
	*/
	public Queue()
	{
		rowCorp = new ArrayList<>();
		rowCons = new ArrayList<>();
		requests = new ArrayList<>();
	}
	
	/**
	*	Asks a queue to give a product to a machine
	*	True is returned if a product could be delivered; false if the request is queued
	*/
	public boolean askProduct(Machine machine)
	{
		if(machine.agentType == corporate.AGENTTYPE)
		{
			// This is only possible with a non-empty queue
			if(rowCorp.size()>0)
			{
				// If the machine accepts the product
				if(machine.giveProduct(rowCorp.get(0)))
				{
					rowCorp.remove(0);// Remove it from the queue
					return true;
				}
				else
					return false; // Engine.Machine rejected; don't queue request
			}
			else if(rowCons.size()>0)
			{
				// This is only possible with a non-empty queue
				if(rowCons.size()>0)
				{
					// If the machine accepts the product
					if(machine.giveProduct(rowCons.get(0)))
					{
						rowCons.remove(0);// Remove it from the queue
						return true;
					}
					else
						return false; // Engine.Machine rejected; don't queue request
				}
			}
			else
			{
				requests.add(machine);
				return false; // queue request
			}
		}
		else if(machine.agentType == consumer.AGENTTYPE)
		{
			// This is only possible with a non-empty queue
			if(rowCons.size()>0)
			{
				// If the machine accepts the product
				if(machine.giveProduct(rowCons.get(0)))
				{
					rowCons.remove(0);// Remove it from the queue
					return true;
				}
				else
					return false; // Engine.Machine rejected; don't queue request
			}
			else
			{
				requests.add(machine);
				return false; // queue request
			}
		}
		return false;
	}
	
	/**
	*	Offer a product to the queue
	*	It is investigated whether a machine wants the product, otherwise it is stored
	*/
	public boolean giveProduct(Product p)
	{
		// Check if the machine accepts it
		if(requests.size()<1)
			if(p.getType() == corporate.AGENTTYPE)
			{
				rowCorp.add(p); // Otherwise store it
			}
			else if(p.getType() == consumer.AGENTTYPE)
			{
				rowCons.add(p); // Otherwise store it
			}

		else
		{
			boolean delivered = false;
			while(!delivered & (requests.size()>0))
			{
				delivered=requests.get(0).giveProduct(p);
				// remove the request regardless of whether or not the product has been accepted
				requests.remove(0);
			}
			if(!delivered)
				if(p.getType() == corporate.AGENTTYPE)
				{
					rowCorp.add(p); // Otherwise store it
				}
				else if(p.getType() == consumer.AGENTTYPE)
				{
					rowCons.add(p); // Otherwise store it
				}

				// Joels code row.add(p); // Otherwise store it
		}
		return true;
	}
}