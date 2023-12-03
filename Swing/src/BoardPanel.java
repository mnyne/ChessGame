import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//todo: this gonna be the designated class for gameplay, move rollbacks and pawn promotions are gonna be in different GUI elements

public class BoardPanel extends JPanel {
    private final int BOARD_LENGTH = 8;
    private final int TILE_SIZE = 42;

    public BoardPanel() {
        addMouseListener(new BoardMouseListener());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
        //todo: draw sprites
    }

    private class BoardMouseListener extends MouseAdapter {
        int mouseX;
        int mouseY;
        public void mouseClicked(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            System.out.println(mouseX + "" + mouseY);
        }

        public int getX() {
            return mouseX;
        }

        public int getY() {
            return mouseY;
        }


    }
}

