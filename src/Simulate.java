import Engine.*;
import Engine.Process;

import java.util.ArrayList;
import java.util.Random;

public class Simulate {

    public TruncatedNormalDistribution getServTimeCons() {
        return servTimeCons;
    }

    public TruncatedNormalDistribution getServTimeCorp() {
        return servTimeCorp;
    }

    public Random getUniformVariate() {
        return uniformVariate;
    }

    public PoissonProcess getConsumerArrival() {
        return consumerArrival;
    }

    public PoissonProcess getConsumerArrivalThreeAM() {
        return consumerArrivalThreeAM;
    }

    public PoissonProcess getCorporateArrivalEightToSix() {
        return corporateArrivalEightToSix;
    }

    public PoissonProcess getCorporateArrivalSixToEight() {
        return corporateArrivalSixToEight;
    }

    private TruncatedNormalDistribution servTimeCons;
    private TruncatedNormalDistribution servTimeCorp;
    private Random uniformVariate;
    private PoissonProcess consumerArrival;
    private PoissonProcess consumerArrivalThreeAM;
    private PoissonProcess corporateArrivalEightToSix;
    private PoissonProcess corporateArrivalSixToEight;

    public ArrayList<CustomerServiceAgent> getShift1() {
        return shift1;
    }

    public ArrayList<CustomerServiceAgent> getShift2() {
        return shift2;
    }

    public ArrayList<CustomerServiceAgent> getShift3() {
        return shift3;
    }

    private ArrayList<CustomerServiceAgent> shift1;
    private ArrayList<CustomerServiceAgent> shift2;
    private ArrayList<CustomerServiceAgent> shift3;
    /** simulation time in minutes.*/
    public Simulate(int time)
    {
        Corporate corporate = new Corporate();
        Consumer consumer = new Consumer();
        //Uniform Distribution
        uniformVariate = new Random();
        //Truncated Normal distribution
        servTimeCons = new TruncatedNormalDistribution(consumer.AVERAGE_SERVICE_TIME, consumer.STANDARD_DEV_SERVICE_TIME, consumer.MIN_CALL_TIME);
        servTimeCorp = new TruncatedNormalDistribution(corporate.AVERAGE_SERVICE_TIME, corporate.STANDARD_DEV_SERVICE_TIME, corporate.MIN_CALL_TIME);


        int sampleCount = 100;
        //Three shifts, for now we just record 10 events, either increase row count for more data or use Arraylist
        double[][] timeShift = new double[3][sampleCount];

        //Arrival rates from calls: Poisson Process
        //Using Normal variate causes issues.
        consumerArrival = new PoissonProcess(consumer.AVERAGE_RATE_PER_MINUTE, uniformVariate);
        consumerArrivalThreeAM = new PoissonProcess(consumer.AVERAGE_RATE_PER_MINUTE_3_AM, uniformVariate);
        corporateArrivalEightToSix = new PoissonProcess(corporate.RATE_EIGHT_am_to_SIX_pm, uniformVariate);
        corporateArrivalSixToEight = new PoissonProcess(corporate.RATE_SIX_am_to_EIGHT_am, uniformVariate);
        System.out.println("Call recieved at first run: " + consumer.events());
        //TODO: we need to link everything together, get the service linked with the serviceTime
        System.out.println("duration of call if consumer = " + servTimeCons.generate());
        System.out.println("duration of call if corporate = " + servTimeCorp.generate());

        simulationLoop(time);
    }

    public void simulationLoop(int time)
    {
        System.out.println("Cost of this roster = " + createRoster(5,5,5,5,5,5));
        CEventList eventList = new CEventList(new DataClass());
        ArrayList<Customer> customerList = new ArrayList<>();
        int j = 0;
        for(int i = 0; i<time; i++)
        {
            //TO-DO : Figure out queueing time
            /** 6-8am*/
            if(j<2*60)
            {
                int cons = consumerArrival.events(i);
                for(int k = 0; k<cons; k++)
                {
                    double dur = servTimeCons.generate();

                    eventList.add(new Process(dur), 0, i);
                }
                int corp = corporateArrivalSixToEight.events(i);
                for(int k = 0; k<corp; k++)
                {
                    double dur = servTimeCorp.generate();
                    eventList.add(new Process(dur), 1, i);
                }
            }/**8am-6pm*/
            else if(j<14*60)
            {
                int cons = consumerArrival.events(i);
                for(int k = 0; k<cons; k++)
                {
                    double dur = servTimeCons.generate();
                    eventList.add(new Process(dur), 0, i);
                }
                int corp = corporateArrivalEightToSix.events(i);
                for(int k = 0; k<corp; k++)
                {
                    double dur = servTimeCorp.generate();
                    eventList.add(new Process(dur), 1, i);
                }
            }/** 6pm-6am*/
            else if(j<24*60)
            {

                int corp = corporateArrivalSixToEight.events(i);
                for(int k = 0; k<corp; k++)
                {
                    double dur = servTimeCorp.generate();
                    eventList.add(new Process(dur), 1, i);
                }

                /** 3am*/
                if(21*60<j && j<22*60)
                {
                    int cons = consumerArrivalThreeAM.events(i);
                    for(int k = 0; k<cons; k++)
                    {
                        double dur = servTimeCons.generate();
                        eventList.add(new Process(dur), 0, i);
                    }
                }
                else
                {
                    int cons = consumerArrival.events(i);
                    for(int k = 0; k<cons; k++)
                    {
                        double dur = servTimeCons.generate();
                        eventList.add(new Process(dur), 0, i);
                    }
                }
            }

            /** increment j*/
            /** reset j-clock to 0 at 6am*/
            if(j<24*60)
            {
                j++;
            }
            if(j == 24*60)
            {
                j = 0;
            }
        }
    }

    public int createRoster(int consumerShift1, int consumerShift2, int consumerShift3, int corporateShift1, int corporateShift2, int corporateShift3)
    {
        int shiftDuration = 8;
        int consumerCost = 35;
        int corporateCost = 60;

        shift1 = new ArrayList<>();
        shift2 = new ArrayList<>();
        shift3 = new ArrayList<>();

        for( int i = 0; i<consumerShift1; i++)
        {
            shift1.add(new Consumer());
        }
        for( int i = 0; i<consumerShift2; i++)
        {
            shift2.add(new Consumer());
        }
        for( int i = 0; i<consumerShift3; i++)
        {
            shift3.add(new Consumer());
        }
        for( int i = 0; i<corporateShift1; i++)
        {
            shift1.add(new Corporate());
        }
        for( int i = 0; i<corporateShift2; i++)
        {
            shift2.add(new Corporate());
        }
        for( int i = 0; i<corporateShift3; i++)
        {
            shift3.add(new Corporate());
        }

        int cost = consumerCost*(consumerShift1+consumerShift2+consumerShift3)*shiftDuration + corporateCost*(corporateShift1+corporateShift2+corporateShift3)*shiftDuration;

        return cost;
    }




}
