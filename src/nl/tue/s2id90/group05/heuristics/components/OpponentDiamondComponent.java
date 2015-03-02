
package nl.tue.s2id90.group05.heuristics.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.tue.s2id90.draughts.Draughts;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.HeuristicComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class OpponentDiamondComponent implements HeuristicComponent {
    private final static List<int[]> DIAMOND_INDICES = new ArrayList<>();
    static {
        for (int i = 1; i <= 40; i++) {
            int mod10 = i % 10;
            if ((mod10 >= 1 && mod10 <= 5) || (mod10 >= 7 && mod10 <= 9)) {
                DIAMOND_INDICES.add(new int[] { i, i + 5, i + 6 , i + 10 });
            }
        }
    }
    
    @Override
    public int calculateComponentValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth, final int currentHeuristicValue) {
        //gives -5 per opponent diamond structure on the board
        
        int[] pieces = draughtsState.getPieces();
        
        if (isWhitePlayer) {
            return DIAMOND_INDICES.stream()
                .filter(intArray -> Arrays.stream(intArray).allMatch(index -> Draughts.isBlack(pieces[index])))
                .mapToInt(intArray -> -5)
                .sum();
        } else {
            return DIAMOND_INDICES.stream()
                .filter(intArray -> Arrays.stream(intArray).allMatch(index -> Draughts.isWhite(pieces[index])))
                .mapToInt(intArray -> -5)
                .sum();
        }
    }
}
