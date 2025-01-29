package Graphique;
import Logique_du_jeu.*;
import Pieces.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceGraphique extends JPanel {
    private FenetreJeu fenetreJeu; // Référence à FenetreJeu
    private Jeu jeu; // Instance du jeu
    private JButton[][] buttons; // Tableau de boutons pour représenter les cases
    private static final int TAILLE = 10;

    public InterfaceGraphique(FenetreJeu fenetreJeu) {
        this.fenetreJeu = fenetreJeu;
        this.jeu = fenetreJeu.getJeu();
        this.buttons = new JButton[TAILLE][TAILLE];
        setLayout(new GridLayout(TAILLE, TAILLE)); // Organise les boutons en grille 8x8
        initialiserPlateau(); // Initialiser le plateau
    }

    // Méthode pour obtenir la taille de la case
    public int getCaseSize() {
        return 70; // Taille par défaut d'une case (en pixels)
    }

    public JButton getButton(int x, int y) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            return buttons[x][y];
        }
        return null;
    }

    // Méthode pour initialiser le plateau avec des boutons
    public void initialiserPlateau() {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                final int x = i; // Variable finale pour éviter l'erreur
                final int y = j; // Variable finale pour éviter l'erreur

                buttons[i][j] = new JButton() {
                    @Override
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        // Couleur de la case (beige clair ou foncé)
                        if ((x + y) % 2 == 0) {
                            g.setColor(new Color(245, 222, 179)); // Beige clair
                        } else {
                            g.setColor(new Color(210, 180, 140)); // Beige foncé
                        }
                        g.fillRect(0, 0, getWidth(), getHeight());

                        // Dessiner la pièce si elle est présente
                        Piece piece = jeu.getPlateau().getCase(x, y).getPiece();
                        if (piece != null) {
                            if (piece.getCouleur() == Couleur.BLANC) {
                                g.setColor(Color.WHITE); // Cercle blanc pour les pièces blanches
                            } else {
                                g.setColor(Color.BLACK); // Cercle noir pour les pièces noires
                            }

                            // Dessiner un cercle au centre de la case
                            int padding = getWidth() / 6; // Marge autour du cercle
                            g.fillOval(
                                    padding,
                                    padding,
                                    getWidth() - 2 * padding,
                                    getHeight() - 2 * padding
                            );

                            // Dessiner un contour pour le cercle
                            g.setColor(Color.GRAY);
                            g.drawOval(
                                    padding,
                                    padding,
                                    getWidth() - 2 * padding,
                                    getHeight() - 2 * padding
                            );

                            // Ajouter le texte de la pièce (toString())
                            g.setColor(piece.getCouleur() == Couleur.BLANC ? Color.BLACK : Color.WHITE);
                            g.setFont(new Font("Arial", Font.BOLD, 12)); // Police pour le texte
                            String pieceText = piece.toString(); // Texte de la pièce
                            FontMetrics fm = g.getFontMetrics();
                            int textWidth = fm.stringWidth(pieceText);
                            int textHeight = fm.getAscent();
                            g.drawString(
                                    pieceText,
                                    (getWidth() - textWidth) / 2, // Centrer horizontalement
                                    (getHeight() + textHeight) / 2 // Centrer verticalement
                            );
                        }
                    }
                };

                // Configuration du bouton
                buttons[i][j].setPreferredSize(new Dimension(getCaseSize(), getCaseSize()));
                buttons[i][j].setOpaque(true);

                // Ajout d'un écouteur d'action pour chaque case
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fenetreJeu.handleCaseClick(x, y); // Appel de la méthode de FenetreJeu
                    }
                });

                add(buttons[i][j]); // Ajout du bouton au layout
            }
        }
        updatePlateau(); // Mise à jour du plateau avec les pièces au départ
    }

    // Mise à jour du plateau
    public void updatePlateau() {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                buttons[i][j].repaint(); // Redessine chaque case
            }
        }
    }
}


