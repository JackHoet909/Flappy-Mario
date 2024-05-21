/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication15;


import javax.swing.*;
/**
 *
 * @author jackh
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //dimensions of our window
        int boardWidth = 474;
        int boardHeight = 266;
        
        JFrame frame = new JFrame("Flappy Mario");
        frame.setLocationRelativeTo(null); //places the windown at the censor
        // of the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boardWidth, boardHeight);
        
        FlappyMario flappyMario = new FlappyMario();
        frame.add(flappyMario);
        //adds flappy mario to the window
        frame.pack(); //title bar lines up with the background
        flappyMario.requestFocus();
        frame.setVisible(true);

        
       
    }
    
}
