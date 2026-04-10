public class CleanExtract {
    public static String extract(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        // On sépare les segments par le caractère '|'
        String[] parts = s.split("\\|");
        StringBuilder result = new StringBuilder();

        for (String part : parts) {
            int firstDot = part.indexOf('.');
            int lastDot = part.lastIndexOf('.');

            // On vérifie qu'il existe bien au moins deux points distincts
            if (firstDot != -1 && lastDot != -1 && firstDot < lastDot) {
                // On extrait d'abord ce qui est strictement entre le premier et dernier point
                String sub = part.substring(firstDot + 1, lastDot);
                
                // On retire les points résiduels au début et à la fin de cette extraction
                // (Cas des séquences comme ...texte...)
                sub = trimDotsAndSpaces(sub);

                if (!sub.isEmpty()) {
                    if (result.length() > 0) {
                        result.append(" ");
                    }
                    result.append(sub);
                }
            }
        }

        return result.toString();
    }

    /**
     * Méthode utilitaire pour nettoyer les espaces ET les points 
     * en début et fin de chaîne.
     */
    private static String trimDotsAndSpaces(String s) {
        int start = 0;
        int end = s.length();

        // On avance l'index de début tant qu'on trouve un espace ou un point
        while (start < end && (s.charAt(start) == ' ' || s.charAt(start) == '.')) {
            start++;
        }

        // On recule l'index de fin tant qu'on trouve un espace ou un point
        while (end > start && (s.charAt(end - 1) == ' ' || s.charAt(end - 1) == '.')) {
            end--;
        }

        return s.substring(start, end);
    }
}