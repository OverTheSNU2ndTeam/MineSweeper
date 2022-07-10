package kr.ac.snu.overthesnu;

public class BaseMine extends BaseTile {

    /**
     * {@inheritDoc}
     */
    public BaseMine() {
        super();
        this.setState(BaseMineSweeperConstants.UNIDENTIFIED_TILE);
    }

    @Override
    public boolean isMine() {
        return true;
    }

    @Override
    public void open() {
        this.setState(BaseMineSweeperConstants.EXPLODED_TILE);
    }
}
