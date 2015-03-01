
package nl.tue.s2id90.group05.heuristics;

import java.util.Arrays;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.Heuristic;

/**
 *
 * @author Frank van Heeswijk
 */
public class SimpleHeuristic implements Heuristic {
    @Override
    public int calculateValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth) {
        int returnValue;
        int[] pieces = draughtsState.getPieces();
        int whitePieces = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.WHITEPIECE || piece == DraughtsState.WHITEKING)
            .count();
        int blackPieces = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.BLACKPIECE || piece == DraughtsState.BLACKKING)
            .count();
        
        if (isWhitePlayer) {
            returnValue = (whitePieces - blackPieces) * 10;
        }
        else {
            returnValue = (blackPieces - whitePieces) * 10;
        }
        if (returnValue > 0) {
            return returnValue - depth;
        } else if (returnValue < 0) {
            return depth + returnValue;
        } else {
            return 0;
        }
    }
}
