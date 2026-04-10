public class CleanExtract {
    public static String extract(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        // On sépare par le pipe |
        String[] parts = s.split("\\|");
        StringBuilder result = new StringBuilder();

        for (String part : parts) {
            int firstDot = part.indexOf('.');
            int lastDot = part.lastIndexOf('.');

            // On vérifie qu'il y a au moins deux points pour définir une zone
            if (firstDot != -1 && lastDot != -1 && firstDot < lastDot) {
                
                // On extrait strictement ce qui est ENTRE le premier et le dernier point
                String extracted = part.substring(firstDot + 1, lastDot);
                
                // On nettoie les espaces AVANT de vérifier si c'est vide
                extracted = extracted.trim();

                if (!extracted.isEmpty()) {
                    if (result.length() > 0) {
                        result.append(" ");
                    }
                    result.append(extracted);
                }
            }
        }

        return result.toString();
    }
}