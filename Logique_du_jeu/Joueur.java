package Logique_du_jeu;
import Graphique.Plateau;
import java.util.Scanner;

public class Joueur {
    @SuppressWarnings("FieldMayBeFinal")
    private String nom;
    @SuppressWarnings("FieldMayBeFinal")
    private Couleur couleur;

    public Joueur(String nom, Couleur couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    public String getNom() {
        return nom;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void jouerTour(Plateau plateau) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(nom + " (" + couleur + ") joue un tour.");
            System.out.print("Entrez la position de la pièce à déplacer (x y): ");
            int xDepart = scanner.nextInt();
            int yDepart = scanner.nextInt();
            System.out.print("Entrez la position d'arrivée (x y): ");
            int xArrivee = scanner.nextInt();
            int yArrivee = scanner.nextInt();

            // Vérifier si le déplacement est valide
            if (plateau.getCase(xDepart, yDepart).getPiece() != null && plateau.getCase(xDepart, yDepart).getPiece().estDeplacementValide(xDepart, yDepart, xArrivee, yArrivee, plateau)) {
                // Effectuer le déplacement
                plateau.getCase(xArrivee, yArrivee).setPiece(plateau.getCase(xDepart, yDepart).getPiece());
                plateau.getCase(xDepart, yDepart).setPiece(null);
            } else {
                System.out.println("Déplacement invalide. Essayez encore.");
            }
        }
    }
}
