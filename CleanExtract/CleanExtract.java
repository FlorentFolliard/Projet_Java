public class CleanExtract {
    public static String extract(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        // On sépare la chaîne par le caractère '|'
        // Le double backslash est nécessaire car split utilise des expressions régulières
        String[] parts = s.split("\\|");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            
            int firstDot = part.indexOf('.');
            int lastDot = part.lastIndexOf('.');

            // On vérifie qu'il y a au moins deux points distincts pour extraire du contenu
            if (firstDot != -1 && lastDot != -1 && firstDot < lastDot) {
                // On extrait entre le premier et le dernier point
                String extracted = part.substring(firstDot + 1, lastDot).trim();
                
                // On n'ajoute au résultat que si la partie extraite n'est pas vide
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