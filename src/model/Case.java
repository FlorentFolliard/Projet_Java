package model;

public class Case {
    private int x, y;
    private Piece piece; // null si la case est vide

    public Case(int x, int y) {
        this.x = x;
        this.y = y;
        this.piece = null;
    }

    public boolean estOccupee() {
        return piece != null;
    }

    public Piece getPiece() { return piece; }
    public void setPiece(Piece piece) { this.piece = piece; }
}