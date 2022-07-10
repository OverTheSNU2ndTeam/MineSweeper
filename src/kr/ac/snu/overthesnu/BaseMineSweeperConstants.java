package kr.ac.snu.overthesnu;

public class BaseMineSweeperConstants {

    static final int DEFAULT_WIDTH = 18;
    static final int DEFAULT_HEIGHT = 14;
    static final int DEFAULT_NUM_OF_MINES = 40;
    static final int FLAG_TILE = -10;
    static final int UNIDENTIFIED_TILE = -20;
    static final int EXPLODED_TILE = -30;
    static final int NUMBERED_TILE = -40;
    static final int[] DX = {1, 1, 1, 0, -1, -1, -1, 0};
    static final int[] DY = {1, 0, -1, -1, -1, 0, 1, 1};
}
