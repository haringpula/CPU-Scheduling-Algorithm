package schedalgo;

import java.util.Scanner;
public class FCFS {
    public void fcfs(int pctr, int[] at, int[] bt, int[] p){
        //so ang plano.. gagawa ng ibat ibang variations ng ganito.. depends sa kung anong algo,
        // di ko muna ilalagay dito sa loob yung akin kayo nalang mag integrate hahaha
    }
    
    public static void main() {
        Scanner in=new Scanner(System.in);
        int pcount,ctr,alp=65,alpi,time,btt=0,ttt=0,wtt=0;
        char temp;
        System.out.println("How many Processes will enter the CPU?");
        pcount=in.nextInt();
        int[] arrival = new int[pcount];
        int[] burst = new int[pcount];
        int[] priority = new int[pcount];
        String[] id = new String[pcount];
        int[] end = new int[pcount];
        int[] turn = new int[pcount];
        int[] wait = new int[pcount];
        for (ctr=0; ctr<pcount; ctr++){
            if (ctr==0)
                alpi=0;
            else
                alpi=1;
            alp=alp+alpi;
            temp=((char)alp);
            System.out.println("Please enter the arrival time for process "+((char)alp)+": ");
            arrival[ctr]=in.nextInt();
            in.nextLine();
            System.out.println("Please enter the burst time for process "+((char)alp)+": ");
            burst[ctr]=in.nextInt();
            in.nextLine();
            System.out.println("Please enter the priority for process "+((char)alp)+": ");
            priority[ctr]=in.nextInt();
            in.nextLine();
            id[ctr]=String.valueOf(temp);
            alpi++;
        }
        int i,j,pos,tmp;
        String ctmp;
        for (i=0; i<pcount-1; i++){
            pos=i;
            for (j=i+1; j<pcount; j++){
                if (arrival[j]<arrival[pos])
                    pos=j;
            }
            tmp=arrival[i];
            arrival[i]=arrival[pos];
            arrival[pos]=tmp;
            tmp=burst[i];
            burst[i]=burst[pos];
            burst[pos]=tmp;
            tmp=priority[i];
            priority[i]=priority[pos];
            priority[pos]=tmp;
            ctmp=id[i];
            id[i]=id[pos];
            id[pos]=ctmp;
        }
        
        time=0;
        for (ctr=0; ctr<pcount; ctr++){
            for (i=0; i<burst[ctr]; i++){
                System.out.print(id[ctr]);
                time++;
            }
            end[ctr]=time;
        }
        
        for (ctr=0; ctr<pcount; ctr++)
            turn[ctr]=end[ctr]-arrival[ctr];
        for (ctr=0; ctr<pcount; ctr++)
            wait[ctr]=turn[ctr]-burst[ctr];
        
        for (ctr=0; ctr<pcount; ctr++)
            btt=btt+burst[ctr];
        for (ctr=0; ctr<pcount; ctr++)
            ttt=ttt+turn[ctr];
        for (ctr=0; ctr<pcount; ctr++)
            wtt=wtt+wait[ctr];
        
        System.out.println("\nArrival");
        for (ctr=0; ctr<pcount; ctr++)
            System.out.print(arrival[ctr]+" ");
        System.out.println("\nBurst");
        for (ctr=0; ctr<pcount; ctr++)
            System.out.print(burst[ctr]+" ");        
        System.out.println("\nPriority");
        for (ctr=0; ctr<pcount; ctr++)
            System.out.print(priority[ctr]+" ");        
        System.out.println("\nID");
        for (ctr=0; ctr<pcount; ctr++)
            System.out.print(id[ctr]+" ");   
        System.out.println("\nEnd");
        for (ctr=0; ctr<pcount; ctr++)
            System.out.print(end[ctr]+" ");  
        System.out.println("\nTurn");
        for (ctr=0; ctr<pcount; ctr++)
            System.out.print(turn[ctr]+" ");
        System.out.println("\nWait");
        for (ctr=0; ctr<pcount; ctr++)
            System.out.print(wait[ctr]+" ");
        System.out.println("\nProcessing end at: " + end[pcount-1]);
        System.out.println("Burst time total is: " + btt);
        System.out.println("Turn ave is: " + (ttt/pcount) );
        System.out.println("Wait ave is: " + (wtt/pcount) );

    }
}