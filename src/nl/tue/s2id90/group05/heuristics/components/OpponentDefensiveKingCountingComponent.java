
package nl.tue.s2id90.group05.heuristics.components;

import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.HeuristicComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class OpponentDefensiveKingCountingComponent implements HeuristicComponent {
    @Override
    public int calculateComponentValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth, final int currentHeuristicValue) {
        //gives modifier * -10 per opponent piece
        //gives modifier * -30 per opponent king
        //where modifier is 1 if the piece is at your side and 0 if it is at the opponent side, averaged in between
        
        int[] pieces = draughtsState.getPieces();
        
        if (isWhitePlayer) {
            double value = 0d;
            for (int index = 1; index < pieces.length; index++) {
                if (pieces[index] == DraughtsState.BLACKPIECE) {
                    value += (getBlackModifier(index) * -10);
                }
                else if (pieces[index] == DraughtsState.BLACKKING) {
                    value += (getBlackModifier(index) * -30);
                }
            }
            return (int)Math.round(value);
        }
        else {
            double value = 0d;
            for (int index = 1; index < pieces.length; index++) {
                if (pieces[index] == DraughtsState.WHITEPIECE) {
                    value += (getWhiteModifier(index) * -10);
                }
                else if (pieces[index] == DraughtsState.WHITEKING) {
                    value += (getWhiteModifier(index) * -30);
                }
            }
            return (int)Math.round(value);
        }
    }
    
    private static double getWhiteModifier(final int index) {
        int row = (int)Math.floor((index - 1) / 5);
        return (row / 9d);
    }
    
    private static double getBlackModifier(final int index) {
        int row = (int)Math.floor((index - 1) / 5);
        return ((9 - row) / 9d);
    }
}
