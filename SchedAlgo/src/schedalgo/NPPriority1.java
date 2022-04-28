/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedalgo;

import java.util.Scanner;

/**
 *
 * @author brodt_000
 */

 // Itong class struct na to, ito yung array/structure ko, ito yung ginagamit ko pang-store ng mga process, burst time, etc.
// ito rin ginagamit ko sa queue
 class Struct{
    public String procName;
    public int arrival;
    public int burst;
    public int prio1;
    public int endTime;
    public int turnAround;
    public int waitTime;
    
    //constructor, pag ni-call mo yung structure/array kailangan ma-lagyan yung mga variables
    public Struct(String procName,int arrival,int burst,int prio1)
    {
        this.procName = procName;
        this.arrival = arrival;
        this.burst = burst;
        this.prio1 = prio1;
    }
    
    //getter, para makuha or ma-print mo yung value ng variables
    public String getProcName(){ return procName; }
    public int getArrival(){ return arrival; }
    public int getBurst(){ return burst; }
    public int getPrio(){ return prio1; }
    public int getEndTime() { return endTime; }
    
    //setter, pang-edit or pang-lagay ng value bawat variables
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
    public void setPrio(int prio1)
    {
        this.prio1 = prio1;
    }
    public void setEndTime(int endTime)
    {
        this.endTime = endTime;
    }
    
    // pang-print lang to ng processes
    public void printAll()
    {
        System.out.println(this.procName + "\t\t" + this.arrival + "\t\t" + this.burst + "\t\t" + this.prio1);
    }
    
    // pang-print ng TT at WT
    public void printTurnWaitTable()
    {
        System.out.println(this.procName + "\t\t" + this.turnAround + "\t\t" + this.waitTime);
    }
} // END CLASS STRUCT
        
        
public class NPPriority1 {
    
    // Eto yung method pang-compute ng mga TT at WT ng ganttChart
    public static void compute(Struct[] ganttChart)
    {
        int counter;
        int arrLength = ganttChart.length;
        
        // Eto cinocompute yung TT at WT
        for(counter = 0; counter < arrLength; counter++)
        {
            ganttChart[counter].turnAround = ganttChart[counter].endTime - ganttChart[counter].arrival;
            ganttChart[counter].waitTime = ganttChart[counter].turnAround - ganttChart[counter].burst;
        }
        System.out.println();
        System.out.println("Job\tTurnaround Time\tWaiting Time");
        
        // Eto nagpiprint ng TT at WT gamit yung function ng structure na ginawa ko sa Class Struct
        for(counter=0;counter<arrLength;counter++)
        {
            ganttChart[counter].printTurnWaitTable();
        }
        float aveTT=0,aveWT=0,totalBurst=0;
        
        // Eto pangcompute ng average TT at WT pati Total Burst Time
        // Gagamitin ko kasi yung total burst time para macompute yung CPU Utilization
        for(counter=0;counter<arrLength;counter++)
        {
            aveTT += ganttChart[counter].turnAround;
            aveWT += ganttChart[counter].waitTime;
            totalBurst += ganttChart[counter].burst;
        }
        
        // Puro print nalang to
        System.out.println();
        System.out.println("Average Turnaround Time: "+aveTT/arrLength);
        System.out.println("Average Waiting Time: "+aveWT/arrLength);
        System.out.println("Percent CPU Utilization: "+(totalBurst/ganttChart[arrLength-1].endTime)*100+"%");
        System.out.println("=============================================");
    }
    
