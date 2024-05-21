/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication15;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; //stores all the pipes in the game
import java.util.Map;
import java.util.Random;
import javax.swing.*; //import this to bring in our JPanel
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * FlappyMario class represents the Flappy Mario game.
 * It handles the game logic, rendering, and user interactions.
 * The class extends JPanel and implements ActionListener and KeyListener interfaces.
 * 
 * @author Jack Hoeting
 */
public class FlappyMario extends JPanel implements ActionListener, KeyListener {
    //dimensions
    int boardWidth = 474;
    int boardHeight = 266;
    
    // unsorted leaderboard
    private Map<String, Integer> unsortedLeaderboard = new HashMap<>();
    
    //images
    Image backgroundImg;
    Image MariobirdImg;
    Image topPipeImg;
    Image bottomPipeImg;
    Image GameOverImg;
    Image pressSpaceImg;
    Image titleImg;
    Image gameCreatorImg;
    
    //bird
    int birdX = boardWidth / 8; // one eighth of the left side of screen
    int birdY = boardHeight / 6; // one sixth of the board height of the screen
    int birdWidth = 35;
    int birdHeight = 30;
    

    /**
     * Lets the user start the game by interacting with the space bar
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameStarted && e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            spacePressed = true;
            gameStarted = true;
            startGame();
            velocityY = -9; //if you hit space, the bird goes up 9 pixels
            
            
            if (gameOver){
                // reset the conditions since we restart the game
                previousScore = score; // Store the current score as previous score
                bird.y = birdY;
                pipes.clear();
                gameOver = false;
                velocityY = 0;
                score = 0;
                gameLoop.start();
                placePipesTimer.start();
            }
        }
        
        else if (gameOver && e.getKeyCode() == KeyEvent.VK_L) 
        {
            LPressed = true;
            String playerName = JOptionPane.showInputDialog("Enter your name:");
            if (playerName != null && !playerName.trim().isEmpty()) 
            {
                unsortedLeaderboard.put(playerName, (int) score);
                if (score < previousScore)
                {
                    sortByValue(unsortedLeaderboard);
                }
            }
            showLeaderBoard = true;
            repaint();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    /**
    * Sorts a given leaderboard map by its values in descending order.
    * The leaderboard is represented as a map where the keys are player names
    * and the values are their respective scores.
    *
    * @param unsortLeaderboard The unsorted leaderboard map.
    * @return A sorted map with entries sorted by scores in descending order.
    */
    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortLeaderboard) 
    {
        //Convert Map to List of Map
        List<Map.Entry<String, Integer>> list;
        list = new LinkedList<>(unsortLeaderboard.entrySet());

        // Our high scores come first in the rank
        Collections.sort(list, (Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> (o2.getValue()).compareTo(o1.getValue()));

        //Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<String, Integer> sortedLeaderboard = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) 
        {
            sortedLeaderboard.put(entry.getKey(), entry.getValue()); //sorts the name and the score
        }
        return sortedLeaderboard;
    } 
    
    /**
    * Represents the bird in the Flappy Mario game.
    */
    public class Bird
    {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;
       
        /**
        * Constructs a Bird object with the specified image.
        *
        * @param img The image representing the bird.
        */
        public Bird(Image img)
        {
            this.img = img;
        }
    }
    
    //Pipes
    int pipeX = boardWidth; //x position of pipe will be on the right side of our board
    int pipeY = 0;
    int pipeWidth = 64; //64 pixels
    int pipeHeight = 252; //252 pixels
    
    /**
    * Represents a pipe in the Flappy Mario game.
    */
    public class Pipe
    {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false; //checks if Flappy Mario passes pipe
        
        /**
        * Constructs a Pipe object with the specified image.
        *
        * @param img The image representing the pipe.
        */
        public Pipe(Image img)
        {
            this.img = img;
        }
    }
    
    // game logic
    Bird bird;
    int velocityX = -6; //move pipe to the left
    int velocityY = 0;
    int gravity = 1;
    
    ArrayList<Pipe> pipes;
    Random random = new Random();
    
    Timer gameLoop;
    Timer placePipesTimer;
    
    boolean gameOver = false;
    boolean gameStarted = false;
    double score = 0;
    private double previousScore = 0; // To store the previous score
    
    private boolean spacePressed = false; //remove the title once the space is pressed
    private boolean LPressed = false;
    private boolean showLeaderBoard = false;
    
    /**
     * Constructor for FlappyMario class.
     * Initializes the game components, loads images, and sets up timers.
     */
    public FlappyMario()
    {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true); //JPanel takes our key events
        addKeyListener(this); //checks our key panels
        
        //load images
        backgroundImg = new ImageIcon(getClass().getResource("./marioBackground (1).png")).getImage();
        MariobirdImg = new ImageIcon(getClass().getResource("./flappyMario.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./uppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./downpipe.png")).getImage();
        GameOverImg = new ImageIcon(getClass().getResource("./game_over.png")).getImage();
        pressSpaceImg = new ImageIcon(getClass().getResource("./space.png")).getImage();
        titleImg = new ImageIcon(getClass().getResource("./FlappyMarioTitle.png")).getImage();
        gameCreatorImg = new ImageIcon(getClass().getResource("./createdJack.png")).getImage();
        
        //bird object
        bird = new Bird(MariobirdImg);
        
        //pipe object
        pipes = new ArrayList<Pipe>();
        
        //place timer of the pipes
        placePipesTimer = new Timer(1500, new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e)
            {
                placePipes(); 
            }
        }); //calls an action every 1.5 seconds
        
        
        
        //game timer
        gameLoop = new Timer(16, this); //16.6 frames per second
        
    }
    
    /**
     * Paints the game components on the screen.
     * @param g The Graphics object used for drawing.
     */
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); //invokes function from JPanel
        draw(g);
    }
    
    
    /**
     * Places pipes at random positions on the screen.
     */
    public void placePipes()
    {
        // (between 0 and 1) * pipeHeight/2 -> (0-256)
        //128
        //0 - 128 - (0-256) -> 1/4 pipeHeight -> 3/4 pipeheight
        
        //Moving each pipe by a quarter of its height
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() *  
        (pipeHeight / 2)); //every pipe is shifted by a random value 0-256
        
        int openingSpace = boardHeight / 3; //between the two pipes is 88 pixels
        
        //bottom pipe
        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = randomPipeY;
        pipes.add(bottomPipe);
        
        //top pipe
        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = bottomPipe.y + pipeHeight + openingSpace;
        pipes.add(topPipe);
    }
    
    /**
     * Draws the game components on the screen.
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g)
    {
        //background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
        
        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
        
        
        if (!spacePressed) // the title will be removed once the user presses the space button
        {
            if (titleImg != null)
            {
                g.drawImage(titleImg, 0, 0, boardWidth, 180, null);
                g.drawImage(gameCreatorImg, 150, 175, 180, 50, null);
            }
        }
        
        //pipes 
        for (int i = 0; i < pipes.size(); i++)
        {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
        if (gameOver)
        {
            g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
            g.drawImage(GameOverImg, 140, 40, 190, 180, null);
            g.drawImage(pressSpaceImg, 150, 190, 180, 50, null);
            
            //score
            g.setColor(Color.black);
            Font customFont = new Font("Press Start 2P", Font.PLAIN, 32);
            g.setFont(customFont);
            g.drawString("Score: " + String.valueOf((int) score), 10, 35);
            if (LPressed)
            {
                displayLeaderboard(g); // Call displayLeaderboard to show leaderboard
            }
        }
    }
    
    
    /**
    * Displays the leaderboard on the screen.
    * @param g The Graphics object used for drawing.
    */
    public void displayLeaderboard(Graphics g) {
        // Set the color for the leaderboard text
        g.setColor(Color.black);
    
        // Create and set the font for the leaderboard text
        Font leaderboardFont = new Font("Press Start 2P", Font.PLAIN, 16);
        g.setFont(leaderboardFont);

        // Initial vertical position for the leaderboard text
        int y = 50;
    
        // Draw the leaderboard title
        g.drawString("Leaderboard:", 10, y);
        y += 20; // Move to the next line for player entries
    
        // Initialize the rank counter
        int rank = 1;
        
        Map<String, Integer> sortedLeaderboard = sortByValue(unsortedLeaderboard);

        
    
        // Iterate over the entries in the leaderboard map
        for (HashMap.Entry<String, Integer> entry : sortedLeaderboard.entrySet())    
        {
            // Draw the player's rank, name, and score
            g.drawString(rank + ". " + entry.getKey() + ": " + entry.getValue(), 10, y);
            y += 20; // Move to the next line for the next entry
            rank++; // Increment the rank
            
        
            // Show only the top 10 scores
            if (rank > 10) break;
        }
    
   

    }
    
    /**
     * Starts the game by starting the timers for placing pipes and the game loop.
     */
    public void startGame()
    {
        placePipesTimer.start(); // Start placing pipes
        gameLoop.start(); // Start the game loop
    }
    
    
    /**
     * Checks for collision between the bird and a pipe.
     * @param a The bird.
     * @param b The pipe.
     * @return True if there is a collision, false otherwise.
     */
    public boolean collision(Bird a, Pipe b)
    {
        return a.x < b.x + b.width && // bird's left corner fails to reach pipe's top right corner
                a.x + a.width > b.x &&// bird's top right corner passes pipe's top left corner
                a.y < b.y + b.height &&// bird's top left corner fails to reach pipe's bottom left corner
                a.y + a.height > b.y;//bird's bottom left corner passes pipe's top left corner 
    }
    
    
    /**
     * Moves the bird and pipes, and checks for collisions.
     */
    public void move()
    {
        //bird
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0); //bird is not jumping off screen
        
        //pipes
        for (int i = 0; i < pipes.size(); i++)
        {
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;
            
            // if we pass the pipe, increment the score
            if (!pipe.passed && bird.x > pipe.x + pipe.width)
            {
                pipe.passed = true;
                score+= 0.5; //there are two pipes the bird is passing
            }
            
            if (collision(bird, pipe))
            {
                gameOver = true;   
            }
        }
        
        //game ends if bird falls off the map
        if (bird.y > boardHeight) 
        {
            gameOver = true;
        }
    }
    /**
     * The action performed during each game loop iteration.
     * @param e The ActionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (gameStarted = false)
        {
            // Handle actions if game hasn't started yet
        }
        move();
        repaint(); //calls the paint component
        if (gameOver)
        {
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }
}
