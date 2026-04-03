package model;

public abstract class Piece {
    public static final String BLANC = "Blanc";
    public static final String NOIR = "Noir";

    private final String couleur;
    private int x;
    private int y;

    public Piece(String couleur, int x, int y) {
        this.couleur = couleur;
        this.x = x;
        this.y = y;
    }

    public boolean estDeplacementValide(int cibleX, int cibleY, Plateau plateau) {
        return estDeplacementSimpleValide(cibleX, cibleY, plateau)
            || estCaptureValide(cibleX, cibleY, plateau);
    }

    public abstract boolean estDeplacementSimpleValide(int cibleX, int cibleY, Plateau plateau);

    public abstract boolean estCaptureValide(int cibleX, int cibleY, Plateau plateau);

    public abstract String getSymbole();

    public String getCouleur() {
        return couleur;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean estBlanche() {
        return BLANC.equals(couleur);
    }

    public boolean estNoire() {
        return NOIR.equals(couleur);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
