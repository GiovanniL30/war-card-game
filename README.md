# War Card Game

A console-based implementation of the classic War card game, built with Java 25. This project supports 2 to 8 players, riffle shuffling, automated gameplay, and persistent game history.

## How It Works

### Setup

1. **Deck Loading** -- A standard 52-card deck is read from a text file in the `files/` directory. You can choose from available deck files by number or filename.
2. **Shuffling** -- Choose how many times to shuffle the deck (1 to 1000). The deck is shuffled using a riffle shuffle: it is split in half and interleaved card by card.
3. **Players** -- Select the number of players (2 to 8).
4. **Dealing** -- The shuffled deck is flipped, then all 52 cards are dealt round-robin to each player.

### Playing a Round

- Each round, every player draws their top card.
- All drawn cards are placed into a shared pile.
- Cards are compared by rank first (2 is lowest, Ace is highest). In case of a tie, the suit decides the winner: Diamonds > Hearts > Spades > Clubs.
- The player with the highest card wins the round and takes all played cards into their deck.
- Players who run out of cards are eliminated.

### Winning

The game continues until only one player remains. That player is declared the winner. The winner's deck is automatically saved to a file for future use.

### Session Tracking

After finishing a game, you can play again without restarting the program. When you quit, a session summary is displayed showing total games played, average rounds per game, and win counts per player.

## Features

- 2 to 8 player support
- Riffle shuffle with configurable number of passes
- Round-by-round or automatic play mode (type "auto" to skip manual round advancement)
- Player elimination when decks run out
- Deck validation (checks for 52 unique, correctly formatted cards)
- Winner's deck saved to file automatically after each game
- Session statistics tracking
- ASCII art title screen

## Requirements

- Java 25 or later

## Running the Game

Run the `Main` class. No external dependencies are required -- the project uses only the Java standard library.

### From the command line

```
javac src/Main.java
java -cp src Main
```

### From IntelliJ IDEA

Open the project in IntelliJ IDEA and run the `Main` class using the provided run configuration.

## Project Structure

```
src/
  Main.java              -- Entry point
  enums/
    Rank.java            -- Card rank enum (2 through Ace)
    Suit.java            -- Card suit enum (Clubs, Spades, Hearts, Diamonds)
  model/
    Card.java            -- Single card representation
    Deck.java            -- Card collection with shuffle and draw operations
    Player.java          -- Player with a name and personal deck
  game/
    Game.java            -- Core game loop logic
    GameFileManager.java -- File input/output for deck files
files/
  input.txt              -- Master sorted deck (52 cards)
  game2..gameN           -- Previously saved winning decks
docs/
  documentation.txt      -- Detailed game logic rules
  program_flow.txt       -- Step-by-step program flow
```

## Deck File Format

Deck files are plain text files containing 52 cards in `Suit-Rank` format, separated by commas. Suits are represented by single letters: `C` (Clubs), `S` (Spades), `H` (Hearts), `D` (Diamonds). Ranks use standard notation: `2` through `10`, `J`, `Q`, `K`, `A`.

Example: `D-A, H-K, S-Q, C-J, ...`