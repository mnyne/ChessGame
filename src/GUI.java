import javax.swing.*;


public class GUI extends JFrame {
    private final int BOARD_LENGTH = 8;

    public GUI() {
        setTitle("Chess Engine");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("graphics/knight1.png");
        setIconImage(icon.getImage());

        BoardPanel boardPanel = new BoardPanel();
        add(boardPanel);
    }

    public void showGUI() {
        setVisible(true);
    }

    public void hideGUI() {
        setVisible(false);
    }
}
