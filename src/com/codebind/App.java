package com.codebind;

import ViewModel.MyButton;
import model.Labyrinth;
import model.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame{

    private JButton solveButton = new JButton("solve");
    private JButton clearButton = new JButton("clear");
    //private GridLayout mazeLayout = new GridLayout(0,Point.SIZE*2+1);

    private Labyrinth labyrinth = new Labyrinth();

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
        mazePanel.setLayout(new GridBagLayout());//mazeLayout);

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
                Color color = labyrinth.getValue(i,j)? Color.WHITE : Color.BLACK;
                if(i%2==0 && j%2==0)  color = Color.gray;
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
                JOptionPane.showMessageDialog(null, "TODO solve");
            }

        });

        clearButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(null, "TODO clear");
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
        App frame = new App("GridLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
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
