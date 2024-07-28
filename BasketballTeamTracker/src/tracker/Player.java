package tracker;

/**
 * The Player class is inherited from the Roster class and is designed to represent a player
 * on the team's roster, which consists of three unique fields: the player's jersey number,
 * a list of their overall stats, and a list of their playoff stats. This class inherits all
 * methods from the Roster class. It performs operations such as modifying statistics. It also
 * implements the abstract methods in the Roster class.
 */

public class Player extends Roster implements Comparable<Player> {
    // FIELDS //
    private final int jerseyNumber;
    private double[] statistics;
    private double[] playoffStatistics;

    // CONSTRUCTOR //

    /**
     * Constructs a Player object from a Roster object alongside other statistics.
     *
     * @param name
     * @param age
     * @param jerseyNumber
     * @param matchesAttended
     * @param playoffMatchesAttended
     */
    public Player(String name, int age, int jerseyNumber, int matchesAttended, int playoffMatchesAttended) {
        super(name, age, matchesAttended, playoffMatchesAttended);
        this.jerseyNumber = jerseyNumber;
        statistics = new double[3];
        playoffStatistics = new double[5];
    }


    // INSTANCE METHODS //

    /**
     * Modifies the statistics of a player based off of a list of their stats from a match.
     *
     * @param stats the list of their stats from a match
     * @param isPlayoff true if the match was a playoff match
     */
    public void modifyStatistic(String[] stats, boolean isPlayoff) {
        for (int i = 0; i < 3; i++) {
            statistics[i] = round((statistics[i] * matchesAttended + Double.parseDouble(stats[i + 1])) / (matchesAttended + 1));

            if(isPlayoff) {
                playoffStatistics[i] = round((playoffStatistics[i] * playoffMatchesAttended + Double.parseDouble(stats[i + 1])) / (playoffMatchesAttended + 1));
            }
        }

        if(isPlayoff) {
            for (int i = 3; i < 5; i++) {
                playoffStatistics[i] = round((playoffStatistics[i] * playoffMatchesAttended + Double.parseDouble(stats[i + 1])) / (playoffMatchesAttended + 1));
            }
        }

    }

    /**
     * Sets the matches played all to zero and sets all of their stats to zero.
     */
    public void resetStatistics() {
        super.resetStatistics();
        statistics = new double[3];
        playoffStatistics = new double[5];
    }

    /**
     * Saves all the statistics based off a list of all the stats.
     *
     * @param stats the list of all the stats
     */
    public void saveStatistics(String[] stats) {
        statistics[0] = Double.parseDouble(stats[6]);
        statistics[1] = Double.parseDouble(stats[7]);
        statistics[2] = Double.parseDouble(stats[8]);
        playoffStatistics[0] = Double.parseDouble(stats[9]);
        playoffStatistics[1] = Double.parseDouble(stats[10]);
        playoffStatistics[2] = Double.parseDouble(stats[11]);
        playoffStatistics[3] = Double.parseDouble(stats[12]);
        playoffStatistics[4] = Double.parseDouble(stats[13]);
    }

    /**
     * Lists out all the stats separated by comma, ready to be saved onto a .csv file.
     *
     * @return the stats
     */
    public String listOutStats() {
        return jerseyNumber + "," + statistics[0] + "," + statistics[1] + "," + statistics[2] + "," + playoffStatistics[0] + "," + playoffStatistics[1] + "," + playoffStatistics[2] + "," + playoffStatistics[3] + "," + playoffStatistics[4];

    }

    /**
     * Nicely displays all the information about the player.
     */
    public void display() {
        System.out.printf("\t   -----------------------------\n");
        System.out.printf("\t   | %-25s |\n", jerseyNumber + " - " + name);
        System.out.printf("\t   -----------------------------\n");
        System.out.printf("Regular Season Stats | Playoff Season Stats\n");
        System.out.printf("---------------------|---------------------\n");
        System.out.printf("> %-18s | > %-21s\n", matchesAttended + " games", playoffMatchesAttended + " games");
        System.out.printf("> %-18s | > %-21s\n", statistics[0] + " ppg", playoffStatistics[0] + " ppg");
        System.out.printf("> %-18s | > %-21s\n", statistics[1] + " apg", playoffStatistics[1] + " apg");
        System.out.printf("> %-18s | > %-21s\n", statistics[2] + " rpg", playoffStatistics[2] + " rpg");
        System.out.printf("> %-18s | > %-21s\n", "N/A", playoffStatistics[3] + "%");
        System.out.printf("> %-18s | > %-21s\n", "N/A", playoffStatistics[4] + "%");

    }


    /**
     * Returns a nicely formatted String with all the necessary information.
     *
     * @return the nicely formatted String
     */
    @Override
    public String toString() {
        return super.toString() + "Jersey number: " + jerseyNumber + "\n"+
                "Statistics: " + statistics[0] + "ppg, " + statistics[1] + "apg, " + statistics[2] + "rpg"  + "\n" +
                "Playoff statistics: " + playoffStatistics[0] + "ppg, " + playoffStatistics[1] + "apg, " + playoffStatistics[2] + "rpg, " + playoffStatistics[3] + "%, " + playoffStatistics[4] + "%";
    }

    /**
     * Compares points per game.
     *
     * @param other the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Player other) {
        return Double.compare(other.statistics[0], this.statistics[0]);
    }
}
