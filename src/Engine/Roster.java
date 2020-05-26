package Engine;

import Products.Consumer;
import Products.Corporate;

import java.util.ArrayList;

public class Roster {

    public ArrayList<Machine> activeAgents = new ArrayList<>();
    public int consShift1,consShift2,consShift3,corpShift1, corpShift2, corpShift3;
    public int currentShift;
    private Queue queue;
    private ProductAcceptor productAcceptor;
    private CEventList eventlist;
    private Consumer consumer = new Consumer();
    private Corporate corporate = new Corporate();

    public Roster(Queue q, ProductAcceptor s, CEventList e)
    {
        queue = q;
        productAcceptor = s;
        eventlist = e;
        currentShift = -1;
    }

    public void executeRoster()
    {

        double dTime = eventlist.getDayTime();
        if(dTime < 60*8)
        {
            if(currentShift != 1)
            {
                currentShift = 1;
                set(consShift1, corpShift1);
            }
        }
        else if(dTime >= 60*8 && dTime <= 60*16)
        {
            if(currentShift != 2)
            {
                currentShift = 2;
                set(consShift2, corpShift2);
            }
        }
        else if(dTime > 60*16 && dTime <= 60*24)
        {
            if(currentShift != 3)
            {
                currentShift = 3;
                set(consShift3, corpShift3);
            }
        }
    }

    private void set(int consShift, int corpShift)
    {
        if(!activeAgents.isEmpty())
        {
            for(Machine agent:activeAgents)
            {
                if(agent.active == true) {
                    System.out.println("this agent is now inactve + " + agent.getName());
                    agent.setInactive();
                }
            }
        }
        for(int i = 0; i<consShift; i++)
        {
            System.out.println("NEW AGENT MADE IN SHIFT " + currentShift + "------------------------------------------------------------------------------------------------------------------------------------------");
            activeAgents.add(new Machine(queue, productAcceptor, eventlist, "Consumer shift" + currentShift + " #" + i, consumer.AGENTTYPE));
        }
        for(int i = 0; i<corpShift; i++)
        {
            System.out.println("NEW AGENT MADE IN SHIFT " + currentShift + "------------------------------------------------------------------------------------------------------------------------------------------");
            activeAgents.add(new Machine(queue, productAcceptor, eventlist, "Corporate shift" + currentShift + " #" + i, corporate.AGENTTYPE));
        }
    }

    public int createRoster(int consumerShift1, int consumerShift2, int consumerShift3, int corporateShift1, int corporateShift2, int corporateShift3)
    {
        int shiftDuration = 8;
        int consumerCost = 35;
        int corporateCost = 60;

        consShift1 = consumerShift1;
        consShift2 = consumerShift2;
        consShift3 = consumerShift3;
        corpShift1 = corporateShift1;
        corpShift2 = corporateShift2;
        corpShift3 = corporateShift3;

        int cost = consumerCost*(consumerShift1+consumerShift2+consumerShift3)*shiftDuration + corporateCost*(corporateShift1+corporateShift2+corporateShift3)*shiftDuration;
        currentShift = 1;
        set(consShift1, corpShift1);
        return cost;
    }
}
