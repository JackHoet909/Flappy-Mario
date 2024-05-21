FlappyMario Project

Overview

Flappy Mario is a Java-based game inspired by the popular Flappy Bird game. 
I used Java Swing for graphics rendering and implementing game mechanics such as gravity, collision detection, and dynamic scoring. 
The game features custom Mario-themed graphics and a leaderboard that tracks and displays player scores from top to bottom.


Features


Custom Graphics: Utilizes Mario-themed images for the background, bird, pipes, and other game elements.

Game Mechanics: Implements gravity, pipe generation, collision detection, and scoring.

Interactive Gameplay: Players control the bird using the spacebar to keep it afloat and navigate through gaps in the pipes.

Leaderboard: Tracks and displays player scores, updating and sorting them dynamically.

Restart and Leaderboard View: Players can restart the game or view the leaderboard after a game over.


Code Explanation


Class Structure


FlappyMario: The main class that extends JPanel and implements ActionListener and KeyListener.

Bird: A nested class representing the bird character with attributes for position, size, and image.

Pipe: A nested class representing the pipes with attributes for position, size, image, and a boolean to check if the bird has passed the pipe.


Game Dimensions


The game board has a width of 474 pixels and a height of 266 pixels.


Images


The game uses several images for the background, bird, pipes, game over screen, title, and creator credits. These images are loaded using the ImageIcon class.


Bird and Pipe Initialization


The bird's initial position and size are set, and its image is loaded.

Pipes are initialized with a starting position on the right side of the board and move leftward.


Game Mechanics


Gravity: The bird's downward movement is simulated by increasing its vertical velocity.

Pipe Generation: Pipes are generated at regular intervals with random vertical positions to create the game's challenge.

Collision Detection: The game checks for collisions between the bird and pipes or the edges of the game board.


Game Loop

Timers: Two timers control the game loop and pipe placement.

gameLoop: Runs the main game loop, updating positions and checking for collisions.

placePipesTimer: Adds new pipes at regular intervals.


Key Events

Spacebar: Starts the game and makes the bird jump.

L Key: After a game over, allows the player to input their name and updates the leaderboard.


Drawing

The paintComponent method handles drawing all game elements, including the background, bird, pipes, score, and game over screen.


Leaderboard

The leaderboard is a Map that stores player names and scores. It is updated and sorted dynamically to display the top scores.


Game Over and Restart


When the game is over, the game loop and pipe placement timer stop. Players can restart the game by pressing the spacebar or view the leaderboard by pressing the 'L' key.


Sorting the Leaderboard


The leaderboard is sorted by score in descending order using a custom comparator:

Collections.sort(list, (Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) -> (o2.getValue()).compareTo(o1.getValue()));

This comparator ensures that higher scores appear at the top of the leaderboard.


How to Run


Ensure you have Java installed on your machine.

Clone the repository and navigate to the project directory.

Compile the Java files using javac.

Run the game! :)
