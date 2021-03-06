
package nl.tue.s2id90.group05;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 *
 * @author Frank van Heeswijk
 */
public class AlphaBetaPlayer extends DraughtsPlayer {
    /**
     * Function mapping a (state, depth) pair to a heuristic value.
     * 
     * The state corresponds to the current state of the board.
     * The depth corresponds to the current depth in the alpha beta search.
     */
    private final Heuristic heuristic;
    
    private boolean hasToStop = false;
    private Integer bestMoveValue = null;
    
    public AlphaBetaPlayer(final Heuristic heuristic) {
        super();
        this.heuristic = Objects.requireNonNull(heuristic, "heuristic");
    }

    @Override
    public Move getMove(final DraughtsState draughtsState) {
        Map<Triple, Integer> transpositionTable = new HashMap<>();
        Map<Triple, Move> bestMoveTable = new HashMap<>();
        
        boolean isWhitePlayer = draughtsState.isWhiteToMove();  //calculate before going into the alphaBeta algorithm
        GameNode<DraughtsState> node = new GameNode<>(draughtsState);

        Move bestMove = null;
        for (int depthLimit = 1; ; depthLimit++) {
            try {
                bestMoveValue = alphaBeta(transpositionTable, bestMoveTable, node, isWhitePlayer, depthLimit, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                bestMove = node.getBestMove();
            } catch (AIStoppedException ex) {
                break;
            }
            System.out.println("Evaluated AlphaBeta with depth limit " + depthLimit);
        }
        
        return bestMove;
    }

    @Override
    public Integer getValue() {
        return bestMoveValue;
    }
    
    private int alphaBeta(final Map<Triple, Integer> transpositionTable, final Map<Triple, Move> bestMoveTable, final GameNode<DraughtsState> node, final boolean isWhitePlayer, final int depthLimit, final int depth, final int alpha, final int beta, final boolean maximizingPlayer)
        throws AIStoppedException {
        if (hasToStop) {
            hasToStop = false;
            throw new AIStoppedException();
        }
        
        DraughtsState state = node.getState();
        List<Move> moves = state.getMoves();
        Collections.shuffle(moves); //ensure random behavior if the value of moves is the same
        moves.sort(Comparator.comparing(Move::getCaptureCount).reversed()); //sort for better pruning performance
        
        //if leaf node
        Triple triple = new Triple(state, isWhitePlayer, depth);
        if (state.isEndState() || depth == depthLimit) {
            if (transpositionTable.containsKey(triple)) {
                return transpositionTable.get(triple);
            }
            int heuristicValue = heuristic.calculateValue(state, isWhitePlayer, depth);
            transpositionTable.put(triple, heuristicValue);
            return heuristicValue;
        }
        
        if (maximizingPlayer) {
            Move bestMove = moves.get(0);   //make sure bestMove is set
            int newAlpha = alpha;
            
            //analyze previously known best move first
            Move bestMoveFromTable = bestMoveTable.get(triple);
            if (bestMoveFromTable != null) {
                moves.add(0, bestMoveFromTable);
            }
            
            //create children states
            for (Move move : moves) {
                state.doMove(move);
                int alphaBetaValue = alphaBeta(transpositionTable, bestMoveTable, node, isWhitePlayer, depthLimit, depth + 1, newAlpha, beta, false);
                if (alphaBetaValue > newAlpha) {
                    newAlpha = alphaBetaValue;
                    bestMove = move;
                }
                state.undoMove(move);
                if (beta <= newAlpha) {
                    break;  //stop evaluation
                }
            }
            
            node.setBestMove(bestMove);
            bestMoveTable.put(triple, bestMove);
            return newAlpha;
        }
        else {
            Move bestMove = moves.get(0);   //make sure bestMove is set
            int newBeta = beta;
            
            //analyze previously known best move first
            Move bestMoveFromTable = bestMoveTable.get(triple);
            if (bestMoveFromTable != null) {
                moves.add(0, bestMoveFromTable);
            }
            
            //create children states
            for (Move move : moves) {
                state.doMove(move);
                int alphaBetaValue = alphaBeta(transpositionTable, bestMoveTable, node, isWhitePlayer, depthLimit, depth + 1, alpha, newBeta, true);
                if (alphaBetaValue < newBeta) {
                    newBeta = alphaBetaValue;
                    bestMove = move;
                }
                state.undoMove(move);
                if (newBeta <= alpha) {
                    break;  //stop evaluation
                }
            }
            
            node.setBestMove(bestMove);
            bestMoveTable.put(triple, bestMove);
            return newBeta;
        }
    }
    
    @Override
    public void stop() {
        hasToStop = true;
    }
    
    private static class Triple {
        private final DraughtsState draughtsState;
        private final int[] draughtsStatePieces;
        private final boolean isWhitePlayer;
        private final int depth;
        
        private Triple(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth) {
            this.draughtsState = draughtsState;
            this.draughtsStatePieces = draughtsState.getPieces();
            this.isWhitePlayer = isWhitePlayer;
            this.depth = depth;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 23 * hash + Arrays.hashCode(this.draughtsStatePieces);
            hash = 23 * hash + (this.isWhitePlayer ? 1 : 0);
            hash = 23 * hash + this.depth;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Triple other = (Triple) obj;
            if (!Arrays.equals(this.draughtsStatePieces, other.draughtsStatePieces)) {
                return false;
            }
            if (this.isWhitePlayer != other.isWhitePlayer) {
                return false;
            }
            if (this.depth != other.depth) {
                return false;
            }
            return true;
        }
    }
}
