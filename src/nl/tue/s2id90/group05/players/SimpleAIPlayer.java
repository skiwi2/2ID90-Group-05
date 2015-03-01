
package nl.tue.s2id90.group05.players;

import nl.tue.s2id90.group05.AlphaBetaPlayer;
import nl.tue.s2id90.group05.heuristics.SimpleHeuristic;

/**
 *
 * @author Frank van Heeswijk
 */
public class SimpleAIPlayer extends AlphaBetaPlayer {
    public SimpleAIPlayer() {
        super(new SimpleHeuristic());
    }
}
