package tracker;

/**
 * The PlayoffMatch class extends the Match class is designed to represent a match
 * - specifically a playoff match - played by the team consisting of four fields:
 * three from the Match class and the bracket stage. Each playoff match looks at
 * five stats for every player who has played: the three from the match class as
 * well as field goal percentage and three point percentage.
 */

public class PlayoffMatch extends Match {
    // FIELDS //
    private String bracketStage;


    // CONSTRUCTORS //

    /**
     * Constructs a match object given the name of the opponent team, your score, the opponent score, and the bracket stage.
     *
     * @param opponentTeam
     * @param yourScore
     * @param opponentScore
     * @param bracketStage
     */
    public PlayoffMatch(String opponentTeam, int yourScore,  int opponentScore, String bracketStage) {
        super(opponentTeam,  yourScore, opponentScore);
        this.bracketStage = bracketStage;
    }


    /**
     * Returns a nicely formatted String with all the necessary information.
     *
     * @return the nicely formatted String
     */
    @Override
    public String toString() {
        return super.toString() + "\n" + "BracketStage: " + bracketStage;
    }


}
