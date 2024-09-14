package jeuColtExpress.Vue;

import jeuColtExpress.Modeles.Observer;
import jeuColtExpress.Modeles.Train;
import jeuColtExpress.Modeles.Wagon;
import jeuColtExpress.Modeles.Butin;
import jeuColtExpress.Modeles.Bandit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * La classe VueActionsJoueur représente la vue des actions des joueurs dans le jeu.
 * Elle étend JFrame et implémente l'interface Observer pour observer les mises à jour.
 */
public class VueActionsJoueur extends JFrame implements Observer {
    private final ArrayList<Bandit> bandits;
    private final Train train;

    /**
     * Constructeur de la classe VueActionsJoueur.
     *
     * @param train   Le train dans le jeu.
     * @param bandits La liste des bandits dans le jeu.
     */
    public VueActionsJoueur(Train train, ArrayList<Bandit> bandits) {
        this.bandits = bandits;
        this.train = train;

        setSize(800, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Chargement des images pour le fond et le train
        ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/plan.png")));
        ImageIcon trainImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/train.png")));
        ImageIcon bijoux = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/bijoux.png")));
        ImageIcon bourse = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/Bourse.png")));
        ImageIcon magot = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/magot.png")));

        // Création et ajout du panneau principal de la vue
        MainPanel mainPanel = new MainPanel(backgroundImage, trainImage, bijoux, bourse, magot);
        add(mainPanel);
    }

    /**
     * Méthode appelée lorsqu'un observable (dans ce cas, la VueActionsJoueur elle-même) est mis à jour.
     * Elle récupère le panneau principal et le redessine.
     *
     * @param o   L'observable mis à jour.
     * @param arg L'argument passé lors de la mise à jour.
     */
    @Override
    public void update(VueActionsJoueur o, Object arg) {
        MainPanel mainPanel;
        mainPanel = (MainPanel) getContentPane().getComponent(0);
        mainPanel.repaint();
    }

    @Override
    public void update1(VueJoueur o, Object arg) {
        MainPanel mainPanel = (MainPanel) getContentPane().getComponent(0);
        mainPanel.repaint();
    }

    @Override
    public void update2(VuePlanification o, Object args){
        MainPanel mainPanel = (MainPanel) getContentPane().getComponent(0);
        mainPanel.repaint();
    }

    /**
     * La classe MainPanel représente le panneau principal de la vue.
     */
    private class MainPanel extends JPanel {
        private final ImageIcon backgroundImage;
        private final ImageIcon trainImage;
        private final ImageIcon bijoux;
        private final ImageIcon bourse;
        private final ImageIcon magot;

        /**
         * Constructeur de la classe MainPanel.
         *
         * @param backgroundImage L'image de fond du panneau.
         * @param trainImage      L'image représentant le train.
         * @param bijoux          L'image représentant les bijoux.
         * @param bourse          L'image représentant la bourse.
         * @param magot           L'image représentant le magot.
         */
        public MainPanel(ImageIcon backgroundImage, ImageIcon trainImage, ImageIcon bijoux, ImageIcon bourse, ImageIcon magot) {
            this.backgroundImage = backgroundImage;
            this.trainImage = trainImage;
            this.bijoux = bijoux;
            this.bourse = bourse;
            this.magot = magot;
        }

        /**
         * Méthode permettant de dessiner les éléments sur le panneau.
         *
         * @param g L'objet Graphics utilisé pour dessiner.
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int x = 0;

            // Dessin du fond et du train
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            g.drawImage(trainImage.getImage(), 0, 150, getWidth(), getHeight() / 2, this);

            // Dessin des butins
            Wagon wagon = train.getLocomotive();
            do{
                int distance = 10;
                int y = 200;
                switch (wagon.getNumero()) {
                    case 0 : x = 160;
                        break;
                    case 1 : x = 300;
                        break;
                    case 2 : x = 500;
                        break;
                    case 3 : x = 750;
                        break;
                    case 4 : x = 1000;
                        break;
                    default:
                        break;
                }

                for (Butin butin : wagon.getButins()) {
                    if (butin.getName().equals("Bijou")) {
                        g.drawImage(bijoux.getImage(), x + 20 + distance, y, getWidth() / 25, getHeight() / 25, this);
                        distance += 20;
                    } else if (butin.getName().equals("Bourse")) {
                        g.drawImage(bourse.getImage(), x + 20 + distance, y, getWidth() / 25, getHeight() / 25, this);
                        distance += 20;
                    }
                    else{
                        g.drawImage(magot.getImage(), 180, 200, getWidth() / 20, getHeight() / 20, this);
                    }
                }
                wagon = wagon.getSuivant();
            } while (wagon != train.getLocomotive());

            int distance = 10;
            int y = 0;
            // Dessin des bandits
            for (Bandit b : bandits) {
                switch (b.getWagon().getNumero()) {
                    case 0 -> {
                        x = 150;
                        y = 150;
                    }
                    case 1 -> {
                        x = 280;
                        y = 150;
                    }
                    case 2 -> {
                        x = 480;
                        y = 150;
                    }
                    case 3 -> {
                        x = 750;
                        y = 150;
                    }
                    case 4 -> {
                        x = 980;
                        y = 150;
                    }
                }
                // Dessin du bandit
                if (b.getInterieur()) {
                    g.drawImage(b.getImagePersonnage().getImage(), x + distance, y, 70, 70, this);
                } else {
                    g.drawImage(b.getImagePersonnage().getImage(), x + distance, y - 50, 70, 70, this);
                }
                distance += 20;
            }
        }
    }
}

