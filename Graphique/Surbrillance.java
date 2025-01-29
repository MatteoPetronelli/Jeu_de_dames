package Graphique;
import Logique_du_jeu.*;
import Pieces.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Surbrillance {
    private Set<Point> casesSurbrillees; // Ensemble de cases à surbriller
    private InterfaceGraphique plateauGraphique; // Référence à l'interface graphique

    public Surbrillance(InterfaceGraphique plateauGraphique) {
        this.plateauGraphique = plateauGraphique;
        this.casesSurbrillees = new HashSet<>();
    }

    // Méthode pour ajouter une case à la surbrillance
    public void ajouterCase(Point caseAPointer) {
        casesSurbrillees.add(caseAPointer);
        plateauGraphique.repaint(); // Redessiner l'interface graphique pour afficher la surbrillance
    }

    // Méthode pour réinitialiser toute la surbrillance
    public void reset() {
        enleverSurbrillance();
        casesSurbrillees.clear();
        plateauGraphique.repaint(); // Redessiner l'interface graphique pour réinitialiser la surbrillance
    }

    // Méthode pour vérifier si une case est surbrillée
    public boolean estSurbrillee(Point caseAChecker) {
        return casesSurbrillees.contains(caseAChecker);
    }

    // Méthode pour calculer les déplacements valides pour une pièce sur un plateau
    public void calculerDeplacementsValides(Point positionPiece, Jeu jeu) {
        // On parcourt toutes les cases du plateau
        for (int i = 0; i < jeu.getPlateau().getTaille(); i++) {
            for (int j = 0; j < jeu.getPlateau().getTaille(); j++) {
                Case caseCible = jeu.getPlateau().getCase(i, j);
                Piece piece = caseCible.getPiece();
                // Si le déplacement est valide, on surbrille la case
                if (jeu.getPlateau().getCase(positionPiece.x, positionPiece.y).getPiece().estDeplacementValide(positionPiece.x, positionPiece.y, i, j, jeu.getPlateau())) {
                    ajouterCase(new Point(i, j)); // Ajouter la case à la surbrillance
                }
            }
        }
        surbrillerCases();
    }

    // Méthode pour mettre en surbrillance les cases
    public void surbrillerCases() {
        for (Point caseSurbrillee : casesSurbrillees) {
            int x = caseSurbrillee.x;
            int y = caseSurbrillee.y;
            plateauGraphique.getButton(x, y).setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3)); // Bordure rouge
        }
    }

    // Méthode pour enlever la surbrillance des cases
    public void enleverSurbrillance() {
        for (Point caseSurbrillee : casesSurbrillees) {
            int x = caseSurbrillee.x;
            int y = caseSurbrillee.y;
            plateauGraphique.getButton(x, y).setBorder(BorderFactory.createEmptyBorder()); // Pas de bordure
        }
    }
}
