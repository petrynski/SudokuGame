import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Main and the most important class of game.
 * Sudoku class collects all other classes and is used to manage everything to work properly.
 * This class has most important values saved. Nearly everything is static in this class.
 */
public class Sudoku {
    static final int Size = 9;
    /**
     * Table of Cells, on which user can play the game.
     * @see Cell
     */
    static Cell[][] grid = new Cell[Size][Size];
    /**
     * Copy of grid that is generated at the beginning of the game.
     */
    static int[][] generatedGrid;
    /**
     * Copy of the solved grid that is generated in the process of constructing game grid.
     */
    static int[][]  solutionGrid;
    /**
     * Mask for the game grid, which separates cells that user can change from game grid cells.
     */
    static boolean[][] gridMask = new boolean[Size][Size];
    /**
     * Main window of the game.
     */
    private static GUI gui;
    /**
     * Popup that shows up, when player solves the sudoku.
     */
    private static JFrame popupFrame;
    /**
     * Timer that is used for playtime counting.
     */
    static Timer timer;
    /**
     * In Sudoku class constructor all start values are set, and important objects generated.
     * Constructor sets values of all grids, starts timer and generates graphical interface for player.
     */
    static RankingManager writerToFile;

    public Sudoku() throws FileNotFoundException {
        GridGenerator generator = new GridGenerator(30);
        generatedGrid = generator.getReadyGrid();
        solutionGrid = generator.getSolvedGrid();
        for (int i = 0; i < Size; i++) {
            for (int j = 0; j < Size; j++) {
                grid[i][j] = new Cell(generatedGrid[i][j],i,j);
                gridMask[i][j] = (generatedGrid[i][j] != 0);
            }
        }
        generator.printSudoku();
        writerToFile = new RankingManager();
        timer = new Timer();
        Thread thread = new Thread(timer);
        thread.start();
        gui = new GUI();
    }

    public static void main(String[] args) throws IOException {
        File ranking = new File("ranking.txt");
        if(!ranking.exists())
            if (!ranking.createNewFile())
            {
                System.out.println("Error while creating file!");
                return;
            }
        new Sudoku();
    }

    /**
     * Method which purpose is to restart whole Sudoku app.
     */
    static void restart() throws FileNotFoundException {
        if(gui!= null)
            Sudoku.gui.dispose();
        if(popupFrame!=null) {
            Sudoku.popupFrame.dispose();
            Sudoku.popupFrame = null;
        }
        timer.stop();
        writerToFile.save();
         new Sudoku();
    }

    /**
     * Method which disposes of game window, and makes popup show.
     */
    static void finish() {
        if(popupFrame==null)
            popupFrame = new winPopup();
        gui.dispose();
    }
}