    // Eto naman yung method para sa paglalagay ng process sa queue, at pag-p-process ng queue
    public static void NPP(Struct[] arr1)
    {
        int counter1;
        int counter2;
        int arrLength = arr1.length;
        Struct tempStr; // if an error occur saying NullPointException, try populating this adding tempStr = new Struct(" ",0,0,0);
        Struct[] ganttChart = new Struct[arrLength];
        // Etong ganttChart, nandito yung order ng processes pagtapos nilang
        // ma-process lahat
        
        //arrange the structure base sa arrival time
        for(counter1 = 0; counter1 < arrLength; counter1++)
            for(counter2 = 1; counter2 < (arrLength-counter1);counter2++)
            {
                if(arr1[counter2 - 1].getArrival() > arr1[counter2].getArrival())
                {
                    tempStr = arr1[counter2 - 1];
                    arr1[counter2 - 1] = arr1[counter2];
                    arr1[counter2] = tempStr;
                }
            }
        
        int currentTime = 0;
        int startingPoint = 0;
        int rear = -1;
        int ganttChartCounter = 0;
        int ascii1,ascii2;
        Struct[] queue = new Struct[arrLength];
        
        System.out.println();
        System.out.println("=============================================");
        System.out.println("Job\tArrival Time\tBurst Time\tPriority Level");
        
        // Dito sa part na to pino-process yung array/structure
        while(startingPoint < arrLength || rear >= 0)
        {
           // Dito sa part na to nagpapasok ng process/job sa queue base sa kung less than or equal yung arrival time
           // sa current time.
           // Yung REAR na variable, yan yung pinaka likuran ng queue
           // Di mo naman need ng REAR, pwedeng FRONT ang gamitin, which is unahan ng queue.
           // Although nilalagay ko kasi sa likod ng queue yung next na ipoprocess imbis na
           // sa harap nilalagay, kaya REAR yung ginamit ko.
           // Also, importanteng may FRONT o kaya REAR ka sa queue.
           // Kasi yung laman ng queue mo ay hindi laging puno.
           // Which means pag nag-process ka sa queue, maaaring pumunta ka sa isang
           // index na walang laman, tapos magkakaron ng NullPointerException kapag inacces mo yung index na walang laman.
           // Hindi always puno yung queue kasi yung size ng queue is same sa size ng array
           // since hindi naman lahat ng process papasok sa queue, hindi nila mapupuno yung queue
           // so importanteng either may FRONT o REAR variable ka sa queue para alam mo kung hanggang saan lang
           // May laman yung queue at di mo ma-access yung walang laman, kasi magkaka-NullPointerException error.
           // BTW, yung FRONT ay INDEX yun nung first element sa array, yung REAR ay INDEX ng last element sa ARRAY.
           for(counter1=startingPoint;counter1<arrLength;counter1++)
           {
                if(arr1[counter1].getArrival() <= currentTime)
                {
                    queue[(rear+1)] = arr1[counter1];
                    rear += 1;
                    startingPoint++;
                }
                else if(arr1[counter1].getArrival() > currentTime)
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
           
           // ina-arrange neto yung priority sa queue,
           // nilalagay yung highest priority sa REAR
           // Nalimutan ko na kung bakit ko nilalagay sa REAR eh,
           // Pero alam ko may importanteng bearing kung bakit sa likod ko nilalagay
           // Nakalimutan ko lang kung ano yung reason HAHAHA pero alam ko mas better
           // kapag nasa REAR yung highest priority
           // Though, di ko naman sinasabing ilagay mo sa REAR yung highest priority
           // Pero if ever man maglagay ka sa FRONT at magka-error ka ng NULLPOINTEREXCEPTION
           // Try mo ilagay sa REAR yung mga importante pag ganun yung error.
           // Also, sorting to. HAHAHA selection sort ata ginamit ko para ma-sort yung priority
           // BTW, kung makikita mo yung mga .getPrio() tapos .getProcName(), mga priority at names
           // lang yun ng process, wag ka malito. Function kasi yun nung Class Struct eh.
           for(counter1 = 0; counter1 < rear; counter1++)
           { 
                for(counter2 = 1; counter2 < ((rear+1)-counter1);counter2++)
                {
                    if(queue[counter2 - 1].getPrio() < queue[counter2].getPrio())
                    {
                        tempStr = queue[counter2 - 1];
                        queue[counter2 - 1] = queue[counter2];
                        queue[counter2] = tempStr;
                    }
                    // Etong code segment sa baba nito is di naman masyadong importante
                    // pero ginawa ko to para if ever man na parehas silang nasa QUEUE at equal sila ng Priority
                    // Ipa-prioritize nya yung nauna base sa pangalan.
                    // Kunwari si A at D parehong may priority na 1
                    // Gamit tong code segment na to ipa-process muna si A kaysa kay D even though parehas sila ng
                    // priority. HAHAHA ganun kasi sabi ni Maam eh, pag parehas silang nasa queue at parehas priority
                    // mauuna daw yung UNANG LETTER sa ALPHABET
                    // Though di naman ata importante masyado, kaya medyo optional lang tong process na to
                    else if(queue[counter2-1].getPrio() == queue[counter2].getPrio())
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
               
           // Eto na yung ipapasok na yung process sa ganttChart kapag natapos na syang maprocess
           // kukunin yung burst time nya gamit yung .getBurst(); tapos idadagdag nya sa
           // current time para malaman kelan yung endTime ng isang process
           queue[rear].printAll();
           ganttChart[ganttChartCounter] = queue[rear];
           currentTime += queue[rear].getBurst();
           ganttChart[ganttChartCounter].setEndTime(currentTime);
           ganttChartCounter++;
                
           //Pagka-tapos ng isang process, nababawasan yung laman ng queue so bawasan yung REAR. REAR--;
           rear--;
        }
        
        // After ng lahat ng process cocomputing naman yung TT, WT, at CPU Util gamit tong method na to.
        compute(ganttChart);
    }
    
    // Eto yung main method, dito start
    public static void main()
    {
        System.out.println("Welcome to Non-Preemptive Priority Scheduling!");
        int arrLength=0;
        Scanner in = new Scanner(System.in);
        String continuation; // pang "press any key to continue" lang to, wag mo na isipin para san to haha
        
        while(arrLength<=0) // Eto para hindi mag-error if ever mag-enter ang user ng zero or negative sa number of process
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
            Struct[] arr;
            arr = new Struct[arrLength];
            int count;
            String jobNumber;
            
            // Wala lang to, wag mo na isipin, nilagyan ko lang para hindi null yung laman ng array/structure
            for(count=0;count<arrLength;count++)
            {
                 arr[count] = new Struct(" ",0,0,0);
            }
        
            // lagyan and array ng process(es) ng names, jobNumber is name ng process like P1, P2, P3, etc...
            for(count=0;count<arrLength;count++)
            {
            jobNumber = "P" + Integer.toString(count+1);
            arr[count].setProcName(jobNumber);
            }
        
            //Lagyan ng laman ang Arrival time
            System.out.println();
            System.out.println("Please enter the Arrival Time for each job respectively! ");
            for(count=0;count<arrLength;count++)
            {
                System.out.print("Arrival Time for " + arr[count].getProcName() + ": " );
                arr[count].setArrival(in.nextInt());
            }
        
            //Lagyan ng laman ang Burst
            System.out.println();
            System.out.println("Please enter the Burst time for each job respectively!");
            for(count=0;count<arrLength;count++)
            {
                System.out.print("Burst Time of " + arr[count].getProcName() + ": ");
                arr[count].setBurst(in.nextInt());
            }
        
            //Lagyan ang Priority
            System.out.println();
            System.out.println("Please enter the Priority for each job respectively!");
            for(count=0;count<arrLength;count++)
            {
                System.out.print("Priority for " + arr[count].getProcName() + ": ");
                arr[count].setPrio(in.nextInt());
            }
            
            // call the NPPriority method to enqueue and process
            NPP(arr);
        }
}
