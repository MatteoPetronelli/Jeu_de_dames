// Classe représentant le plateau de jeu

public class Plateau {
    @SuppressWarnings("FieldMayBeFinal")
    private Case[][] cases;
    private static int TAILLE = 8;

    public Plateau() {
        cases = new Case[TAILLE][TAILLE];
        initialiserPlateau();
    }

    private void initialiserPlateau() {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                cases[i][j] = new Case(i, j, (i + j) % 2 != 0);  // Case noire ou blanche

                // Placement des pièces
                if ((i + j) % 2 != 0 && (i < 3 || i > 4)) {
                    // Si la case est noire et dans les 3 premières ou 3 dernières rangées
                    if (i < 3) {
                        cases[i][j].setPiece(new Pion(Couleur.NOIR)); // Pièce noire
                    } else if (i > 4) {
                        cases[i][j].setPiece(new Pion(Couleur.BLANC)); // Pièce blanche
                    }
                }
            }
        }
    }

    public Case getCase(int x, int y) {
        return cases[x][y];
    }

    public int getTaille() {
        return TAILLE;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                builder.append(cases[i][j].toString());
                if (j < TAILLE - 1) {
                    builder.append(" ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}