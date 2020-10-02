import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Class implementing DocumentListener interface for Cell class.
 * Main purpose of this class is to validate input for Cell class. Valid input is single digit of 1-9.
 * @see Validator
 * @see Cell
 */
public class ChangedValueListener implements DocumentListener {
    /**
     * Cell that ChangedValueListener listens to.
     */
    private Cell listenedCell;

    ChangedValueListener(Cell listenedCell) {
        this.listenedCell = listenedCell;
    }

    /**
     * Method used to handle input validation.
     * Uses runnable assist, that is invoked later by Swing event-dispatching thread, to validate input from player.
     * Must be invoked later because of the errors that non-safe Swing data structures are causing.
     * Uses Validator class, which methods simply return answer to question 'Is input valid?'.
     */
    private void changeText()
    {
        Runnable assist = () -> {
            String input = listenedCell.getText();
            Validator valid = new Validator();
            if(!valid.isInputValid(input))      //if input is not of [1-9], cell's content is reset to blank
            {
                listenedCell.setContent(0);
                listenedCell.setText("");
                return;
            }

            //Getting Cell's row and column number
            int row = listenedCell.getRow();
            int column = listenedCell.getColumn();
            //Creating copy of Sudoku's grid values
            int[][] grid = new int[Sudoku.Size][Sudoku.Size];
            for (int i = 0; i < Sudoku.Size; i++) {
                for (int j = 0; j < Sudoku.Size; j++) {
                    grid[i][j] = Sudoku.grid[i][j].getContent();
                }
            }
            //According to Sudoku game rules, number's colour is set to red when invalid, or dark gray if ok
            if(!valid.isNumberCorrect(row,column,grid,Integer.parseInt(input)))
            {
                listenedCell.setForeground(Color.RED);
                System.out.println(row+" "+column);
            }
            else
            {
                listenedCell.setContent(Integer.parseInt(input));
                listenedCell.setForeground(Color.DARK_GRAY);
            }
        };
        SwingUtilities.invokeLater(assist);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changeText();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        listenedCell.setContent(0);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
