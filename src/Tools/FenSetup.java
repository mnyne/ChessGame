package Tools;

import Figures.*;
import Figures.FairyChessPieces.Chaturanga.Ferz;
import Figures.FairyChessPieces.Chaturanga.Alfil;
import Figures.OrthodoxChessPieces.*;
import Game.ChessBoard;
import Game.GameLog;

public class FenSetup {

	private final int BOARD_LENGTH = 8;
	private final int ASCII_NUMBER_DIFF = 48;
	private final int ASCII_LETTER_START = 65;
	private final int ASCII_LOWERCASE_START = 97;
	private CoordinateHelper coordinateHelper = new CoordinateHelper();
	private GameLog gameLog = new GameLog();
	private ChessBoard chessBoard = new ChessBoard(BOARD_LENGTH * BOARD_LENGTH);

	private String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
	private String fenFigureNotation = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
	private String toMoveNotation = "w";
	private String castlingRightsNotation = "KQkq";
	private String enPassantTarget = "-";
	private int halfMoveClock = 0;
	private int fullMoveCounter = 1;
	private int targetSquare;
	private boolean enPassant = false;
	Figure enPassantPawn;
	
	public FenSetup() {

	}

	/**
	 * Sets the FEN notation for the chess board.
	 *
	 * @param  fen_input	the FEN notation to be set
	 */
	public void setFenNotation(String fen_input) {
		//add validity check later
		fen = fen_input;
		fenFigureNotation = extractFigures();
		toMoveNotation = extractToMoveNotation();
		castlingRightsNotation = extractCastlingRightsNotation();
		enPassantTarget = extractEnPassantTarget();
		halfMoveClock = extractHalfMoveClock();
		fullMoveCounter = extractFullMoveCounter();
	}

	/**
	 * Retrieves the value of the halfMoveClock variable.
	 *
	 * @return the value of the halfMoveClock variable
	 */
	public int getHalfMoveClock() {
		return halfMoveClock;
	}

	/**
	 * Returns the current half move.
	 *
	 * @return the current half move
	 */
	public int getCurrentHalfMove() {
		int currentHalfMove = fullMoveCounter * 2;
		if(toMoveNotation.charAt(0) == 'b') {
			currentHalfMove += 1;
		}
		if(enPassant) {
			currentHalfMove -= 1;
		}
		return currentHalfMove;
	}

