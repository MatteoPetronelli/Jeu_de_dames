package Pieces;
import Logique_du_jeu.*;
import Graphique.*;
// Classe représentant une pièce de jeu
public abstract class Piece {
    private Couleur couleur;
    private boolean estPromue;

    public Piece(Couleur couleur) {
        this.couleur = couleur;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public boolean estPromue() {
        return estPromue;
    }

    public void promouvoir() {
        this.estPromue = true;
    }

    public abstract boolean estDeplacementValide(int xDepart, int yDepart, int xArrivee, int yArrivee, Plateau plateau);
    
    @Override
    public String toString() {
        return couleur.name().charAt(0) + "P"; // Exemple : BP pour pion blanc, NP pour pion noir
    }
}