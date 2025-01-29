package Graphique;
import Logique_du_jeu.*;
import Pieces.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FenetreJeu extends JFrame {
    private Jeu jeu;  // Instance du jeu;
    private InterfaceGraphique plateauGraphique;  // Panneau pour afficher le plateau
    private Case caseSelectionnee;  // Case actuellement sélectionnée
    private JTextArea historiqueTextArea;  // Zone de texte pour afficher le déroulement de la partie
    private Surbrillance surbrillance = new Surbrillance(plateauGraphique);  // Surbrillance des cases

    public FenetreJeu() {
        // Initialisation de l'interface graphique
        setTitle("Jeu de Dames");
        setSize(900, 600); // Taille de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Comportement à la fermeture
        setLocationRelativeTo(null); // Centrer la fenêtre

        jeu = new Jeu(true); // Créer une instance du jeu
        plateauGraphique = new InterfaceGraphique(this); // Créer le panneau du plateau graphique avec une référence à FenetreJeu
        surbrillance = new Surbrillance(plateauGraphique); // Créer une instance de Surbrillance

        // Créer un panneau pour l'historique des coups
        JPanel historiquePanel = new JPanel();
        historiquePanel.setLayout(new BorderLayout());
        historiqueTextArea = new JTextArea(10, 20);
        historiqueTextArea.setEditable(false);  // Ne pas permettre à l'utilisateur de modifier l'historique
        JScrollPane scrollPane = new JScrollPane(historiqueTextArea);
        historiquePanel.add(scrollPane, BorderLayout.CENTER);
        historiquePanel.setPreferredSize(new Dimension(300, 600));  // Panneau à droite

        // Définir les couleurs du texte et du fond de la zone d'historique
        historiqueTextArea.setBackground(Color.BLACK);  // Fond noir pour la zone d'historique
        historiqueTextArea.setForeground(Color.WHITE);  // Texte en blanc

        // Mettre en place un layout pour organiser le plateau et l'historique
        setLayout(new BorderLayout());
        add(plateauGraphique, BorderLayout.CENTER);  // Plateau au centre
        add(historiquePanel, BorderLayout.EAST);  // Historique à droite

        // Ne pas redimensionner la fenêtre
        setResizable(false);

        // Ajout de l'écouteur de clic dans le plateau
        plateauGraphique.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / plateauGraphique.getCaseSize();
                int y = e.getY() / plateauGraphique.getCaseSize();

                // Gérer le clic sur la case
                handleCaseClick(x, y);
            }
        });

        // Rendre visible la fenêtre
        setVisible(true);
    }

    // Gère un clic sur une case
    public void handleCaseClick(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            surbrillance.reset();
            System.out.println("Case sélectionnée : (" + x + ", " + y + ")");

            // Si aucune case n'est encore sélectionnée, essayer de sélectionner une case de départ
            if (caseSelectionnee == null) {
                Case selectedCase = jeu.getPlateau().getCase(x, y);

                // Vérifier si la case est occupée par une pièce et si cette pièce appartient au joueur actuel
                if (selectedCase.estOccupee() && selectedCase.getPiece().getCouleur().equals(jeu.getJoueurActuel().getCouleur())) {
                    caseSelectionnee = selectedCase;  // Sélectionner la case de départ
                    System.out.println("Case de départ sélectionnée : (" + x + ", " + y + ")");

                    // Calculer les déplacements valides pour la pièce
                    Point positionPiece = new Point(x, y);
                    surbrillance.calculerDeplacementsValides(positionPiece, jeu);
                }
            } else {
                // Si une case est déjà sélectionnée, essayer de déplacer la pièce
                Piece piece = caseSelectionnee.getPiece();
                if (piece.estDeplacementValide(caseSelectionnee.getX(), caseSelectionnee.getY(), x, y, jeu.getPlateau())) {
                    // Si le déplacement est valide, effectuer le coup
                    int startX = caseSelectionnee.getX();
                    int startY = caseSelectionnee.getY();
                    jeu.jouerCoup(startX, startY, x, y);  // Effectuer le mouvement
                    Coup dernierCoup = jeu.getDernierCoup();
                    caseSelectionnee = null;  // Réinitialiser la sélection

                    // Mise à jour du plateau graphique
                    plateauGraphique.updatePlateau();

                    // Mise à jour de l'historique du jeu dans un thread d'événements
                    SwingUtilities.invokeLater(() -> {
                        historiqueTextArea.append(dernierCoup.toString() + "\n");
                        historiqueTextArea.append(jeu.verifierVictoire());
                        historiqueTextArea.setCaretPosition(historiqueTextArea.getDocument().getLength());  // Défiler automatiquement
                    });

                    // Revalidation et redessin de l'interface
                    revalidate();
                    repaint();

                } else {
                    // Si le déplacement est invalide, annuler la sélection de la case
                    caseSelectionnee = null;
                    System.out.println("Mouvement invalide !");
                }
                surbrillance.reset();
            }
        }
    }

    public Jeu getJeu() {
        return jeu;
    }

    public InterfaceGraphique getPlateauGraphique() {
        return plateauGraphique;
    }
}
