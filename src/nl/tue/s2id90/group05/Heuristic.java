
package nl.tue.s2id90.group05;

import nl.tue.s2id90.draughts.DraughtsState;

/**
 *
 * @author Frank van Heeswijk
 */
public interface Heuristic {
    int calculateValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth);
}
