public enum Direction {
    NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST;

    public Index next(Index index) {
        switch (this) {
            case NORTH:
                return new Index( index.getRow(), index.getColumn() - 1);
            case NORTH_EAST:
                return new Index(index.getRow() + 1, index.getColumn() - 1);
            case EAST:
                return new Index(index.getRow() + 1, index.getColumn());
            case SOUTH_EAST:
                return new Index(index.getRow() + 1, index.getColumn() + 1);
            case SOUTH:
                return new Index(index.getRow(), index.getColumn() + 1);
            case SOUTH_WEST:
                return new Index(index.getRow() - 1, index.getColumn() + 1);
            case WEST:
                return new Index(index.getRow() - 1, index.getColumn());
            case NORTH_WEST:
                return new Index(index.getRow() - 1, index.getColumn() - 1);
            default:
                return new Index(0,0);
        }
    }

}
