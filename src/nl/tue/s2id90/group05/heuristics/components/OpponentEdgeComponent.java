
package nl.tue.s2id90.group05.heuristics.components;

import java.util.Arrays;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.HeuristicComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class OpponentEdgeComponent implements HeuristicComponent {
    @Override
    public int calculateComponentValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth, final int currentHeuristicValue) {
        //gives -5 per opponent piece at the edge of the board
        
        int[] edgePieces = draughtsState.getPieces();
        removePiecesNotAtEdge(edgePieces);
        int whitePieces = (int)Arrays.stream(edgePieces, 1, edgePieces.length)
            .filter(piece -> piece == DraughtsState.WHITEPIECE || piece == DraughtsState.WHITEKING)
            .count();
        int blackPieces = (int)Arrays.stream(edgePieces, 1, edgePieces.length)
            .filter(piece -> piece == DraughtsState.BLACKPIECE || piece == DraughtsState.BLACKKING)
            .count();
        
        if (isWhitePlayer) {
            return (-blackPieces * 5);
        }
        else {
            return (-whitePieces * 5);
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
        } else if (index == 5 || index == 15 || index == 25 || index == 35 || index == 45) {
            return true;
        } else if (index == 6 || index == 16 || index == 26 || index == 36 || index == 46) {
            return true;
        }
        return false;
    }
}
