public class MovementValidityChecker {

	CoordinateHelper coordinateHelper = new CoordinateHelper();

	private ChessBoard currentBoard;
	private Figure currentFigure;
	private int targetIndex;

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
		this.targetIndex = targetIndex;

		currentX = currentFigure.getXPosition();
		currentY = currentFigure.getYPosition();
		targetX = coordinateHelper.convertIndextoX(targetIndex);
		targetY = coordinateHelper.convertIndextoY(targetIndex);

		xDiff = coordinateHelper.getAdjustedDiff(targetX, currentX);
		yDiff = coordinateHelper.getAdjustedDiff(targetY, currentY);
		yDiffRaw = coordinateHelper.getRawDiff(targetY, currentY);
	}
	
	public boolean pawnMove() {
		if (currentFigure.getFigureColor() == 0) {
			return whitePawnMove();
		} else {
			return blackPawnMove();
		}
	}

	private boolean whitePawnMove() {
		if (whitePawnDiagonalCapture() || whitePawnDoubleMove()
				|| whitePawnSingleMove()) {
			return true;
		}
		return false;
	}

	private boolean blackPawnMove() {
		if (blackPawnDiagonalCapture() || blackPawnDoubleMove()
				|| blackPawnSingleMove()) {
			return true;
		}
		return false;
	}

	private boolean whitePawnDiagonalCapture() {
		if (targetY == currentY - 1) {
			if (targetX == currentX - 1 || targetX == currentX + 1) {
				if (currentBoard.getFigureAt(targetX, targetY) != null) {
					return true;
				}
				if (currentBoard.getFigureAt(targetX, targetY + 1) != null
						&& currentBoard.getFigureAt(targetX, targetY + 1)
								.getEnPassantStatus()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean blackPawnDiagonalCapture() {
		if (targetY == currentY + 1) {
			if (targetX == currentX - 1 || targetX == currentX + 1) {
				if (currentBoard.getFigureAt(targetX, targetY) != null) {
					return true;
				}
				if (currentBoard.getFigureAt(targetX, targetY - 1) != null
						&& currentBoard.getFigureAt(targetX, targetY - 1)
								.getEnPassantStatus()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean whitePawnDoubleMove() {
		if (targetY == currentY - 2 && !currentFigure.hasMovedStatus()
				&& currentBoard.getFigureAtIndex(targetIndex) == null
				&& !orthogonalCollision() && targetX == currentX)  {
			return true;
		}
		return false;
	}

	private boolean blackPawnDoubleMove() {
		if (targetY == currentY + 2 && !currentFigure.hasMovedStatus()
				&& currentBoard.getFigureAtIndex(targetIndex) == null
				&& !orthogonalCollision() && targetX == currentX) {
			return true;
		}
		return false;
	}

	private boolean whitePawnSingleMove() {
		if (xDiff == 0 && -yDiffRaw < 0 && -yDiffRaw > -2
				&& currentBoard.getFigureAtIndex(targetIndex) == null) {
			return true;
		}
		return false;
	}

	private boolean blackPawnSingleMove() {
		if (xDiff == 0 && -yDiffRaw > 0 && -yDiffRaw < 2
				&& currentBoard.getFigureAtIndex(targetIndex) == null) {
			return true;
		}
		return false;
	}

	public boolean diagonalMove() {
		if (xDiff == yDiff && !diagonalCollision()) {
			return true;
		}
		return false;
	}

	public boolean orthogonalMove() {
		if (xDiff == 0 || yDiff == 0) {
			if (!orthogonalCollision()) {
				return true;
			}

		}
		return false;
	}

	public boolean lShapedMove() {
		if (xDiff + yDiff == 3 && xDiff != 0) {
			return true;
		}
		return false;
	}

	public boolean canCastle() {
		if (canCastleKingside() || canCastleQueenside()) {
			return true;
		}
		return false;
	}

	public boolean orthogonalCollision() {
		if (verticalCollisionNeg() || verticalCollisionPos()
				|| horizontalCollisionNeg() || horizontalCollisionPos()) {
			return true;
		}
		return false;
	}

	public boolean diagonalCollision() {
		if (diagonalCollisionPosPos() || diagonalCollisionNegNeg()
				|| diagonalCollisionPosNeg() || diagonalCollisionNegPos()) {
			return true;
		}
		return false;
	}

	public boolean hasLength(int moveLength) {
		if (xDiff <= moveLength && yDiff <= moveLength) {
			return true;
		}
		return false;
	}

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

	private boolean castlingCollisionKingside() {
		for (int i = currentX + 1; i < 7; i++) {
			Figure collisionFigure = currentBoard.getFigureAt(i, currentY);
			if (collisionFigure != null) {
				return true;
			}
		}
		return false;
	}

	private boolean castlingCollisionQueenside() {
		for (int i = currentX - 1; i > 0; i--) {
			Figure collisionFigure = currentBoard.getFigureAt(i, currentY);
			if (collisionFigure != null) {
				return true;
			}
		}
		return false;
	}

	private boolean canCastleKingside() {
		if (!currentFigure.hasMovedStatus() && !castlingCollisionKingside()) {
			if (targetY == currentY && targetX == currentX + 2) {
				Figure towerFigure = currentBoard.getFigureAt(currentX + 3,
						currentY);
				if (towerFigure != null && !towerFigure.hasMovedStatus()) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean canCastleQueenside() {
		if (!currentFigure.hasMovedStatus() && !castlingCollisionQueenside()) {
			if (targetY == currentY && targetX == currentX - 2) {
				Figure towerFigure = currentBoard.getFigureAt(currentX - 4,
						currentY);
				if (towerFigure != null && !towerFigure.hasMovedStatus()) {
					return true;
				}
			}
		}
		return false;
	}
}
