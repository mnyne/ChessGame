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
	//TODO: everything that isn't figure notation
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
		if(figureChar < ASCII_LOWERCASE_START) {
			figureColor = 0;
		}
		
		switch(transformCharToLowercase(figureChar)) {
		case 'r':
			//todo: hasMoved => castling rights
			Figure rook = new Rook(figureColor, index);
			return rook;
		case 'n':
			return new Knight(figureColor, index);
		case 'b':
			return new Bishop(figureColor, index);
		case 'q':
			return new Queen(figureColor, index);
		case 'p':
			//todo: eligibleforenpassant => en passant square
			Figure pawn = new Pawn(figureColor, index);
			return pawn;
		case 'k':
			return new King(figureColor, index);
		default:
			// dummy figure
			return new Pawn(-1, 0);
		}
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

	public void generateLogEntry() {
		//todo: generate log entries
	}
	
}
