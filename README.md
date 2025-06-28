# Sudoku OOP Study Project

This project is a console-based Sudoku game developed in Java. It is designed as a study project to practice and demonstrate Object-Oriented Programming (OOP) concepts, such as encapsulation, abstraction, and modular design.

## Project Goals
- Practice OOP principles in Java
- Implement a playable Sudoku game in the console
- Structure code using classes and objects (e.g., Board, Space, PlayGame)

## How It Works
- The main class is `SudokuApplication`, which starts the application and launches the game loop.
- The `PlayGame` class manages the game logic and user interaction.
- The board is represented by the `Board` and `Space` classes.
- The user interacts with the game via a text-based menu, choosing options to start a new game, input numbers, remove numbers, view the board, check status, clear, or finish the game.

## How to Run the Project

### Prerequisites
- Java 21 or higher
- Maven (for building the project)

### Steps
1. **Clone the repository** (if not already):
   ```sh
   git clone <repository-url>
   cd sudoku
   ```
2. **Build the project:**
   ```sh
   ./mvnw clean package
   ```
3. **Run the application:**
   ```sh
   java -cp target/classes com.fs.sudoku.SudokuApplication
   ```
   - The game will start in the console and display a menu for interaction.

### How to Set the Initial Board (args)

When running the application, you can pass the initial board configuration as command-line arguments. Each argument should be in the format:

```
<row>,<col>;<value>,<fixed>
```
- `<row>`: Row index (0-8)
- `<col>`: Column index (0-8)
- `<value>`: Initial value for the cell (1-9)
- `<fixed>`: `true` if the value is fixed (cannot be changed by the player), `false` otherwise

**Example:**
To set cell (0,0) to 5 (fixed) and cell (0,1) to 3 (not fixed), run:
```sh
java -cp target/classes com.fs.sudoku.SudokuApplication "0,0;5,true" "0,1;3,false"
```
If no arguments are provided, the game will start with an empty or default board (depending on implementation).

### Notes
- The initial board configuration can be customized in the code. By default, the game starts with an empty or pre-defined board.
- This project is for educational purposes and is not intended for production use.

## License
This project is for study and personal use only.
