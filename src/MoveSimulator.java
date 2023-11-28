//for later; get your movement code into MovementHandler first

public class MoveSimulator {

	CoordinateHelper coordinateHelper = new CoordinateHelper();
	
	Figure simulatedFigure;
	ChessBoard simulatedBoard;

	public MoveSimulator(ChessBoard currentBoard) {
		simulatedBoard = currentBoard.deepCopy();
	}
}
