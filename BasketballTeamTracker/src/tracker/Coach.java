package tracker;

/**
 * The Coach class is inherited from the Roster class and is designed to represent a coach
 * on the team's roster, which consists of two unique fields: the coach's overall win percentage
 * and their playoff win percentage. This class inherits all methods from the Roster class.
 * It performs operations such as modifying the win percentage. It also implements the abstract
 * methods in the Roster class.
 */

public class Coach extends Roster implements Comparable<Coach>{
    // FIELDS //
    private double winPercentage;
    private double playoffWinPercentage;


    // CONSTRUCTORS //

    /**
     * Constructs a Coach object from a Roster object alongside other statistics.
     *
     * @param name
     * @param age
     * @param matchesAttended
     * @param playoffMatchesAttended
     */
    public Coach(String name, int age, int matchesAttended, int playoffMatchesAttended) {
        super(name, age, matchesAttended, playoffMatchesAttended);
    }


    // INSTANCE METHODS //

    /**
     * Modifies the statistics of a coach depending on if they won the match or not.
     *
     * @param matchWon true if the match was a win
     * @param isPlayoff true if the match was a playoff match
     */
    public void modifyWinPercentage(boolean matchWon, boolean isPlayoff) {
        if(matchWon) {
            winPercentage = round((100 + winPercentage * matchesAttended) / (1 + matchesAttended));
            if(isPlayoff) {
                playoffWinPercentage = round((100 + playoffWinPercentage * playoffMatchesAttended) / (1 + playoffMatchesAttended));
            }
        } else {
            winPercentage = round((winPercentage * matchesAttended) / (1 + matchesAttended));
            if(isPlayoff) {
                playoffWinPercentage = round((playoffWinPercentage * playoffMatchesAttended) / (1 + playoffMatchesAttended));
            }
        }
    }

    /**
     * Sets the matches played all to zero and sets all of their stats to zero.
     */
    public void resetStatistics() {
        super.resetStatistics();
        winPercentage = 0;
        playoffWinPercentage = 0;
    }

    /**
     * Saves all the statistics based off a list of all the stats.
     *
     * @param stats the list of all the stats
     */
    void saveStatistics(String[] stats) {
        winPercentage = Double.parseDouble(stats[5]);
        playoffWinPercentage = Double.parseDouble(stats[6]);
    }

    /**
     * Lists out all the stats separated by comma, ready to be saved onto a .csv file.
     *
     * @return the stats
     */
    public String listOutStats() {
        return winPercentage + "," + playoffWinPercentage;
    }

    /**
     * Nicely displays all the information about the player.
     */
    public void display() {
        System.out.printf("\t\t -----------------------------\n");
        System.out.printf("\t\t | %-25s |\n", name);
        System.out.printf("\t\t -----------------------------\n");
        System.out.printf("Regular Season Win (%%) | Playoff Season Win (%%) \n");
        System.out.printf("-----------------------|------------------------\n");
        System.out.printf("> %-20s | > %-21s\n", matchesAttended + " games", playoffMatchesAttended + " games");
        System.out.printf("> %-20s | > %-21s\n", winPercentage + "%", playoffWinPercentage + "%");
    }


    /**
     * Returns a nicely formatted String with all the necessary information.
     *
     * @return the nicely formatted String
     */
    @Override
    public String toString() {
        return super.toString() + "Win percentage (regular / playoff): " + winPercentage + "% / " + playoffWinPercentage + "%";
    }

    /**
     * Compares win%.
     *
     * @param other the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Coach other) {
        return Double.compare(other.winPercentage,  this.winPercentage);
    }
}
