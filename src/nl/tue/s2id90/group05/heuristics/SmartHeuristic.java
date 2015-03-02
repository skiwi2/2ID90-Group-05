
package nl.tue.s2id90.group05.heuristics;

import nl.tue.s2id90.group05.heuristics.components.DepthFixComponent;
import nl.tue.s2id90.group05.heuristics.components.OpponentDiamondComponent;
import nl.tue.s2id90.group05.heuristics.components.OpponentEdgeComponent;
import nl.tue.s2id90.group05.heuristics.components.OpponentKingCountingComponent;
import nl.tue.s2id90.group05.heuristics.components.SelfDiamondComponent;
import nl.tue.s2id90.group05.heuristics.components.SelfEdgeComponent;
import nl.tue.s2id90.group05.heuristics.components.SelfKingCountingComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class SmartHeuristic extends ComponentHeuristic {
    public SmartHeuristic() {
        super(
            new SelfKingCountingComponent(),
            new SelfKingCountingComponent(),
            new OpponentKingCountingComponent(),
            new SelfEdgeComponent(),
            new OpponentEdgeComponent(),
            new SelfDiamondComponent(),
            new OpponentDiamondComponent(),
            new DepthFixComponent()
        );
    }
}
