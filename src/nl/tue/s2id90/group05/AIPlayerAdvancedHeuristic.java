
package nl.tue.s2id90.group05;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 *
 * @author Frank van Heeswijk
 */
public class AIPlayerAdvancedHeuristic extends DraughtsPlayer {
    /**
     * Function mapping a (state, depth) pair to a heuristic value.
     * 
     * The state corresponds to the current state of the board.
     * The depth corresponds to the current depth in the alpha beta search.
     */
    private final HeuristicFunction heuristicFunction;
    
    private boolean hasToStop = false;
    private Integer bestMoveValue = null;
    
    public AIPlayerAdvancedHeuristic() {
        super();
        
        //initialize heuristic function
        this.heuristicFunction = this::advancedHeuristic;
    }
    
    private int advancedHeuristic(final DraughtsState draughtsState, final boolean isWhitePlayer, final int depth) {
        //gives -100000 to a loss
        //gives -1000 per opponent king
        //gives -10 per opponent piece
        //gives 10 per own piece
        //gives 1000 per own piece
        //gives 100000 to a win
        
        //if returnValue > 0: return returnValue - depth
        //if returnValue < 0: return depth + returnValue
        
        int returnValue;
        
        int[] pieces = draughtsState.getPieces();
        int whitePieces = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.WHITEPIECE)
            .count();
        int whiteKings = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.WHITEKING)
            .count();
        int blackPieces = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.BLACKPIECE)
            .count();
        int blackKings = (int)Arrays.stream(pieces, 1, pieces.length)
            .filter(piece -> piece == DraughtsState.BLACKKING)
            .count();
        
        if (isWhitePlayer) {
            //am white player
            returnValue = (whiteKings * 1000) + (whitePieces * 10) - (blackKings * 1000) - (blackPieces * 10);
        }
        else {
            //am black player
            returnValue = (blackKings * 1000) + (blackPieces * 10) - (whiteKings * 1000) - (whitePieces * 10);
        }
        if (returnValue > 0) {
            return returnValue - depth;
        } else if (returnValue < 0) {
            return depth + returnValue;
        } else {
            return 0;
        }
    }

    @Override
    public Move getMove(final DraughtsState draughtsState) {
        boolean isWhitePlayer = draughtsState.isWhiteToMove();  //calculate before going into the alphaBeta algorithm
        GameNode<DraughtsState> node = new GameNode<>(draughtsState);

        Move bestMove = null;
        for (int depthLimit = 1; ; depthLimit++) {
            try {
                bestMoveValue = alphaBeta(node, isWhitePlayer, depthLimit, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
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
    
    private int alphaBeta(final GameNode<DraughtsState> node, final boolean isWhitePlayer, final int depthLimit, final int depth, final int alpha, final int beta, final boolean maximizingPlayer)
        throws AIStoppedException {
        if (hasToStop) {
            hasToStop = false;
            throw new AIStoppedException();
        }
        
        DraughtsState state = node.getState();
        List<Move> moves = state.getMoves();
        Collections.shuffle(moves); //ensure random behavior if the value of moves is the same
        
        //if leaf node
        if (moves.isEmpty() || depth == depthLimit) {
            return heuristicFunction.apply(state, isWhitePlayer, depth);
        }
        
        if (maximizingPlayer) {
            Move bestMove = moves.get(0);   //make sure bestMove is set
            int newAlpha = alpha;
            
            //create children states
            for (Move move : moves) {
                state.doMove(move);
                int alphaBetaValue = alphaBeta(node, isWhitePlayer, depthLimit, depth + 1, newAlpha, beta, false);
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
            return newAlpha;
        }
        else {
            Move bestMove = moves.get(0);   //make sure bestMove is set
            int newBeta = beta;
            
            //create children states
            for (Move move : moves) {
                state.doMove(move);
                int alphaBetaValue = alphaBeta(node, isWhitePlayer, depthLimit, depth + 1, alpha, newBeta, true);
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
            return newBeta;
        }
    }
    
    @Override
    public void stop() {
        hasToStop = true;
    }
}
