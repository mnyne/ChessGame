package Figures.FairyChessPieces.Chaturanga;

import Figures.Figure;
import Game.ChessBoard;
import Game.GameLog;
import Game.MovementValidityChecker;

public class Padati extends Figure {

    public Padati(int color, int startIndex) {
        super(8, color, startIndex);
    }

    public Padati(int color, int currentID, int currentIndex) {
        super(8, color, currentID, currentIndex);
    }

    public boolean moveIsLegal(ChessBoard currentBoard, Figure selectedFigure, int targetIndex) {
        MovementValidityChecker move = new MovementValidityChecker (currentBoard, selectedFigure, targetIndex);
        if(move.isForwardMove(this.getFigureColor())) {
            if (forwardMoveLegal(move) || diagonalMoveLegal(move)) {
                return true;
            }
        }
        return false;
    }

    private boolean diagonalMoveLegal(MovementValidityChecker move) {
        if (move.diagonalMove() && move.hasLength(1)) {
            if(move.canCapture()) {
                return true;
            }
        }
        return false;
    }

    private boolean forwardMoveLegal(MovementValidityChecker move) {
        if (move.orthogonalMove() && !move.orthogonalCollision()  && !move.canCapture()) {
            if (move.hasLength(1)) {
                return true;
            }
        }
        return false;
    }

    public Figure deepCopy() {
        Padati copy = new Padati(this.getFigureColor(), this.getFigureID(), this.getCurrentIndex());
        copy.updateMovedStatus(this.hasMoved());
        return copy;
    }

    public void updateEnPassantEligibility(GameLog gameLog) {
        setEnPassantStatus(false);
        int yMovement = gameLog.getYMovementfromEntry(gameLog.getPriorEntry(1));
        if(gameLog.getFigureIDfromEntry(gameLog.getPriorEntry(1)) == this.getFigureID()) {
            if(yMovement == 2 || yMovement == -2) {
                setEnPassantStatus(true);
            }
        }
    }
}