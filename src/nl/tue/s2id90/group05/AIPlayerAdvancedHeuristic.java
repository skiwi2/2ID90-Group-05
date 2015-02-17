
package nl.tue.s2id90.group05;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
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
    private final BiFunction<DraughtsState, Integer, Integer> heuristicFunction;
    
    private boolean hasToStop = false;
    private Integer bestMoveValue = null;
    private int moveRequests = 0;
    private boolean isWhitePlayerAtFirstMoveRequest;
    
    public AIPlayerAdvancedHeuristic() {
        super();
        
        //initialize heuristic function
        this.heuristicFunction = this::advancedHeuristic;
    }
    
    private int advancedHeuristic(final DraughtsState draughtsState, final int depth) {
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
        
        if (isWhitePlayerAtFirstMoveRequest) {
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
    
    private boolean isWhitePlayerAtFirstMoveRequest(final DraughtsState draughtsState) {
        //TODO use draughtsState.isWhiteToMove()?
        
        //I am the white player if the board has not been changed the first time I am requested to make a move
        //There is no DraughtState.equals method, so we need to manually check if states are equal
        
        DraughtsState beginState = draughtsState.clone();
        beginState.reset();
        
        return draughtStateEquals(draughtsState, beginState);
    }
    
    private boolean draughtStateEquals(final DraughtsState draughtsState1, final DraughtsState draughtsState2) {
        int[] draughtsState1Pieces = draughtsState1.getPieces();
        int[] draughtsState2Pieces = draughtsState2.getPieces();
        for (int i = 1; i < draughtsState1Pieces.length; i++) {
            if (draughtsState1Pieces[i] != draughtsState2Pieces[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Move getMove(final DraughtsState draughtsState) {
        moveRequests++;
        if (moveRequests == 1) {
            isWhitePlayerAtFirstMoveRequest = isWhitePlayerAtFirstMoveRequest(draughtsState);
        }
        
        GameNode<DraughtsState> node = new GameNode<>(draughtsState);

        Move bestMove = null;
        for (int depthLimit = 1; ; depthLimit++) {
            try {
                bestMoveValue = alphaBeta(node, depthLimit, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
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
    
    private int alphaBeta(final GameNode<DraughtsState> node, final int depthLimit, final int depth, final int alpha, final int beta, final boolean maximizingPlayer)
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
            return heuristicFunction.apply(state, depth);
        }
        
        if (maximizingPlayer) {
            Move bestMove = moves.get(0);   //make sure bestMove is set
            int newAlpha = alpha;
            
            //create children states
            for (Move move : moves) {
                state.doMove(move);
                int alphaBetaValue = alphaBeta(node, depthLimit, depth + 1, newAlpha, beta, false);
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
                int alphaBetaValue = alphaBeta(node, depthLimit, depth + 1, alpha, newBeta, true);
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
