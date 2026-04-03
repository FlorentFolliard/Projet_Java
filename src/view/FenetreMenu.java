package view;

import model.Jeu;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class FenetreMenu extends JFrame {
    private final JTextField champJoueurBlanc;
    private final JTextField champJoueurNoir;

    public FenetreMenu() {
        this("Joueur 1", "Joueur 2");
    }

    public FenetreMenu(String nomJoueurBlanc, String nomJoueurNoir) {
        super("Menu - Jeu de Dames");
        champJoueurBlanc = new JTextField(nomJoueurBlanc);
        champJoueurNoir = new JTextField(nomJoueurNoir);
        initialiserFenetre();
    }

    private void initialiserFenetre() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));

        JLabel titre = new JLabel("Jeu de Dames", SwingConstants.CENTER);
        titre.setFont(new Font("SansSerif", Font.BOLD, 24));
        titre.setBorder(BorderFactory.createEmptyBorder(18, 12, 4, 12));

        JLabel sousTitre = new JLabel("Entrez les noms des joueurs puis lancez la partie.", SwingConstants.CENTER);
        sousTitre.setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 12));

        JPanel entete = new JPanel();
        entete.setLayout(new BoxLayout(entete, BoxLayout.Y_AXIS));
        entete.add(titre);
        entete.add(sousTitre);

        JPanel formulaire = new JPanel(new GridLayout(2, 2, 10, 10));
        formulaire.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        formulaire.add(new JLabel("Joueur blanc :"));
        formulaire.add(champJoueurBlanc);
        formulaire.add(new JLabel("Joueur noir :"));
        formulaire.add(champJoueurNoir);

        JButton boutonDemarrer = new JButton("Demarrer la partie");
        boutonDemarrer.setBackground(new Color(71, 133, 74));
        boutonDemarrer.setForeground(Color.WHITE);
        boutonDemarrer.setFocusPainted(false);
        boutonDemarrer.addActionListener(e -> demarrerPartie());
        getRootPane().setDefaultButton(boutonDemarrer);

        JPanel pied = new JPanel();
        pied.setBorder(BorderFactory.createEmptyBorder(8, 12, 18, 12));
        pied.add(boutonDemarrer);

        add(entete, BorderLayout.NORTH);
        add(formulaire, BorderLayout.CENTER);
        add(pied, BorderLayout.SOUTH);

        setMinimumSize(new Dimension(420, 220));
        pack();
        setLocationRelativeTo(null);
    }

    private void demarrerPartie() {
        String nomJoueurBlanc = nettoyerNom(champJoueurBlanc.getText(), "Joueur 1");
        String nomJoueurNoir = nettoyerNom(champJoueurNoir.getText(), "Joueur 2");

        Jeu jeu = new Jeu(nomJoueurBlanc, nomJoueurNoir);
        FenetreJeu fenetreJeu = new FenetreJeu();
        fenetreJeu.afficher(jeu);
        dispose();
    }

    private String nettoyerNom(String valeur, String valeurParDefaut) {
        String texte = valeur == null ? "" : valeur.trim();
        return texte.isEmpty() ? valeurParDefaut : texte;
    }

    public static void lancer() {
        SwingUtilities.invokeLater(() -> new FenetreMenu().setVisible(true));
    }

    public static void lancer(String nomJoueurBlanc, String nomJoueurNoir) {
        SwingUtilities.invokeLater(() -> new FenetreMenu(nomJoueurBlanc, nomJoueurNoir).setVisible(true));
    }
}
