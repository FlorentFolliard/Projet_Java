package model;

public class Pion extends Piece {
    public Pion(String couleur, int x, int y) {
        super(couleur, x, y);
    }

    @Override
    public boolean estDeplacementSimpleValide(int cibleX, int cibleY, Plateau plateau) {
        if (!plateau.estDansLimites(cibleX, cibleY) || plateau.getCase(cibleX, cibleY).estOccupee()) {
            return false;
        }

        int diffX = cibleX - getX();
        int diffY = cibleY - getY();
        int direction = estBlanche() ? -1 : 1;

        return Math.abs(diffX) == 1 && diffY == direction;
    }

    @Override
    public boolean estCaptureValide(int cibleX, int cibleY, Plateau plateau) {
        if (!plateau.estDansLimites(cibleX, cibleY) || plateau.getCase(cibleX, cibleY).estOccupee()) {
            return false;
        }

        int diffX = cibleX - getX();
        int diffY = cibleY - getY();
        if (Math.abs(diffX) != 2 || Math.abs(diffY) != 2) {
            return false;
        }

        int milieuX = getX() + diffX / 2;
        int milieuY = getY() + diffY / 2;
        Case caseMilieu = plateau.getCase(milieuX, milieuY);
        return caseMilieu != null
            && caseMilieu.estOccupee()
            && !caseMilieu.getPiece().getCouleur().equals(getCouleur());
    }

    @Override
    public String getSymbole() {
        return estBlanche() ? "b" : "n";
    }
}
