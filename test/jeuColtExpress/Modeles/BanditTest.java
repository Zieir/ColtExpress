package jeuColtExpress.Modeles;


import jeuColtExpress.Vue.VueActionsJoueur;
import jeuColtExpress.Vue.VueJoueur;
import jeuColtExpress.Vue.VuePlanification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class BanditTest {
    private  Train train;
    private  ArrayList<Bandit> joueurs;

    @BeforeEach
    public void setUp() {
        train = new Train(5);
        joueurs = new ArrayList<>();
        joueurs.add(new Bandit(train.getFirstWagon(), "Joueur1", new ImageIcon(), new ImageIcon()));
        joueurs.add(new Bandit(train.getDernierWagon(), "Joueur2", new ImageIcon(), new ImageIcon()));
        train.getFirstWagon().ajouterPersonnages(joueurs.getFirst());
        train.getDernierWagon().ajouterPersonnages(joueurs.getLast());
        Marshall marshall = new Marshall(train.getLocomotive(), new ImageIcon());
        train.getLocomotive().ajouterPersonnages(marshall);
        VueActionsJoueur vueActionsJoueur = new VueActionsJoueur(train, joueurs);
        VueJoueur vueJoueur = new VueJoueur(joueurs);
        VuePlanification vuePlanification = new VuePlanification(joueurs);
        for (Bandit b : joueurs) {
            b.addObserver(vueActionsJoueur);
            b.addObserver(vueJoueur);
            b.addObserver(vuePlanification);
        }
    }
    @Test
    public void testEncoreVivant() {
        Wagon wagon = new Wagon(new Train(5), 1);
        Bandit bandit = new Bandit(wagon, "TestBandit", new ImageIcon(), new ImageIcon());
        assertTrue(bandit.encoreVivant());
        bandit.setCoeursRestants();
        assertFalse(bandit.encoreVivant());
    }

    @Test
    public void testMonterEtDescendre() {
        Bandit bandit = joueurs.getFirst();
        Assertions.assertTrue(bandit.getInterieur());
        bandit.monter();
        Assertions.assertFalse(bandit.getInterieur());
        bandit.descendre();
        Assertions.assertTrue(bandit.getInterieur());
    }

    @Test
    public void testAvanceEtRecule() {
        Wagon wagon1 = train.getFirstWagon();
        Wagon wagon2 = train.getFirstWagon().getSuivant();
        Bandit bandit = joueurs.getFirst();
        bandit.avance();
        assertEquals(wagon2, bandit.getWagon());
        bandit.recule();
        assertEquals(wagon1, bandit.getWagon());
    }

    @Test
    public void testAvancerDernierWagon(){
        Bandit bandit = joueurs.getLast();
        assertSame(bandit.getWagon(), train.getDernierWagon());
        bandit.avance();
        assertSame(bandit.getWagon(), train.getDernierWagon());
    }

    @Test
    public void testReculerVersLocomotive(){
        Bandit bandit = joueurs.getFirst();
        assertSame(bandit.getWagon(), train.getFirstWagon());
        bandit.recule();
        assertSame(bandit.getWagon(), train.getLocomotive());
        assertFalse(bandit.getInterieur());
    }

    @Test
    public void testGainsBandit1(){
        Bandit bandit = joueurs.getFirst();
        assertFalse(bandit.possedeDesGains());
        Butin butin = Butin.Bijou;
        bandit.getGains().add(butin);
        assertTrue(bandit.possedeDesGains());
    }

    @Test
    public void testButinBandit(){
        Bandit bandit = joueurs.getFirst();
        assertFalse(bandit.possedeDesGains());
        Butin butin = Butin.Bijou;
        bandit.getGains().add(butin);
        assertTrue(bandit.possedeDesGains());
    }

    @Test
    public void testBraquerWagonGains(){
        Wagon wagon = train.getFirstWagon();
        Bandit bandit = joueurs.getFirst();
        Butin butin = Butin.Bijou;
        wagon.butins.add(butin);
        assertFalse(bandit.possedeDesGains());
        bandit.braquer();
        assertTrue(bandit.possedeDesGains());
    }

    @Test
    public void testBraquerWagonPasDeGains(){
        Wagon wagon = train.getFirstWagon();
        Bandit bandit = joueurs.getFirst();
        wagon.butins.clear();
        assertFalse(bandit.possedeDesGains());
        bandit.braquer();
        assertFalse(bandit.possedeDesGains());
    }

    @Test
    public void testBanditPrendUnSeulButin(){
        Wagon wagon = train.getFirstWagon();
        Bandit bandit = joueurs.getFirst();
        Butin butin1 = Butin.Bijou;
        Butin butin2 = Butin.Bourse;
        wagon.butins.clear();
        wagon.butins.add(butin1);
        wagon.butins.add(butin2);
        assertFalse(bandit.possedeDesGains());
        bandit.braquer();
        assertTrue(bandit.possedeDesGains());
        assertTrue(wagon.thereIsButin());
        bandit.braquer();
        assertFalse(wagon.thereIsButin());
    }

    @Test
    public void testTirer(){
        Bandit bandit = joueurs.getFirst();
        assertEquals(bandit.NB_BALLES, 6);
        bandit.tirer();
        assertEquals(bandit.NB_BALLES, 5);
    }

    @Test
    public void testTireAvecAucuneBalle(){
        Bandit bandit = joueurs.getFirst();
        assertEquals(bandit.NB_BALLES, 6);
        bandit.setNB_BALLES(0);
        assertEquals(bandit.NB_BALLES, 0);
        bandit.tirer();
        assertEquals(bandit.NB_BALLES, 0);
    }
}

