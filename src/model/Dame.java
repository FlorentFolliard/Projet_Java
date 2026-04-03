package model;

public class Dame extends Piece {
    public Dame(String couleur, int x, int y) {
        super(couleur, x, y);
    }

    @Override
    public boolean estDeplacementSimpleValide(int cibleX, int cibleY, Plateau plateau) {
        if (!plateau.estDansLimites(cibleX, cibleY) || plateau.getCase(cibleX, cibleY).estOccupee()) {
            return false;
        }

        int diffX = cibleX - getX();
        int diffY = cibleY - getY();
        if (Math.abs(diffX) != Math.abs(diffY) || diffX == 0) {
            return false;
        }

        int pasX = Integer.signum(diffX);
        int pasY = Integer.signum(diffY);
        int x = getX() + pasX;
        int y = getY() + pasY;

        while (x != cibleX && y != cibleY) {
            Piece piece = plateau.getCase(x, y).getPiece();
            if (piece != null) {
                return false;
            }
            x += pasX;
            y += pasY;
        }

        return true;
    }

    @Override
    public boolean estCaptureValide(int cibleX, int cibleY, Plateau plateau) {
        if (!plateau.estDansLimites(cibleX, cibleY) || plateau.getCase(cibleX, cibleY).estOccupee()) {
            return false;
        }

        int diffX = cibleX - getX();
        int diffY = cibleY - getY();
        if (Math.abs(diffX) != Math.abs(diffY) || diffX == 0) {
            return false;
        }

        int pasX = Integer.signum(diffX);
        int pasY = Integer.signum(diffY);
        int x = getX() + pasX;
        int y = getY() + pasY;
        int adversairesRencontres = 0;

        while (x != cibleX && y != cibleY) {
            Piece piece = plateau.getCase(x, y).getPiece();
            if (piece != null) {
                if (piece.getCouleur().equals(getCouleur())) {
                    return false;
                }
                adversairesRencontres++;
                if (adversairesRencontres > 1) {
                    return false;
                }
            }
            x += pasX;
            y += pasY;
        }

        if (adversairesRencontres != 1) {
            return false;
        }

        return true;
    }

    @Override
    public String getSymbole() {
        return estBlanche() ? "B" : "N";
    }
}
