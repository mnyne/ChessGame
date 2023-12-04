import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardPanel extends JPanel {
    private final int BOARD_LENGTH = 8;
    private final int TILE_SIZE = 42;
    private final int MOVE_INDICATOR_SIZE = TILE_SIZE / 2 - 1;
    private final int CANVAS_SIZE = 336;
    CoordinateHelper coordinateHelper = new CoordinateHelper();
    private Game game = new Game();
    private MovePanel movePanel = new MovePanel();
    boolean clicked = false;
    private int mouseX;
    private int mouseY;

    public BoardPanel() {
        game.initializeGame();
        addMouseListener(new BoardMouseListener());
        setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));
        setMovePanel(movePanel);
    }

    public void setGame (Game game) {
        this.game = game;
    }

    public void setMovePanel(MovePanel movePanel) {
        this.movePanel = movePanel;
    }

    public void updateMoveList() {
        movePanel.updateGameLog(game.getGameLog());
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoardTiles(g);
        drawFigures(g);
        if(clicked) {
            highlightSelection(g);
            if(game.getSelectedFigure() != null) {
                showLegalMoves(g);
                showCheckMoves(g);
            }
        }
    }

    private void showCheckMoves(Graphics g) {
        MovementArray checkMoveArray = game.getMovementHandler().kingInCheckArray(game.getChessBoard(),game.getSelectedFigure(), game.getCurrentHalfMove(), game.getGameLog());
        for (int kingInCheckIndex = 0; kingInCheckIndex < game.getChessBoard().getLength(); kingInCheckIndex++) {
            if (!checkMoveArray.moveAtIndexAllowed(kingInCheckIndex)) {
                g.setColor(Color.RED);
                g.drawRect(coordinateHelper.convertIndextoOpticalX(kingInCheckIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2),coordinateHelper.convertIndextoOpticalY(kingInCheckIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2), MOVE_INDICATOR_SIZE, MOVE_INDICATOR_SIZE);
            }
        }
    }

    private void showLegalMoves(Graphics g) {
        for (int legalMoveIndex = 0; legalMoveIndex < game.getChessBoard().getLength(); legalMoveIndex++) {
            if (game.getLegalMoveArray().moveAtIndexAllowed(legalMoveIndex)) {
                g.setColor(Color.GREEN);
                g.drawRect(coordinateHelper.convertIndextoOpticalX(legalMoveIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2),coordinateHelper.convertIndextoOpticalY(legalMoveIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2), MOVE_INDICATOR_SIZE, MOVE_INDICATOR_SIZE);
            }
        }
    }

    private void drawFigures(Graphics g) {
        for (int i = 0; i < game.getChessBoard().getLength(); i++) {
            if (game.getChessBoard().getFigureAtIndex(i) != null) {
                g.drawImage(game.getChessBoard().getFigureAtIndex(i).getSprite(), game.getChessBoard().getFigureAtIndex(i).getOpticalX(),game.getChessBoard().getFigureAtIndex(i).getOpticalY(), this);
            }
        }
    }

    private void drawBoardTiles(Graphics g) {
        for (int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_LENGTH; y++) {
                if (x % 2 == y % 2) {
                    g.setColor(Color.WHITE);
                } else {
                    g.setColor(Color.GRAY);
                }
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private class BoardMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            //todo: build in check to see if it's actually on the board
            mouseX = e.getX();
            mouseY = e.getY();
            if (!clicked) {
                runFirstClick();
            } else {
                runSecondClick();
            }
        }
    }

    private void runFirstClick() {
        int targetIndex = coordinateHelper.convertOpticalXYtoIndex(mouseX, mouseY);
        if (game.getChessBoard().getFigureAtIndex(targetIndex) != null) {
            game.setSelectedFigure(game.getChessBoard().getFigureAtIndex(targetIndex));
            game.updateLegalMoveArray();
            clicked = true;
            repaint();
        }
    }

    private void highlightSelection(Graphics g) {
        g.setColor(Color.BLUE);
        if(game.getSelectedFigure() != null) {
            g.drawRect(game.getSelectedFigure().getOpticalX(), game
                    .getSelectedFigure().getOpticalY(), TILE_SIZE, TILE_SIZE);
        }
    }

    private void runSecondClick() {
        int targetIndex = coordinateHelper.convertOpticalXYtoIndex(mouseX, mouseY);
        if(game.getSelectedFigure() != null) {
            game.makeMove(targetIndex);
            updateMoveList();
        }
        setCanvasForNextTurn();
    }

    private void setCanvasForNextTurn() {
        game.setSelectedFigureToNull();
        clicked = false;
        repaint();
    }

    public void revertToMove(int index) {
        game.revertToMove(index);
        repaint();
    }

    public GameLog getGameLog() {
        return game.getGameLog();
    }
}

