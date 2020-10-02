/**
 * Class that's used to generate random sudoku grid.
 * Class produces random solved grid, and then takes given number of elements out generating random game grid.
 */
class GridGenerator {
    private int size, squareSize;
    private int[][] solvedGrid;
    private int[][] readyGrid;
    private int howManyMissing;

    /**
     * Constructor causes generation of random grid of given numbers missing.
     * @param nums Amount of numbers to be missing from game cell.
     */
    GridGenerator(int nums)
    {
        size = Sudoku.Size;
        squareSize = (int) Math.sqrt(Sudoku.Size);
        howManyMissing = nums;
        solvedGrid = new int[size][size];
        readyGrid = new int[size][size];
        generateGrid();
        removeKDigits();
    }

    /**
     * Generation of grid in two steps.
     * First step is to fill three diagonal squares (top-left, middle and low-right). That makes randomizing sudoku, because those squared do not impact each other.
     * Second step is to fill remaining six squares, which requires whole grid awareness.
     */
    private void generateGrid()
    {
        fillDiagonal();
        fillRemaining(0, squareSize);
    }

    /**
     * Filling 3 diagonal squares from top left to bottom-right.
     */
    private void fillDiagonal()
    {
        for (int i = 0; i<size; i=i+ squareSize)
            fillSquare(i, i);
    }

    /**
     * Filling square with random numbers of 1-9 with regard only for square
     * @param row Top square's cells row.
     * @param column The most left square's column.
     */
    private void fillSquare(int row, int column)
    {
        int num;
        for (int i = 0; i< squareSize; i++)
        {
            for (int j = 0; j< squareSize; j++)
            {
                do
                {
                    num = (int) (Math.random()*size+1);
                }
                while (isUsedInBox(row, column, num));

                solvedGrid[row+i][column+j] = num;
            }
        }
    }

    /**
     * Checking if given number has been already used in certain square.
     * @param rowStart Square top row's number.
     * @param colStart Square's most left column number.
     * @param num Number to be checked.
     * @return boolean true if is already used.
     */
    private boolean isUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i< squareSize; i++)
            for (int j = 0; j< squareSize; j++)
                if (solvedGrid[rowStart+i][colStart+j]==num)
                    return true;

        return false;
    }

    /**
     * Recursive function to fill whole grid with random numbers, but strict with game rules.
     * @see Validator Used to check number validity
     * @param i row number
     * @param j column number
     * @return true if end is reached.
     */
    private boolean fillRemaining(int i, int j) {
        if (j >= size && i < size - 1) {
            i = i + 1;
            j = 0;
        }
        if (i >= size && j >= size)
            return true;

        if (i < squareSize)
        {
            if (j < squareSize)
                j = squareSize;
        }
        else if (i < size - squareSize)
        {
            if (j == (i / squareSize) * squareSize)
                j = j + squareSize;
        }
        else
        {
            if (j == size - squareSize)
            {
                i = i + 1;
                j = 0;
                if (i >= size)
                    return true;
            }
        }

        for (int num = 1; num<=size; num++)
        {
            Validator valid = new Validator();
            if (valid.isNumberCorrect(i,j,solvedGrid,num,false))
            {
                solvedGrid[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                solvedGrid[i][j] = 0;
            }
        }
        return false;
    }

    /**
     * Used to remove numbers from random cell.
     */
    private void removeKDigits()
    {
        int count = howManyMissing;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                readyGrid[i][j] = solvedGrid[i][j];
            }
        }
        while (count != 0)
        {
            int cellId = (int)(Math.random()*size*size+1);

            int i = (cellId/size);
            int j = cellId%size;
            if (i == size)
                i = i - 1;

            if (j == size)
                j = j - 1;
            if (readyGrid[i][j] != 0)
            {
                count--;
                readyGrid[i][j] = 0;
            }
        }
    }

    int[][] getReadyGrid() {
        return readyGrid;
    }

    int[][] getSolvedGrid() {
        return solvedGrid;
    }

    void printSudoku()
    {
        for (int i = 0; i<size; i++)
        {
            for (int j = 0; j<size; j++)
                System.out.print(readyGrid[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }
}
