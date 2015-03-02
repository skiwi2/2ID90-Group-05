
package nl.tue.s2id90.group05.heuristics.components;

import java.util.Arrays;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.HeuristicComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class SelfSimpleCountingComponent implements HeuristicComponent {
    @Override
    public int calculateComponentValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth, final int currentHeuristicValue) {
        //gives 10 per own piece
        
        int[] pieces = draughtsState.getPieces();
        int whitePieces = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.WHITEPIECE || piece == DraughtsState.WHITEKING)
            .count();
        int blackPieces = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.BLACKPIECE || piece == DraughtsState.BLACKKING)
            .count();
        
        if (isWhitePlayer) {
            return (whitePieces * 10);
        }
        else {
            return (blackPieces * 10);
        }
    }
}
