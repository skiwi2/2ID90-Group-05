
package nl.tue.s2id90.group05.players;

import nl.tue.s2id90.group05.AlphaBetaPlayer;
import nl.tue.s2id90.group05.heuristics.PositionHeuristic;

/**
 *
 * @author Frank van Heeswijk
 */
public class PositionAIPlayer extends AlphaBetaPlayer {
    public PositionAIPlayer() {
        super(new PositionHeuristic());
    }
}
