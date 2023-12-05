import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.NORTH;


public class GUI extends JFrame {
    private final int BOARD_LENGTH = 8;
    private final int TILE_SIZE = 96;

    public GUI() {
        setTitle("Chess Engine");
        setSize(13*TILE_SIZE, 9 * TILE_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("graphics_deja_view/knight1.png");
        setIconImage(icon.getImage());
        setLayout(new FlowLayout());
        setResizable(false);


        BoardPanel boardPanel = new BoardPanel();

        GridBagConstraints boardPanelConstraints = new GridBagConstraints();
        boardPanelConstraints.gridx = 0;
        boardPanelConstraints.gridy = 0;
        boardPanelConstraints.gridheight = 2;
        boardPanelConstraints.gridwidth = 2;
        boardPanelConstraints.fill = GridBagConstraints.BOTH;

        MovePanel movePanel = new MovePanel(boardPanel);

        GridBagConstraints movePanelConstraints = new GridBagConstraints();

        movePanelConstraints.gridx = 2;
        movePanelConstraints.gridy = 0;
        movePanelConstraints.gridheight = 2;
        movePanelConstraints.gridwidth = 1;
        movePanelConstraints.fill = GridBagConstraints.BOTH;
        movePanelConstraints.anchor = GridBagConstraints.NORTH;


        boardPanel.setMovePanel(movePanel);
        add(boardPanel, boardPanelConstraints);
        JScrollPane scrollableMovePanel = new JScrollPane(movePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableMovePanel.setPreferredSize(new Dimension(4*TILE_SIZE, 8*TILE_SIZE));
        add(scrollableMovePanel, movePanelConstraints);

    }

    public void showGUI() {
        setVisible(true);
    }

    public void hideGUI() {
        setVisible(false);
    }
}
