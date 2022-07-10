package kr.ac.snu.overthesnu;

public class BaseNumberTile extends BaseTile {
    /**
     * {@inheritDoc}
     */

    /**
     * the number of mines around this tile
     *
     * @see #getNumber()
     * @see #setNumber(int)
     */
    private int number;

    public BaseNumberTile() {
        super();
        this.setState(BaseMineSweeperConstants.UNIDENTIFIED_TILE);
        this.number = -1;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean isMine() {
        return false;
    }

    @Override
    public void open() {
        this.setState(BaseMineSweeperConstants.NUMBERED_TILE);
    }
}
