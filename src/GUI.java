import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/**
 * Main graphical interface class, on which game window relies.
 *
 */
class GUI extends JFrame {
    private static final int SIZE = Sudoku.Size;
    private static final int SQUARE_SIZE = (int) Math.sqrt(Sudoku.Size);
    private static final int CELL_SIZE = 50;
    private static final int SUDOKU_SIZE = SIZE*CELL_SIZE;

    /**
     * Constructor generates JFrame with two JPanels - one for grid and second for user menu.
     * Contains code for buttons ActionListeners. Uses javax.swing Timer class (not to mistake for package Timer) for refreshing label text and package Timer for getting current playtime.
     */
    GUI()
    {
        JPanel sudokuPanel = new JPanel();
        sudokuPanel.setLayout(new GridLayout(SIZE,SIZE));

        for (int row = 0; row < SIZE; row++)
        {
            for (int column = 0; column < SIZE; column++)
            {
                sudokuPanel.add(Sudoku.grid[row][column]);
                GUI.setCellAttributes(Sudoku.grid[row][column], row, column);
            }
        }
        sudokuPanel.setPreferredSize(new Dimension(SUDOKU_SIZE,SUDOKU_SIZE));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());

        {
            JButton newGame = new JButton("NEW GAME");
            JButton solve = new JButton("SOLVE");
            ActionListener actSolve = e -> {
                for (int i = 0; i < Sudoku.Size; i++) {
                    for (int j = 0; j < Sudoku.Size; j++) {
                        if(Sudoku.grid[i][j].getContent() == 0)
                        {
                            Validator validator = new Validator();
                            int[][] grid = new int[Sudoku.Size][Sudoku.Size];
                            for (int k = 0; k < Sudoku.Size; k++) {
                                for (int l = 0; l < Sudoku.Size; l++) {
                                    grid[k][l] = Sudoku.grid[k][l].getContent();
                                }
                            }

                            for (int k = 1; k <= Sudoku.Size; k++) {
                                if(validator.isNumberCorrect(i,j,grid,k,false))
                                {
                                    grid[i][j]=k;
                                    if(validator.isSolvable(grid)) {
                                        Sudoku.grid[i][j].setContent(k);
                                        Sudoku.grid[i][j].setText(Integer.toString(k));
                                        return;
                                    }
                                    grid[i][j]=0;
                                }
                            }
                        }
                    }
                }
            };

            ActionListener actRestart = e -> {
                try {
                    Sudoku.restart();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            };

            solve.addActionListener(actSolve);
            newGame.addActionListener(actRestart);

            JLabel title = new JLabel("Sudoku!");
            title.setFont(new Font("Arial",Font.BOLD,35));
            JLabel time = new JLabel("Time: 0");
            time.setFont(new Font("Arial",Font.PLAIN,25));

            GridBagConstraints gbc = new GridBagConstraints();

            ActionListener refresh = e -> {
                time.setText("Time: " + Sudoku.timer.getTimeDiff());
                time.repaint();
            };

            javax.swing.Timer refreshTimer = new javax.swing.Timer(1000,refresh);
            refreshTimer.start();
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.insets = new Insets(25,25,25,25);

            gbc.gridx = 0;
            gbc.gridy = 0;
            menuPanel.add(title, gbc);
            gbc.gridx = 0;
            gbc.gridy = 1;
            menuPanel.add(time, gbc);
            gbc.gridx = 0;
            gbc.gridy = 2;
            menuPanel.add(newGame,gbc);
            gbc.gridx = 0;
            gbc.gridy = 3;
            menuPanel.add(solve,gbc);
        }
        setLayout(new FlowLayout());
        add(sudokuPanel);
        add(menuPanel);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SUDOKU GAME");
        setResizable(false);
        setVisible(true);
    }

    /**
     * Method used to set Cell's borders and editability.
     * @param textField Cell to edit
     * @param row Cell's row number
     * @param column Cell's column number
     */
    private static void setCellAttributes(JTextField textField, int row, int column)
    {
        if(row % SQUARE_SIZE == 0 || column % SQUARE_SIZE == 0)
        {
            if(row % SQUARE_SIZE == 0 && column % SQUARE_SIZE == 0)
            {
                if(row == 0 && column == 0)
                    textField.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));

                else if(row == 0)
                    textField.setBorder(BorderFactory.createMatteBorder(1,4,1,1,Color.BLACK));

                else if(column==0)
                    textField.setBorder(BorderFactory.createMatteBorder(4,1,1,1,Color.BLACK));

                else
                    textField.setBorder(BorderFactory.createMatteBorder(4,4,1,1,Color.BLACK));

            }

            else if(row % SQUARE_SIZE == 0)
            {
                if(row == 0)
                    textField.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));

                else
                    textField.setBorder(BorderFactory.createMatteBorder(4,1,1,1,Color.BLACK));
            }

            else if(column % SQUARE_SIZE == 0)
            {
                if(column == 0)
                    textField.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
                else
                    textField.setBorder(BorderFactory.createMatteBorder(1,4,1,1,Color.BLACK));
            }
        }
        else
            textField.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));

        if(Sudoku.gridMask[row][column])
        {
            textField.setEditable(false);
            textField.setFont(new Font("Arial", Font.BOLD, 20));
        }
        else
        {
            textField.setFont(new Font("Arial", Font.PLAIN, 20));
            textField.setText("");
        }
        textField.setHorizontalAlignment(JTextField.CENTER);
    }
}
