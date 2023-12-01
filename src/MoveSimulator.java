//for later; get your movement code into MovementHandler first

public class MoveSimulator {

	private CoordinateHelper coordinateHelper = new CoordinateHelper();
	private MovementHandler movementHandler = new MovementHandler();

	private Figure simulatedFigure;
	private ChessBoard simulatedBoard;
	private int simulatedTurn;

	private final int MAX_FIGURES_PER_PLAYER = 16;

	public MoveSimulator(ChessBoard currentBoard, Figure selectedFigure,
			int currentHalfMove) {
		simulatedBoard = currentBoard.deepCopy();
		simulatedFigure = simulatedBoard.getFigureAt(
				selectedFigure.getXPosition(), selectedFigure.getYPosition());
		simulatedTurn = currentHalfMove + 1;
	}

	public void simulateMove(int simulatedTargetIndex) {
		int xCache = simulatedFigure.getXPosition();
		int yCache = simulatedFigure.getYPosition();
		int newX = coordinateHelper.convertIndextoX(simulatedTargetIndex);
		int newY = coordinateHelper.convertIndextoY(simulatedTargetIndex);
		simulatedBoard.moveFigure(simulatedFigure, newX, newY);
		simulatedBoard.removeFigure(xCache, yCache);
	}
	
//todo: try to clean up that mess
	public ChessBoard simulatedPlayerFigures() {
		ChessBoard simulatedPlayerFigures = new ChessBoard(MAX_FIGURES_PER_PLAYER);
		int figureIndex = 0;
		for (int boardIndex = 0; boardIndex < simulatedBoard.getLength(); boardIndex++) {
			if (simulatedBoard.getFigureAtIndex(boardIndex) != null&& simulatedBoard.getFigureAtIndex(boardIndex).getFigureColor() == simulatedTurn % 2) {
				simulatedPlayerFigures.addFigure(figureIndex,simulatedBoard.getFigureAtIndex(boardIndex));
				figureIndex += 1;
			}
		}
		return simulatedPlayerFigures;
	}

	public MovementArray possiblePlayerMoves() {
		ChessBoard simulatedPlayerFigures = simulatedPlayerFigures();
		MovementArray possiblePlayerMoves = new MovementArray();
		for (int arrayIndex = 0; arrayIndex < simulatedPlayerFigures.getLength(); arrayIndex++) {
			Figure figureAtIndex = simulatedPlayerFigures.getFigureAtIndex(arrayIndex);
			if (figureAtIndex != null) {
				MovementArray possibleMoveForFigureAtIndex = movementHandler.possibleMoveArray(simulatedBoard, figureAtIndex,simulatedTurn);
				for (int figureArrayIndex = 0; figureArrayIndex < possibleMoveForFigureAtIndex.getLength(); figureArrayIndex++) {
					if (possibleMoveForFigureAtIndex.moveAtIndexAllowed(figureArrayIndex)) {
						possiblePlayerMoves.setIndexToTrue(figureArrayIndex);
					}
				}
			}
		}
		return possiblePlayerMoves;
	}

	public int getSimulatedKingIndex() {
		int simulatedKingIndex = 0;
		for (int boardIndex = 0; boardIndex < simulatedBoard.getLength(); boardIndex++) {
			Figure potentialKing = simulatedBoard.getFigureAtIndex(boardIndex);
			if (potentialKing != null && potentialKing.getFigureType() == 1&& potentialKing.getFigureColor() != simulatedTurn % 2) {
				simulatedKingIndex = boardIndex;
			}
		}

		return simulatedKingIndex;
	}
}
