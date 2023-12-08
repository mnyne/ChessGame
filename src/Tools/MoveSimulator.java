package Tools;//for later; get your movement code into Game.MovementHandler first

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementArray;
import Game.MovementHandler;
import Tools.CoordinateHelper;

public class MoveSimulator {

	private CoordinateHelper coordinateHelper = new CoordinateHelper();
	private MovementHandler movementHandler = new MovementHandler();

	private Figure simulatedFigure;
	private ChessBoard simulatedBoard;
	private int simulatedTurn;

	private GameLog simulatedLog = new GameLog();

	private final int MAX_FIGURES_PER_PLAYER = 16;

	public MoveSimulator(ChessBoard currentBoard, Figure selectedFigure,
			int currentHalfMove, GameLog gameLog) {
		simulatedBoard = currentBoard.deepCopy();
		simulatedFigure = simulatedBoard.getFigureAt(
				selectedFigure.getXPosition(), selectedFigure.getYPosition());
		simulatedTurn = currentHalfMove + 1;
		simulatedLog.addEntry(gameLog.getPriorEntry(1));
	}

	/**
	 * Simulates a move in the chess game.
	 *
	 * @param  simulatedTargetIndex  the index of the target position on the board
	 */
	public void simulateMove(int simulatedTargetIndex) {
		int xCache = simulatedFigure.getXPosition();
		int yCache = simulatedFigure.getYPosition();
		int newX = coordinateHelper.convertIndextoX(simulatedTargetIndex);
		int newY = coordinateHelper.convertIndextoY(simulatedTargetIndex);
		simulatedBoard.moveFigure(simulatedFigure, newX, newY, simulatedLog);
		simulatedLog.logMove(simulatedFigure, simulatedTargetIndex);
		simulatedBoard.removeFigure(xCache, yCache);
		movementHandler.removePawnAfterEnPassant(simulatedBoard, simulatedLog);
	}
	
//todo: try to clean up that mess
	/**
	 * Generates a new ChessBoard object that contains only the figures of the player whose turn it is.
	 *
	 * @return   The ChessBoard object containing the player's figures.
	 */
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

	/**
	 * Generates the array of possible moves for the player.
	 *
	 * @return         	The array of possible player moves.
	 */
	public MovementArray possiblePlayerMoves() {
		ChessBoard simulatedPlayerFigures = simulatedPlayerFigures();
		MovementArray possiblePlayerMoves = new MovementArray();
		for (int arrayIndex = 0; arrayIndex < simulatedPlayerFigures.getLength(); arrayIndex++) {
			Figure figureAtIndex = simulatedPlayerFigures.getFigureAtIndex(arrayIndex);
			if (figureAtIndex != null) {
				MovementArray possibleMoveForFigureAtIndex = movementHandler.figureMovementArray(simulatedBoard, figureAtIndex,simulatedTurn);
				for (int figureArrayIndex = 0; figureArrayIndex < possibleMoveForFigureAtIndex.getLength(); figureArrayIndex++) {
					if (possibleMoveForFigureAtIndex.moveAtIndexAllowed(figureArrayIndex)) {
						possiblePlayerMoves.setIndexToTrue(figureArrayIndex);
					}
				}
			}
		}
		return possiblePlayerMoves;
	}

	/**
	 * Retrieves the index of the simulated king on the board.
	 *
	 * @return 	the index of the simulated king
	 */
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
