package Figures.FairyChessPieces.Chaturanga;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class Ferz extends Figure {
    public Ferz(int color, int startIndex) {
        super(7, color, startIndex);
    }

    public Ferz(int color, int currentID, int currentIndex) {
        super(7, color, currentID, currentIndex);
    }

    public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
        MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);

        if (move.hasLength(1) && move.diagonalMove()) {
            return true;
        }
        return false;
    }

    public void updateEnPassantEligibility(GameLog gameLog) {
    }

    public Figure deepCopy() {
        Ferz copy = new Ferz(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
        copy.updateMovedStatus(this.hasMoved());
        return copy;
    }
}