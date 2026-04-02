package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class FenetreJeu extends Application {

    private final int TAILLE_CASE = 60; // Taille en pixels d'une case
    private final int NB_CASES = 10;

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();

        // Création du damier
        for (int row = 0; row < NB_CASES; row++) {
            for (int col = 0; col < NB_CASES; col++) {
                Rectangle caseGraphique = new Rectangle(TAILLE_CASE, TAILLE_CASE);

                // Alternance des couleurs (marron clair / marron foncé)
                if ((row + col) % 2 == 0) {
                    caseGraphique.setFill(Color.BEIGE);
                } else {
                    caseGraphique.setFill(Color.SADDLEBROWN);
                }

                root.add(caseGraphique, col, row);
            }
        }

        Scene scene = new Scene(root, NB_CASES * TAILLE_CASE, NB_CASES * TAILLE_CASE);
        primaryStage.setTitle("Projet Dames - JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void lancer() {
        launch();
    }
}