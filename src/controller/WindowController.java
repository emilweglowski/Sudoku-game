package controller;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import model.Sudoku;

public class WindowController {
	
	int sudokuTableSize = 9;
	int rows = sudokuTableSize;
	int cols = sudokuTableSize;
	int[][] currentGameTable = new int[rows][cols];
	Font cellFont = new Font("SansSerif", Font.BOLD, 25);
	Color colorGray = new Color(230, 230, 230);
	JFormattedTextField[][] cellsTable = new JFormattedTextField[rows][cols];
	
	public JFormattedTextField[][] getCellsTable() {
		return this.cellsTable;
	}
	
	public int getSudokuSize() {
		return this.sudokuTableSize;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return this.cols;
	}
	
	public String getInfo() {
		String info = "Sudoku is a 9x9 cells table which finally has to be filled with numbers ranging from 1 to 9.\r\n\r\n"
				+ "The sudoku table consists of 9 smaller 3x3 squares (visibly marked with thicker border)\r\n"
				+ "with 9 cells each, what gives 81 cells in general.\r\n"
				+ "Every cell of sudoku has to be filled with numbers ranging from 1 to 9, so that none of them can be used twice\r\n"
				+ "for the row, column and 3x3 square it is in.\r\n\r\n"
				+ "To start your game just click on 'Start new game' button. Then choose level of difficulty. A new sudoku table\r\n"
				+ "filled with digits will show up, but a number of digits will be missing. The goal is to fill in the whole grid.\r\n"
				+ "Once you complete filling the sudoku, check your result by clicking 'Check your sudoku' button.";
		return info;
	}
	
	public void setNewSudoku(int nomd, JFormattedTextField cellsTable[][]) {
		clearTable();
		Sudoku sudoku = new Sudoku(sudokuTableSize, nomd);
		currentGameTable = sudoku.fillSudokuTable();
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				cellsTable[i][j].setText(String.valueOf(currentGameTable[i][j]));
				if ((cellsTable[i][j].getText()).matches("[1-9]")) {
					cellsTable[i][j].setEditable(false);
					cellsTable[i][j].setBackground(colorGray);
				}
			}
		}
	}
	
	public void clearTable() {
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				cellsTable[i][j].setText("");
				cellsTable[i][j].setEditable(true);
				cellsTable[i][j].setBackground(Color.WHITE);
			}
		}
	}
	
	public boolean checkIfFilled() {
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				if (currentGameTable[i][j] < 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean checkGame() {
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				if (!cellsTable[i][j].getText().equals(String.valueOf(Math.abs(currentGameTable[i][j])))) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void validateGame(JPanel panel) {
//		if (!checkIfFilled()) {
//			JOptionPane.showMessageDialog(panel, "Complete current game", "NOT YET", JOptionPane.INFORMATION_MESSAGE, null);
//		} else if (checkGame()) {
		if (checkGame()) {
			JOptionPane.showMessageDialog(panel, "Congratulations, you have filled sudoku correctly", "YOU WON", JOptionPane.INFORMATION_MESSAGE, null);
		} else {
			JOptionPane.showMessageDialog(panel, "Try once again", "YOU LOST", JOptionPane.INFORMATION_MESSAGE, null);
		}
	}
	
	public JFormattedTextField createInputCell(int i, int j, Font cellFont) {
		NumberFormat format = NumberFormat.getInstance();
		format.setMaximumFractionDigits(1);
		NumberFormatter formatter = new NumberFormatter(format) {
			@Override
			public Object stringToValue(String text) throws ParseException {
				if (text.length() == 0) {
					return null;
				}
				return super.stringToValue(text);
			}
		}; 
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(9);
		formatter.setAllowsInvalid(false);
		JFormattedTextField cellInput = new JFormattedTextField(formatter);
		cellInput.setFont(cellFont);
		cellInput.setHorizontalAlignment(JTextField.CENTER);
		createBorders(i, j, cellInput);
		return cellInput;
	}
	
	public void fillGridPanelWithCells(JPanel gridPanel) {
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				JFormattedTextField cellInput = createInputCell(i, j, cellFont);
				gridPanel.add(cellInput);
				cellsTable[i][j] = cellInput;
			}
		}
	}
	
	public JFormattedTextField createBorders(int i, int j, JFormattedTextField cellInput) {
		if (i==0) {
			if (j==0) {
				cellInput.setBorder(BorderFactory.createMatteBorder(3, 3, 1, 1, Color.BLACK));
			} else if (j==2 || j==5 || j==8) {
				cellInput.setBorder(BorderFactory.createMatteBorder(3, 1, 1, 3, Color.BLACK));
			} else {
				cellInput.setBorder(BorderFactory.createMatteBorder(3, 1, 1, 1, Color.BLACK));
			}
		}
		if (i==2 || i==5 || i==8) {
			if (j==0) {
				cellInput.setBorder(BorderFactory.createMatteBorder(1, 3, 3, 1, Color.BLACK));
			} else if (j==2 || j==5 || j==8) {
				cellInput.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.BLACK));
			} else {
				cellInput.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 1, Color.BLACK));
			}
		}
		if (i==1 || i==3 || i==4 || i==6 || i==7) {
			if (j==0) {
				cellInput.setBorder(BorderFactory.createMatteBorder(1, 3, 1, 1, Color.BLACK));
			} else if (j==2 || j==5 || j==8) {
				cellInput.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 3, Color.BLACK));
			} else {
				cellInput.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
			}
		}
		return cellInput;
	}
	
	
//	
//	public void validateGame(JPanel panel) {
//		if (!checkIfFilled()) {
//			JOptionPane.showMessageDialog(panel, "Complete current game", "NOT YET", JOptionPane.INFORMATION_MESSAGE, null);
//		} else if (checkGame()) {
//			JOptionPane.showMessageDialog(panel, "Congratulations, you have filled sudoku correctly", "YOU WON", JOptionPane.INFORMATION_MESSAGE, null);
//		} else {
//			JOptionPane.showMessageDialog(panel, "Try once again", "YOU LOST", JOptionPane.INFORMATION_MESSAGE, null);
//		}
//	}

}
