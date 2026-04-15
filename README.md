# Chess Game – Phase 1

## Overview
This project is a console-based chess game written in Java. It allows two players to play chess by entering moves through the terminal. The program includes a fully initialized board, piece movement, capturing, turn handling, and input validation.

---

## Features

### 1. Board Initialization
- 8x8 chessboard
- All pieces placed in correct starting positions
- Black pieces on top (rows 0–1)
- White pieces on bottom (rows 6–7)

### 2. Piece System
Each piece is implemented as its own class:
- Pawn
- Rook
- Knight
- Bishop
- Queen
- King

All pieces inherit from an abstract `Piece` class.

### 3. Board Display
- Board is printed in the console
- Columns labeled A–H
- Rows labeled 1–8
- Pieces displayed as:
    - White: WP, WR, WN, WB, WQ, WK
    - Black: BP, BR, BN, BB, BQ, BK

---

### 4. Input Parsing
- Users enter moves in format:
- - Program validates:
- Correct format
- Valid board positions (A–H, 1–8)

---

### 5. Turn-Based Gameplay
- Alternates between White and Black
- Only current player can move their pieces

---

### 6. Movement Rules
Basic movement rules implemented:

- Pawn:
- Moves forward 1 square
- Moves 2 squares from starting position
- Captures diagonally
- Rook:
- Horizontal and vertical movement
- Knight:
- L-shaped movement
- Bishop:
- Diagonal movement
- Queen:
- Combines rook and bishop movement
- King:
- Moves one square in any direction

---

### 7. Capturing
- Pieces can capture opponent pieces
- Captured pieces are removed from the board
- Cannot capture your own piece

---

### 8. End Condition
- Game ends when a King is captured
- Winner is printed in the console

---

### 9. Error Handling
The program handles:
- Invalid input format
- Out-of-bounds moves
- Moving from empty squares
- Moving opponent’s pieces
- Invalid piece movement
- Attempting to capture own piece

---

## Project Structure
