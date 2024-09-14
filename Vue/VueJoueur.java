package jeuColtExpress.Vue;

import jeuColtExpress.Modeles.Bandit;
import jeuColtExpress.Modeles.Butin;
import jeuColtExpress.Modeles.Marshall;
import jeuColtExpress.Modeles.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class VueJoueur extends JFrame implements Observer {
    private final ArrayList<Bandit> bandits;

    public VueJoueur(ArrayList<Bandit> bandits) {
        this.bandits = bandits;

        // Chargement des images pour le fond, le train, les butins et les coeurs
        ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/plan.png")));
        ImageIcon bijoux = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/bijoux.png")));
        ImageIcon bourse = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Bourse.png")));
        ImageIcon magot = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/magot.png")));

        ImageIcon[] coeurs = {
                new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/coeur1.png"))),
                new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/coeur2.png"))),
                new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/coeur3.png"))),
                new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/coeur4.png")))
        };

        // Création et ajout du panneau principal de la vue
        MainPanel mainPanel = new MainPanel(backgroundImage, bijoux, bourse, magot, coeurs);
        add(mainPanel);
    }


    @Override
    public void update(VueActionsJoueur o, Object arg) {
        MainPanel mainPanel = (MainPanel) getContentPane().getComponent(0);
        mainPanel.repaint();
    }
    @Override
    public void update1(VueJoueur o, Object arg) {
        MainPanel mainPanel = (VueJoueur.MainPanel) getContentPane().getComponent(0);
        mainPanel.repaint();
    }

    public void update2(VuePlanification o, Object args){
        MainPanel mainPanel = (MainPanel) getContentPane().getComponent(0);
        mainPanel.repaint();
    }

    private class MainPanel extends JPanel {
        private final ImageIcon backgroundImage;
        private final ImageIcon bijoux;
        private final ImageIcon bourse;
        private final ImageIcon magot;
        private final ImageIcon[] coeurs;

        /**
         * Constructeur de la classe MainPanel.
         *
         * @param backgroundImage L'image de fond du panneau.
         * @param bijoux          L'image représentant les bijoux.
         * @param bourse          L'image représentant la bourse.
         * @param magot           L'image représentant le magot.
         * @param coeurs          Les images représentant les coeurs
         */
        public MainPanel(ImageIcon backgroundImage, ImageIcon bijoux, ImageIcon bourse, ImageIcon magot, ImageIcon[] coeurs) {
            this.backgroundImage = backgroundImage;
            this.bijoux = bijoux;
            this.bourse = bourse;
            this.magot = magot;
            this.coeurs = coeurs;
        }

        /**
         * Méthode permettant de dessiner les éléments sur le panneau.
         *
         * @param g L'objet Graphics utilisé pour dessiner.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            int x = 0;
            int y = 150;

            for (Bandit b : bandits) {
                int distance = 0;
                String message = b.getName();
                String score = "Score : " + b.getScore();
                Font font = new Font("Arial", Font.BOLD, 15);
                g.setFont(font);

                //Dessiner le nom du joueur
                g.drawString(message, x + 10, y - 20);
                if (!(b instanceof Marshall)) {
                    //Afficher le score du joueur
                    g.drawString(score, x + 10, y - 5);
                    //Dessiner le coeur du joueur
                    switch (b.getCoeursRestants()) {
                        case 0:
                            g.drawImage(coeurs[3].getImage(), x + 10, y - 60, getWidth() / 25, getHeight() / 10, this);
                            break;
                        case 1:
                            g.drawImage(coeurs[2].getImage(), x + 10, y - 60, getWidth() / 25, getHeight() / 10, this);
                            break;
                        case 2:
                            g.drawImage(coeurs[1].getImage(), x + 10, y - 60, getWidth() / 25, getHeight() / 10, this);
                            break;
                        case 3:
                            g.drawImage(coeurs[0].getImage(), x + 10, y - 60, getWidth() / 25, getHeight() / 10, this);
                            break;
                        default:
                            break;
                    }
                }
                //Dessiner le personnage (Marshall | bandit)
                g.drawImage(b.getImagePersonnage().getImage(), x, y, 100, 100, this);
                x += 200;

                //Dessiner les gains du joueur
                for (Butin butin : b.getGains()) {
                    if (butin.getName().equals("Bijou")) {
                        g.drawImage(bijoux.getImage(), x - 100, y + distance, getWidth() / 25, getHeight() / 25, this);
                        distance += 20;
                    } else if (butin.getName().equals("Bourse")) {
                        g.drawImage(bourse.getImage(), x - 100, y + distance, getWidth() / 25, getHeight() / 25, this);
                        distance += 20;
                    } else {
                        g.drawImage(magot.getImage(), x - 100, y + distance, getWidth() / 25, getHeight() / 25, this);
                        distance += 20;
                    }
                }
            }
        }
    }
}
