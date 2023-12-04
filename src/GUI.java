import javax.swing.*;
import java.awt.*;


public class GUI extends JFrame {
    private final int BOARD_LENGTH = 8;

    public GUI() {
        setTitle("Chess Engine");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("graphics/knight1.png");
        setIconImage(icon.getImage());



        setLayout(new GridLayout());

        BoardPanel boardPanel = new BoardPanel();
        MovePanel movePanel = new MovePanel(boardPanel);

        boardPanel.setMovePanel(movePanel);
        JScrollPane scrollableMovePanel = new JScrollPane(movePanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(boardPanel);
        add(scrollableMovePanel);

    }

    public void showGUI() {
        setVisible(true);
    }

    public void hideGUI() {
        setVisible(false);
    }
}
