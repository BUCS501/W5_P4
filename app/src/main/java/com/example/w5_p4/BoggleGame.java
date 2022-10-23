package com.example.w5_p4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BoggleGame {

    private static final int BOARD_SIZE = 4;
    private static final int MIN_WORD_LENGTH = 4;
    private char[][] board;
    private boolean[][] visited;
    private HashSet<String> possibleWords;
    private HashSet<String> foundWords;
    private int[] lastLetter;
    private String currWord;
    private int gameScore;

    public BoggleGame() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        possibleWords = readDictionaryFile("./app/src/main/res/values/words.txt");
        foundWords = new HashSet<>();
        lastLetter = new int[] {-1, -1};
        currWord = "";
        gameScore = 0;
        randomizeBoard();
    }

    public char[][] getBoard() {
        return this.board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public boolean[][] getVisited() {
        return this.visited;
    }

    public void setVisited(boolean[][] visited) {
        this.visited = visited;
    }

    public HashSet<String> getPossibleWords() {
        return this.possibleWords;
    }

    public void setPossibleWords(HashSet<String> possibleWords) {
        this.possibleWords = possibleWords;
    }

    public HashSet<String> getFoundWords() {
        return this.foundWords;
    }

    public void setFoundWords(HashSet<String> foundWords) {
        this.foundWords = foundWords;
    }

    public int[] getLastLetter() {
        return this.lastLetter;
    }

    public void setLastLetter(int[] lastLetter) {
        this.lastLetter = lastLetter;
    }

    public String getCurrWord() {
        return this.currWord;
    }

    public void setCurrWord(String currWord) {
        this.currWord = currWord;
    }

    public int getGameScore() {
        return this.gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public boolean isValidMove(int row, int col) {
        // Checks if indices are out of bounds
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return false;
        }

        // Checks if the letter has already been visited
        if (visited[row][col]) {
            return false;
        }

        // Checks if this is the first guess of a new word
        if (lastLetter[0] == -1 && lastLetter[1] == -1) {
            return true;
        }

        // Checks if the letter is adjacent to the last letter
        if (Math.abs(row - lastLetter[0]) > 1 || Math.abs(col - lastLetter[1]) > 1) {
            return false;
        }
        return true;
    }

    public char addLetter(int row, int col) {
        // Checks if the move is valid
        if (!isValidMove(row, col)) {
            return ' ';
        }

        // Updates the last letter
        lastLetter[0] = row;
        lastLetter[1] = col;

        // Updates the current word
        currWord += board[row][col];

        // Marks the letter as visited
        visited[row][col] = true;

        return board[row][col];
    }

    public int scoreWord(String word) {
        int score = 0;
        int vowelCount = 0;
        boolean doubleScore = false;

        // Iterates through each letter to calculate the score
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (letter == 'S' || letter == 'Z' || letter == 'P' || letter == 'X' || letter == 'Q') {
                score++;
                doubleScore = true;
            } else if (letter == 'A' || letter == 'E' || letter == 'I' || letter == 'O' || letter == 'U') {
                vowelCount++;
                score += 2;
            } else {
                score++;
            }
        }

        // Doubles score if one of the necessary letters was used
        if (doubleScore) {
            score *= 2;
        }

        // Checks if word is missing vowels or is too short or has already been found or is not in the dictionary of words
        if (vowelCount < 2 || word.length() < MIN_WORD_LENGTH || foundWords.contains(word) || !possibleWords.contains(word)) {
            return -10;
        }
        
        return score;
    }

    public int submitWord() {
        int wordScore = scoreWord(currWord);
        if (wordScore > 0) {
            foundWords.add(currWord);
        }
        gameScore += wordScore;
        resetCurrWord();
        return wordScore;
    }

    public void resetCurrWord() {
        // Resets the visited array
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                visited[i][j] = false;
            }
        }

        // Resets the last letter
        lastLetter[0] = -1;
        lastLetter[1] = -1;

        // Resets the current word
        currWord = "";
    }

    public void resetGame() {
        // Resets the board
        randomizeBoard();

        // Resets the current word along with the visited array and the lastLetter indices
        resetCurrWord();

        // Resets the found words
        foundWords.clear();

        // Resets the game score
        gameScore = 0;
    }

    public char letterButton(int row, int col) {
        return addLetter(row, col);
    }

    public int submitButton() {
        return submitWord();
    }

    public void clearButton() {
        resetCurrWord();
    }

    public void newGameButton() {
        resetGame();
    }

    public void randomizeBoard() {
        // Iterates through the board assigning each letter a random character
        Random rand = new Random();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = (char) (rand.nextInt(26) + 'A');
            }
        }
    }

    public static HashSet<String> readDictionaryFile(String fileName) throws FileNotFoundException {
        // Initializes the HashSet to hold all unique words
        HashSet<String> wordSet = new HashSet<String>();
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()) {
            wordSet.add(scanner.nextLine());
        }
        return wordSet;
    }

    public void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}