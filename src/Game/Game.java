package Game;

import Figures.Figure;
import Tools.CoordinateHelper;
import Tools.FenSetup;

public class Game {
	
	private final int BOARD_LENGTH = 8;
	
	private int currentHalfMove = 2;
	private int currentFullMove = currentHalfMove / 2;
	//todo: make game end when half move clock reaches 50
	private int halfMoveClock = 0;
	
	private ChessBoard chessBoard;
	private Figure selectedFigure;
	private MovementArray legalMoveArray;
	private int gameType;
	private int clearLogAfter = -1;

	FenSetup fenSetup = new FenSetup();
	MovementHandler movementHandler = new MovementHandler();
	CoordinateHelper coordinateHelper = new CoordinateHelper();
	GameLog gameLog;

	public Game(int gameType) {
		//0: regular starting grid from fen
		//1: game with custom figures
	this.gameType = gameType;
	}

	/**
	 * Retrieves the game log.
	 *
	 * @return  the game log
	 */
	public GameLog getGameLog() {
		return gameLog;
	}

	/**
	 * Returns the movement handler associated with this object.
	 *
	 * @return         	the movement handler
	 */
	public MovementHandler getMovementHandler() {
		return movementHandler;
	}

	/**
	 * Retrieves the legal move array.
	 *
	 * @return the legal move array
	 */
	public MovementArray getLegalMoveArray() {
		return legalMoveArray;
	}

	/**
	 * Returns the current half move.
	 *
	 * @return the current half move
	 */
	public int getCurrentHalfMove() {
		return currentHalfMove;
	}

	/**
	 * Retrieves the current full move.
	 *
	 * @return the current full move
	 */
	public int getCurrentFullMove() {
		return currentFullMove;
	}

	/**
	 * Sets the selected figure.
	 *
	 * @param  selectedFigure  the figure to be set as selected
	 */
	public void setSelectedFigure(Figure selectedFigure) {
		this.selectedFigure = selectedFigure;
	}

	/**
	 * Sets the selected figure to null.
	 *
	 * @return None    No return value.
	 */
	public void setSelectedFigureToNull() {
		selectedFigure = null;
	}

	/**
	 * Updates the half move clock based on the game log.
	 *
	 * @return None
	 */
	public void updateHalfMoveClock() {
		if(gameLog.getMovementTypeFromEntry(gameLog.getPriorEntry(1)) != 1
		&& gameLog.getFigureTypefromEntry(gameLog.getPriorEntry(1)) != 3) {
			halfMoveClock += 1;
		} else {
			halfMoveClock = 0;
		}
	}

	/**
	 * Retrieves the selected figure.
	 *
	 * @return the selected figure
	 */
	public Figure getSelectedFigure() {
		return selectedFigure;
	}

	/**
	 * Retrieves the chess board object.
	 *
	 * @return         	The chess board object.
	 */
	public ChessBoard getChessBoard() {
		return chessBoard;
	}

	/**
	 * Updates the legalMoveArray by calling the legalMoveArray method from the movementHandler object.
	 *
	 * @param  None This function does not take any parameters.
	 * @return None This function does not return a value.
	 */
	public void updateLegalMoveArray() {
		legalMoveArray = movementHandler.legalMoveArray(chessBoard, selectedFigure, currentHalfMove, gameLog);
	}

	/**
	 * Makes a move in the chess game.
	 *
	 * @param  targetIndex  the index of the target position on the chess board
	 */
	public void makeMove(int targetIndex) {
		//maybe circumvent the cache stuff with log once thats done
		int xCache = selectedFigure.getXPosition();
		int yCache = selectedFigure.getYPosition();
		if (legalMoveArray.moveAtIndexAllowed(targetIndex)) {
			if(clearLogAfter != -1) {
				for (int i = gameLog.getLength()-1; i>clearLogAfter; i--) {
					gameLog.removeEntry(i);
				}
				clearLogAfter = -1;
			}
			gameLog.logMove(chessBoard, selectedFigure, selectedFigure.getFigureColor(), targetIndex);
			chessBoard.moveFigure(selectedFigure,
					coordinateHelper.convertIndextoX(targetIndex),
					coordinateHelper.convertIndextoY(targetIndex), gameLog);
			chessBoard.removeFigure(xCache, yCache);
			updateHalfMoveClock();
			updateEnPassantEligibility(gameLog);
			handleSpecialCasesAfterMove(gameLog);
			currentHalfMove += 1;
		}
	}