	/**
	 * Extracts the full move counter from the given 'fen' string.
	 *
	 * @return         	The extracted full move counter as an integer.
	 */
	private int extractFullMoveCounter() {
		String s = fen.substring(fen.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		return stringToInt(s.substring(0));
	}

	/**
	 * Extracts the half move clock from the given FEN string.
	 *
	 * @return  the extracted half move clock
	 */
	private int extractHalfMoveClock() {
		String s = fen.substring(fen.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		return stringToInt(s.substring(0, s.indexOf(' ')));
	}

	/**
	 * Converts a string to an integer.
	 *
	 * @param  input  the string to be converted
	 * @return        the converted integer
	 */
	private int stringToInt (String input) {
		int convertedInt = 0;
		for(int i=0; i<input.length(); i++) {
			char charAt = input.charAt(i);
			int digit = (int) charAt - ASCII_NUMBER_DIFF;
			convertedInt = convertedInt + digit * (int) Math.pow(10, (input.length() - 1 - i));
		}
		return convertedInt;
	}

	/**
	 * Extracts the En Passant target from the given FEN string.
	 *
	 * @return  The En Passant target.
	 */
	private String extractEnPassantTarget() {
		String s = fen.substring(fen.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		return s.substring(0, s.indexOf(' '));
	}

	/**
	 * Extracts the castling rights notation from the given FEN string.
	 *
	 * @return          The castling rights notation.
	 */
	private String extractCastlingRightsNotation() {
		String s = fen.substring(fen.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		return s.substring(0, s.indexOf(' '));
	}


	/**
	 * Extracts the move notation from the given string.
	 *
	 * @return         	The move notation extracted from the string.
	 */
	public String extractToMoveNotation() {
		String s = fen.substring(fen.indexOf(' ') + 1);
		return s.substring(0, s.indexOf(' '));
	}

	/**
	 * Returns the ChessBoard object.
	 *
	 * @return     the ChessBoard object
	 */
	public ChessBoard getBoard() {
		return chessBoard;
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
	 * Generates a chess board.
	 *
	 * @return  the generated chess board
	 */
	public ChessBoard generateChessBoard() {
		char figureChar[] = figureDataArray();
		for(int i = 0; i < figureChar.length; i++) {
			if (figureChar[i] != '_') {
				chessBoard.addFigure(i, generateFigure(i, figureChar[i]));
			}
		}
		if(!enPassantTarget.equals("-")) {
			int cacheX = enPassantPawn.getXPosition();
			int cacheY = enPassantPawn.getYPosition();
			targetSquare = coordinateHelper.convertXYtoIndex(cacheX, cacheY);
			chessBoard.moveFigure(enPassantPawn, gameLog.getOldXfromEntry(gameLog.getPriorEntry(1)), gameLog.getOldYfromEntry(gameLog.getPriorEntry(1)), gameLog);
			chessBoard.removeFigure(cacheX, cacheY);
			enPassant = true;
		}
		return chessBoard;
	}

	public boolean hasEnPassant() {
		return enPassant;
	}

	/**
	 * Generates a Figure object based on the given index and figureChar.
	 *
	 * @param index       the index of the figure
	 * @param figureChar  the character representing the figure
	 * @return            the generated Figure object
	 */
	public Figure generateFigure(int index, char figureChar) {
		
		int figureColor = 1;
		int yPawnStart = 1;
		if(figureChar < ASCII_LOWERCASE_START) {
			figureColor = 0;
			yPawnStart = 6;
		}
		
		switch(transformCharToLowercase(figureChar)) {
		case 'r':
			Figure rook = new Rook(figureColor, index);
				setCastlingRights(rook);
			return rook;
		case 'n':
			return new Knight(figureColor, index);
		case 'b':
			return new Bishop(figureColor, index);
		case 'q':
			return new Queen(figureColor, index);
		case 'p':
			Figure pawn = new Pawn(figureColor, index);
			if(yPawnStart != coordinateHelper.convertIndextoY(index)) {
				pawn.setMovedStatus();
			}
			if(!enPassantTarget.equals("-")) {
				setEnPassantEligibility(pawn, figureColor);
			}
			return pawn;
		case 'k':
			return new King(figureColor, index);
		default:
			// dummy figure
			return new Pawn(-1, 0);
		}
	}

	/**
	 * Sets the castling rights for a given rook.
	 *
	 * @param  rook  the rook figure
	 */
	private void setCastlingRights(Figure rook) {
		switch(rook.getCurrentIndex()) {
			case 0:
				if(castlingRightsNotation.indexOf('q') == -1) {
					rook.setMovedStatus();
				}
				break;

			case 7:
				if(castlingRightsNotation.indexOf('k') == -1) {
					rook.setMovedStatus();
				}
				break;

			case 56:
				if(castlingRightsNotation.indexOf('Q') == -1) {
					rook.setMovedStatus();
				}
				break;

			case 63:
				if(castlingRightsNotation.indexOf('K') == -1) {
					rook.setMovedStatus();
				}

				break;
			default:
				rook.setMovedStatus();
				break;
		}
	}

	/**
	 * Sets the en passant eligibility for a pawn.
	 *
	 * @param  pawn         the pawn figure
	 * @param  figureColor  the color of the figure
	 */
	private void setEnPassantEligibility(Figure pawn, int figureColor) {

		int pawnX = pawn.getXPosition();
		int pawnY = pawn.getYPosition();
		int targetX = coordinateHelper.convertNotationToX(enPassantTarget);
		int targetY = coordinateHelper.convertNotationToY(enPassantTarget);
		if (pawnX == targetX && (pawnY-1 == targetY || pawnY+1 == targetY)) {
			enPassantPawn = pawn;
			addPawnMoveToLog(pawn, figureColor, targetX, targetY);
		}
	}

	/**
	 * Adds a pawn move to the game log.
	 *
	 * @param  pawn         the pawn figure that made the move
	 * @param  figureColor  the color of the pawn figure (1 for white, 0 for black)
	 * @param  targetX      the target x-coordinate of the pawn move
	 * @param  targetY      the target y-coordinate of the pawn move
	 */
	private void addPawnMoveToLog(Figure pawn, int figureColor, int targetX, int targetY) {
		int startIndex;
		if(figureColor == 1) {
			startIndex = coordinateHelper.convertXYtoIndex(targetX, targetY -1);
		} else {
			startIndex = coordinateHelper.convertXYtoIndex(targetX, targetY +1);
		}
		gameLog.logMoveFromFen(pawn, startIndex);
	}


	/**
	 * Transforms the given character to lowercase.
	 *
	 * @param  figureChar  the character to be transformed
	 * @return             the lowercase version of the character
	 */
	private char transformCharToLowercase(char figureChar) {
		String s = "" + figureChar;
		s = s.toLowerCase();
		return s.charAt(0);
	}

	/**
	 * Generates a char array containing the figure data.
	 *
	 * @return   the char array containing the figure data
	 */
	public char[] figureDataArray() {
		String FigureData = transformEmptySpaces(removeDelimiters(fenFigureNotation, '/'));
		char[] FigureDataArray = splitFigureStringIntoArray(FigureData);
		return FigureDataArray;
	}

	/**
	 * Extracts the figures from the given string.
	 *
	 * @return  the extracted figures as a string
	 */
	public String extractFigures() {
		return fen.substring(0, fen.indexOf(' '));
	}

	/**
	 * Transforms empty spaces in the given figureData string into underscores.
	 *
	 * @param  figureData  the input string containing the figure data
	 * @return             the transformed string with empty spaces replaced by underscores
	 */
	public String transformEmptySpaces(String figureData) {
		for (int i = 0; i < figureData.length(); i++) {
			if (figureData.charAt(i) < ASCII_LETTER_START) {
			int spaces = (int) figureData.charAt(i) - ASCII_NUMBER_DIFF;
			String replacedCharacters = "";
				for (int s = 0; s < spaces; s++) {
					replacedCharacters = replacedCharacters + "_";
				}
			figureData = figureData.replace(""+figureData.charAt(i), replacedCharacters);
			}
		}
		return figureData;
	}

	/**
	 * Removes all occurrences of the specified delimiter character from the input string.
	 *
	 * @param  inputString  the string from which the delimiters should be removed
	 * @param  delimiter    the character to be removed from the input string
	 * @return              the resulting string after removing all occurrences of the delimiter
	 */
	private String removeDelimiters(String inputString, char delimiter) {
		
		String outputString = "";
		int delimiterPosition;
		while (true) {
			delimiterPosition = inputString.indexOf(delimiter);
			if (delimiterPosition == -1) {
				outputString = outputString + inputString;
				break;
			}
			String substring = inputString.substring(0, delimiterPosition);
			inputString = inputString.substring(delimiterPosition + 1);
			outputString = outputString + substring;
		}

		return outputString;
	}

	/**
	 * Splits a given figure string into a char array.
	 *
	 * @param  figurePlacementData  the figure string to be split
	 * @return                      the resulting char array
	 */
	public char[] splitFigureStringIntoArray(String figurePlacementData) {
		char[] charArray = new char[BOARD_LENGTH * BOARD_LENGTH];
		for (int index = 0; index < figurePlacementData.length(); index++) {
			charArray[index] = figurePlacementData.charAt(index);
		}
		return charArray;
	}

	public Figure getEnPassantPawn() {
		return enPassantPawn;
	}

	public int getTargetSquare() {
		return targetSquare;
	}
}
