
package nl.tue.s2id90.group05.heuristics.components;

import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.HeuristicComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class DepthFixComponent implements HeuristicComponent {
    @Override
    public int calculateComponentValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth, final int currentHeuristicValue) {
        if (currentHeuristicValue > 0) {
            return (currentHeuristicValue - depth);
        } else if (currentHeuristicValue < 0) {
            return (depth - currentHeuristicValue);
        }
        else {
            return 0;
        }
    }
}
