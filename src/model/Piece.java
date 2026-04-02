package model;

public abstract class Piece {
    private String couleur; // "Blanc" ou "Noir"
    private int x, y;

    public Piece(String couleur, int x, int y) {
        this.couleur = couleur;
        this.x = x;
        this.y = y;
    }

    // Méthode abstraite : chaque type de pièce se déplace différemment
    public abstract boolean estDeplacementValide(int cibleX, int cibleY, Plateau plateau);

    // Getters et Setters (Encapsulation)
    public String getCouleur() { return couleur; }
    public int getX() { return x; }
    public int getY() { return y; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}