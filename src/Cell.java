import javax.swing.*;

/**
 * Cell class is used as a single text field for a number in Sudoku.
 * Main purpose is to store and visualise sudoku grid numbers.
 * Extending JTextField thus it has it's methods, even though they are not written below.
 */
class Cell  extends JTextField {
    /**
     * Int value that is in the text field.
     * Valid value of content is a number of 0 to 9. 0 stands for 'empty' cell.
     */
    private int content;
    /**
     * Coordinates of a Cell in Grid which are row number and column number.
     */
    private int row,column;

    /**
     * Constructor for Cell class.
     * @param content Integer of 0 to 9.
     * @param row Integer of 0 to 9. Cannot be reset after construction of an object.
     * @param column Integer of 0 to 9. Cannot be reset after construction of an object.
     */
    Cell(int content, int row, int column)
    {
        this.content = content;
        this.row = row;
        this.column = column;
        setText(Integer.toString(content));
        this.getDocument().addDocumentListener(new ChangedValueListener(this));
    }

    void setContent(int content) {
        this.content = content;
    }

    int getContent() {
        return content;
    }

    int getColumn() {
        return column;
    }

    int getRow() {
        return row;
    }
}
