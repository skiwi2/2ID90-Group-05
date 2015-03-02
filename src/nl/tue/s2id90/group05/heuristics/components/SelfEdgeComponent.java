
package nl.tue.s2id90.group05.heuristics.components;

import java.util.Arrays;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.HeuristicComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class SelfEdgeComponent implements HeuristicComponent {
    @Override
    public int calculateComponentValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth, final int currentHeuristicValue) {
        //gives 5 per own piece at the edge of the board
        
        int[] edgePieces = draughtsState.getPieces();
        removePiecesNotAtEdge(edgePieces);
        int whitePieces = (int)Arrays.stream(edgePieces, 1, edgePieces.length)
            .filter(piece -> piece == DraughtsState.WHITEPIECE || piece == DraughtsState.WHITEKING)
            .count();
        int blackPieces = (int)Arrays.stream(edgePieces, 1, edgePieces.length)
            .filter(piece -> piece == DraughtsState.BLACKPIECE || piece == DraughtsState.BLACKKING)
            .count();
        
        if (isWhitePlayer) {
            return (whitePieces * 5);
        }
        else {
            return (blackPieces * 5);
        }
    }
    
    private static void removePiecesNotAtEdge(final int[] pieces) {
        for (int index = 1; index < pieces.length; index++) {
            if (!isPieceAtEdge(index)) {
                pieces[index] = DraughtsState.EMPTY;
            }
        }
    }
    
    private static boolean isPieceAtEdge(final int index) {
        if (index >= 1 && index <= 5) {
            return true;
        } else if (index >= 46 && index <= 50) {
            return true;
        } else if (index % 10 == 5) {
            return true;
        } else if (index % 10 == 6) {
            return true;
        }
        return false;
    }
}
