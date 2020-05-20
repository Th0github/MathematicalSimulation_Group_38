package Engine;

public class Process implements CProcess {

    public double duration;
    public Process(double d)
    {
        this.duration = d;
    }
    public void execute(int type, double time)
    {

        System.out.println("This process is executed");
    }
}
