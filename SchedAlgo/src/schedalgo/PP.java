package schedalgo;
import java.util.Scanner;
public class PP {
    
    public static void main() {
        
        Scanner s=new Scanner(System.in);
        
        int i, n=0, p, alp=65, alpi, time;
        int tburstTime=0, aveturnAroundTime=0, aveWaitingTime=0;
        char temp;
        
        System.out.print("Enter number of jobs:");
        n = s.nextInt();
        
        int[] job = new int[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] priority = new int[n];
        String[] id = new String[n];
        int[] endTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] waitingTime = new int[n];
        
        for (i=0; i<n; i++)
        {
            if (i==0)
                alpi=0;
            else
                alpi=1;
            alp = alp+alpi;
            temp = ((char)alp);
            
            System.out.print("\nJOB ["+((char)alp)+"]");
            
            System.out.print("\n\tArrival Time: ");
            arrivalTime[i] = s.nextInt();
            
            System.out.print("\tBurst Time: ");
            burstTime[i] = s.nextInt();
            
            System.out.print("\tPriority: ");
            priority[i] = s.nextInt();
            
            id[i]=String.valueOf(temp);
            alpi++;
        }
        
        
        int x;
        time=0;
        for (i=0; i<n; i++)
        {
            for (x=0; x<burstTime[i]; x++)
            {
                time++;
            }
            endTime[i]=time;
        }
        
        //eto naman yung sa una nating program na isosort niya yung jobs based sa priority pero kapag sinasama ko ito, 
        //yung prioritty number lang nasosort hindi yung jobs, hindi gaya doon sa program natin na walang arrival time  
        
        /*for(i=0;i<n;i++)
        {
        for(int j=i+1;j<n;j++)
            {
                if(priority[i]>priority[j])
                {
                p=priority[i];
                priority[i]=priority[j];
                priority[j]=p;
                p=burstTime[i];
                burstTime[i]=burstTime[j];
                burstTime[j]=p;
                p=job[i];
                job[i]=job[j];
                job[j]=p;
                }
            }
        }*/
        
        
        //eto yung para sa pagsolve ng burst based sa priority and magulo siya
        /*for (i=0; i<n; i++)
        {    
            for(int j=i+1;j<n;j++)
            {
            if(priority[i]>priority[j])    
            burstTime[i] = burstTime[i] - arrivalTime[j];
            priority[j] = burstTime[j] - arrivalTime[i];
            }
        }*/
        
        //meron na tayong end time, pero end time niya based sa NP, hindi pa siya nahahati by pieces kasi magulo pa yung sa code ng PP
        for (i=0; i<n; i++)
            turnAroundTime[i]=endTime[i]-arrivalTime[i];
        for (i=0; i<n; i++)
            waitingTime[i]=turnAroundTime[i]-burstTime[i];
        for (i=0; i<n; i++)
            tburstTime=tburstTime+burstTime[i];
        for (i=0; i<n; i++)
            aveturnAroundTime=aveturnAroundTime+turnAroundTime[i];
        for (i=0; i<n; i++)
            aveWaitingTime=aveWaitingTime+waitingTime[i];
        
        
        System.out.print("\n\nJob \t Arrival Time \t Burst Time \t Waiting Time \t Turn Around Time \t  Priority \n");
        for (i=0; i<n; i++)
        System.out.print("\n "+id[i]+"\t  "+arrivalTime[i]+" \t\t "+burstTime[i]+"\t\t     "+waitingTime[i]+"\t\t    "+turnAroundTime[i]+"\t\t\t   "+priority[i]+"\n");
        
        System.out.println("\n\n \t\t\t Total BT \t Average WT \t Average TT\n"  );
        System.out.println("\n \t\t\t "+tburstTime+" \t\t "+(aveWaitingTime/n)+" \t\t "+(aveturnAroundTime/n)+"\n");
        
        System.out.println("\nEnd Time: " + endTime[n-1]);
        
    }
}
