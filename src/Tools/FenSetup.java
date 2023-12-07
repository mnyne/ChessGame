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
	
	public FenSetup() {

	}

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
	public int getHalfMoveClock() {
		return halfMoveClock;
	}
	public int getCurrentHalfMove() {
		int currentHalfMove = fullMoveCounter * 2;
		if(toMoveNotation.charAt(0) == 'b') {
			currentHalfMove += 1;
		}
		return currentHalfMove;
	}

	private int extractFullMoveCounter() {
		String s = fen.substring(fen.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		return stringToInt(s.substring(0));
	}

	private int extractHalfMoveClock() {
		String s = fen.substring(fen.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		return stringToInt(s.substring(0, s.indexOf(' ')));
	}

	private int stringToInt (String input) {
		int convertedInt = 0;
		for(int i=0; i<input.length(); i++) {
			char charAt = input.charAt(i);
			int digit = (int) charAt - ASCII_NUMBER_DIFF;
			convertedInt = convertedInt + digit * (int) Math.pow(10, (input.length() - 1 - i));
		}
		return convertedInt;
	}
	private String extractEnPassantTarget() {
		String s = fen.substring(fen.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		return s.substring(0, s.indexOf(' '));
	}

	private String extractCastlingRightsNotation() {
		String s = fen.substring(fen.indexOf(' ') + 1);
		s = s.substring(s.indexOf(' ') + 1);
		return s.substring(0, s.indexOf(' '));
	}

	public String extractToMoveNotation() {
		String s = fen.substring(fen.indexOf(' ') + 1);
		return s.substring(0, s.indexOf(' '));
	}

	public ChessBoard getBoard() {
		return chessBoard;
	}
	
	public GameLog getGameLog() {
		return gameLog;
	}
	
	public ChessBoard generateChessBoard() {
		char figureChar[] = figureDataArray();
		for(int i = 0; i < figureChar.length; i++) {
			if (figureChar[i] != '_') {
				chessBoard.addFigure(i, generateFigure(i, figureChar[i]));
			}
		}
		return chessBoard;
	}
	
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
				break;
		}
	}

	private void setEnPassantEligibility(Figure pawn, int figureColor) {
		int pawnX = pawn.getXPosition();
		int pawnY = pawn.getYPosition();
		int targetX = coordinateHelper.convertNotationToX(enPassantTarget);
		int targetY = coordinateHelper.convertNotationToY(enPassantTarget);
		if (pawnX == targetX && (pawnY-1 == targetY || pawnY+1 == targetY)) {
			pawn.setEnPassantStatus(true);
			addPawnMoveToLog(pawn, figureColor, targetX, targetY);
		}
	}

	private void addPawnMoveToLog(Figure pawn, int figureColor, int targetX, int targetY) {
		int startIndex;
		if(figureColor == 1) {
			startIndex = coordinateHelper.convertXYtoIndex(targetX, targetY -1);
		} else {
			startIndex = coordinateHelper.convertXYtoIndex(targetX, targetY +1);
		}
		gameLog.logMoveFromFen(pawn, startIndex);
	}


	private char transformCharToLowercase(char figureChar) {
		String s = "" + figureChar;
		s = s.toLowerCase();
		return s.charAt(0);
	}

	public char[] figureDataArray() {
		String FigureData = transformEmptySpaces(removeDelimiters(fenFigureNotation, '/'));
		char[] FigureDataArray = splitFigureStringIntoArray(FigureData);
		return FigureDataArray;
	}
	
	public String extractFigures() {
		return fen.substring(0, fen.indexOf(' '));
	}
	
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
	
	public char[] splitFigureStringIntoArray(String figurePlacementData) {
		char[] charArray = new char[BOARD_LENGTH * BOARD_LENGTH];
		for (int index = 0; index < figurePlacementData.length(); index++) {
			charArray[index] = figurePlacementData.charAt(index);
		}
		return charArray;
	}
}
