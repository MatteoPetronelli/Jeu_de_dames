
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Créer et afficher la fenêtre du jeu
        SwingUtilities.invokeLater(() -> {
            FenetreJeu fenetreJeu = new FenetreJeu();  // Lancer la fenêtre de jeu
            fenetreJeu.setVisible(true);
        });
    }
}
