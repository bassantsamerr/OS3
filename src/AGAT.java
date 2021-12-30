import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static java.lang.Math.ceil;

class AGAT {
    static ArrayList<String> processes = new ArrayList<>();
    static ArrayList<Integer> burstTime = new ArrayList<>();
    static ArrayList<Integer> arrivalTime = new ArrayList<>();
    static ArrayList<Integer> priority = new ArrayList<>();
    static ArrayList<Integer> quantum = new ArrayList<>();
    static ArrayList<String> excOrder = new ArrayList<>();
    ArrayList<Integer> remBurstTime = new ArrayList<>();
    ArrayList<Integer> waitTime = new ArrayList<>();
    ArrayList<Integer> turnAroundTime = new ArrayList<>();
    ArrayList<Integer> leaveTime = new ArrayList<>();
    ArrayList<String> readyQ = new ArrayList<>();
    ArrayList<String> color = new ArrayList<>();

    double V1;
    double V2;
    int noProc;
    double avgTAT = 0;
    double avgWT = 0;

    int calcFactor(int pr, int arrival, int rem) {
        double res;
        res = (10 - pr) + ceil(arrival / V1) + ceil(rem / V2);
        return (int) res;
    }

    int maxBurst(int time) {
        int max = 0;
        for (int i = 0; i < noProc; i++) {
            if (time >= arrivalTime.get(i) && max <= remBurstTime.get(i)) {
                max = remBurstTime.get(i);
            }
        }
        return max;
    }

    void calcV1() {
        if (arrivalTime.get(noProc - 1) > 10) {
            V1 = arrivalTime.get(noProc - 1) * 1.0 / 10;
        } else
            V1 = 1;
    }

    void calcV2(int time) {
        if (this.maxBurst(time) > 10) {
            V2 = this.maxBurst(time) * 1.0 / 10;
        } else
            V2 = 1;
    }

    int minFactor(int indx, int currentTime) {
        double minFactor = 10e9;
        int minInd = 0;
        for (int j = 0; j < noProc; j++) {
            if (currentTime >= arrivalTime.get(j) && quantum.get(j) > 0) {
                if (minFactor > calcFactor(priority.get(j), arrivalTime.get(j), remBurstTime.get(j))
                        && indx != j) {
                    minFactor = calcFactor(priority.get(j), arrivalTime.get(j), remBurstTime.get(j));
                    minInd = j;
                }
            }
        }
        return minInd;
    }

    void checkArrival(int time) {
        for (int i = 0; i < noProc; i++) {
            if (arrivalTime.get(i) == time && readyQ.indexOf(processes.get(i)) == -1) {
                readyQ.add(processes.get(i));
            }
        }
    }

    void calcTRT() {
        for (int i = 0; i < noProc; i++) {
            turnAroundTime.add(leaveTime.get(i) - arrivalTime.get(i));
            avgTAT += turnAroundTime.get(i);
        }
        avgTAT /= noProc;
    }

    void calcWT() {
        for (int i = 0; i < noProc; i++) {
            waitTime.add(turnAroundTime.get(i) - burstTime.get(i));
            avgWT += waitTime.get(i);
        }
        avgWT /= noProc;
    }

    void printAgats(int currentTime) {
        System.out.print("[");
        for (int j = 0; j < noProc; j++) {
            if (currentTime >= arrivalTime.get(j) && quantum.get(j) > 0) {
                System.out.print(calcFactor(priority.get(j), arrivalTime.get(j), remBurstTime.get(j)) + ", ");
            } else {
                System.out.print("_,");
            }
        }

        System.out.println("]");
    }

    public void running() {
        int indx = 0;
        calcV1();
        int minIndx;
        int _40quantum;
        int time = 0;
        int countDone = 0;
        for (int i = 0; i < noProc; i++) {
            leaveTime.add(0);
            remBurstTime.add(burstTime.get(i));
        }
        while (true) {

            if (remBurstTime.get(indx) > 0) {
                //start of none pre emptive
                excOrder.add(processes.get(indx));
                _40quantum = (int) Math.round(quantum.get(indx) * 4.0 / 10);
                System.out.print("quantums:  ");
                System.out.println(quantum);
                System.out.print("Agats:    ");
                calcV2(time);
                printAgats(time);
                if (remBurstTime.get(indx) > _40quantum) {
                    int takenQ = 0;
                    //check the arrival at every sec of the 40Q.
                    for (int i = time; i < time + _40quantum; i++) checkArrival(i);
                    // the 40% of the quantum.
                    takenQ += _40quantum;
                    time += _40quantum;
                    // end of none preemptive
                    this.calcV2(time);
                    minIndx = minFactor(-1, time);
                    remBurstTime.set(indx, remBurstTime.get(indx) - takenQ);
                    if (indx == minIndx) {
                        int remQ = quantum.get(indx) - takenQ;
                        int remQAftLoop = 0;
                        for (int i = 0; i < remQ; i++) {
                            // preemptive
                            checkArrival(time);
                            time++;
                            takenQ++;
                            remQAftLoop = quantum.get(indx) - takenQ;
                            remBurstTime.set(indx, remBurstTime.get(indx) - 1);
                            calcV2(time);
                            minIndx = minFactor(-1, time);
                            if (indx != minIndx) {
                                break;
                            }
                            if (remBurstTime.get(indx) == 0) {// the remaining burst time  = zero
                                readyQ.remove(0);
                                quantum.set(indx, 0);
                                leaveTime.set(indx, time);
                                countDone++;
                                break;
                            }
                        }
                        if (quantum.get(indx) > 0) {
                            if (remQAftLoop == 0) {// used all the quantum
                                quantum.set(indx, quantum.get(indx) + 2);
                                readyQ.add(readyQ.get(0));
                                readyQ.remove(readyQ.get(0));
                                int nxtindex = processes.indexOf(readyQ.get(0));
                                indx = nxtindex;
                                continue;

                            } else {
                                if (remQAftLoop > 0) {// not used all the qunatum and i was removed
                                    quantum.set(indx, quantum.get(indx) + (quantum.get(indx) - takenQ));
                                    readyQ.add(readyQ.get(0));
                                    readyQ.remove(readyQ.get(0));
                                }
                            }
                            if (remBurstTime.get(indx) == 0) // take all remq or and finish
                            {
                                readyQ.remove(0);
                                quantum.set(indx, 0);
                                leaveTime.set(indx, time);
                                int nxtindex = processes.indexOf(readyQ.get(0));
                                indx = nxtindex;
                                countDone++;
                                continue;
                            }
                        } else {
                            if (readyQ.size() != 0) {
                                int nxtindex = processes.indexOf(readyQ.get(0));
                                indx = nxtindex;
                                continue;
                            }
                        }

                    } else {
                        readyQ.add(readyQ.get(0));
                        readyQ.remove(readyQ.get(0));
                        quantum.set(indx, quantum.get(indx) + (quantum.get(indx) - takenQ));

                    }
                } else {
                    time = time + remBurstTime.get(indx);
                    remBurstTime.set(indx, 0);
                    quantum.set(indx, 0);
                    readyQ.remove(0);
                    leaveTime.set(indx, time);
                    countDone++;
                }
                this.calcV2(time);
                minIndx = minFactor(indx, time);
                readyQ.remove(processes.get(minIndx));
                readyQ.add(0, processes.get(minIndx));
                indx = minIndx;
            }
            if (countDone == noProc) {
                break;
            }
        }
    }

}

/*
4
p1 a 17 0 4 4
p2 b 6 3 9 3
p3 c 10 4 3 5
p4 d 4 29 10 2*/