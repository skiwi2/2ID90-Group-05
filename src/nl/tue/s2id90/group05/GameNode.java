
package nl.tue.s2id90.group05;

import java.util.Objects;
import nl.tue.s2id90.game.GameState;
import org10x10.dam.game.Move;

/**
 *
 * @author Frank van Heeswijk
 */
public class GameNode<S extends GameState<Move>> {
    private final S state;
    
    private Move bestMove;
    
    public GameNode(final S draughtsState) {
        this.state = Objects.requireNonNull(draughtsState, "draughtsState");
    }

    public S getState() {
        return state;
    }

    public void setBestMove(final Move bestMove) {
        this.bestMove = Objects.requireNonNull(bestMove, "bestMove");
    }

    public Move getBestMove() {
        return bestMove;
    }
}
