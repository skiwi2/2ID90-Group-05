
package nl.tue.s2id90.group05.players;

import nl.tue.s2id90.group05.AlphaBetaPlayer;
import nl.tue.s2id90.group05.heuristics.AdvancedHeuristic;

/**
 *
 * @author Frank van Heeswijk
 */
public class AdvancedAIPlayer extends AlphaBetaPlayer {
    public AdvancedAIPlayer() {
        super(new AdvancedHeuristic());
    }
}
