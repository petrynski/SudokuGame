/**
 * Class used to check if sudoku rules has not been broken.
 * Checks input, number's validity and game end condition.
 */
class Validator {
    /**
     * Checks if given input from user is digit of 1-9.
     * @param input User's input
     * @return true if valid
     */
    boolean isInputValid(String input) {
        return input.matches("[1-9]");
    }

    /**
     * Used to check if given grid is valid.
     * @param row
     * @param column
     * @param grid
     * @param checkedField
     * @return true if valid
     */
    boolean isNumberCorrect(int row, int column, int[][] grid, int checkedField)
    {
        return isNumberCorrect(row, column, grid, checkedField, true);
    }

    /**
     * Checks if given cell is valid for whole sudoku grid.
     * @param row Cell's row
     * @param column Cell's column
     * @param grid Whole sudoku grid
     * @param checkedField Given cell's content
     * @param isItUserInput Flag used to check if info should be printed to console.
     * @return true if valid
     */
    boolean isNumberCorrect(int row, int column, int[][] grid, int checkedField, boolean isItUserInput)
    {
        int anotherField;
        //Checking if row is valid
        for(int i = 0; i < 9; i++)
        {
            if(i == column)
                continue;
            else
            {
                anotherField = grid[row][i];
                if (anotherField == checkedField)
                {
                    if(isItUserInput)
                    {
                        System.out.println("Checked Row" + " row " + row + " column " + i);
                        System.out.println("Checked value " + checkedField + " value invalid " + anotherField);
                    }
                    return false;
                }
            }
        }
        //Checking if column is valid
        for(int i = 0; i < 9; i++)
        {
            if(i == row)
                continue;
            else
            {
                anotherField = grid[i][column];
                if (anotherField == checkedField)
                {
                    if(isItUserInput)
                    {
                        System.out.println("Checked Col " + " row " + i + " column " + column);
                        System.out.println("Checked value " + checkedField + " value invalid " + anotherField);
                    }
                    return false;
                }
            }
        }
        //Checking if square is valid
        int squareBeginningRow = (row/3)*3;
        int squareBeginningColumn = (column/3)*3;
        for(int i = squareBeginningRow; i < squareBeginningRow + 3; i++)
        {
            for(int j = squareBeginningColumn; j < squareBeginningColumn + 3; j++)
            {
                if(i == row && j == column)
                    continue;

                else
                {
                    anotherField = grid[i][j];
                    if (checkedField == anotherField)
                    {
                        if(isItUserInput)
                        {
                            System.out.println("Checked SQR " + " row " + i + " column " + j);
                            System.out.println("Checked value " + checkedField + " value invalid " + anotherField);
                        }
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Checks if whole sudoku grid is filled.
     * @return true if solved
     */
    boolean isSudokuSolved()
    {
        for (Cell[] row: Sudoku.grid)
        {
            for (Cell x: row)
            {
                if (x.getContent()==0)
                    return false;
            }
        }
        return true;
    }




    /**
     * Checks if given sudoku grid is solvable.
     * Based on backtracking algorithm, recursive brute force function, giving answer to question "Is it still solvable?".
     * @param board Given int grid of sudoku nums.
     * @return Returns true if sudoku is valid.
     */
    boolean isSolvable(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    continue;
                }
                for (int k = 1; k <= 9; k++) {
                    if (isNumberCorrect(i, j, board, k,false)) {
                        board[i][j] = k;
                        if (isSolvable(board)) {
                            return true;
                        }
                        board[i][j] = 0;
                    }
                }
                return false;
            }
        }
        return true; //return true if all cells are checked
    }
}
