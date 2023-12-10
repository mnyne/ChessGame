package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import Figures.Figure;
import Game.Game;
import Game.MovementArray;
import Game.GameLog;
import Tools.CoordinateHelper;

public class BoardPanel extends JPanel {
    private final int BOARD_LENGTH = 8;
    private final int TILE_SIZE = 96;
    private final int MOVE_INDICATOR_SIZE = TILE_SIZE / 10 * 8;
    private final int CANVAS_SIZE = TILE_SIZE * 8;
    CoordinateHelper coordinateHelper = new CoordinateHelper();
    private Game game = new Game(0);
    private MovePanel movePanel = new MovePanel();
    boolean clicked = false;
    private int mouseX;
    private int mouseY;
    private int revertIndex;
    Image boardImage;
    ImageIcon boardIcon;
    private boolean moveUndone = false;


    public BoardPanel() {
        boardIcon = new ImageIcon("graphics_deja_view/board.png");
        boardImage = boardIcon.getImage().getScaledInstance(TILE_SIZE * 8, TILE_SIZE * 8, Image.SCALE_DEFAULT);
        game.initializeGame();
        addMouseListener(new BoardMouseListener());
        setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));
        setMovePanel(movePanel);
    }

    /**
     * Sets the game object for this instance.
     *
     * @param  game  the game object to be set
     */
    public void setGame (Game game) {
        this.game = game;
    }

    /**
     * Sets the move panel for this object.
     *
     * @param  movePanel  the move panel to be set
     */
    public void setMovePanel(MovePanel movePanel) {
        this.movePanel = movePanel;
    }

    /**
     * Updates the move list by calling the `updateGameLog` method of the `movePanel`
     * object, passing in the `gameLog` obtained from the `game` object.
     *
     * @return None
     */
    public void updateMoveList() {
        movePanel.updateGameLog(game.getGameLog());
    }

    /**
     * Paints the component using the specified Graphics object.
     *
     * @param  g  the Graphics object used for painting
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (boardIcon.getIconWidth() != -1) {
            g.drawImage(boardImage, 0, 0, this);
        } else {
            drawBoardTiles(g);
        }

        drawFigures(g);
        if(clicked) {
            highlightSelection(g);
            if(game.getSelectedFigure() != null) {
                showLegalMoves(g);
                showCheckMoves(g);
            }
        }
        if(moveUndone) {
            highlightLastSquare(g);
            moveUndone = false;
        }
    }

    /**
     * Renders the check moves on the graphics object.
     *
     * @param  g   the graphics object to render on
     */
    private void showCheckMoves(Graphics g) {
        MovementArray checkMoveArray = game.getMovementHandler().kingInCheckArray(game.getChessBoard(),game.getSelectedFigure(), game.getCurrentHalfMove(), game.getGameLog());
        for (int kingInCheckIndex = 0; kingInCheckIndex < game.getChessBoard().getLength(); kingInCheckIndex++) {
            if (!checkMoveArray.moveAtIndexAllowed(kingInCheckIndex)) {
                g.setColor(Color.RED);
                g.drawRect(coordinateHelper.convertIndextoOpticalX(kingInCheckIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2),coordinateHelper.convertIndextoOpticalY(kingInCheckIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2), MOVE_INDICATOR_SIZE, MOVE_INDICATOR_SIZE);
            }
        }
    }

    /**
     * Displays the legal moves on the chessboard.
     *
     * @param  g  the Graphics object to draw the legal moves
     */
    private void showLegalMoves(Graphics g) {
        for (int legalMoveIndex = 0; legalMoveIndex < game.getChessBoard().getLength(); legalMoveIndex++) {
            if (game.getLegalMoveArray().moveAtIndexAllowed(legalMoveIndex)) {
                g.setColor(new Color(0x79D221));
                g.drawRect(coordinateHelper.convertIndextoOpticalX(legalMoveIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2),coordinateHelper.convertIndextoOpticalY(legalMoveIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2), MOVE_INDICATOR_SIZE, MOVE_INDICATOR_SIZE);
            }
        }
    }

    /**
     * Draws all the figures on the chessboard.
     *
     * @param  g  the graphics object to draw the figures on
     */
    private void drawFigures(Graphics g) {
        for (int i = 0; i < game.getChessBoard().getLength(); i++) {
            if (game.getChessBoard().getFigureAtIndex(i) != null) {
                g.drawImage(game.getChessBoard().getFigureAtIndex(i).getSprite(), game.getChessBoard().getFigureAtIndex(i).getSpriteX(),game.getChessBoard().getFigureAtIndex(i).getSpriteY(), this);
            }
        }
    }

    /**
     * Draws the board tiles using the given Graphics object.
     *
     * @param  g  the Graphics object used for drawing
     */
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

    /**
     * Highlights the last square on the game board.
     *
     * @param  g  the graphics object used for drawing
     */
    private void highlightLastSquare(Graphics g) {
        g.setColor(Color.BLUE);
        int squareIndex = coordinateHelper.convertXYtoIndex(getGameLog().getOldXfromEntry(getGameLog().getEntryAt(revertIndex)), getGameLog().getOldYfromEntry(getGameLog().getEntryAt(revertIndex)));
        g.drawRect(coordinateHelper.convertIndextoOpticalX(squareIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2),coordinateHelper.convertIndextoOpticalY(squareIndex)+ ((TILE_SIZE - MOVE_INDICATOR_SIZE) / 2), MOVE_INDICATOR_SIZE, MOVE_INDICATOR_SIZE);
    }

    private class BoardMouseListener extends MouseAdapter {

        /**
         * Handles the mouse click event.
         *
         * @param  e  the MouseEvent object representing the click event
         */
        public void mouseClicked(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            if (mouseX < TILE_SIZE * BOARD_LENGTH && mouseY < TILE_SIZE * BOARD_LENGTH) {
                if (!clicked) {
                    runFirstClick();
                } else {
                    runSecondClick();
                }
            }
        }
    }

    /**
     * Runs the first click event.
     *
     * @return None
     */
    private void runFirstClick() {
        int targetIndex = coordinateHelper.convertOpticalXYtoIndex(mouseX, mouseY);
        if (game.getChessBoard().getFigureAtIndex(targetIndex) != null) {
            game.setSelectedFigure(game.getChessBoard().getFigureAtIndex(targetIndex));
            game.updateLegalMoveArray();
            clicked = true;
            repaint();
        }
    }

    /**
     * Highlights the selected figure on the graphics.
     *
     * @param  g  the Graphics object to draw on
     */
    private void highlightSelection(Graphics g) {
        g.setColor(Color.BLUE);
        if(game.getSelectedFigure() != null) {
            g.drawRect(game.getSelectedFigure().getOpticalX(), game
                    .getSelectedFigure().getOpticalY(), TILE_SIZE, TILE_SIZE);
        }
    }

    /**
     * Perform the actions associated with the second click event.
     *
     * @return           none
     */
    private void runSecondClick() {
        int targetIndex = coordinateHelper.convertOpticalXYtoIndex(mouseX, mouseY);
        if(game.getSelectedFigure() != null) {
            game.makeMove(targetIndex);
            updateMoveList();
        }
        setCanvasForNextTurn();
    }

    /**
     * Resets the canvas for the next turn.
     *
     */
    private void setCanvasForNextTurn() {
        game.setSelectedFigureToNull();
        clicked = false;
        repaint();
    }

    /**
     * Reverts the game to a previous move at the specified index.
     *
     * @param  index  the index of the move to revert to
     */
    public void revertToMove(int index) {
        game.revertToMove(index);
        revertIndex = index;
        moveUndone = true;
        repaint();
    }

    /**
     * Retrieves the game log.
     *
     * @return  the game log
     */
    public GameLog getGameLog() {
        return game.getGameLog();
    }
}

