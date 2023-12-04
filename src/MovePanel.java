import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovePanel extends JPanel {
    GameLog gameLog;
    BoardPanel boardPanel;

    public MovePanel() {

        gameLog = new GameLog();
        setLayout(new GridLayout(0, 2));
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
        revalidate();
        repaint();
    }
}
    class moveButton extends JButton {
    ImageIcon icon;
    BoardPanel boardPanel;
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
        }
        private ImageIcon getImageIcon(int figureColor, int figureType) {
            Sprite buttonSprite = new Sprite(figureType, figureColor);
            return buttonSprite.getSprite();
        }

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() instanceof moveButton) {
                boardPanel.revertToMove(index);
            }
        }
    }