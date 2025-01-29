package Pieces;
import Graphique.*;
import Logique_du_jeu.*;
// Classe représentant une dame (héritée de Piece)
public class Dame extends Piece {
    public Dame(Couleur couleur) {
        super(couleur);
    }

    @Override
    public boolean estDeplacementValide(int xDepart, int yDepart, int xArrivee, int yArrivee, Plateau plateau) {

        int diffX = xArrivee - xDepart;  // Différence en X
        int diffY = Math.abs(yArrivee - yDepart);  // Différence en Y (absolue)

        System.out.println("Vérification du déplacement : (" + xDepart + ", " + yDepart + ") -> (" + xArrivee + ", " + yArrivee + ")");
        System.out.println("Différence X : " + diffX + ", Différence Y : " + diffY);

        // Vérification du déplacement simple (une case en diagonale)
        if ((diffX == 1 || diffX == -1) && diffY == 1 && xArrivee >= 0 && xArrivee < plateau.getTaille() && yArrivee >= 0 && yArrivee < plateau.getTaille()) {
            if (!plateau.getCase(xArrivee, yArrivee).estOccupee()) {
                System.out.println("Déplacement simple valide : (" + xDepart + ", " + yDepart + ") à (" + xArrivee + ", " + yArrivee + ")");
                return true;
            } else {
                System.out.println("Case d'arrivée occupée pour déplacement simple : (" + xArrivee + ", " + yArrivee + ")");
            }
        } else {
            System.out.println("Le déplacement simple n'est pas valide.");
        }

        // Vérification de la capture (deux cases en diagonale)
        if (diffX == 2 && diffY == 2 || diffX == -2 && diffY == 2) {
            int xIntermediaire = (xDepart + xArrivee) / 2;
            int yIntermediaire = (yDepart + yArrivee) / 2;
            Case caseIntermediaire = plateau.getCase(xIntermediaire, yIntermediaire);

            System.out.println("Vérification de la capture :");
            System.out.println("Case intermédiaire : (" + xIntermediaire + ", " + yIntermediaire + ")");
            if (caseIntermediaire.estOccupee()) {
                Piece pieceIntermediaire = caseIntermediaire.getPiece();
                System.out.println("La pièce à capturer est : " + pieceIntermediaire);
                if (pieceIntermediaire.getCouleur() != getCouleur() && !plateau.getCase(xArrivee, yArrivee).estOccupee()) {
                    System.out.println("Capture valide : (" + xDepart + ", " + yDepart + ") à (" + xArrivee + ", " + yArrivee + ")");
                    return true;
                } else {
                    System.out.println("Capture impossible : la case intermédiaire est occupée par une pièce de la même couleur ou la case d'arrivée n'est pas vide.");
                }
            } else {
                System.out.println("Aucune pièce à capturer à (" + xIntermediaire + ", " + yIntermediaire + ")");
            }
        } else {
            System.out.println("Le déplacement pour la capture n'est pas valide.");
        }

        // Si aucune des conditions n'est remplie, le déplacement est invalide
        System.out.println("Déplacement invalide.");
        return false;
    }

    @Override
    public String toString() {
        return getCouleur().toString().charAt(0) + "D"; // Exemple : BD pour dame blanche, ND pour dame noire
    }
}