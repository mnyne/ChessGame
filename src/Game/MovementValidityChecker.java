package Game;

import Figures.Figure;
import Tools.CoordinateHelper;

public class MovementValidityChecker {

	CoordinateHelper coordinateHelper = new CoordinateHelper();

	private ChessBoard currentBoard;
	private Figure currentFigure;

	private int currentX;
	private int currentY;
	private int targetX;
	private int targetY;

	private int xDiff;
	private int yDiff;
	private int yDiffRaw;

	public MovementValidityChecker(ChessBoard chessBoard, Figure currentFigure,
								   int targetIndex) {
		this.currentBoard = chessBoard;
		this.currentFigure = currentFigure;

		currentX = currentFigure.getXPosition();
		currentY = currentFigure.getYPosition();
		targetX = coordinateHelper.convertIndextoX(targetIndex);
		targetY = coordinateHelper.convertIndextoY(targetIndex);

		xDiff = coordinateHelper.getAdjustedDiff(targetX, currentX);
		yDiff = coordinateHelper.getAdjustedDiff(targetY, currentY);
		yDiffRaw = coordinateHelper.getRawDiff(targetY, currentY);
	}

	/**
	 * Determines if the current figure can capture the target position on the board.
	 *
	 * @return  true if the current figure can capture the target position, false otherwise
	 */
	public boolean canCapture() {
		if (currentBoard.getFigureAt(targetX, targetY) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if en passant is possible.
	 *
	 * @return  true if en passant is possible, false otherwise
	 */
	public boolean canEnPassant() {
		if (currentBoard.getFigureAt(targetX, currentY) != null
				&& currentBoard.getFigureAt(targetX, currentY)
						.eligibleForEnPassant()) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if the move is diagonal.
	 *
	 * @return true if the move is diagonal, false otherwise
	 */
	public boolean diagonalMove() {
		if (xDiff == yDiff) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if the move is orthogonal.
	 *
	 * @return   true if the move is orthogonal, false otherwise
	 */
	public boolean orthogonalMove() {
		if (xDiff == 0 || yDiff == 0) {
				return true;
		}
		return false;
	}

	/**
	 * Determines if the current move is an L-shaped move.
	 *
	 * @return true if the move is L-shaped, false otherwise
	 */
	public boolean lShapedMove() {
		if (xDiff + yDiff == 3 && xDiff != 0 && yDiff != 0) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if the player can perform a castle move.
	 *
	 * @return  true if the player can perform a castle move, false otherwise
	 */
	public boolean canCastle() {
		if (canCastleKingside() || canCastleQueenside()) {
			return true;
		}
		return false;
	}

	/**
	 * Determines if there is an orthogonal collision.
	 *
	 * @return true if there is an orthogonal collision, false otherwise
	 */
	public boolean orthogonalCollision() {
		if (verticalCollisionNeg() || verticalCollisionPos()
				|| horizontalCollisionNeg() || horizontalCollisionPos()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if there is a collision on the diagonal.
	 *
	 * @return  true if there is a collision, false otherwise
	 */
	public boolean diagonalCollision() {
		if (diagonalCollisionPosPos() || diagonalCollisionNegNeg()
				|| diagonalCollisionPosNeg() || diagonalCollisionNegPos()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the given move length is greater than or equal to the absolute
	 * difference between xDiff and yDiff.
	 *
	 * @param  moveLength the move length to be checked
	 * @return true if the move length is greater than or equal to the absolute
	 *         difference between xDiff and yDiff, false otherwise
	 */
	public boolean hasLength(int moveLength) {
		if (xDiff <= moveLength && yDiff <= moveLength) {
			return true;
		}
		return false;
	}

	/**
	 * Determines whether the move has an exact length.
	 *
	 * @param  moveLength  the length of the move
	 * @return             true if the move has an exact length, false otherwise
	 */
	public boolean hasExactLength(int moveLength) {
		if (xDiff == moveLength || yDiff == moveLength) {
			return true;
		}
		return false;
	}

	/**
	 * Checks for a vertical collision in the negative direction.
	 *
	 * @return  true if there is a collision, false otherwise
	 */
	private boolean verticalCollisionNeg() {
		if (targetY < currentY && targetX == currentX) {
			for (int i = yDiff - 1; i > 0; i--) {
				Figure collisionFigure = currentBoard.getFigureAt(currentX,
						currentY - i);
				if (collisionFigure != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Determines if there is a vertical collision at the current position.
	 *
	 * @return  true if there is a vertical collision, false otherwise
	 */
	private boolean verticalCollisionPos() {
		if (targetY > currentY && targetX == currentX) {
			for (int i = 1; i < yDiff; i++) {
				Figure collisionFigure = currentBoard.getFigureAt(currentX,
						currentY + i);
				if (collisionFigure != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks for a horizontal negative collision.
	 *
	 * @return  true if there is a collision, false otherwise
	 */
	private boolean horizontalCollisionNeg() {
		if (targetX < currentX && targetY == currentY) {
			for (int i = xDiff - 1; i > 0; i--) {
				Figure collisionFigure = currentBoard.getFigureAt(currentX - i,
						currentY);
				if (collisionFigure != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if there is a horizontal collision at the current position.
	 *
	 * @return  true if there is a horizontal collision, false otherwise
	 */
	private boolean horizontalCollisionPos() {
		if (targetX > currentX && targetY == currentY) {
			for (int i = 1; i < xDiff; i++) {
				Figure collisionFigure = currentBoard.getFigureAt(currentX + i,
						currentY);
				if (collisionFigure != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks for diagonal collision in the negative to positive direction.
	 *
	 * @return true if there is a collision, false otherwise
	 */
	private boolean diagonalCollisionNegPos() {
		if (targetX < currentX && targetY > currentY) {
			for (int i = xDiff - 1; i > 0; i--) {
				Figure collisionFigure = currentBoard.getFigureAt(currentX - i,
						currentY + i);
				if (collisionFigure != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if there is a collision on the diagonal path with a positive slope from the current position to the target position.
	 *
	 * @return  true if there is a collision, false otherwise
	 */
	private boolean diagonalCollisionPosNeg() {
		if (targetX > currentX && targetY < currentY) {
			for (int i = 1; i < xDiff; i++) {
				Figure collisionFigure = currentBoard.getFigureAt(currentX + i,
						currentY - i);
				if (collisionFigure != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks for a collision in the negative diagonal direction.
	 *
	 * @return  true if there is a collision, false otherwise
	 */
	private boolean diagonalCollisionNegNeg() {
		if (targetX < currentX && targetY < currentY) {
			for (int i = xDiff - 1; i > 0; i--) {
				Figure collisionFigure = currentBoard.getFigureAt(currentX - i,
						currentY - i);
				if (collisionFigure != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Determines if there is a collision along the diagonal path from the
	 * current position to the target position when both positions are in the
	 * positive direction.
	 *
	 * @return true if there is a collision, false otherwise
	 */
	private boolean diagonalCollisionPosPos() {
		if (targetX > currentX && targetY > currentY) {
			for (int i = 1; i < xDiff; i++) {
				Figure collisionFigure = currentBoard.getFigureAt(currentX + i,
						currentY + i);
				if (collisionFigure != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks for collision during kingside castling.
	 *
	 * @return 	true if there is a collision, false otherwise
	 */
	private boolean castlingCollisionKingside() {
		for (int i = currentX + 1; i < 7; i++) {
			Figure collisionFigure = currentBoard.getFigureAt(i, currentY);
			if (collisionFigure != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check for collision when performing a queenside castling.
	 *
	 * @return  true if there is a collision, false otherwise
	 */
	private boolean castlingCollisionQueenside() {
		for (int i = currentX - 1; i > 0; i--) {
			Figure collisionFigure = currentBoard.getFigureAt(i, currentY);
			if (collisionFigure != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if the current figure can castle kingside.
	 *
	 * @return  true if the current figure can castle kingside, false otherwise
	 */
	private boolean canCastleKingside() {
		if (!currentFigure.hasMoved() && !castlingCollisionKingside()) {
			if (targetY == currentY && targetX == currentX + 2) {
				if(currentX + 2 < 8) {
					Figure towerFigure = currentBoard.getFigureAt(currentX + 3,
							currentY);
					if (towerFigure != null && !towerFigure.hasMoved()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Determines if the current figure can perform a queenside castling move.
	 *
	 * @return  true if the current figure can perform a queenside castling move, false otherwise
	 */
	private boolean canCastleQueenside() {
		if (!currentFigure.hasMoved() && !castlingCollisionQueenside()) {
			if (targetY == currentY && targetX == currentX - 2) {
				if(currentX - 4 >= 0) {
					Figure towerFigure = currentBoard.getFigureAt(currentX - 4, currentY);
					if (towerFigure != null && !towerFigure.hasMoved()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * Determines if the move is a forward move based on the figure's color.
	 *
	 * @param  figureColor  the color of the figure (0 for white, 1 for black)
	 * @return              true if the move is a forward move, false otherwise
	 */
	public boolean isForwardMove(int figureColor) {
		if (yDiffRaw > 0 && figureColor == 0) {
			return true;
		}
		if (yDiffRaw < 0 && figureColor == 1) {
			return true;
		}
		return false;
	}
}
