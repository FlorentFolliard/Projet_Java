package view;

import model.Dame;
import model.Piece;

import javax.swing.JButton;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class BoutonCase extends JButton {
    private static final Color COULEUR_SELECTION = new Color(246, 246, 105);
    private static final Color COULEUR_CAPTURE_OBLIGATOIRE = new Color(88, 214, 141);
    private static final Color PION_BLANC_CLAIR = new Color(250, 250, 245);
    private static final Color PION_BLANC_FONCE = new Color(210, 210, 205);
    private static final Color PION_NOIR_CLAIR = new Color(90, 90, 90);
    private static final Color PION_NOIR_FONCE = new Color(25, 25, 25);
    private static final Color COURONNE = new Color(201, 162, 39);

    private Piece piece;
    private Color couleurFond;
    private boolean selectionnee;
    private boolean captureObligatoire;

    public BoutonCase() {
        couleurFond = Color.LIGHT_GRAY;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
    }

    public void mettreAJour(Piece piece, Color couleurFond, boolean selectionnee, boolean captureObligatoire) {
        this.piece = piece;
        this.couleurFond = couleurFond;
        this.selectionnee = selectionnee;
        this.captureObligatoire = captureObligatoire;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g2 = (Graphics2D) graphics.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(couleurFond);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (captureObligatoire) {
            g2.setColor(new Color(
                COULEUR_CAPTURE_OBLIGATOIRE.getRed(),
                COULEUR_CAPTURE_OBLIGATOIRE.getGreen(),
                COULEUR_CAPTURE_OBLIGATOIRE.getBlue(),
                70
            ));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

        if (selectionnee) {
            g2.setColor(COULEUR_SELECTION);
            g2.setStroke(new BasicStroke(4f));
            g2.drawRect(3, 3, getWidth() - 7, getHeight() - 7);
        }

        if (piece != null) {
            dessinerPiece(g2);
        }

        g2.dispose();
    }

    private void dessinerPiece(Graphics2D g2) {
        int marge = 10;
        int taille = Math.min(getWidth(), getHeight()) - marge * 2;
        int x = (getWidth() - taille) / 2;
        int y = (getHeight() - taille) / 2;

        if (captureObligatoire) {
            g2.setColor(new Color(
                COULEUR_CAPTURE_OBLIGATOIRE.getRed(),
                COULEUR_CAPTURE_OBLIGATOIRE.getGreen(),
                COULEUR_CAPTURE_OBLIGATOIRE.getBlue(),
                110
            ));
            g2.fillOval(x - 4, y - 4, taille + 8, taille + 8);
        }

        g2.setColor(new Color(0, 0, 0, 45));
        g2.fillOval(x + 3, y + 4, taille, taille);

        Color clair = piece.estBlanche() ? PION_BLANC_CLAIR : PION_NOIR_CLAIR;
        Color fonce = piece.estBlanche() ? PION_BLANC_FONCE : PION_NOIR_FONCE;
        g2.setPaint(new GradientPaint(x, y, clair, x + taille, y + taille, fonce));
        g2.fillOval(x, y, taille, taille);

        g2.setColor(piece.estBlanche() ? new Color(140, 140, 140) : new Color(10, 10, 10));
        g2.setStroke(new BasicStroke(2.5f));
        g2.drawOval(x, y, taille, taille);

        if (captureObligatoire) {
            g2.setColor(COULEUR_CAPTURE_OBLIGATOIRE);
            g2.setStroke(new BasicStroke(3.5f));
            g2.drawOval(x - 2, y - 2, taille + 4, taille + 4);
        }

        g2.setColor(new Color(255, 255, 255, piece.estBlanche() ? 120 : 60));
        g2.fillOval(x + taille / 5, y + taille / 6, taille / 3, taille / 4);

        if (piece instanceof Dame) {
            dessinerMarqueDame(g2, x, y, taille);
        }
    }

    private void dessinerMarqueDame(Graphics2D g2, int x, int y, int taille) {
        int margeInterieure = taille / 4;
        g2.setColor(COURONNE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawOval(x + margeInterieure, y + margeInterieure, taille - 2 * margeInterieure, taille - 2 * margeInterieure);

        int largeurSocle = taille / 3;
        int hauteurSocle = taille / 8;
        int socleX = x + (taille - largeurSocle) / 2;
        int socleY = y + taille / 2;
        g2.fillRoundRect(socleX, socleY, largeurSocle, hauteurSocle, 6, 6);

        int rayon = Math.max(4, taille / 14);
        int centreY = socleY - rayon;
        g2.fillOval(socleX - rayon / 2, centreY, rayon, rayon);
        g2.fillOval(socleX + largeurSocle / 2 - rayon / 2, centreY - rayon / 2, rayon, rayon);
        g2.fillOval(socleX + largeurSocle - rayon / 2, centreY, rayon, rayon);
    }
}
