package kr.ac.snu.overthesnu;

import java.util.*;

public class BaseMineSweeper {

    /**
     * The width of the game window
     *
     * @see #getWidth()
     * @see #setWidth(int)
     */
    private int width;

    /**
     * The height of the game window
     *
     * @see #getHeight()
     * @see #setHeight(int)
     */
    private int height;

    /**
     * The number of mines in the game at the beginning
     *
     * @see #getNumOfMines()
     * @see #setNumOfMines(int)
     */
    private int numOfMines;

    /**
     * The number of mines not found at the time
     *
     * @see #getNumOfMinesNotFound()
     */
    private int numOfMinesNotFound;

    /**
     * The tiles of the game
     *
     * @see #getBaseTiles()
     * @see #getBaseTile(int, int)
     */
    private BaseTile[][] baseTiles;

    public BaseMineSweeper(){
        this.width = BaseMineSweeperConstants.DEFAULT_WIDTH;
        this.height = BaseMineSweeperConstants.DEFAULT_HEIGHT;
        this.numOfMines = BaseMineSweeperConstants.DEFAULT_NUM_OF_MINES;
        this.numOfMinesNotFound = BaseMineSweeperConstants.DEFAULT_NUM_OF_MINES;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setNumOfMines(int numOfMines) {
        this.numOfMines = numOfMines;
    }

    public int getNumOfMines() {
        return this.numOfMines;
    }

    public int getNumOfMinesNotFound() {
        return this.numOfMinesNotFound;
    }

    public BaseTile[][] getBaseTiles() {
        return this.baseTiles;
    }

    public BaseTile getBaseTile(int xPos, int yPos){
        return this.baseTiles[xPos][yPos];
    }

    /** Initializes the tiles */
    public void init() {
        this.baseTiles = new BaseTile[this.width][this.height];
    }

    /**
     * Starts the game
     * No mines are produced at the starting position
     * Mines are produced at the positions generated randomly
     *
     * @param xPos x position of the starting position
     * @param yPos y position of the starting position
     */
    public void startGame(int xPos, int yPos) {
        this.numOfMinesNotFound = this.numOfMines;
        Integer[] array = new Integer[this.width*this.height];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        List<Integer> list = Arrays.asList(array);
        Collections.shuffle(list);
        list.toArray(array);
        int cnt = 0;
        int idx = 0;
        while (cnt < this.numOfMines) {
            if (array[idx] != xPos + yPos * this.width) {
                this.baseTiles[array[idx] % this.width][array[idx] / this.width] = new BaseMine();
                idx++;
                cnt++;
            }
            else {
                this.baseTiles[array[idx] % this.width][array[idx] / this.width] = new BaseNumberTile();
                idx++;
            }
        }
        for (; idx < this.width * this.height; idx++) {
            this.baseTiles[array[idx] % this.width][array[idx] / this.width] = new BaseNumberTile();
        }
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (!this.baseTiles[i][j].isMine()) {
                    ((BaseNumberTile)this.baseTiles[i][j]).setNumber(this.count(i, j));
                }
            }
        }
        this.openTile(xPos, yPos);
    }

    /**
     * Counts the mines around the tile
     *
     * @param xPos x position
     * @param yPos y position
     *
     * @return the number of mines around the input position
     */
    public int count(int xPos, int yPos) {
        int cnt = 0;
        for (int i = 0; i < 8; i++) {
            if (0 <= xPos + BaseMineSweeperConstants.DX[i] && xPos + BaseMineSweeperConstants.DX[i] < this.width && 0 <= yPos + BaseMineSweeperConstants.DY[i] && yPos + BaseMineSweeperConstants.DY[i] < this.height) {
                if (this.baseTiles[xPos + BaseMineSweeperConstants.DX[i]][yPos + BaseMineSweeperConstants.DY[i]].isMine()) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    /**
     * Opens the tile
     *
     * @param xPos x position of the clicked tile
     * @param yPos y position of the clicked tile
     */
    public void openTile(int xPos, int yPos) {
        BaseTile baseTile = this.baseTiles[xPos][yPos];
        if (baseTile.getState() != BaseMineSweeperConstants.UNIDENTIFIED_TILE || baseTile.getFlag()) return;

        baseTile.open();
        if (!baseTile.isMine() && ((BaseNumberTile)baseTile).getNumber() == 0) {
            for (int i = 0; i < 8; i++) {
                if (0 <= xPos + BaseMineSweeperConstants.DX[i] && xPos + BaseMineSweeperConstants.DX[i] < this.width && 0 <= yPos + BaseMineSweeperConstants.DY[i] && yPos + BaseMineSweeperConstants.DY[i] < height) {
                    this.openTile(xPos + BaseMineSweeperConstants.DX[i], yPos + BaseMineSweeperConstants.DY[i]);
                }
            }
        }
    }

    /**
     * Toggles the flag
     *
     * @param xPos x position of clicked tile
     * @param yPos y position of clicked tile
     */
    public void toggleFlag(int xPos, int yPos) {
        if (this.baseTiles[xPos][yPos].getFlag()) {
            this.numOfMinesNotFound++;
        } else {
            this.numOfMinesNotFound--;
        }
        this.baseTiles[xPos][yPos].toggleFlag();
    }

    /**
     * Determine if the game is ended
     * @return true if game is ended; false otherwise
     */
    public boolean isEnded() {
        boolean undiscovered = false;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                BaseTile baseTile = this.baseTiles[i][j];
                if (baseTile.getState() == BaseMineSweeperConstants.EXPLODED_TILE) return true;
                if (!baseTile.isMine() && baseTile.getState() == BaseMineSweeperConstants.UNIDENTIFIED_TILE) undiscovered = true;
            }
        }
        return !undiscovered;
    }
}
