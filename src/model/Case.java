package model;

public class Case {
    private final int x;
    private final int y;
    private Piece piece;

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean estOccupee() {
        return piece != null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
