package model;

public class Pion extends Piece {
    public Pion(String couleur, int x, int y) {
        super(couleur, x, y);
    }

    @Override
    public boolean estDeplacementValide(int cibleX, int cibleY, Plateau plateau) {
        int diffX = Math.abs(cibleX - getX());
        int diffY = cibleY - getY();

        // Logique simplifiée : avance d'une case en diagonale
        // (À compléter avec la logique de capture et de direction selon la couleur)
        if (getCouleur().equals("Blanc")) {
            return diffX == 1 && diffY == -1; // Monte le plateau
        } else {
            return diffX == 1 && diffY == 1;  // Descend le plateau
        }
    }
}