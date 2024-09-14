package jeuColtExpress.Modeles;

import jeuColtExpress.Vue.VueActionsJoueur;
import jeuColtExpress.Vue.VueJoueur;
import jeuColtExpress.Vue.VuePlanification;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Modele extends JFrame {
    public Modele(int nombreWagon, int nombreDeJoueurs) {
        Train train = new Train(nombreWagon);
        ImageIcon[] banditImages = {
                new ImageIcon(Objects.requireNonNull(VuePlanification.class.getResource("../Images/Bandit1.png"))),
                new ImageIcon(Objects.requireNonNull(VuePlanification.class.getResource("../Images/Bandit2.png"))),
                new ImageIcon(Objects.requireNonNull(VuePlanification.class.getResource("../Images/Bandit3.png"))),
                new ImageIcon(Objects.requireNonNull(VuePlanification.class.getResource("../Images/Bandit4.png")))
        };

        ImageIcon[] banditImagesMorts = {
                new ImageIcon(Objects.requireNonNull(VuePlanification.class.getResource("../Images/BanditMort1.png"))),
                new ImageIcon(Objects.requireNonNull(VuePlanification.class.getResource("../Images/BanditMort2.png"))),
                new ImageIcon(Objects.requireNonNull(VuePlanification.class.getResource("../Images/BanditMort3.png"))),
                new ImageIcon(Objects.requireNonNull(VuePlanification.class.getResource("../Images/BanditMort4.png")))
        };

        Marshall marshall = new Marshall(train.getLocomotive(), new ImageIcon(Objects.requireNonNull(VuePlanification.class.getResource("../Images/Marshall.png"))));
        marshall.setActions(genererActionsAleatoires());

        ArrayList<Bandit> bandits = new ArrayList<>();
        bandits.add(marshall);
        for (int i = 1; i <= nombreDeJoueurs; i++) {
            bandits.add(new Bandit(train.getFirstWagon(), "Joueur " + i, banditImages[i - 1], banditImagesMorts[i - 1]));
        }

        // Création des vues
        VuePlanification vuePlanification = new VuePlanification(bandits);
        VueActionsJoueur vueActionsJoueur = new VueActionsJoueur(train, bandits);
        VueJoueur vueJoueur = new VueJoueur(bandits);

        // Ajout d'observateurs pour les bandits
        for (Bandit b : bandits) {
            train.getFirstWagon().ajouterPersonnages(b);
            b.addObserver(vueActionsJoueur);
            b.addObserver(vueJoueur);
            b.addObserver(vuePlanification);
        }

        // Création d'un conteneur principal
        JPanel mainPanel = new JPanel(new GridLayout(3, 1));

        // Ajout des vues au conteneur principal
        mainPanel.add(vuePlanification.getContentPane());
        mainPanel.add(vueActionsJoueur.getContentPane());
        mainPanel.add(vueJoueur.getContentPane());

        // Ajout du conteneur principal à la fenêtre
        this.add(mainPanel);

        // Configuration de la fenêtre principale
        setTitle("Jeu");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static ArrayList<jeuColtExpress.Modeles.Action> genererActionsAleatoires() {
        ArrayList<jeuColtExpress.Modeles.Action> actions = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            if (random.nextBoolean()) {
                actions.add(Action.Avance);
            } else {
                actions.add(Action.Recule);
            }
        }
        return actions;
    }
}


