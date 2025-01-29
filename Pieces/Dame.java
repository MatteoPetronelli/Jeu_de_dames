package Pieces;
import Logique_du_jeu.*;
import Graphique.*;

public class Dame extends Piece {
    public Dame(Couleur couleur) {
        super(couleur);
    }

    @Override
    public boolean estDeplacementValide(int xDepart, int yDepart, int xArrivee, int yArrivee, Plateau plateau) {
        // Vérifier que le déplacement est diagonal
        int diffX = xArrivee - xDepart;
        int diffY = yArrivee - yDepart;
        
        // Le déplacement doit être diagonal (|diffX| = |diffY|)
        if (Math.abs(diffX) != Math.abs(diffY)) {
            return false;
        }

        // Vérifier que la destination est dans les limites du plateau
        if (xArrivee < 0 || xArrivee >= plateau.getTaille() || 
            yArrivee < 0 || yArrivee >= plateau.getTaille()) {
            return false;
        }

        // Vérifier que la case d'arrivée est libre
        if (plateau.getCase(xArrivee, yArrivee).estOccupee()) {
            return false;
        }

        // Vérifier le chemin
        int pasX = Integer.compare(diffX, 0);  // -1, 0 ou 1
        int pasY = Integer.compare(diffY, 0);  // -1, 0 ou 1
        
        int x = xDepart + pasX;
        int y = yDepart + pasY;
        boolean pieceTrouvee = false;
        int piecesRencontrees = 0;

        while (x != xArrivee || y != yArrivee) {
            if (plateau.getCase(x, y).estOccupee()) {
                // Vérifier que la pièce est de couleur opposée
                if (plateau.getCase(x, y).getPiece().getCouleur() == this.getCouleur()) {
                    return false;
                }
                piecesRencontrees++;
                System.out.println(piecesRencontrees);
            }
            x += pasX;
            y += pasY;
        }

        // Le déplacement est valide si:
        // - soit c'est un déplacement simple sans capture (aucune pièce sur le chemin)
        // - soit c'est une capture (exactement une pièce adverse sur le chemin)
        return piecesRencontrees == 0 || piecesRencontrees == 1;
    }
}