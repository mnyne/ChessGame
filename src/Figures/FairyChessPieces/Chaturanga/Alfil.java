package Figures.FairyChessPieces.Chaturanga;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class Alfil extends Figure {
    public Alfil(int color, int startIndex) {
        super(6, color, startIndex);
    }

    public Alfil(int color, int currentID, int currentIndex) {
        super(6, color, currentID, currentIndex);
    }

    public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
        MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);

        if (move.hasExactLength(2) && move.diagonalMove()) {
            return true;
        }
        return false;
    }

    public void updateEnPassantEligibility(GameLog gameLog) {
    }

    public Figure deepCopy() {
        Alfil copy = new Alfil(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
        copy.updateMovedStatus(this.hasMoved());
        return copy;
    }
}