package Graphique;
import Pieces.*;
// Classe représentant une case du plateau de jeu
public class Case {
    @SuppressWarnings("FieldMayBeFinal")
    private boolean estNoire;
    private Piece piece;
    @SuppressWarnings("FieldMayBeFinal")
    private int x, y;  // Coordonnées de la case

    public Case(int x, int y, boolean estNoire) {
        this.x = x;
        this.y = y;
        this.estNoire = estNoire;
        this.piece = null;
    }

    public boolean estVide() {
        return piece == null;  // Si la case n'a pas de pièce, elle est vide
    }

    public boolean estOccupee() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        if (piece != null) {
            return piece.toString();  // Affiche la pièce (par exemple "BP" pour un pion blanc)
        }
        return estNoire ? "[ ]" : "   ";  // Affiche une case vide (noire ou blanche)
    }
}
