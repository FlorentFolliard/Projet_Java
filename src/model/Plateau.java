package model;

public class Plateau {
    public static final int TAILLE = 10;

    private final Case[][] grille;

    public Plateau() {
        grille = new Case[TAILLE][TAILLE];
        initialiserPlateau();
    }

    private void initialiserPlateau() {
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                grille[x][y] = new Case(x, y);

                if ((x + y) % 2 != 0) {
                    if (y < 4) {
                        grille[x][y].setPiece(new Pion(Piece.NOIR, x, y));
                    } else if (y > 5) {
                        grille[x][y].setPiece(new Pion(Piece.BLANC, x, y));
                    }
                }
            }
        }
    }

    public Case getCase(int x, int y) {
        if (estDansLimites(x, y)) {
            return grille[x][y];
        }
        return null;
    }

    public boolean estDansLimites(int x, int y) {
        return x >= 0 && x < TAILLE && y >= 0 && y < TAILLE;
    }

    public int getTaille() {
        return TAILLE;
    }

    public boolean deplacerPiece(int sourceX, int sourceY, int cibleX, int cibleY) {
        if (!estDansLimites(sourceX, sourceY) || !estDansLimites(cibleX, cibleY)) {
            return false;
        }

        Case caseSource = getCase(sourceX, sourceY);
        Case caseCible = getCase(cibleX, cibleY);
        if (!caseSource.estOccupee() || caseCible.estOccupee()) {
            return false;
        }

        Piece piece = caseSource.getPiece();
        boolean estCapture = piece.estCaptureValide(cibleX, cibleY, this);
        boolean estDeplacementSimple = piece.estDeplacementSimpleValide(cibleX, cibleY, this);
        if (!estCapture && !estDeplacementSimple) {
            return false;
        }

        if (estCapture) {
            supprimerPieceCapturee(piece, sourceX, sourceY, cibleX, cibleY);
        }

        caseSource.setPiece(null);
        caseCible.setPiece(piece);
        piece.setPosition(cibleX, cibleY);
        return true;
    }

    private void supprimerPieceCapturee(Piece piece, int sourceX, int sourceY, int cibleX, int cibleY) {
        if (piece instanceof Pion) {
            int diffX = cibleX - sourceX;
            int diffY = cibleY - sourceY;
            int milieuX = sourceX + diffX / 2;
            int milieuY = sourceY + diffY / 2;
            getCase(milieuX, milieuY).setPiece(null);
            return;
        }

        int pasX = Integer.signum(cibleX - sourceX);
        int pasY = Integer.signum(cibleY - sourceY);
        int x = sourceX + pasX;
        int y = sourceY + pasY;

        while (x != cibleX && y != cibleY) {
            Piece pieceRencontree = getCase(x, y).getPiece();
            if (pieceRencontree != null && !pieceRencontree.getCouleur().equals(piece.getCouleur())) {
                getCase(x, y).setPiece(null);
                return;
            }
            x += pasX;
            y += pasY;
        }
    }

    public boolean promouvoirPieceSiNecessaire(int x, int y) {
        Case caseCible = getCase(x, y);
        if (caseCible == null || !caseCible.estOccupee()) {
            return false;
        }

        Piece piece = caseCible.getPiece();
        if (!(piece instanceof Pion)) {
            return false;
        }

        if (piece.estBlanche() && piece.getY() == 0) {
            caseCible.setPiece(new Dame(piece.getCouleur(), piece.getX(), piece.getY()));
            return true;
        }

        if (piece.estNoire() && piece.getY() == TAILLE - 1) {
            caseCible.setPiece(new Dame(piece.getCouleur(), piece.getX(), piece.getY()));
            return true;
        }

        return false;
    }

    public int compterPieces(String couleur) {
        int compteur = 0;
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                Piece piece = grille[x][y].getPiece();
                if (piece != null && piece.getCouleur().equals(couleur)) {
                    compteur++;
                }
            }
        }
        return compteur;
    }

    public boolean piecePeutCapturer(Piece piece) {
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (piece.estCaptureValide(x, y, this)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean piecePeutSeDeplacer(Piece piece) {
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                if (piece.estDeplacementSimpleValide(x, y, this) || piece.estCaptureValide(x, y, this)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean joueurPossedeCapture(String couleur) {
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                Piece piece = grille[x][y].getPiece();
                if (piece != null && piece.getCouleur().equals(couleur) && piecePeutCapturer(piece)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean joueurPeutJouer(String couleur) {
        boolean captureDisponible = joueurPossedeCapture(couleur);
        for (int x = 0; x < TAILLE; x++) {
            for (int y = 0; y < TAILLE; y++) {
                Piece piece = grille[x][y].getPiece();
                if (piece == null || !piece.getCouleur().equals(couleur)) {
                    continue;
                }

                if (captureDisponible && piecePeutCapturer(piece)) {
                    return true;
                }

                if (!captureDisponible && piecePeutSeDeplacer(piece)) {
                    return true;
                }
            }
        }
        return false;
    }
}
