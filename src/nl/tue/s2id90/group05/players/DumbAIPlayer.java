
package nl.tue.s2id90.group05.players;

import nl.tue.s2id90.group05.AlphaBetaPlayer;
import nl.tue.s2id90.group05.heuristics.DumbHeuristic;

/**
 *
 * @author Frank van Heeswijk
 */
public class DumbAIPlayer extends AlphaBetaPlayer {
    public DumbAIPlayer() {
        super(new DumbHeuristic());
    }
}
