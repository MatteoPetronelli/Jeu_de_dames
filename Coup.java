public class Coup {
    private int xDepart, yDepart, xArrivee, yArrivee;
    private boolean captureEffectuee;
    private Joueur joueur;
    private Piece pieceCapturee;
    private boolean promotion;  // Indique si une promotion a eu lieu

    public Coup(int xDepart, int yDepart, int xArrivee, int yArrivee, boolean captureEffectuee, Joueur joueur, Piece pieceCapturee, boolean promotion) {
        this.xDepart = xDepart;
        this.yDepart = yDepart;
        this.xArrivee = xArrivee;
        this.yArrivee = yArrivee;
        this.captureEffectuee = captureEffectuee;
        this.joueur = joueur;
        this.pieceCapturee = pieceCapturee;
        this.promotion = promotion;
    }

    // Getters pour accéder aux informations
    public int getXDepart() {
        return xDepart;
    }

    public int getYDepart() {
        return yDepart;
    }

    public int getXArrivee() {
        return xArrivee;
    }

    public int getYArrivee() {
        return yArrivee;
    }

    public boolean isCaptureEffectuee() {
        return captureEffectuee;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public Piece getPieceCapturee() {
        return pieceCapturee;
    }

    public boolean isPromotion() {
        return promotion;
    }  

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Coup de ").append(joueur.getCouleur());
        sb.append(" de (").append(xDepart).append(", ").append(yDepart).append(") à (").append(xArrivee).append(", ").append(yArrivee).append(")");
        if (captureEffectuee) {
            sb.append(" avec capture de ").append(pieceCapturee);
        }
        // Vérifie si la pièce est promue pour la première fois
        if (promotion && !(pieceCapturee instanceof Dame)) {
            sb.append(" avec promotion !");
        }
        return sb.toString();
    }
}
