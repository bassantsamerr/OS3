

import java.util.ArrayList;
import java.util.Collections;
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


    public static SJF getMinBurst(ArrayList<SJF> waitingQueue) {
        SJF minimum = waitingQueue.get(0);
        for (int i = 1; i < waitingQueue.size(); i++) {
            if (minimum.burstTime > waitingQueue.get(i).burstTime)
                minimum = waitingQueue.get(i);
        }
        return minimum;
    }

    public static float getAverageWaitingTime(ArrayList<SJF> executedProcesses) {
        float sum = 0;
        for (int i = 0; i < executedProcesses.size(); i++) {
            sum = sum + executedProcesses.get(i).getWaitingTime();
        }
        return sum / executedProcesses.size();
    }

    public static float getAverageTurnaroundTime(ArrayList<SJF> executedProcesses) {
        float sum = 0;
        for (int i = 0; i < executedProcesses.size(); i++) {
            sum = sum + executedProcesses.get(i).getTurnaroundTime();
        }
        return sum / executedProcesses.size();
    }

    public static ArrayList<SJF> schedulingSJF(ArrayList<SJF> arrivedProcesses, int conSwitch) {
        int time = 0;
        int wholeTime = 0;
        ArrayList<SJF> executedProcesses = new ArrayList<>();
        ArrayList<SJF> waitingQueue = new ArrayList<>();
        for (int i = 0; i < arrivedProcesses.size(); i++) {//calculating the whole time
            wholeTime += arrivedProcesses.get(i).getBurstTime() + conSwitch;
        }

        wholeTime = wholeTime - conSwitch;
        for (int i = 0; i < wholeTime; i++) {
            for (int j = 0; j < arrivedProcesses.size(); j++) { //find waiting processes
                if (arrivedProcesses.get(j).getArrivalTime() <= time && !waitingQueue.contains(arrivedProcesses.get(j))) {
                    waitingQueue.add(arrivedProcesses.get(j));
                }
            }
            SJF readyProcess = getMinBurst(waitingQueue);
            int processTime = readyProcess.getBurstTime();
            while (processTime > 0) {//process executing
                processTime--;
                time++;
                i++;

            }
            readyProcess.setTurnaroundTime(time - readyProcess.getArrivalTime());
            readyProcess.setWaitingTime(readyProcess.getTurnaroundTime() - readyProcess.getBurstTime());
            i += conSwitch;
            time += conSwitch;
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
}