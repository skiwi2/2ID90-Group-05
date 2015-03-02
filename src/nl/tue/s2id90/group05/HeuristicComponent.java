
package nl.tue.s2id90.group05;

import nl.tue.s2id90.draughts.DraughtsState;

/**
 *
 * @author Frank van Heeswijk
 */
public interface HeuristicComponent {
    int calculateComponentValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth, final int currentHeuristicValue);
}
