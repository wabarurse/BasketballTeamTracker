package tracker;

/**
 * The Match class is designed to represent a match played by the team consisting
 * of three fields: the name of the opponent team, the number of points your team
 * has scored, and the number of points the opponent team has scored. Each match
 * looks at three stats for every player who has played: their points per game,
 * assists per game, and rebounds per game.
 */

public class Match implements Comparable<Match> {
    // FIELDS //
    private String opponentTeam;
    private int yourScore;
    private int opponentScore;


    // CONSTRUCTORS //

    /**
     * Constructs a match object given the name of the opponent team, your score, and the opponent score
     *
     * @param opponentTeam
     * @param yourScore
     * @param opponentScore
     */
    public Match(String opponentTeam, int yourScore,  int opponentScore) {
        this.opponentTeam = opponentTeam;
        this.yourScore = yourScore;
        this.opponentScore = opponentScore;
    }

    // INSTANCE METHODS //

    /**
     * Returns a nicely formatted String with all the necessary information.
     *
     * @return the nicely formatted String
     */
    @Override
    public String toString() {
        return "Opponent team: " + opponentTeam + "\n" +
               "Your score - Opponent score: " + yourScore + " - " + opponentScore;
    }


    /**
     * Compares your point to opponent's points.
     *
     * @param other the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Match other) {
        return Integer.compare(other.yourScore - other.opponentScore, this.yourScore - this.opponentScore);
    }
}
