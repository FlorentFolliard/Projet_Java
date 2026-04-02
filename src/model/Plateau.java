package model;

public class Plateau {
    private Case[][] grille;
    private final int TAILLE = 10;

    public Plateau() {
        grille = new Case[TAILLE][TAILLE];
        initialiserPlateau();
    }

    private void initialiserPlateau() {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                grille[i][j] = new Case(i, j);

                // Placement initial des pions (sur les cases sombres uniquement)
                if ((i + j) % 2 != 0) {
                    if (j < 4) {
                        grille[i][j].setPiece(new Pion("Noir", i, j));
                    } else if (j > 5) {
                        grille[i][j].setPiece(new Pion("Blanc", i, j));
                    }
                }
            }
        }
    }

    public Case getCase(int x, int y) {
        if (x >= 0 && x < TAILLE && y >= 0 && y < TAILLE) {
            return grille[x][y];
        }
        return null;
    }
}