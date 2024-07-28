import tracker.Team;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    // GLOBAL INSTANCES //
    public static final Scanner scan = new Scanner(System.in);
    public static final Team team = new Team("Toronto Raptors");


    // CONSTANTS //

    static final String ROSTER_FILE_PATH = "src/tracker/Roster.csv";

    static final String EXTRA_PLAYER_FILE_PATH = "src/tracker/extraPlayers/";

    static final String EXTRA_COACH_FILE_PATH = "src/tracker/extraCoaches/";

    static final String MATCH_FILE_PATH = "src/tracker/matches/";


    // METHODS //

    /**
     * Asks for a valid integer user input handling invalid inputs taking min and max values for valid options.
     *
     * @return a valid integer from the user
     */
    public static int getInput(int min, int max, String dialogue) {
        int n;
        System.out.println(dialogue);
        while(true) {
            try {
                System.out.print("> ");
                n = Integer.parseInt(scan.nextLine());
                if(n >= min && n <= max) { // not only does it check for valid input but it must also be in range of the min and max parameters
                    break;
                } else {
                    System.out.println("Invalid option! Please try again...");
                }
            } catch (NumberFormatException ex) { // catches exception if the input is a floating point number or a string
                System.out.println("Invalid input! Please try again...");
            }
        }
        return n;
    }

    /**
     * The load / save menu is where you load and save information such as...
     * Match statistics.
     * Roster statistics.
     */
    public static void loadSaveMenu() throws InterruptedException {
        String fileName;

        while(true){
            System.out.println("Load / Save");
            System.out.println("\t1. Load Match");
            System.out.println("\t2. Load Roster");
            System.out.println("\t3. Save Roster");
            System.out.println("\t0. Go Back");


            int option = getInput(0, 3, "Enter your option");
            if(option == 0) { // Go Back
                System.out.println("Going back...\n");
                mainMenu();
            } else {
                switch(option) {
                    case 1:
                        System.out.print("Enter the file name for the match you want to load (do not include .csv)\n> ");
                        fileName = scan.nextLine();
                        if(team.loadMatchStatistics(MATCH_FILE_PATH, fileName + ".csv")) {
                            System.out.println("Successfully loaded " + MATCH_FILE_PATH + fileName);
                        }
                        break;
                    case 2:
                        if(team.loadRosterStatistics(ROSTER_FILE_PATH)) {
                            System.out.println("Successfully loaded " + ROSTER_FILE_PATH);
                        }
                        break;
                    case 3:
                        if(team.saveRosterStatistics(ROSTER_FILE_PATH)) {
                            System.out.println("Successfully saved roster!");
                        }
                        break;
                }
                TimeUnit.SECONDS.sleep(1);
            }
        }

    }


    /**
     * The management menu is where you manage team related information such as...
     * Sorting the roster or matches.
     * Resetting statistics.
     * Adding and removing people from the roster
     */
    public static void managementMenu() throws InterruptedException {
        String fileName;
        String rosterName;

        while(true){
            System.out.println("Manage");
            System.out.println("\t1. Organize the Roster");
            System.out.println("\t2. Organize the Matches");
            System.out.println("\t3. Reset Statistics");
            System.out.println("\t4. Trade Player");
            System.out.println("\t5. Hire Coach");
            System.out.println("\t6. Fire Coach");
            System.out.println("\t0. Go Back");


            int option = getInput(0, 6, "Enter your option");
            if(option == 0) { // Go Back
                System.out.println("Going back...\n");
                mainMenu();
            } else {
                switch(option) {
                    case 1:
                        team.organizeRoster();
                        break;
                    case 2:
                        team.organizeMatches();
                        break;
                    case 3:
                        System.out.println("Warning: if you reset all stats will be lost");
                        System.out.print("Please enter \"y\" if you confirm to reset, otherwise enter any key to go back\n> ");
                        if(scan.nextLine().equals("y")) {
                            team.resetStatistics();
                            team.saveRosterStatistics(ROSTER_FILE_PATH);
                        } else {
                            System.out.println("Statistics were NOT reset");
                        }
                        break;
                    case 4:
                        System.out.print("Enter the name of the player you want to get\n> ");
                        fileName = scan.nextLine();
                        System.out.print("Enter the name of the player you want to give away\n> ");
                        rosterName = scan.nextLine();
                        team.tradePlayer(EXTRA_PLAYER_FILE_PATH, fileName + ".csv", rosterName);
                        break;
                    case 5:
                        System.out.print("Enter the name of the coach you want to hire\n> ");
                        fileName = scan.nextLine();
                        team.hireCoach(EXTRA_COACH_FILE_PATH, fileName + ".csv");
                        break;
                    case 6:
                        System.out.print("Enter the name of the coach you want to fire\n> ");
                        rosterName = scan.nextLine();
                        team.fireCoach(rosterName);
                        break;
                }
                TimeUnit.SECONDS.sleep(1);
            }
        }

    }

    /**
     * The print menu is where you can print nicely formatted information regarding the team.
     */
    public static void printMenu() throws InterruptedException {

        while(true) {
            System.out.println("\t1. Particular Player / Coach");
            System.out.println("\t2. The Roster");
            System.out.println("\t3. All Matches");
            System.out.println("\t0. Go Back");

            int option = getInput(0, 4, "Enter your option");
            if(option == 0) { // Go Back
                System.out.println("Going back...\n");
                mainMenu();
            } else {
                switch(option) {
                    case 1:
                        System.out.print("Enter the name of player / coach\n> ");
                        String name = scan.nextLine();
                        team.displayRoster(name);
                        System.out.println();
                        break;
                    case 2:
                        team.displayWholeRoster();
                        break;
                    case 3:
                        team.displayAllMatches();
                        break;
                }
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }

    /**
     * The main menu is the central hub for all the sub-methods such as...
     * loadSaveMenu()
     * managementMenu()
     * printMenu()
     */
    public static void mainMenu() throws InterruptedException {
        System.out.println("Tracker for the " + team.getTeamName());
        System.out.println("\t1. Load / save");
        System.out.println("\t2. Manage");
        System.out.println("\t3. Print");
        System.out.println("\t0. Exit and save");
        while(true) {
            int option = getInput(0, 3, "Enter your option");
            if(option == 0) {

                System.out.println("Do you want to automatically save the roster for the " + team.getTeamName() + "?");
                System.out.print("If yes please enter \"y\", otherwise enter any key to continue\n> ");
                String input = scan.nextLine();
                if(input.equals("y")) {
                    team.saveRosterStatistics(ROSTER_FILE_PATH);
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("Roster saved successfully!");
                }
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Exited program successfully!");
                System.exit(0);
            } else {
                switch(option) {
                    case 1:
                        loadSaveMenu();
                        break;
                    case 2:
                        managementMenu();
                        break;
                    case 3:
                        printMenu();
                        break;
                }
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }


    // MAIN METHOD //
    public static void main(String[] args) throws InterruptedException {
        System.out.println("NOTE: when you are inputting the name of a player, it is case sensitive and include the \"_\"\n");
        System.out.println("Do you want to automatically load the roster for the " + team.getTeamName() + "?");
        System.out.print("If yes please enter \"y\", otherwise enter any key to continue\n> ");
        String input = scan.nextLine();
        if(input.equals("y")) {
            team.loadRosterStatistics(ROSTER_FILE_PATH);
        }
        mainMenu();
    }
}