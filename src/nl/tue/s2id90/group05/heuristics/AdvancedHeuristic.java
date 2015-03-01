
package nl.tue.s2id90.group05.heuristics;

import java.util.Arrays;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.Heuristic;

/**
 *
 * @author Frank van Heeswijk
 */
public class AdvancedHeuristic implements Heuristic {
    @Override
    public int calculateValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth) {
        //gives -1000 per opponent king
        //gives -10 per opponent piece
        //gives 10 per own piece
        //gives 1000 per own piece
        
        //if returnValue > 0: return returnValue - depth
        //if returnValue < 0: return depth + returnValue
        
        int returnValue;
        
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
            //am white player
            returnValue = (whiteKings * 1000) + (whitePieces * 10) - (blackKings * 1000) - (blackPieces * 10);
        }
        else {
            //am black player
            returnValue = (blackKings * 1000) + (blackPieces * 10) - (whiteKings * 1000) - (whitePieces * 10);
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
