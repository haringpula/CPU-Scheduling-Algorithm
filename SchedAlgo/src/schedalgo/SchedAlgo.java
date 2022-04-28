/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedalgo;

/**
 *
 * @author brodt_000
 */
import java.util.Scanner;
import java.io.IOException;
/**
 *
 * @author
 * 
 * Sanchez, King Red
 * Navarro, Angelo
 * Ognita, Michelle
 * Gudio, Dianne
 * Mondia, Robbie
 */

public class SchedAlgo {
    public static void main(String[] args) throws IOException, InterruptedException {
        int choice=1;
        String choice1;
        Scanner in = new Scanner(System.in);
        while(true)
        {
            System.out.println("===============================================");
            System.out.println("Welcome to Scheduling Algorithm!");
            System.out.println();
            System.out.println("Please choose your algorithm.");
            System.out.println("1.) Non-preemptive Priority");
            System.out.println("2.) Shortest Job First");
            System.out.println("3.) Shortest Remaining Time First");
            System.out.println("4.) First Come First Serve");
            System.out.println("5.) PP");
            System.out.println("6.) Exit");
            System.out.print("Your choice: ");
            choice = in.nextInt();
            in.nextLine();
            System.out.println("==============================================");
            System.out.println();
            switch(choice)
            {
                case 1: NPPriority1.main();
                        System.out.println();
                        System.out.println("Press ANY KEY to return to menu.");
                        choice1 = in.nextLine();
                        break;
                case 2: SJFirst.main();
                        System.out.println("Press ANY KEY to return to menu.");
                        System.out.println();
                        choice1 = in.nextLine();
                        break;
                case 3: SRTF.main();
                        System.out.println("Press ANY KEY to return to menu.");
                        System.out.println();
                        choice1 = in.nextLine();
                        break;
                case 4: FCFS.main();
                        System.out.println("Press ANY KEY to return to menu.");
                        System.out.println();
                        choice1 = in.nextLine();
                        break;
                case 5: PP.main();
                        System.out.println("Press ANY KEY to return to menu.");
                        System.out.println();
                        choice1 = in.nextLine();
                        break;
                case 6: System.out.println("Exiting, press any key to continue.");
                        choice1 = in.nextLine();
                        System.exit(1);
                default: System.out.println("Invalid input!");
                         System.out.println("Press any key to return to menu.");
                         choice1 = in.nextLine();
                         break;
            }
        }
        
    }
    
}
