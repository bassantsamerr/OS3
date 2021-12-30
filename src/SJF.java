

import java.util.ArrayList;
import java.util.Scanner;

public class SJF {
    private String processName;
    private String color;
    private int arrivalTime;
    private int burstTime;
    private int priorityNumber;
    private int waitingTime;
    private int turnaroundTime;

    public SJF(String processName, String color, int arrivalTime, int burstTime) {
        this.processName = processName;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }


    public static SJF getMinBurst(ArrayList<SJF>waitingQueue){
        SJF minimum = waitingQueue.get(0);
        for(int i=1;i<waitingQueue.size();i++){
            if (minimum.burstTime > waitingQueue.get(i).burstTime)
                minimum = waitingQueue.get(i);
        }
        return minimum;
    }
    private static float getAverageWaitingTime(ArrayList<SJF>executedProcesses){
        float sum=0;
        for(int i=0;i<executedProcesses.size();i++){
            sum=sum+executedProcesses.get(i).getWaitingTime();
        }
        return sum/executedProcesses.size();
    }

    private static float getAverageTurnaroundTime(ArrayList<SJF>executedProcesses){
        float sum=0;
        for(int i=0;i<executedProcesses.size();i++){
            sum=sum+executedProcesses.get(i).getTurnaroundTime();
        }
        return sum/executedProcesses.size();
    }

    public static ArrayList<SJF> schedulingSJF(ArrayList<SJF>arrivedProcesses, int conSwitch) {
       int time=0;
       int wholeTime=0;
       ArrayList<SJF>executedProcesses=new ArrayList<>();
       ArrayList<SJF>waitingQueue = new ArrayList<>();
       for (int i=0;i<arrivedProcesses.size();i++) {//calculating the whole time
           wholeTime += arrivedProcesses.get(i).getBurstTime() + conSwitch;
       }

       wholeTime=wholeTime-conSwitch;
       for(int i=0;i<wholeTime;i++){
           for(int j=0;j<arrivedProcesses.size();j++){ //find waiting processes
               if(arrivedProcesses.get(j).getArrivalTime()<=time&&!waitingQueue.contains(arrivedProcesses.get(j))){
                   waitingQueue.add(arrivedProcesses.get(j));
               }
           }
        SJF readyProcess=getMinBurst(waitingQueue);
        int processTime=readyProcess.getBurstTime();
        while(processTime>0){//process executing
            processTime--;
            time++;
            i++;

        }
           readyProcess.setTurnaroundTime(time-readyProcess.getArrivalTime());
           readyProcess.setWaitingTime(readyProcess.getTurnaroundTime()-readyProcess.getBurstTime());
           i+=conSwitch;
           time+=conSwitch;
           arrivedProcesses.remove(readyProcess);
           waitingQueue.remove(readyProcess);//removed from waiting queue
           executedProcesses.add(readyProcess);//added to executed processes
       }
       return executedProcesses;
    }
    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int getPriorityNumber() {
        return priorityNumber;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setPriorityNumber(int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<SJF> sjf = new ArrayList<SJF>();
        System.out.println("1)Priority 2)SJF 3)SRTF 4)AGAT");
        int choice = sc.nextInt();
        System.out.println("What is the number of processes");
        int noProc = sc.nextInt();
        System.out.println("What is the context switch");
        int conSwitch = sc.nextInt();
        if (choice == 2 || choice == 3) {
            System.out.println("Enter processName, its color, its arrivalTime and its burstTime seprated with spaces");
            if (choice == 2) {
                for (int i = 0; i < noProc; i++) {
                    String processName = sc.next();
                    String color = sc.next();
                    int arrivalTime = sc.nextInt();
                    int burstTime = sc.nextInt();
                    SJF s = new SJF(processName, color, arrivalTime, burstTime);
                    sjf.add(s);

                }
                ArrayList<SJF> schedule = new ArrayList<SJF>();
                schedule = schedulingSJF(sjf, conSwitch);
                System.out.println("Processes execution order:");
                for (int i = 0; i < schedule.size(); i++) {
                    System.out.print(schedule.get(i).getProcessName() + " ");
                    System.out.print(schedule.get(i).getColor() + " ");
                    System.out.print(schedule.get(i).getArrivalTime() + " ");
                    System.out.print(schedule.get(i).getBurstTime() + " ");
                    System.out.print(schedule.get(i).getWaitingTime() + " ");
                    System.out.print(schedule.get(i).getTurnaroundTime() + " ");
                    System.out.println();

                }
                System.out.println("The average turnaround time is: " + getAverageTurnaroundTime(schedule));
                System.out.println("The average waiting time is: " + getAverageWaitingTime(schedule));
            }
            if (choice == 3) {
                process processes [] = new process[noProc];
                for(int i = 0; i < noProc; i++){
                    String processName = sc.next();
                    String color = sc.next();
                    int arrivalTime = sc.nextInt();
                    int burstTime = sc.nextInt();

                    process p =new process(processName,color,arrivalTime,burstTime);

                    processes[i] = p;
                }
                SRTF obj = new SRTF(processes , noProc , conSwitch);
            }

        }
        else if(choice==4){
            AGAT agat = new AGAT();
            int arg;
            agat.noProc = noProc;
            for (int index = 0; index < noProc; index++) {
                Scanner input2 = new Scanner(System.in);
                String name = input2.next();
                agat.processes.add(name);
                String color= input2.next();
                agat.color.add(color);
                int burst = input2.nextInt();
                agat.burstTime.add(burst);
                int arrival = input2.nextInt();
                agat.arrivalTime.add(arrival);
                int priority = input2.nextInt();
                agat.priority.add(priority);
                int quantum = input2.nextInt();
                agat.quantum.add(quantum);
            }
            agat.running();
            System.out.println("execution order");
            for (int index = 0; index < agat.excOrder.size()-1; index++) {
                System.out.print(agat.excOrder.get(index)+" ");
            }
            System.out.println();
            agat.calcTRT();
            agat.calcWT();
            for (int i = 0; i < noProc; i++) {
                System.out.println("turn around time for p"+(i+1)+": "+agat.turnAroundTime.get(i));
                System.out.println("wait time for p"+(i+1)+": "+agat.waitTime.get(i));
            }
            System.out.println("average turn around time: "+agat.avgTAT);
            System.out.println("average waiting time: "+agat.avgWT);
        }

    }
}

/*test
lecture example
P1 a 0 7
P2 b 2 4
P3 c 4 1
P4 d 5 4
out: p1, p3 ,p2, p4

lecture example
P1 a 0 6
P2 b 0 8
P3 c 0 7
P4 s 0 3
out: p4, p1, p3, p2


P1 a 2 6
P2 b 5 2
P3 c 1 8
P4 d 0 3
P5 e 4 4
out:p4 p1 p2 p5 p3
test srtf:
p1 a 0 8
p2 b 1 4
p3 c 2 9
p4 d 3 5

p1 a 0 3
p2 b 2 6
p3 c 4 4
p4 d 6 5
p5 e 8 2
out: p1, p2, p5, p3, p4

agat test:
p1 a 17 0 4 4
p2 b 6 3 9 3
p3 c 10 4 3 5
p4 d 4 29 10 2
 */