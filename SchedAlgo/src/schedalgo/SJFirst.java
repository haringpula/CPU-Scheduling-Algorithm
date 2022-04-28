/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedalgo;

import java.util.Scanner;

class sjfStruct{
    public String procName;
    public int arrival;
    public int burst;
    public int endTime;
    public int turnAround;
    public int waitTime;
    
    //constructor
    public sjfStruct(String procName,int arrival,int burst)
    {
        this.procName = procName;
        this.arrival = arrival;
        this.burst = burst;
    }
    
    //getter
    public String getProcName(){ return procName; }
    public int getArrival(){ return arrival; }
    public int getBurst(){ return burst; }
    public int getEndTime() { return endTime; }
    
    //setter
    public void setProcName(String procName)
    {
        this.procName = procName; 
    }
    public void setArrival(int arrival)
    { 
        this.arrival = arrival;
    }
    public void setBurst(int burst)
    {
        this.burst = burst;
    }
    public void setEndTime(int endTime)
    {
        this.endTime = endTime;
    }
    
    public void printAll()
    {
        System.out.println(this.procName + "\t\t" + this.arrival + "\t\t" + this.burst);
    }
    
    public void printTurnWaitTable()
    {
        System.out.println(this.procName + "\t\t" + this.turnAround + "\t\t" + this.waitTime);
    }
}

public class SJFirst {
    
    public static void compute(sjfStruct[] ganttChart)
    {
        int counter;
        int arrLength = ganttChart.length;
        for(counter = 0; counter < arrLength; counter++)
        {
            ganttChart[counter].turnAround = ganttChart[counter].endTime - ganttChart[counter].arrival;
            ganttChart[counter].waitTime = ganttChart[counter].turnAround - ganttChart[counter].burst;
        }
        System.out.println();
        System.out.println("Job\tTurnaround Time\tWaiting Time");
        for(counter=0;counter<arrLength;counter++)
        {
            ganttChart[counter].printTurnWaitTable();
        }
        float aveTT=0,aveWT=0,totalBurst=0;
        for(counter=0;counter<arrLength;counter++)
        {
            aveTT += ganttChart[counter].turnAround;
            aveWT += ganttChart[counter].waitTime;
            totalBurst += ganttChart[counter].burst;
        }
        System.out.println();
        System.out.println("Average Turnaround Time: "+aveTT/arrLength);
        System.out.println("Average Waiting Time: "+aveWT/arrLength);
        System.out.println("Percent CPU Utilization: "+(totalBurst/ganttChart[arrLength-1].endTime)*100+"%");
        System.out.println("=============================================");
    }
    
    public static void SJFirst(sjfStruct[] arr)
    {
        int counter1;
        int counter2;
        int arrLength = arr.length;
        sjfStruct tempStr; // if an error occur, try populating this with tempStr = new Struct("",0,0,0);
        sjfStruct[] ganttChart = new sjfStruct[arrLength];
        
        //arrange the arrival time
        for(counter1 = 0; counter1 < arrLength; counter1++)
            for(counter2 = 1; counter2 < (arrLength-counter1);counter2++)
            {
                if(arr[counter2 - 1].getArrival() > arr[counter2].getArrival())
                {
                    tempStr = arr[counter2 - 1];
                    arr[counter2 - 1] = arr[counter2];
                    arr[counter2] = tempStr;
                }
            }
        
        int currentTime = 0;
        int startingPoint = 0;
        int rear = -1;
        int ganttChartCounter = 0;
        int ascii1,ascii2;
        sjfStruct[] queue = new sjfStruct[arrLength];
        
        System.out.println();
        System.out.println("=============================================");
        System.out.println("Job\tArrival Time\tBurst Time");
        
        //nag-p-process ng array
        while(startingPoint < arrLength || rear >= 0)
        {
           for(counter1=startingPoint;counter1<arrLength;counter1++) //nag-q-queue
           {
                if(arr[counter1].getArrival() <= currentTime)
                {
                    queue[(rear+1)] = arr[counter1];
                    rear += 1;
                    startingPoint++;
                }
                else if(arr[counter1].getArrival() > currentTime)
                {
                    if(rear==-1)
                    {
                        currentTime++;
                        counter1=startingPoint-1;
                    }
                    else
                        break;
                }
           }
            
           for(counter1 = 0; counter1 < rear; counter1++)// arrange the burst, put shortest burst at the back
           { 
                for(counter2 = 1; counter2 < ((rear+1)-counter1);counter2++)
                {
                    if(queue[counter2 - 1].getBurst() < queue[counter2].getBurst())
                    {
                        tempStr = queue[counter2 - 1];
                        queue[counter2 - 1] = queue[counter2];
                        queue[counter2] = tempStr;
                    }
                    else if(queue[counter2-1].getBurst() == queue[counter2].getBurst())
                    {
                       
                        ascii1 = queue[counter2-1].getProcName().charAt(1);
                        ascii2 = queue[counter2].getProcName().charAt(1);
                        if(ascii1 < ascii2)
                        {
                            tempStr = queue[counter2 - 1];
                            queue[counter2 - 1] = queue[counter2];
                            queue[counter2] = tempStr;
                        }
                                
                    }
                }
           }
               
           //process yung highest prio dito
           queue[rear].printAll();
           ganttChart[ganttChartCounter] = queue[rear];
           currentTime += queue[rear].getBurst();
           ganttChart[ganttChartCounter].setEndTime(currentTime);
           ganttChartCounter++;
                
           //adjust yung size ng queue
           rear--;
        }
        compute(ganttChart);
    }
    
    public static void main() {
        System.out.println("Welcome to Shortest Job First Scheduling!");
        Scanner in = new Scanner(System.in);
        int arrLength=0;
        String continuation;
        while(arrLength<=0)
        {
            System.out.print("Please enter the number of process(es): ");
            arrLength = in.nextInt();
            in.nextLine();
            if(arrLength<=0)
            {
                System.out.println();
                System.out.println("Processes(es) cannot be zero or negative");
                System.out.println("Press any key to continue.");
                continuation = in.nextLine();
            }
        }
        
        sjfStruct[] arr;
        arr = new sjfStruct[arrLength];
        int count;
        String jobNumber;
        
        for(count=0;count<arrLength;count++)
        {
            arr[count] = new sjfStruct(" ",0,0);
        }
        
        //populate array of process(es)
        for(count=0;count<arrLength;count++)
        {
            jobNumber = "P" + Integer.toString(count+1);
            arr[count].setProcName(jobNumber);
        }
        
        //populate arrTime
        System.out.println();
        System.out.println("Please enter the Arrival Time for each job respectively! ");
        for(count=0;count<arrLength;count++)
        {
            System.out.print("Arrival Time for " + arr[count].getProcName() + ": " );
            arr[count].setArrival(in.nextInt());
        }
        
        //populate burst time
        System.out.println();
        System.out.println("Please enter the Burst time for each job respectively!");
        for(count=0;count<arrLength;count++)
        {
            System.out.print("Burst Time of " + arr[count].getProcName() + ": ");
            arr[count].setBurst(in.nextInt());
        }
        SJFirst(arr);
    }
    
}
