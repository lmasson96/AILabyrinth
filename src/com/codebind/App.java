package com.codebind;

import ViewModel.MyButton;
import model.Labyrinth;
import model.Point;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App extends JFrame{

    private JButton solveButton = new JButton("solve");
    private JButton clearButton = new JButton("clear");

    private Labyrinth labyrinth = new Labyrinth();
    private MyButton tileButtons [][] = new MyButton [Point.SIZE][Point.SIZE];

    private List<Point> path;

    public App(String name) {
        super(name);
        setResizable(false);
    }

    public void addComponentsToPane(final Container pane) {

        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(0,2));

        controls.add(solveButton);
        controls.add(clearButton);

        JPanel mazePanel = new JPanel();
        mazePanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        //natural height, maximum width
        c.fill = GridBagConstraints.HORIZONTAL;

        for(int i=0; i< Point.SIZE*2+1; ++i){
            for(int j=0; j< Point.SIZE*2+1; ++j){
                MyButton currentButton = new MyButton(i,j);
                currentButton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        labyrinth.changeValue(new Point(currentButton.getRow(), currentButton.getCol()));
                        currentButton.setBackground(currentButton.getBackground() == Color.BLACK ? Color.WHITE : Color.BLACK);
                    }
                });
                if(i== 0 || i== Point.SIZE*2 || j== 0 || j== Point.SIZE*2 || i%2==j%2){
                    currentButton.setEnabled(false);
                }
                if(new Point(i,j).isTile()){
                    tileButtons[i/2][j/2] = currentButton;
                }
                Color color = labyrinth.getValue(i,j)? Color.WHITE : Color.BLACK;
                if(i%2==0 && j%2==0)  color = Color.BLACK;
                currentButton.setBackground(color);
                c.gridy = i;
                c.gridx = j;
                c.ipady = i%2==0 ? 0 : 50;
                c.ipadx = j%2==0 ? -20 : 40;
                mazePanel.add(currentButton,c);
            }
        }


        solveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                  List<Point> solution = labyrinth.solve();

                if(solution == null){
                    JOptionPane.showMessageDialog(null, "Solver can't find a path");
                } else {
                    updatePath(solution);
                }
            }

        });

        clearButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                clearPath();
            }

        });

        pane.add(controls, BorderLayout.NORTH);
        pane.add(new JSeparator(), BorderLayout.CENTER);
        pane.add(mazePanel, BorderLayout.SOUTH);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method is invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        App frame = new App("Labyrinth solver with A*");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    private void updatePath(List<Point> list){
        if(this.path!=null)
            clearPath();
        this.path = list;
        for(Point p : this.path)
            tileButtons[p.x/2][p.y/2].setBackground(Color.YELLOW);
    }

    private void clearPath(){
        if(this.path == null) return;
        for(Point p : this.path)
            tileButtons[p.x/2][p.y/2].setBackground(Color.WHITE);
        this.path.clear();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
