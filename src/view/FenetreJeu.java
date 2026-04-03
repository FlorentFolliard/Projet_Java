package view;

import model.Case;
import model.Jeu;
import model.Piece;
import model.Plateau;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

public class FenetreJeu extends JFrame implements InterfaceGraphique {
    private static final Color CASE_CLAIRE = new Color(240, 217, 181);
    private static final Color CASE_SOMBRE = new Color(181, 136, 99);

    private final BoutonCase[][] boutons;
    private final JLabel statut;
    private Jeu jeu;
    private int selectionX = -1;
    private int selectionY = -1;

    public FenetreJeu() {
        super("Jeu de Dames");
        boutons = new BoutonCase[Plateau.TAILLE][Plateau.TAILLE];
        statut = new JLabel("Initialisation...", SwingConstants.CENTER);
        initialiserFenetre();
    }

    @Override
    public void afficher(Jeu jeu) {
        this.jeu = jeu;
        rafraichirPlateau();
        statut.setText(jeu.getMessageEtat());
        setVisible(true);
    }

    private void initialiserFenetre() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel plateauPanel = new JPanel(new GridLayout(Plateau.TAILLE, Plateau.TAILLE));
        JButton boutonAbandonner = new JButton("Abandonner");
        boutonAbandonner.setFocusPainted(false);
        boutonAbandonner.setBackground(new Color(178, 34, 34));
        boutonAbandonner.setForeground(Color.WHITE);
        boutonAbandonner.addActionListener(e -> abandonnerPartie());

        JPanel barreInferieure = new JPanel(new BorderLayout(8, 0));
        barreInferieure.add(statut, BorderLayout.CENTER);
        barreInferieure.add(boutonAbandonner, BorderLayout.EAST);

        for (int y = 0; y < Plateau.TAILLE; y++) {
            for (int x = 0; x < Plateau.TAILLE; x++) {
                BoutonCase bouton = new BoutonCase();
                bouton.setPreferredSize(new Dimension(64, 64));
                final int caseX = x;
                final int caseY = y;
                bouton.addActionListener(e -> gererClic(caseX, caseY));
                boutons[x][y] = bouton;
                plateauPanel.add(bouton);
            }
        }

        add(plateauPanel, BorderLayout.CENTER);
        add(barreInferieure, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
    }

    private void gererClic(int x, int y) {
        if (jeu == null || jeu.estPartieTerminee()) {
            return;
        }

        if (selectionX == -1) {
            if (!jeu.peutSelectionnerPiece(x, y)) {
                statut.setText(jeu.doitContinuerCapture()
                    ? "Vous devez continuer avec la piece selectionnee."
                    : "Selectionnez une de vos pieces jouables.");
                return;
            }

            selectionX = x;
            selectionY = y;
            statut.setText("Selection : (" + x + ", " + y + "). Choisissez la destination.");
            rafraichirPlateau();
            return;
        }

        if (selectionX == x && selectionY == y && !jeu.doitContinuerCapture()) {
            selectionX = -1;
            selectionY = -1;
            statut.setText(jeu.getMessageEtat());
            rafraichirPlateau();
            return;
        }

        if (!jeu.doitContinuerCapture() && jeu.peutSelectionnerPiece(x, y)) {
            selectionX = x;
            selectionY = y;
            statut.setText("Selection : (" + x + ", " + y + "). Choisissez la destination.");
            rafraichirPlateau();
            return;
        }

        boolean succes = jeu.jouerTour(selectionX, selectionY, x, y);
        if (succes) {
            if (jeu.doitContinuerCapture()) {
                selectionX = jeu.getXPieceObligatoire();
                selectionY = jeu.getYPieceObligatoire();
            } else {
                selectionX = -1;
                selectionY = -1;
            }
        }
        rafraichirPlateau();
        statut.setText(jeu.getMessageEtat());

        if (succes && jeu.estPartieTerminee() && jeu.getGagnant() != null) {
            JOptionPane.showMessageDialog(this, "Victoire de " + jeu.getGagnant().getNom() + " !");
            retournerAuMenu();
        }
    }

    private void rafraichirPlateau() {
        if (jeu == null) {
            return;
        }

        Plateau plateau = jeu.getPlateau();
        for (int y = 0; y < Plateau.TAILLE; y++) {
            for (int x = 0; x < Plateau.TAILLE; x++) {
                BoutonCase bouton = boutons[x][y];
                Case caseCourante = plateau.getCase(x, y);
                bouton.mettreAJour(
                    caseCourante.getPiece(),
                    couleurCase(x, y),
                    selectionX == x && selectionY == y,
                    jeu.pieceDoitCapturer(x, y)
                );
            }
        }
    }

    private Color couleurCase(int x, int y) {
        return (x + y) % 2 == 0 ? CASE_CLAIRE : CASE_SOMBRE;
    }

    private void abandonnerPartie() {
        if (jeu == null || jeu.estPartieTerminee()) {
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
            this,
            jeu.getJoueurCourant().getNom() + ", voulez-vous vraiment abandonner ?",
            "Confirmer l'abandon",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        String vainqueur = Piece.BLANC.equals(jeu.getJoueurCourant().getCouleur())
            ? jeu.getNomJoueurNoir()
            : jeu.getNomJoueurBlanc();

        JOptionPane.showMessageDialog(
            this,
            jeu.getJoueurCourant().getNom() + " abandonne la partie.\nVictoire de " + vainqueur + " !"
        );
        retournerAuMenu();
    }

    private void retournerAuMenu() {
        String nomJoueurBlanc = jeu.getNomJoueurBlanc();
        String nomJoueurNoir = jeu.getNomJoueurNoir();
        dispose();
        FenetreMenu.lancer(nomJoueurBlanc, nomJoueurNoir);
    }

    public static void lancer(Jeu jeu) {
        SwingUtilities.invokeLater(() -> new FenetreJeu().afficher(jeu));
    }
}
