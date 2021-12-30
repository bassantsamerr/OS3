import java.util.ArrayList;
import java.util.Scanner;

class process {
    public String name;
    public String color;
    public int Arrival_Time;
    public int Burst_Time;
    public int completion_time;
    public int turnAround_Time;
    public int waiting_Time;
    public int remaining_Time;

    public process(String name , String color, int Arrival_Time , int Burst_Time)
    {
        this.name = name;
        this.color = color;
        this.Arrival_Time = Arrival_Time;
        this.Burst_Time = Burst_Time;
        this.remaining_Time = Burst_Time;
    }

    public void printProcess()
    {
        System.out.println("name : "+ name +
                "   Arrival_Time : " + Arrival_Time+
                "   Burst_Time : " + Burst_Time+
                "   waiting_Time : " + waiting_Time+
                "   turnAround_Time : "+ turnAround_Time+
                "   completion_time : "+ completion_time);
    }
}

class SRTF {

    public process processes [];
    public int n ;                 // number of the processes
    public int context_switching;

    public  int totalWaitingTime ;
    public  int totalTurnAroundTime ;

    double average_Waiting_Time;
    double average_TurnAround_Time;

    public ArrayList<process> Processes_execution_order = new ArrayList<>();

    public SRTF(process processes [] , int n , int context_switching)
    {
        this.context_switching = context_switching;
        this.n = n;
        this.processes = processes;

        calculateCompletionTime();
        calculateWaitingTime();
        calculateTurnAroundTime();

        print();
    }

    public void calculateCompletionTime()
    {
        // to count number of completed processes
        int finished_processes = 0;

        int currentTime = 0;

        boolean check = false;

        int minimum = 0;

        int index = 0;  // index of process with minimum remaining time

        while(finished_processes < n) {

            if (minimum == 0 || finished_processes == 0 )
                minimum = Integer.MAX_VALUE;

            // get the process with minimum remaining time
            for (int p = 0; p < n; p++) {
                if ((processes[p].remaining_Time < minimum && processes[p].remaining_Time > 0) &&(processes[p].Arrival_Time <= currentTime)) {
                    minimum = processes[p].remaining_Time;
                    index = p;
                    check = true;
                }
            }

            if (check == false) {
                currentTime++;
                continue;
            }

            // the context switching part
            if (index > 0){
                currentTime = applyContextSwitching(index , currentTime);
            }

            // add to the list
            Processes_execution_order.add(processes[index]);

            processes[index].remaining_Time --;
            minimum = processes[index].remaining_Time;

            // If the process finished
            if(processes[index].remaining_Time == 0)
            {
                check = false;

                finished_processes ++;

                processes[index].completion_time = currentTime + 1;
            }
            currentTime++;
        }
    }

    public int applyContextSwitching(int index , int time)
    {
        int currentTime = time;
        process p1 = processes[index];
        process p2 = Processes_execution_order.get(Processes_execution_order.size()-1);
        if ((p1 != p2 )) {
            currentTime += context_switching;
        }
        return currentTime;
    }

    public void calculateWaitingTime()
    {
        int wait = 0;
        for(int p = 0; p < n ; p++) {
            wait = processes[p].completion_time- processes[p].Burst_Time - processes[p].Arrival_Time;
            totalWaitingTime += wait;
            processes[p].waiting_Time = wait;
        }
        average_Waiting_Time = (double) totalWaitingTime / n;
    }

    public void calculateTurnAroundTime()
    {
        int aroundTime = 0;
        for(int p = 0; p < n ; p++)
        {
            aroundTime = processes[p].completion_time - processes[p].Arrival_Time;
            totalTurnAroundTime += aroundTime;
            processes[p].turnAround_Time = aroundTime;
        }
        average_TurnAround_Time = (double) totalTurnAroundTime / n;
    }

    public void print()
    {
        System.out.print('\n');
        System.out.println("The Processes execution order");

        for (int i = 0; i < Processes_execution_order.size(); i++){
            System.out.print(Processes_execution_order.get(i).name + "  ");
        }
        System.out.print('\n');
        System.out.print('\n');

        ArrayList <process> temp = new ArrayList<>();
        for (int i = 0; i < Processes_execution_order.size(); i++)
        {
            if(temp.contains(Processes_execution_order.get(i)))
            {
                continue;
            }
            else {
                temp.add(Processes_execution_order.get(i));
                Processes_execution_order.get(i).printProcess();
            }
        }

        System.out.print('\n');
        System.out.println("Average Waiting Time = " +average_Waiting_Time );
        System.out.println("Average TurnAround Time = " +average_TurnAround_Time );
        System.out.print('\n');

    }

}

