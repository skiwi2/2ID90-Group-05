
package nl.tue.s2id90.group05.heuristics;

import nl.tue.s2id90.draughts.Draughts;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.Heuristic;

/**
 *
 * @author Frank van Heeswijk
 */
public class PositionHeuristic implements Heuristic {
    @Override
    public int calculateValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth) {
        //gives -1000 per opponent king
        //gives -10 per opponent piece
        //gives 10 per own piece
        //gives 1000 per own piece
        
        //value own pieces more if they are closer to the opponent
        //value opponent pieces more if they are closer to me
        
        //if returnValue > 0: return returnValue - depth
        //if returnValue < 0: return depth + returnValue
        
        int returnValue;
        
        int[] pieces = draughtsState.getPieces();
        
        if (isWhitePlayer) {
            double value = 0d;
            for (int i = 1; i < pieces.length; i++) {
                int row = (i - 1) / 5;
                double rowScaled = row * 1d / 9d;
                if (Draughts.isWhite(pieces[i])) {
                    if (Draughts.isKing(pieces[i])) {
                        value += 100d;
                    }
                    else {
                        double modifier = 1d + rowScaled;   //closer to 1 (bottom) is better
//                            double modifier = 1d + (1d - rowScaled);   //closer to 0 (top) is better
                        value += (1d * modifier);
                    }
                }
                else if (Draughts.isBlack(pieces[i])) {
                    if (Draughts.isKing(pieces[i])) {
                        value -= 100d;
                    }
                    else {
                        double modifier = 1d + rowScaled;   //closer to 1 (bottom) is bigger threat
                        value -= (1d * modifier);
                    }
                }
            }
            returnValue = (int)(value * 10d);
        }
        else {
            double value = 0d;
            for (int i = 1; i < pieces.length; i++) {
                int row = (i - 1) / 5;
                double rowScaled = row * 1d / 9d;
                if (Draughts.isBlack(pieces[i])) {
                    if (Draughts.isKing(pieces[i])) {
                        value += 100d;
                    }
                    else {
//                            //TODO fix this fix as this fix should not be needed
//                            double modifier = 1d + (1d - rowScaled);
//    //                        double modifier = 1d + rowScaled;   //closer to 1 (bottom) is better
                        double modifier = 1d + (1d - rowScaled);   //with fix ^, closer to 0 (top) is better
                        value += (1d * modifier);
                    }
                }
                else if (Draughts.isWhite(pieces[i])) {
                    if (Draughts.isKing(pieces[i])) {
                        value -= 100d;
                    }
                    else {
                        //TODO fix this fix as this fix should not be needed
                        double modifier = 1d + (1d - rowScaled);
//                        double modifier = 1d + (1d - rowScaled);   //closer to 0 (top) is bigger threat
                        value -= (1d * modifier);
                    }
                }
            }
            returnValue = (int)(value * 10d);
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
