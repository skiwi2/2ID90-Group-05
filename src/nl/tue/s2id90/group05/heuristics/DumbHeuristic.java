
package nl.tue.s2id90.group05.heuristics;

import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.Heuristic;

/**
 *
 * @author Frank van Heeswijk
 */
public class DumbHeuristic implements Heuristic {
    @Override
    public int calculateValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth) {
        return 0;
    }
}
