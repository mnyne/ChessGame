package GUI;

import Tools.PGNParser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.SwingConstants.NORTH;


public class GUI extends JFrame {
    private final int BOARD_LENGTH = 8;
    private final int TILE_SIZE = 96;
    private PGNParser parser = new PGNParser("Carlsen.pgn");

    public GUI() {

        //Set up the window
        setTitle("Chess Engine");
        setSize(13*TILE_SIZE, 9 * TILE_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("graphics_deja_view/knight1.png");
        setIconImage(icon.getImage());
        setLayout(new FlowLayout());
        setResizable(false);

        //Set up the board
        BoardPanel boardPanel = new BoardPanel();

        //Set up the constraints for the board panel
        GridBagConstraints boardPanelConstraints = new GridBagConstraints();
        boardPanelConstraints.gridx = 0;
        boardPanelConstraints.gridy = 0;
        boardPanelConstraints.gridheight = 2;
        boardPanelConstraints.gridwidth = 2;
        boardPanelConstraints.fill = GridBagConstraints.BOTH;

        MovePanel movePanel = new MovePanel(boardPanel);

        //Set up the constraints for the move panel
        GridBagConstraints movePanelConstraints = new GridBagConstraints();

        movePanelConstraints.gridx = 2;
        movePanelConstraints.gridy = 0;
        movePanelConstraints.gridheight = 2;
        movePanelConstraints.gridwidth = 1;
        movePanelConstraints.fill = GridBagConstraints.BOTH;
        movePanelConstraints.anchor = GridBagConstraints.NORTH;


        boardPanel.setMovePanel(movePanel);

        //test
        ArrayList<String> pgnMoves = parser.getMovesFromGame(0);
        ArrayList<String[]> splitMoves = new ArrayList<String[]>();
        for (int i = 0; i<pgnMoves.size(); i++) {
            splitMoves.add(parser.splitMoveIntoSegments(pgnMoves.get(i)));
        }
        boardPanel.importPGN(splitMoves, parser);

        add(boardPanel, boardPanelConstraints);
        JScrollPane scrollableMovePanel = new JScrollPane(movePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableMovePanel.setPreferredSize(new Dimension(4*TILE_SIZE, 8*TILE_SIZE));
        add(scrollableMovePanel, movePanelConstraints);

    }
    /**
     * Shows the graphical user interface.
     *
     * @param  None  This function does not take any parameters.
     * @return None  This function does not return any value.
     */
    public void showGUI() {
        setVisible(true);
    }

    /**
     * Hides the graphical user interface.
     */
    public void hideGUI() {
        setVisible(false);
    }
}
