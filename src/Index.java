public class Index {

    //hold a set of row and column
    private int row;
    private int column;


    public Index(int x, int y) {
        row = x;
        column = y;
    }

    public Index(Index index) {
        row = index.getRow();
        column = index.getColumn();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "row: " + row + " " + "column " + column;
    }
}
