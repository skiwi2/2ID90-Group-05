
package nl.tue.s2id90.group05.heuristics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.group05.Heuristic;
import nl.tue.s2id90.group05.HeuristicComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class ComponentHeuristic implements Heuristic {
    private final List<HeuristicComponent> components = new ArrayList<>();
    
    public ComponentHeuristic(final HeuristicComponent... heuristicComponents) {
        components.addAll(Arrays.asList(heuristicComponents));
    }

    @Override
    final public int calculateValue(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth) {
        int value = 0;
        for (HeuristicComponent component : components) {
            value += component.calculateComponentValue(draughtsState, isWhitePlayer, depth, value);
        }
        return value;
    }
}