	public void updateEnPassantEligibility(GameLog gameLog) {
		for (int i = 0; i<chessBoard.getLength(); i++) {
			if (chessBoard.getFigureAtIndex(i) != null) {
				chessBoard.getFigureAtIndex(i).updateEnPassantEligibility(gameLog);
			}
		}
	}

	/**
	 * Makes a move without performing any checks.
	 *
	 * @param  targetIndex  the index of the target position on the chess board
	 * @param  inputLog     the game log to record the move
	 */
	public void makeMoveWithoutChecks(int targetIndex, GameLog inputLog) {
		int xCache = selectedFigure.getXPosition();
		int yCache = selectedFigure.getYPosition();
			inputLog.logMove(chessBoard, selectedFigure, selectedFigure.getFigureColor(), targetIndex);
			chessBoard.moveFigure(selectedFigure,
					coordinateHelper.convertIndextoX(targetIndex),
					coordinateHelper.convertIndextoY(targetIndex), inputLog);
			chessBoard.removeFigure(xCache, yCache);
			currentHalfMove += 1;
		selectedFigure.updateEnPassantEligibility(inputLog);
		handleSpecialCasesAfterMove(inputLog);
	}

	/**
	 * Handles special cases that occur after a move in the game.
	 *
	 * @param  gameLog  the game log containing the move details
	 */
	public void handleSpecialCasesAfterMove(GameLog gameLog) {
		movementHandler.removePawnAfterEnPassant(chessBoard, gameLog);
		movementHandler.moveTowerAfterCastling(chessBoard, gameLog);
		movementHandler.handlePawnAtBorder(chessBoard, gameLog);
	}

	/**
	 * Initializes the game based on the selected game type.
	 *
	 * This function sets up the chess board and initializes other game variables
	 * based on the selected game type. If the game type is 0, it generates the chess
	 * board using the FEN notation specified in the commented code block. It also
	 * retrieves the current half move, half move clock, and game log from the
	 * FEN setup. If the game type is 1, it generates the starting grid for the
	 * chess board using the commented code block.
	 *
	 * @return None
	 */
	public void initializeGame() {
		if(gameType == 0) {
			//fenSetup.setFenNotation("8/8/1k6/2b5/2pP4/8/5K2/8 b - d3 0 1");
			chessBoard = fenSetup.generateChessBoard();
			currentHalfMove = fenSetup.getCurrentHalfMove();
			halfMoveClock = fenSetup.getHalfMoveClock();
			gameLog = new GameLog();

			if (fenSetup.hasEnPassant()) {
				selectedFigure = fenSetup.getEnPassantPawn();
				makeMoveWithoutChecks(fenSetup.getTargetSquare(), gameLog);
			}
		}
		//if(gameType ==1) {
		//	chessBoard = chessBoard.generateStartingGrid();
		//}

	}

	/**
	 * Reverts the game state to a specific move index.
	 *
	 * @param  moveIndex  the index of the move to revert to
	 */
	public void revertToMove(int moveIndex) {
		GameLog revertLog = new GameLog();
		chessBoard.clearBoard();
		chessBoard = fenSetup.generateChessBoard();
		currentHalfMove = fenSetup.getCurrentHalfMove();
		gameLog.disableLogging();

		for (int i = 0; i<=moveIndex; i++) {
			reconstructMoveAtIndex(i, revertLog);
		}
		revertLog.clearLog();
		gameLog.enableLogging();
		clearLogAfter = moveIndex;
	}

	/**
	 * Reconstructs a move at the specified index in the game log.
	 *
	 * @param  i				the index of the move in the game log
	 * @param  revertLog		the game log to revert the move
	 */
	private void reconstructMoveAtIndex(int i, GameLog revertLog) {
		String entry = gameLog.getEntryAt(i);
		int startIndex = coordinateHelper.convertXYtoIndex(gameLog.getOldXfromEntry(entry), gameLog.getOldYfromEntry(entry));
		int targetIndex = coordinateHelper.convertXYtoIndex(gameLog.getNewXfromEntry(entry), gameLog.getNewYfromEntry(entry));
		selectedFigure = chessBoard.getFigureAtIndex(startIndex);
		//todo: figure out why this won't work if you run it with validity checks... probably something to do with player turns
		makeMoveWithoutChecks(targetIndex, revertLog);
		setSelectedFigureToNull();
	}


}
