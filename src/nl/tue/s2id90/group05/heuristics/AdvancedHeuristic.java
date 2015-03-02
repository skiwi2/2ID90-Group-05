
package nl.tue.s2id90.group05.heuristics;

import nl.tue.s2id90.group05.heuristics.components.DepthFixComponent;
import nl.tue.s2id90.group05.heuristics.components.OpponentKingCountingComponent;
import nl.tue.s2id90.group05.heuristics.components.SelfKingCountingComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class AdvancedHeuristic extends ComponentHeuristic {
    public AdvancedHeuristic() {
        super(
            new SelfKingCountingComponent(),
            new OpponentKingCountingComponent(),
            new DepthFixComponent()
        );
    }
}
