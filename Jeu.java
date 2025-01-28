public class Jeu {
    private Plateau plateau;
    private Joueur joueur1;
    private Joueur joueur2;
    private Joueur joueurActuel;
    private Coup dernierCoup;
    private boolean jeuTermine = false;


    public Jeu(boolean joueur1Commence) {
        plateau = new Plateau();
        joueur1 = new Joueur("Joueur 1", Couleur.BLANC);
        joueur2 = new Joueur("Joueur 2", Couleur.NOIR);
        joueurActuel = joueur1Commence ? joueur1 : joueur2;
    }

    public Plateau getPlateau() {
        return plateau;
    }

    public Joueur getJoueurActuel() {
        return joueurActuel;
    }

    // Récupérer le dernier coup joué
    public Coup getDernierCoup() {
        return dernierCoup;
    }

    private void promouvoirPion(Case caseArrivee) {
        Piece piece = caseArrivee.getPiece();
        if (piece != null && piece instanceof Pion) {  // Vérifie que la pièce est un pion
            int x = caseArrivee.getX();
            Couleur couleur = piece.getCouleur();
    
            // Vérifie si le pion atteint la dernière rangée
            if ((couleur == Couleur.BLANC && x == 0) || (couleur == Couleur.NOIR && x == 7)) {
                // Promotion en dame
                Dame dame = new Dame(couleur);
                caseArrivee.setPiece(dame);  // Remplace le pion par une dame
                System.out.println("Promotion ! Le pion a été promu en dame.");
            }
        }
    }
    
    private boolean estPromotion(Piece piece, int xArrivee) {
        // Les pions blancs atteignent la dernière ligne (ligne 0)
        if (piece.getCouleur() == Couleur.BLANC && xArrivee == 0) {
            return true;
        }
        // Les pions noirs atteignent la dernière ligne (ligne 7)
        return piece.getCouleur() == Couleur.NOIR && xArrivee == 7;
    }    

    // Cette méthode joue un coup
    public void jouerCoup(int xDepart, int yDepart, int xArrivee, int yArrivee) {
        if (jeuTermine) {
            System.out.println("Le jeu est terminé. Aucun déplacement n'est possible.");
            return;
        }
    
        Case caseDepart = plateau.getCase(xDepart, yDepart);
        Case caseArrivee = plateau.getCase(xArrivee, yArrivee);
    
        Piece pieceDeplacee = caseDepart.getPiece();
    
        if (pieceDeplacee != null && pieceDeplacee.estDeplacementValide(xDepart, yDepart, xArrivee, yArrivee, plateau)) {
            boolean captureEffectuee = false;
            Piece pieceCapturee = null;
    
            if (Math.abs(xArrivee - xDepart) == 2) {
                int xCaseCapturee = (xArrivee + xDepart) / 2;
                int yCaseCapturee = (yArrivee + yDepart) / 2;
                pieceCapturee = plateau.getCase(xCaseCapturee, yCaseCapturee).getPiece();
            
                // Vérification que la pièce capturée appartient à l'adversaire
                if (pieceCapturee != null && pieceCapturee.getCouleur() != pieceDeplacee.getCouleur()) {
                    plateau.getCase(xCaseCapturee, yCaseCapturee).setPiece(null);
                    captureEffectuee = true;
                } else {
                    System.out.println("Capture invalide : pièce alliée ou aucune pièce à capturer !");
                    return; // Annule le coup si la capture est invalide
                }
            }
    
            // Déplacement de la pièce
            caseArrivee.setPiece(pieceDeplacee);
            caseDepart.setPiece(null);
    
            // Vérification de la promotion
            boolean promotionEffectuee = false;
            if (pieceDeplacee instanceof Pion && estPromotion(pieceDeplacee, xArrivee)) {
                pieceDeplacee.promouvoir();
                promotionEffectuee = true;
                promouvoirPion(caseArrivee);
            }
    
            // Enregistrer le dernier coup
            dernierCoup = new Coup(xDepart, yDepart, xArrivee, yArrivee, captureEffectuee, joueurActuel, pieceCapturee, promotionEffectuee);
    
            // Passer au joueur suivant
            changerJoueur();
        } else {
            System.out.println("Déplacement invalide !");
        }
    }    
    
    // Changer le joueur actuel
    private void changerJoueur() {
        // Changer le joueur actuel
        joueurActuel = (joueurActuel == joueur1) ? joueur2 : joueur1;
        
        // Changer la couleur du joueur actuel
        System.out.println("C'est au tour de " + joueurActuel.getNom() + " (" + joueurActuel.getCouleur() + ")");
    }

    public String verifierVictoire() {
        boolean piecesBlanchesRestantes = false;
        boolean piecesNoiresRestantes = false;
    
        // Parcourir toutes les cases du plateau
        for (int x = 0; x < plateau.getTaille(); x++) {
            for (int y = 0; y < plateau.getTaille(); y++) {
                Piece piece = plateau.getCase(x, y).getPiece();
                if (piece != null) {
                    if (piece.getCouleur() == Couleur.BLANC) {
                        piecesBlanchesRestantes = true;
                    } else if (piece.getCouleur() == Couleur.NOIR) {
                        piecesNoiresRestantes = true;
                    }
    
                    // Si les deux couleurs ont encore des pièces, pas de victoire
                    if (piecesBlanchesRestantes && piecesNoiresRestantes) {
                        return null; // Pas encore de victoire
                    }
                }
            }
        }
    
        // Déterminer le gagnant
        if (!piecesBlanchesRestantes) {
            return "Victoire du joueur NOIR !";
        } else if (!piecesNoiresRestantes) {
            return "Victoire du joueur BLANC !";
        }
    
        return null; // Cas par défaut (ne devrait pas arriver)
    }
    

    // Affiche l'état du plateau (simple version)
    public void afficherPlateau() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Case caseActuelle = plateau.getCase(i, j);
                if (caseActuelle.estOccupee()) {
                    Piece piece = caseActuelle.getPiece();
                    System.out.print(piece.toString() + " ");  // Affichage du type de la pièce
                } else {
                    System.out.print("XX ");  // Case vide
                }
            }
            System.out.println();
        }
    }      
}