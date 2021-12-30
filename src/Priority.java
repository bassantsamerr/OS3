import java.util.*;

/**
 *
 * @author ALI
 */
class ProcessInfo {
    private String name;
    private int priority ;
    private int arrivalTime ;
    private int brustTime ;
    public ProcessInfo(String n, int p, int aT, int b){
        name = n;
        priority = p;
        brustTime = b ;
        arrivalTime = aT ;
    }
    public String getName(){
        return name ;
    }
    public int getPriority(){
        return priority ;
    }
    public void setPriority(int pri){
        priority= pri ;
    }
    public int getBrustTime(){
        return brustTime ;
    }

    public int getArrivalTime(){
        return arrivalTime ;
    }
    public void printProcessInfo(){
        System.out.println(this.name+" "+this.priority+" "+this.arrivalTime +" "+this.brustTime);
    }


    public static Comparator<ProcessInfo> procesPrio = new Comparator<ProcessInfo>() {
        public int compare(ProcessInfo s1, ProcessInfo s2)
        {

            int rollno1 = s1.getPriority();
            int rollno2 = s2.getPriority();

            // For ascending order
            return rollno1 - rollno2;

            // For descending order
            // rollno2-rollno1;
        }
    };

    @Override public String toString()
    {
        return "[ name=" + name + ", priority="
                + priority + ", arrivalTime="+arrivalTime +", brustTime=" + brustTime + "]";
    }



}

    /*
        input example:
        4
        3
        p1 1 0 3
        p2 3 5 4
        p3 2 7 2
        p4 5 3 15


p1 3 0 10
p2 1 0 1
p3 4 0 2
p4 5 0 1
p5 2 0 5

    */