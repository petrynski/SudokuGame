import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * JFrame for victory popup.
 * Contains two labels and button. One label informs, that user won the game. Second label shows time in which player solved sudoku. Button allows to start new game.
 */
class winPopup extends JFrame {
    winPopup() {
        JLabel nameInstruction = new JLabel("Enter your name here:");

        JTextField nameField = new JTextField();
        JLabel winLabel = new JLabel("Bravo! You won!");
        winLabel.setFont(new Font("Arial",Font.PLAIN,25));
        winLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winLabel.setForeground(new Color(0x70CF70));

        JLabel timeLabel = new JLabel("Your time: " + Sudoku.timer.getFinalTimeDiff());
        timeLabel.setFont(new Font("Arial",Font.PLAIN,15));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(new Color(0x70CF70));
        nameInstruction.setForeground(new Color(0x70CF70));
        JButton retry = new JButton("Save score and play again!");
        ActionListener actRestart = e -> {
            String name = String.valueOf(nameField.getText());
            long time = Sudoku.timer.getFinalTimeDiff();
            RankingEntry entry = new RankingEntry(time, name);
            Sudoku.writerToFile.addEntry(entry);
            try {
                Sudoku.restart();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        };
        retry.addActionListener(actRestart);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5,1));
        panel.setBackground(new Color(0x302030));
        panel.add(winLabel);
        panel.add(timeLabel);
        panel.add(nameInstruction);
        panel.add(nameField);
        panel.add(retry);
        setSize(new Dimension(300,170));
        add(panel);
        setTitle("YOU WON!");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
