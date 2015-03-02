
package nl.tue.s2id90.group05.heuristics;

import nl.tue.s2id90.group05.heuristics.components.DepthFixComponent;
import nl.tue.s2id90.group05.heuristics.components.KingCountingComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class AdvancedHeuristic extends ComponentHeuristic {
    public AdvancedHeuristic() {
        super(
            new KingCountingComponent(),
            new DepthFixComponent()
        );
    }
}
