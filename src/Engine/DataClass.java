package Engine;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataClass {

    public ArrayList<ArrayList<Double>> arrivalList = new ArrayList();
    public ArrayList<ArrayList<Double>> departureList = new ArrayList();
    public ArrayList<ArrayList<Double>> durationList = new ArrayList();
    public double arrivalCounter = 0;
    public double departureCounter = 0;
    public double durationCounter = 0;
    public void arrivalTimes(double tme)
    {
        ArrayList<Double> tup = new ArrayList<>();
        tup.add(tme);
        double currentCount = arrivalCounter;
        tup.add(currentCount);
        arrivalList.add(tup);
    }

    public void departureTimes(double tme)
    {
        ArrayList<Double> tup = new ArrayList<>();
        tup.add(tme);
        double currentCount = departureCounter;
        tup.add(currentCount);
        departureList.add(tup);
    }

    public void durationTimes(double tme)
    {
        ArrayList<Double> tup = new ArrayList<>();
        tup.add(tme);
        double currentCount = durationCounter;
        tup.add(currentCount);
        durationList.add(tup);
    }

    public void print()
    {
        System.out.println("Commence printing: ");
        for(int i = 0; i<maxList(); i++)
        {
            if(i < arrivalList.size())
            {
                System.out.print(arrivalList.get(i).get(0) + " " + arrivalList.get(i).get(1) + " ");
            }
            if(i < departureList.size())
            {
                System.out.print(departureList.get(i).get(0) + " " + departureList.get(i).get(1) + " ");
            }
            if(i < durationList.size())
            {
                System.out.print(durationList.get(i).get(0) + " " + durationList.get(i).get(1));
            }
            System.out.println();
        }
    }

    private int maxList() {

        int max = 0;
        if(arrivalList.size()>max)
        {
            max = arrivalList.size();
        }
        if(departureList.size()>max)
        {
            max = departureList.size();
        }
        if(durationList.size()>max)
        {
            max = durationList.size();
        }

        return max;
    }
}
