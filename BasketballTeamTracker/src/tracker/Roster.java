package tracker;

/**
 * The Roster class is designed to represent a person on the team's roster, which consists of four fields:
 * the person's name, age, and the number of matches/playoff matches they have played. This abstract class
 * is to be inherited from two other classes, the Player and Coach classes. It performs operations such as
 * incrementing the number of matches/playoff matches played and resetting statistics. It prepares operations
 * such as saving, listi ng, and displaying statistics.
 */

public abstract class Roster {
    // FIELDS //
    protected String name;
    protected int age;
    protected int matchesAttended;
    protected int playoffMatchesAttended;


    // CONSTRUCTOR //

    /**
     * Constructs an Roster object given a name, age, the number of matches and playoff matches attended.
     *
     * @param name
     * @param age
     * @param matchesAttended
     * @param playoffMatchesAttended
     */
    public Roster(String name, int age, int matchesAttended, int playoffMatchesAttended) {
        this.name = name;
        this.age = age;
        this.matchesAttended = matchesAttended;
        this.playoffMatchesAttended = playoffMatchesAttended;
    }


    // ACCESSOR METHODS //

    /**
     * Access the name of the roster member.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    // INSTANCE METHODS //

    /**
     * Rounds a number to one decimal place.
     *
     * @param d the unrounded number
     * @return the rounded number
     */
    public static double round(double d) {
        return Math.round(d * 10.0) / 10.0;
    }

    /**
     * Increments the total matches played by one.
     */
    public void addMatch() {
        matchesAttended++;
    }

    /**
     * Increments the total playoff matches played by one.
     */
    public void addPlayoffMatch() {
        playoffMatchesAttended++;
    }

    /**
     * Sets the matches played all to zero.
     */
    public void resetStatistics() {
        matchesAttended = 0;
        playoffMatchesAttended = 0;
    }

    /**
     * Returns a nicely formatted String with all the necessary information.
     *
     * @return the nicely formatted String
     */
    @Override
    public String toString() {
        return "Name: " + name + ", " + "Age: " + age + ", " + "\n" +
               "Matches played (regular / playoff): " + matchesAttended + " / " + playoffMatchesAttended + "\n";
    }

    // ABSTRACT METHODS //
    abstract void saveStatistics(String[] stats);
    abstract String listOutStats();
    abstract void display();
}
