package tracker;

/**
 * The Team class is designed to represent a basketball team consisting
 * of one singular field: the name of the team. This class allows you to
 * execute a variety of operations such as loading all information from a
 * match and the current roster, saving your current roster, sorting your
 * roster and matches, resetting your team's statistics, a variety of displaying,
 * trading players, and firing/hiring coaches.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Team {
    // FIELDS //
    private String teamName;
    private ArrayList<Roster> roster;
    private ArrayList<Match> matches;
    private ArrayList<PlayoffMatch> playoffMatches;


    // CONSTRUCTORS //

    /**
     * Constructs a team object given a team name.
     *
     * @param teamName
     */
    public Team(String teamName) {
        this.teamName = teamName;
        roster = new ArrayList<>();
        matches = new ArrayList<>();
        playoffMatches = new ArrayList<>();
    }


    // ACCESSORS AND MUTATORS //

    /**
     * Accesses the name of the team.
     *
     * @return the team name
     */
    public String getTeamName() {
        return teamName;
    }


    // INSTANCE METHODS //

    /**
     * Loads all the information from a match file and modifies player and coach statistics
     * based on the results of the match. Once a match has been loaded, it will be moved to
     * the trash folder.
     *
     * @param matchFilePath the path where the file is located
     * @param matchFileName the name of the file
     * @return true if the loading was successful
     */
    public boolean loadMatchStatistics(String matchFilePath, String matchFileName) {
        File file = new File(matchFilePath + matchFileName);

        if(!file.exists()) {
            System.out.println(matchFilePath + matchFileName + " does not exist!");
            return false;
        }

        try (Scanner scan = new Scanner(file) ){
            String[] arr = scan.nextLine().split(",");
            boolean isPlayoff = arr[0].equals("playoff");
            boolean isWin = Integer.parseInt(arr[2]) - Integer.parseInt(arr[3]) > 0;

            // create new match
            if(!isPlayoff) {
                matches.add(new Match(arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3])));
            } else {
                playoffMatches.add(new PlayoffMatch(arr[1], Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), arr[4]));
            }

            // update coaches
            for (Roster r : roster) {
                if(r instanceof Coach) {
                    ((Coach) r).modifyWinPercentage(isWin, isPlayoff);
                    r.addMatch();
                    if (isPlayoff) {
                        r.addPlayoffMatch();
                    }
                }
            }

            // update players
            while(scan.hasNext()) {
                String[] playerInfo = scan.nextLine().split(",");
                int rosterIndex = findRoster(playerInfo[0]);

                if (rosterIndex != -1 && roster.get(rosterIndex) instanceof Player) {
                    Player player = (Player) roster.get(rosterIndex);
                    player.modifyStatistic(playerInfo, isPlayoff);
                    player.addMatch();
                    if (isPlayoff) {
                        player.addPlayoffMatch();
                    }
                }

            }

        } catch (IOException iox) {
            System.out.println(iox.getMessage());
        }

        moveToTrash(matchFilePath, matchFileName);

        return true;
    }


    /**
     * Loads all the information from the roster file and modifies player and coach statistics.
     *
     * @param rosterFilePath the file's location
     * @return true if the loading was successful
     */
    public boolean loadRosterStatistics(String rosterFilePath) {
        File file = new File(rosterFilePath);

        if(!file.exists()) {
            System.out.println("Problem reading file");
            return false;
        }

        try (Scanner scan = new Scanner(file)) {
            while (scan.hasNext()) {
                String[] arr = scan.nextLine().split(",");
                Roster r;

                // check if player already exists
                if(findRoster(arr[0]) != -1) {
                    System.out.println("This player already exists!");
                    break;
                }

                // save player data
                if (arr[4].equals("player")) {
                    r = new Player(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[5]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
                } else if (arr[4].equals("coach")) {
                    r = new Coach(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]));
                } else {
                    continue;
                }
                r.saveStatistics(arr);
                roster.add(r);

            }

        } catch (IOException iox) {
            System.out.println(iox.getMessage());
        }
        return true;

    }

    /**
     * Writes all the information from the roster on the roster file.
     *
     * @param rosterFilePath the file's location
     * @return true if the loading was successful
     */
    public boolean saveRosterStatistics(String rosterFilePath) {
        try (FileWriter writer = new FileWriter(rosterFilePath, false)) {
            for (Roster r : roster) {
                // writes roster attributes
                writer.write(r.name + "," + r.age + "," + r.matchesAttended + "," + r.playoffMatchesAttended + ",");

                // differentiates between player and coach
                if (r instanceof Player) {
                    writer.write("player,");
                } else if (r instanceof Coach) {
                    writer.write("coach,");
                }

                // write the stats (different depending on if it's a player or a coach)
                writer.write(r.listOutStats() + "\n");
            }
        } catch (IOException iox) {
            System.out.println("Problem writing " + rosterFilePath);
            return false;
        }
        return true;
    }

    /**
     * Organizes the roster list by first sorting the coaches by highest win% then sorting players by highest points per game.
     */
    public void organizeRoster() {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Coach> coaches = new ArrayList<>();
        ArrayList<Roster> organized = new ArrayList<>();

        for(Roster r : roster) {
            if(r instanceof Player) {
                players.add((Player) r);
            } else if(r instanceof Coach) {
                coaches.add((Coach) r);
            }
        }

        Collections.sort(coaches);
        Collections.sort(players);

        organized.addAll(coaches);
        organized.addAll(players);

        roster = organized;

        System.out.println("Successfully sorted coaches by their win percentage");
        System.out.println("Successfully sorted players by their points per game");

    }

    /**
     * Organizes the matches and playoffMatches list by sorting by the highest point differential.
     */
    public void organizeMatches() {
        Collections.sort(matches);
        Collections.sort(playoffMatches);

        System.out.println("Successfully sorted regular season matches by their point differential");
        System.out.println("Successfully sorted playoff season matches by their point differential");
    }

    /**
     * Resets all players and coaches on the roster statistics to 0.
     */
    public void resetStatistics() {
        for(Roster r : roster) {
            r.resetStatistics();
        }
        System.out.println("Statistics reset successfully!");
    }

    /**
     * Finds the index  give the name of a roster member.
     *
     * @param name name of the roster member
     * @return the index number or -1 if they dont exist
     */
    public int findRoster(String name) {
        int index = -1;
        for (int i = 0; i < roster.size(); i++) {
            if(name.equals(roster.get(i).getName())) {
                index = i;
                break;
            }
        }
        return index;
    }


    /**
     * Nicely displays one roster member given the name.
     *
     * @param name name of the roster member
     */
    public void displayRoster(String name) {
        System.out.println();
        int index = findRoster(name);
        if(index != -1) {
            roster.get(index).display();
        } else {
            System.out.println("Sorry, \"" + name + "\" does not exist!");
        }
    }

    /**
     * Nicely displays the whole roster.
     */
    public void displayWholeRoster() {
        System.out.println();
        if(roster.isEmpty()) {
            System.out.println("There is no one on your roster!");
        } else {
            for(Roster r : roster) {
                System.out.println(r + "\n");
            }
        }
    }

    /**
     * Nicely displays all the matches.
     */
    public void displayAllMatches() {
        System.out.println();
        if(matches.isEmpty() && playoffMatches.isEmpty()) {
            System.out.println("You have not played any matches yet!");
        } else {
            for(Match m : matches) {
                System.out.println(m + "\n");
            }
            for(PlayoffMatch m : playoffMatches) {
                System.out.println(m + "\n");
            }
        }
    }

    /**
     * Trades a player on your roster for a player in the extraPlayers folder.
     * Once a player is chosen from the extraPlayers folder, it will be moved
     * to the trash folder.
     *
     * @param obtainPlayerPath the file path of the player you are obtaining
     * @param obtainPlayerName the name of the player you are obtaining
     * @param losePlayerName the name of the player you are losing
     */
    public void tradePlayer(String obtainPlayerPath, String obtainPlayerName, String losePlayerName) {
        File file = new File(obtainPlayerPath + obtainPlayerName);
        int index = findRoster(losePlayerName);
        if(index != -1) {
            loadRosterStatistics(obtainPlayerPath + obtainPlayerName); // add new player to the team
            moveToTrash(obtainPlayerPath, obtainPlayerName);
            roster.remove(index); // remove old player from the team
            System.out.println("Trade successful!");
        } else {
            System.out.println("Player " + losePlayerName + " doesn't exist");
        }
    }

    /**
     * Hires a coach from the extraCoaches folder. Once a coach is chosen
     * from the extraCoaches folder, it will be moved to the trash folder.
     *
     * @param coachFilePath the file path of the coach you are hiring
     * @param coachName the name of the coach you are hiring
     */
    public void hireCoach(String coachFilePath, String coachName) {
        if(loadRosterStatistics(coachFilePath + coachName)) {
            moveToTrash(coachFilePath, coachName);
            System.out.println("Successfully hired!");
        } else {
            System.out.println("Error: " + coachName + "was not hired");
        }
    }


    /**
     * Removes a coach from the roster.
     *
     * @param coachName the name of the coach
     */
    public void fireCoach(String coachName) {
        int index = findRoster(coachName);
        if(index != -1) {
            roster.remove(index);
            System.out.println("Successfully fired!");
        } else {
            System.out.println("Player " + coachName + " doesn't exist");
        }
    }

    /**
     * Moves a file at a given location to the trash folder.
     *
     * @param filePath the current path of your file
     * @param fileName the name of your file
     */
    public void moveToTrash(String filePath, String fileName) {
        try {
            Path current = Paths.get(filePath + fileName);
            Path destination = Paths.get("src/tracker/trash/" + fileName);
            Files.move(current, destination);
        } catch (IOException iox) {
            System.out.println(iox.getMessage());
        }

    }


}