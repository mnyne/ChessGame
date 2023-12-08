package Figures.OrthodoxChessPieces;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class Pawn extends Figure {

	public Pawn(int color, int startIndex) {
		super(3, color, startIndex);
	}
	
	public Pawn(int color, int currentID, int currentIndex) {
		super(3, color, currentID, currentIndex);
	}

	/**
	 * Determines if a move is legal on the chessboard.
	 *
	 * @param  currentBoard     the current state of the chessboard
	 * @param  selectedFigure   the figure that wants to move
	 * @param  targetIndex      the index of the target position
	 * @return                  true if the move is legal, false otherwise
	 */
	public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
		MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
		if(move.isForwardMove(this.getFigureColor())) {
			if (forwardMoveLegal(move) || diagonalMoveLegal(move)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a diagonal move is legal.
	 *
	 * @param  move  the movement validity checker
	 * @return       true if the move is legal, false otherwise
	 */
	private boolean diagonalMoveLegal(MovementValidityChecker move) {
		if (move.diagonalMove() && move.hasLength(1)) {
			if(move.canCapture() || move.canEnPassant()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if a forward move is legal.
	 *
	 * @param  move  the MovementValidityChecker object representing the move
	 * @return       true if the move is legal, false otherwise
	 */
	private boolean forwardMoveLegal(MovementValidityChecker move) {
		if (move.orthogonalMove() && !move.orthogonalCollision()  && !move.canCapture()) {
			if (!this.hasMoved() && move.hasLength(2)) {
				return true;
			} else if (move.hasLength(1)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Creates a deep copy of the Figure object.
	 *
	 * @return         	A new Figure object that is an exact copy of the original.
	 */
	public Figure deepCopy() {
		Pawn copy = new Pawn(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
		copy.updateMovedStatus(this.hasMoved());
		return copy;
	}

	/**
	 * Updates the en passant eligibility status of the current chess piece.
	 *
	 * @param  gameLog  the game log object containing the movement history
	 */
	public void updateEnPassantEligibility(GameLog gameLog) {
		setEnPassantStatus(false);
        int yMovement = gameLog.getYMovementfromEntry(gameLog.getPriorEntry(1));
        if(gameLog.getFigureIDfromEntry(gameLog.getPriorEntry(1)) == this.getFigureID()) {
        	if(yMovement == 2 || yMovement == -2) {
        		setEnPassantStatus(true);
        	}
        }
	}
}
