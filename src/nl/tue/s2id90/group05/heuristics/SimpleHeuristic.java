
package nl.tue.s2id90.group05.heuristics;

import nl.tue.s2id90.group05.heuristics.components.DepthFixComponent;
import nl.tue.s2id90.group05.heuristics.components.SimpleCountingComponent;

/**
 *
 * @author Frank van Heeswijk
 */
public class SimpleHeuristic extends ComponentHeuristic {
    public SimpleHeuristic() {
        super(
            new SimpleCountingComponent(),
            new DepthFixComponent()
        );
    }
}
