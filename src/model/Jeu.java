package model;

public class Jeu {
    private final Plateau plateau;
    private final Joueur joueurBlanc;
    private final Joueur joueurNoir;
    private Joueur joueurCourant;
    private Piece pieceEnRafle;
    private String messageEtat;

    public Jeu() {
        this("Joueur 1", "Joueur 2");
    }

    public Jeu(String nomJoueurBlanc, String nomJoueurNoir) {
        plateau = new Plateau();
        joueurBlanc = new Joueur(nomJoueurBlanc, Piece.BLANC);
        joueurNoir = new Joueur(nomJoueurNoir, Piece.NOIR);
        joueurCourant = joueurBlanc;
        pieceEnRafle = null;
        messageEtat = "La partie commence. C'est au tour de " + joueurCourant.getNom() + ".";
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Joueur getJoueurCourant() {
        return joueurCourant;
    }

    public String getNomJoueurBlanc() {
        return joueurBlanc.getNom();
    }

    public String getNomJoueurNoir() {
        return joueurNoir.getNom();
    }

    public String getMessageEtat() {
        return messageEtat;
    }

    public boolean doitContinuerCapture() {
        return pieceEnRafle != null;
    }

    public int getXPieceObligatoire() {
        return pieceEnRafle == null ? -1 : pieceEnRafle.getX();
    }

    public int getYPieceObligatoire() {
        return pieceEnRafle == null ? -1 : pieceEnRafle.getY();
    }

    public boolean peutSelectionnerPiece(int x, int y) {
        Case caseSelectionnee = plateau.getCase(x, y);
        if (caseSelectionnee == null || !caseSelectionnee.estOccupee()) {
            return false;
        }

        Piece piece = caseSelectionnee.getPiece();
        if (!piece.getCouleur().equals(joueurCourant.getCouleur())) {
            return false;
        }

        if (pieceEnRafle != null) {
            return piece == pieceEnRafle;
        }

        if (plateau.joueurPossedeCapture(joueurCourant.getCouleur())) {
            return plateau.piecePeutCapturer(piece);
        }

        return true;
    }

    public boolean pieceDoitCapturer(int x, int y) {
        Case caseSelectionnee = plateau.getCase(x, y);
        if (caseSelectionnee == null || !caseSelectionnee.estOccupee()) {
            return false;
        }

        Piece piece = caseSelectionnee.getPiece();
        if (!piece.getCouleur().equals(joueurCourant.getCouleur())) {
            return false;
        }

        if (pieceEnRafle != null) {
            return piece == pieceEnRafle;
        }

        return plateau.joueurPossedeCapture(joueurCourant.getCouleur()) && plateau.piecePeutCapturer(piece);
    }

    public boolean jouerTour(int sourceX, int sourceY, int cibleX, int cibleY) {
        Case caseSource = plateau.getCase(sourceX, sourceY);
        if (caseSource == null || !caseSource.estOccupee()) {
            messageEtat = "Aucune piece sur la case selectionnee.";
            return false;
        }

        Piece piece = caseSource.getPiece();
        if (!piece.getCouleur().equals(joueurCourant.getCouleur())) {
            messageEtat = "Cette piece appartient a l'autre joueur.";
            return false;
        }

        if (pieceEnRafle != null && piece != pieceEnRafle) {
            messageEtat = "Vous devez continuer la rafle avec la meme piece.";
            return false;
        }

        boolean captureObligatoire = pieceEnRafle != null || plateau.joueurPossedeCapture(joueurCourant.getCouleur());
        if (captureObligatoire && !piece.estCaptureValide(cibleX, cibleY, plateau)) {
            messageEtat = "Une capture est obligatoire.";
            return false;
        }

        if (!plateau.deplacerPiece(sourceX, sourceY, cibleX, cibleY)) {
            messageEtat = "Deplacement invalide.";
            return false;
        }

        Piece pieceDeplacee = plateau.getCase(cibleX, cibleY).getPiece();
        if (captureObligatoire && plateau.piecePeutCapturer(pieceDeplacee)) {
            pieceEnRafle = pieceDeplacee;
            messageEtat = "Capture reussie. Continuez la rafle avec la meme piece.";
            return true;
        }

        pieceEnRafle = null;
        boolean promotion = plateau.promouvoirPieceSiNecessaire(cibleX, cibleY);

        if (estPartieTerminee()) {
            Joueur gagnant = getGagnant();
            messageEtat = gagnant == null ? "Partie terminee." : "Victoire de " + gagnant.getNom() + " !";
            return true;
        }

        changerJoueur();
        if (promotion) {
            messageEtat = "Promotion en dame. C'est au tour de " + joueurCourant.getNom() + ".";
        } else {
            messageEtat = "C'est au tour de " + joueurCourant.getNom() + ".";
        }
        return true;
    }

    public boolean estPartieTerminee() {
        return plateau.compterPieces(Piece.BLANC) == 0
            || plateau.compterPieces(Piece.NOIR) == 0
            || !plateau.joueurPeutJouer(Piece.BLANC)
            || !plateau.joueurPeutJouer(Piece.NOIR);
    }

    public Joueur getGagnant() {
        if (!estPartieTerminee()) {
            return null;
        }

        if (plateau.compterPieces(Piece.NOIR) == 0 || !plateau.joueurPeutJouer(Piece.NOIR)) {
            return joueurBlanc;
        }

        if (plateau.compterPieces(Piece.BLANC) == 0 || !plateau.joueurPeutJouer(Piece.BLANC)) {
            return joueurNoir;
        }

        return null;
    }

    private void changerJoueur() {
        joueurCourant = joueurCourant == joueurBlanc ? joueurNoir : joueurBlanc;
    }
}
