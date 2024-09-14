package jeuColtExpress.Modeles;

import static org.junit.jupiter.api.Assertions.*;
import jeuColtExpress.Vue.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.ImageIcon;
import java.util.ArrayList;


class WagonTest {
    private  Train train;
    private ArrayList<Bandit> joueurs;

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
    public void testAjouterButins() {
        Wagon wagon = train.getFirstWagon();
        wagon.ajouterButins();
        assertFalse(wagon.getButins().isEmpty());
    }

    @Test
    public void testPresenceJoueurInterieur() {
        Wagon wagon = train.getFirstWagon();
        wagon.ajouterPersonnages(joueurs.getFirst());
        assertTrue(wagon.presenceJoueur(true));
    }

    @Test
    public void testPresenceJoueurSurLeToit() {
        Wagon wagon = train.getFirstWagon();
        Bandit bandit = joueurs.getFirst();
        bandit.setInterieur();
        wagon.ajouterPersonnages(bandit);
        assertTrue(wagon.presenceJoueur(false));
    }

    @Test
    public void testMarshallPresent() {
        Wagon wagon = train.getLocomotive();
        assertTrue(wagon.marshallPresent());
    }

    @Test
    public void testGetButinAleatoireWagon() {
        Wagon wagon = train.getFirstWagon();
        wagon.ajouterButins();
        assertNotNull(wagon.getButinAleatoireWagon());
    }

    @Test
    public void testGetNumero() {
        assertEquals(1, train.getFirstWagon().getNumero());
        assertEquals(0, train.getFirstWagon().getPrecedent().getNumero());
        assertEquals(2, train.getFirstWagon().getSuivant().getNumero());

    }

    @Test
    public void testGetSuivant(){
        Wagon wagon1 = train.getLocomotive();
        Wagon wagon2 = train.getFirstWagon();
        assertEquals(wagon2, wagon1.getSuivant());
    }

    @Test
    public void testGetPrecedent(){
        Wagon wagon1 = train.getLocomotive();
        Wagon wagon2 = train.getFirstWagon();
        assertEquals(wagon1, wagon2.getPrecedent());
    }
}