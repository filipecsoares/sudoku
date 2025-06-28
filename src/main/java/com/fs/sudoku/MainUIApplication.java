package com.fs.sudoku;

import com.fs.sudoku.domain.Board;
import com.fs.sudoku.domain.Space;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class MainUIApplication extends JFrame {
    private final List<List<JTextField>> fields = new ArrayList<>();
    private final Board board;
    private final int size;
    private final JLabel statusLabel = new JLabel();

    public MainUIApplication(Board board) {
        this.board = board;
        this.size = board.getSpaces().size();
        setTitle("Sudoku Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createBoardPanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.SOUTH);
        setPreferredSize(new Dimension(700, 800)); // Make the main screen bigger
        pack();
        setLocationRelativeTo(null);
        updateStatus();
    }

    private JPanel createBoardPanel() {
        JPanel panel = new JPanel(new GridLayout(size, size));
        int squareSize = (int) Math.sqrt(size);
        for (int i = 0; i < size; i++) {
            List<JTextField> rowFields = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                JTextField field = new JTextField(2);
                Space space = board.getSpaces().get(i).get(j);
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setFont(field.getFont().deriveFont(Font.BOLD, 20f));
                // Set border for 3x3 squares
                int top = (i % squareSize == 0) ? 3 : 1;
                int left = (j % squareSize == 0) ? 3 : 1;
                int bottom = (i == size - 1) ? 3 : 1;
                int right = (j == size - 1) ? 3 : 1;
                field.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));
                if (space.isFixed()) {
                    field.setText(String.valueOf(space.getExpected()));
                    field.setEditable(false);
                    field.setBackground(Color.LIGHT_GRAY);
                } else {
                    field.setText("");
                }
                rowFields.add(field);
                panel.add(field);
            }
            fields.add(rowFields);
        }
        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        JButton checkButton = new JButton("Check");
        JButton resetButton = new JButton("Reset");
        checkButton.addActionListener((ActionEvent e) -> {
            updateBoardFromFields();
            updateStatus();
        });
        resetButton.addActionListener((ActionEvent e) -> {
            board.reset();
            updateFieldsFromBoard();
            updateStatus();
        });
        panel.add(checkButton);
        panel.add(resetButton);
        panel.add(statusLabel);
        return panel;
    }

    private void updateBoardFromFields() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Space space = board.getSpaces().get(i).get(j);
                JTextField field = fields.get(i).get(j);
                if (!space.isFixed()) {
                    String text = field.getText();
                    if (text.isEmpty()) {
                        board.clearValue(i, j);
                    } else {
                        try {
                            int value = Integer.parseInt(text);
                            board.changeValue(i, j, value);
                        } catch (NumberFormatException ignored) {
                            board.clearValue(i, j);
                        }
                    }
                    // After updating, set bold and color for incorrect
                    Integer val = space.getActual();
                    field.setFont(field.getFont().deriveFont(Font.BOLD, 20f));
                    if (val != null && val != space.getExpected()) {
                        field.setForeground(Color.RED);
                    } else {
                        field.setForeground(Color.BLACK);
                    }
                }
            }
        }
    }

    private void updateFieldsFromBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Space space = board.getSpaces().get(i).get(j);
                JTextField field = fields.get(i).get(j);
                if (!space.isFixed()) {
                    Integer val = space.getActual();
                    if (val == null) {
                        field.setText("");
                        field.setFont(field.getFont().deriveFont(Font.BOLD, 20f));
                        field.setForeground(Color.BLACK);
                    } else {
                        field.setText(String.valueOf(val));
                        field.setFont(field.getFont().deriveFont(Font.BOLD, 20f));
                        // Check if the value is different from expected, highlight in bold
                        if (val != space.getExpected()) {
                            field.setForeground(Color.RED);
                        } else {
                            field.setForeground(Color.BLACK);
                        }
                    }
                }
            }
        }
    }

    private void updateStatus() {
        if (board.gameIsFinished()) {
            statusLabel.setText("Congratulations! You solved it!");
        } else if (board.hasErrors()) {
            statusLabel.setText("There are errors in the board.");
        } else {
            statusLabel.setText("Keep going!");
        }
    }

    public static void main(String[] args) {
        int size = 9;
        List<List<Space>> spaces = new ArrayList<>();
        if (args.length == 0) {
            // Default board as per user prompt
            String[] defaultArgs = ("0,0;4,false 1,0;7,false 2,0;9,true 3,0;5,false 4,0;8,true 5,0;6,true 6,0;2,true 7,0;3,false 8,0;1,false " +
                    "0,1;1,false 1,1;3,true 2,1;5,false 3,1;4,false 4,1;7,true 5,1;2,false 6,1;8,false 7,1;9,true 8,1;6,true " +
                    "0,2;2,false 1,2;6,true 2,2;8,false 3,2;9,false 4,2;1,true 5,2;3,false 6,2;7,false 7,2;4,false 8,2;5,true " +
                    "0,3;5,true 1,3;1,false 2,3;3,true 3,3;7,false 4,3;6,false 5,3;4,false 6,3;9,false 7,3;8,true 8,3;2,false " +
                    "0,4;8,false 1,4;9,true 2,4;7,false 3,4;1,true 4,4;2,true 5,4;5,true 6,4;3,false 7,4;6,true 8,4;4,false " +
                    "0,5;6,false 1,5;4,true 2,5;2,false 3,5;3,false 4,5;9,false 5,5;8,false 6,5;1,true 7,5;5,false 8,5;7,true " +
                    "0,6;7,true 1,6;5,false 2,6;4,false 3,6;2,false 4,6;3,true 5,6;9,false 6,6;6,false 7,6;1,true 8,6;8,false " +
                    "0,7;9,true 1,7;8,true 2,7;1,false 3,7;6,false 4,7;4,true 5,7;7,false 6,7;5,false 7,7;2,true 8,7;3,false " +
                    "0,8;3,false 1,8;2,false 2,8;6,true 3,8;8,true 4,8;5,true 5,8;1,false 6,8;4,true 7,8;7,false 8,8;9,false").split(" ");
            for (int i = 0; i < size; i++) {
                List<Space> row = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    row.add(null); // placeholder
                }
                spaces.add(row);
            }
            for (String arg : defaultArgs) {
                String[] parts = arg.split(";");
                String[] pos = parts[0].split(",");
                int x = Integer.parseInt(pos[0]);
                int y = Integer.parseInt(pos[1]);
                String[] valueFixed = parts[1].split(",");
                int value = Integer.parseInt(valueFixed[0]);
                boolean fixed = Boolean.parseBoolean(valueFixed[1]);
                spaces.get(y).set(x, new Space(value, fixed));
            }
        } else {
            for (int i = 0; i < size; i++) {
                List<Space> row = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    row.add(new Space(1, false));
                }
                spaces.add(row);
            }
        }
        Board board = new Board(spaces);
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainUIApplication(board).setVisible(true);
        });
    }
}
