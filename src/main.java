import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<SJF> sjf = new ArrayList<SJF>();
        while (true) {
            System.out.println("1)Priority 2)SJF 3)SRTF 4)AGAT 5)exit");
            int choice = sc.nextInt();
            if(choice==5) {break;}
            System.out.println("What is the number of processes");
            int noProc = sc.nextInt();
            System.out.println("What is the context switch");
            int conSwitch = sc.nextInt();
            if (choice == 1) {
                ArrayList<ProcessInfo> list = new ArrayList<>();
                System.out.println("Enter processes numbers and information(name, priority, arrival time, burst time)\n(ex->p1 5 10 20)");
                for (int i = 0; i < noProc; i++) {
                    String name = sc.next();
                    int prio = sc.nextInt();
                    int at = sc.nextInt();
                    int bt = sc.nextInt();
                    ProcessInfo p = new ProcessInfo(name, prio, at, bt);
                    list.add(p);
                    //increase priority
                    for (int j = 0; j < list.size(); j++) {
                        int oldPriority = list.get(j).getPriority();
                        list.get(j).setPriority(oldPriority - 1);
                    }
                }
                Collections.sort(list, ProcessInfo.procesPrio);

                System.out.println("\nProcesses execution Orderd");
                for (ProcessInfo pi : list)
                    System.out.print(pi.getName() + " ");

                float[] borderList = new float[noProc];
                borderList[0] = list.get(0).getBrustTime() + list.get(0).getArrivalTime() + conSwitch;
                for (int i = 1; i < borderList.length; i++) {
                    borderList[i] = list.get(i).getBrustTime() + borderList[i - 1] + conSwitch;
                }
                float[] TurnarroundTimeList = new float[noProc];
                float sumTurnarroundTime = 0;
                for (int i = 0; i < TurnarroundTimeList.length; i++) {

                    TurnarroundTimeList[i] = borderList[i] - list.get(i).getArrivalTime();
                    sumTurnarroundTime += TurnarroundTimeList[i];
                }
                float[] waitingTimeList = new float[noProc];
                float sumWaitingTime = 0;
                for (int i = 0; i < waitingTimeList.length; i++) {
                    waitingTimeList[i] = TurnarroundTimeList[i] - list.get(i).getBrustTime();
                    sumWaitingTime += waitingTimeList[i];
                }
                System.out.println("\nWaiting Time for each Processe");
                for (int i = 0; i < waitingTimeList.length; i++) {
                    System.out.print(waitingTimeList[i] + " ");
                }
                System.out.println("\nTurnarround Time for each Processe");
                for (int i = 0; i < TurnarroundTimeList.length; i++) {
                    System.out.print(TurnarroundTimeList[i] + " ");
                }

                System.out.println("\nAvarage Waiting Time");
                System.out.println(sumWaitingTime / noProc);

                System.out.println("Avarage Turnarround Time");
                System.out.println(sumTurnarroundTime / noProc);

            } else if (choice == 2 || choice == 3) {
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
                    ArrayList<SJF> schedule = SJF.schedulingSJF(sjf, conSwitch);
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
                    System.out.println("The average turnaround time is: " + SJF.getAverageTurnaroundTime(schedule));
                    System.out.println("The average waiting time is: " + SJF.getAverageWaitingTime(schedule));
                }
                if (choice == 3) {
                    process processes[] = new process[noProc];
                    for (int i = 0; i < noProc; i++) {
                        String processName = sc.next();
                        String color = sc.next();
                        int arrivalTime = sc.nextInt();
                        int burstTime = sc.nextInt();

                        process p = new process(processName, color, arrivalTime, burstTime);

                        processes[i] = p;
                    }
                    SRTF obj = new SRTF(processes, noProc, conSwitch);
                }

            } else if (choice == 4) {
                AGAT agat = new AGAT();
                int arg;
                agat.noProc = noProc;
                for (int index = 0; index < noProc; index++) {
                    Scanner input2 = new Scanner(System.in);
                    String name = input2.next();
                    agat.processes.add(name);
                    String color = input2.next();
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
                for (int index = 0; index < agat.excOrder.size() - 1; index++) {
                    System.out.print(agat.excOrder.get(index) + " ");
                }
                System.out.println();
                agat.calcTRT();
                agat.calcWT();
                for (int i = 0; i < noProc; i++) {
                    System.out.println("turn around time for p" + (i + 1) + ": " + agat.turnAroundTime.get(i));
                    System.out.println("wait time for p" + (i + 1) + ": " + agat.waitTime.get(i));
                }
                System.out.println("average turn around time: " + agat.avgTAT);
                System.out.println("average waiting time: " + agat.avgWT);
            }


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


p test:
p1 3 0 10
p2 1 0 1
p3 4 0 2
p4 5 0 1
p5 2 0 5
 */
