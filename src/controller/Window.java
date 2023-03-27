package controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.NumberFormatter;

import model.Sudoku;

public class Window extends JFrame{
	
	JFrame mainFrame;
	WindowController controller = new WindowController();

	public Window() {
		
		//CREATING CENTER PANEL - SUDOKU TABLE
		JPanel gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(controller.getRows(), controller.getCols()));
		gridPanel.setPreferredSize(new Dimension(350, 350));
		
		controller.fillGridPanelWithCells(gridPanel);
		
		JPanel centeredGrid = new JPanel(new GridBagLayout());
		centeredGrid.add(gridPanel);
		
		//CREATING TOP PANEL
		JPanel topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(500, 40));
		topPanel.setLayout(new FlowLayout());
		
		JButton startGameButton = new JButton("Start new game");
		startGameButton.setPreferredSize(new Dimension(130, 25));
		topPanel.add(startGameButton);
		startGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseLevelWindow();
			}
		});
		
		JButton infoButton = new JButton("How to play?");
		infoButton.setPreferredSize(new Dimension(130, 25));
		topPanel.add(infoButton);
		infoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				controller.getInfo();
				JOptionPane.showMessageDialog(panel, controller.getInfo(), "How to play?", JOptionPane.INFORMATION_MESSAGE, null);
			}
		});
		
		//CREATING BOTTOM PANEL
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		
		JButton checkGameButton = new JButton("Check your sudoku");
		checkGameButton.setPreferredSize(new Dimension(150, 25));
		bottomPanel.add(checkGameButton);
		checkGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				controller.validateGame(panel);
			}
		});
		
		//SETTING MAIN FRAME
		
		mainFrame = new JFrame("Sudoku");
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setPreferredSize(new Dimension(500, 500));
		mainFrame.setResizable(false);
		mainFrame.getContentPane().add(topPanel, BorderLayout.NORTH);
		mainFrame.getContentPane().add(centeredGrid, BorderLayout.CENTER);
		mainFrame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE); 
		mainFrame.pack();
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public void chooseLevelWindow() {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				JDialog choosingLevelFrame = new JDialog(mainFrame, "Sudoku", true);
								
				//CREATING TOP PANEL
				JPanel upperPanel = new JPanel();
				JLabel label = new JLabel("Choose level of difficulty:");
				upperPanel.add(label);
				
				//CREATING CENTER PANEL WITH BUTTONS
				JPanel centerPanel = new JPanel();
				centerPanel.setLayout(new FlowLayout());
								
				JButton easyLevelButton = new JButton("Easy");
				easyLevelButton.setPreferredSize(new Dimension(80, 25));
				centerPanel.add(easyLevelButton);
				easyLevelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int nomdEasy = 25;
						controller.setNewSudoku(nomdEasy, controller.getCellsTable());
						choosingLevelFrame.dispose();
					}
				});
				
				JButton mediumLevelButton = new JButton("Medium");
				mediumLevelButton.setPreferredSize(new Dimension(80, 25));
				centerPanel.add(mediumLevelButton);
				mediumLevelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int nomdMedium = 35;
						controller.setNewSudoku(nomdMedium, controller.getCellsTable());
						choosingLevelFrame.dispose();
					}
				});
				
				JButton difficultLevelButton = new JButton("Difficult");
				difficultLevelButton.setPreferredSize(new Dimension(80, 25));
				centerPanel.add(difficultLevelButton);
				difficultLevelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int nomdDifficult = 45;
						controller.setNewSudoku(nomdDifficult, controller.getCellsTable());
						choosingLevelFrame.dispose();
					}
				});
								
				//SETTING CHOOSING LEVEL WINDOW
				choosingLevelFrame.setLayout(new BorderLayout());
				choosingLevelFrame.setPreferredSize(new Dimension(300, 100));
				choosingLevelFrame.getContentPane().add(upperPanel, BorderLayout.NORTH);
				choosingLevelFrame.getContentPane().add(centerPanel, BorderLayout.CENTER);
				choosingLevelFrame.setAlwaysOnTop(true);
				choosingLevelFrame.setResizable(false);
				choosingLevelFrame.pack();
				choosingLevelFrame.setLocationRelativeTo(null);
				choosingLevelFrame.setVisible(true);
			}
		});
	}
	
	public static void main (String[] args) {
		SwingUtilities.invokeLater(Window::new);
	}
}
