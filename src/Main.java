import model.Plateau;

public class Main {
    public static void main(String[] args) {
        // 1. Initialisation du modèle (la logique)
        Plateau plateau = new Plateau();

        System.out.println("--- Jeu de Dames Initialisé ---");

        // 2. Pour l'instant, on va juste tester si ça tourne
        // Plus tard, c'est ici qu'on lancera la fenêtre (la Vue)
        System.out.println("Plateau créé avec succès.");
    }
}