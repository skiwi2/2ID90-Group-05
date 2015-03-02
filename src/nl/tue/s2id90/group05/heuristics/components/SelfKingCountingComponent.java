
package nl.tue.s2id90.group05.heuristics.components;

import java.util.Arrays;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.HeuristicComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class SelfKingCountingComponent implements HeuristicComponent {
    @Override
    public int calculateComponentValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth, final int currentHeuristicValue) {
        //gives 10 per own piece
        //gives 30 per own king
        
        int[] pieces = draughtsState.getPieces();
        int whitePieces = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.WHITEPIECE)
            .count();
        int whiteKings = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.WHITEKING)
            .count();
        int blackPieces = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.BLACKPIECE)
            .count();
        int blackKings = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.BLACKKING)
            .count();
        
        if (isWhitePlayer) {
            return ((whiteKings * 30) + (whitePieces * 10));
        }
        else {
            return ((blackKings * 30) + (blackPieces * 10));
        }
    }
}
