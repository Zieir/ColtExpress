# Colt Express Game Development in Java (MVC Architecture)
## Project Overview
This project aims to develop a digital version of the popular board game Colt Express using Java, following the Model-View-Controller (MVC) design pattern. In Colt Express, players take on the role of outlaws aboard a moving train, competing to gather the most loot while avoiding the sheriff and outsmarting their rivals.

## Objective
The main goal is to recreate the Colt Express gameplay experience in a well-structured and maintainable codebase using the MVC architecture. This approach ensures a clean separation between game logic (Model), user interface (View), and player interactions (Controller).

## Key Features
#### Turn-based gameplay:
Players will take turns executing actions in pre-determined rounds, focusing on strategic planning and sequencing.
#### Game logic and rules (Model):
Implements the core mechanics such as character movement, loot collection, shooting, and interaction with the sheriff, adhering strictly to the original game rules.
#### User interface (View):
A graphical interface that displays the game state, including the positions of players, loot, and other visual elements that reflect the game board.
#### Player interaction (Controller):
Manages user input, enabling players to select and perform actions each turn, interacting with the game logic through the Model.
## MVC Architecture
#### Model:
Encapsulates all the game data, rules, and logic. This layer controls the game state, ensuring all mechanics are handled correctly without reliance on the interface.
#### View:
Displays the game state to the player via a graphical interface, showing player positions, available actions, and the results of each turn.
#### Controller:
Handles user input, translating player decisions into actions that affect the game state by interacting with the Model and updating the View accordingly.
## Tasks
#### Model implementation:
Design and implement the core game logic, including all the rules and mechanics that govern gameplay.
#### View development:
Create a user-friendly graphical interface that visually represents the game’s current state.
#### Controller logic:
Develop input handling to allow players to interact with the game and control their character’s actions within the game flow.
