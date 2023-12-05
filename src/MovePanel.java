import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovePanel extends JPanel {
    GameLog gameLog;
    BoardPanel boardPanel;
    private final int TILE_SIZE = 96;

    public MovePanel() {

        gameLog = new GameLog();
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(3*TILE_SIZE + TILE_SIZE/2, 0));
    }

    public MovePanel(BoardPanel boardPanel) {
        this();
        this.boardPanel = boardPanel;
    }

    public void updateGameLog(GameLog gameLog) {
        this.gameLog.clearLog();
        removeAll();
        for (int i = 0; i<gameLog.getLength(); i++) {
            this.gameLog.addEntry(gameLog.getEntryAt(i));
            int figureColor = gameLog.getColorfromEntry(gameLog.getEntryAt(i));
            int figureType = gameLog.getFigureTypefromEntry(gameLog.getEntryAt(i));
            add(new moveButton(gameLog.getNotatedEntryAt(i), figureColor, figureType, i, boardPanel));
        }
        setPreferredSize(new Dimension(3*TILE_SIZE + TILE_SIZE/2, (gameLog.getLength()+8)/2*8*TILE_SIZE/12));
        revalidate();
        repaint();
    }
}
    class moveButton extends JButton {
    ImageIcon icon;
    BoardPanel boardPanel;
    private final int TILE_SIZE = 96;
    int index;
        public moveButton(String entry, int figureColor, int figureType, int index, BoardPanel boardPanel) {
            this.boardPanel = boardPanel;
            ImageIcon icon = getImageIcon(figureColor, figureType);
            setIcon(icon);
            setText(entry);
            setForeground(Color.lightGray);
            setBackground(Color.DARK_GRAY);
            this.index = index;
            addActionListener(this::actionPerformed);
            Dimension buttonSize = new Dimension(3*TILE_SIZE/2, 8*TILE_SIZE/12);
            setPreferredSize(buttonSize);
        }
        private ImageIcon getImageIcon(int figureColor, int figureType) {
            Sprite buttonSprite = new Sprite(figureType, figureColor);
            return buttonSprite.getSprite(1);
        }

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() instanceof moveButton) {
                boardPanel.revertToMove(index);
            }
        }
    }