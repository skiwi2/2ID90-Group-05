
package nl.tue.s2id90.group05.heuristics;

import nl.tue.s2id90.group05.heuristics.components.DepthFixComponent;
import nl.tue.s2id90.group05.heuristics.components.OpponentDefensiveKingCountingComponent;
import nl.tue.s2id90.group05.heuristics.components.OpponentKingCountingComponent;
import nl.tue.s2id90.group05.heuristics.components.SelfDefensiveKingCountingComponent;
import nl.tue.s2id90.group05.heuristics.components.SelfKingCountingComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class PositionHeuristic extends ComponentHeuristic {
    public PositionHeuristic() {
        super(
            new SelfKingCountingComponent(),
            new OpponentKingCountingComponent(),
            new SelfDefensiveKingCountingComponent(),
            new OpponentDefensiveKingCountingComponent(),
            new DepthFixComponent()
        );
    }
}
