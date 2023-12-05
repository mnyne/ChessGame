import javax.swing.*;
import java.awt.*;


public class GUI extends JFrame {
    private final int BOARD_LENGTH = 8;

    public GUI() {
        setTitle("Chess Engine");
        setSize(1200, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("graphics_deja_view/knight1.png");
        setIconImage(icon.getImage());
        setLayout(new GridBagLayout());
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


        boardPanel.setMovePanel(movePanel);
        JScrollPane scrollableMovePanel = new JScrollPane(movePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        //todo: make scrollPanel actually scroll
        add(boardPanel, boardPanelConstraints);
        add(scrollableMovePanel, movePanelConstraints);

    }

    public void showGUI() {
        setVisible(true);
    }

    public void hideGUI() {
        setVisible(false);
    }
}
