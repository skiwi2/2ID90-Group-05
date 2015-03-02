
package nl.tue.s2id90.group05.players;

import nl.tue.s2id90.group05.AlphaBetaPlayer;
import nl.tue.s2id90.group05.heuristics.SmartHeuristic;

/**
 *
 * @author Frank van Heeswijk
 */
public class SmartAIPlayer extends AlphaBetaPlayer {
    public SmartAIPlayer() {
        super(new SmartHeuristic());
    }
}
