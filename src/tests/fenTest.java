package tests;

import acm.program.Program;

public class fenTest extends Program {

	private final int BOARD_LENGTH = 8;
	private final int ASCII_NUMBER_DIFF = 48;
	private final int ASCII_LETTER_START = 65;
	private final int ASCII_LOWERCASE_START = 97;
	
	private String fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

	
	public void run() {

		char figureChar[] = figureDataArray();
		for(int i = 0; i < figureChar.length; i++) {
			if (figureChar[i] != '_') {
				println("Color: " + generateFigureColor(i, figureChar[i]) + ", Type: " + getFigureTypeFromChar(figureChar[i]));
			}
		}
		
		
	}
	
	public void setFenNotation(String fen_input) {
		//add validity check later
		fen = fen_input;
	}
		
	public int generateFigureColor(int index, char figureChar) {
		// dummy figure
				int figureColor = 1;
		if(figureChar < ASCII_LOWERCASE_START) {
			figureColor = 0;
		} 
		
		
		return figureColor;
	}
	
	private int getFigureTypeFromChar(char figureChar) {
		figureChar = transformCharToLowercase(figureChar);
		switch(figureChar) {
		case 'r':
			return 5;
		case 'n':
			return 2;
		case 'b':
			return 0;
		case 'q':
			return 4;
		case 'p':
			return 3;
		case 'k':
			return 1;
		}
		return 0;
	}

	private char transformCharToLowercase(char figureChar) {
		String s = "" + figureChar;
		s = s.toLowerCase();
		return s.charAt(0);
	}

	public char[] figureDataArray() {
		String FigureData = transformEmptySpaces(extractFigureString());
		char[] FigureDataArray = splitFigureStringIntoArray(FigureData);
		return FigureDataArray;
	}
	
	public String extractFigureString() {
		String rawFigureData = fen.substring(0, fen.indexOf(' '));
		return removeDelimiters(rawFigureData, '/');
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
		//todo...
	}
	
}