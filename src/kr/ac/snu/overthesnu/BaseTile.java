package kr.ac.snu.overthesnu;

abstract class BaseTile {

    /**
     * whether this tile is a flag
     *
     * @see #getFlag()
     */
    private boolean isFlag;

    /**
     * the state of this tile
     *
     * @see #getState()
     * @see #setState(int)
     */
    private int state;

    public BaseTile() {
        this.state = BaseMineSweeperConstants.UNIDENTIFIED_TILE;
        this.isFlag = false;
    }

    public void toggleFlag() {
        this.isFlag = !this.isFlag;
    }

    public boolean getFlag() {
        return this.isFlag;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    /**
     * @return whether this tile is a mine
     */
    abstract public boolean isMine();

    /**
     * Opens this tile
     */
    abstract public void open();
}
