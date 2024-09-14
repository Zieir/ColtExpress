package jeuColtExpress.Modeles;

import static org.junit.jupiter.api.Assertions.*;
import jeuColtExpress.Vue.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.ImageIcon;
import java.util.ArrayList;


public class MarshallTest {
    private Train train;
    private Marshall marshall;
    private ArrayList<Bandit> joueurs;

    @BeforeEach
    public void setUp() {
        train = new Train(5);
        joueurs = new ArrayList<>();
        joueurs.add(new Bandit(train.getFirstWagon(), "Joueur1", new ImageIcon(), new ImageIcon()));
        joueurs.add(new Bandit(train.getDernierWagon(), "Joueur2", new ImageIcon(), new ImageIcon()));
        train.getFirstWagon().ajouterPersonnages(joueurs.getFirst());
        train.getDernierWagon().ajouterPersonnages(joueurs.getLast());
        marshall = new Marshall(train.getLocomotive(), new ImageIcon());
        train.getLocomotive().ajouterPersonnages(marshall);
        joueurs.add(marshall);
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
    public void nervositeMarshall(){
        marshall.setNERVOSITE_MARSHALL(1);
        assertEquals(1.0, marshall.getNERVOSITE_MARSHALL());
        marshall.setNERVOSITE_MARSHALL(0.9);
        assertEquals(0.9, marshall.getNERVOSITE_MARSHALL());
    }

    @Test
    public void testMarshallResteImmobile() {
        marshall.setNERVOSITE_MARSHALL(0);
        marshall.avance();
        assertSame(marshall.getWagon(), train.getLocomotive());
        marshall.recule();
        assertSame(marshall.getWagon(), train.getLocomotive());
    }

    @Test
    public void testRecule() {
        marshall.setNERVOSITE_MARSHALL(1);
        marshall.recule();
        assertNotNull(marshall.getWagon().getPrecedent());
        assertSame(marshall.getWagon(), train.getLocomotive());
        marshall.avance();
        marshall.recule();
        assertSame(marshall.getWagon(), train.getLocomotive());
    }

    @Test
    public void testAvance() {
        marshall.setNERVOSITE_MARSHALL(1);
        marshall.avance();
        assertNotNull(marshall.getWagon().getSuivant());
        assertSame(marshall.getWagon(), train.getFirstWagon());
    }


    @Test
    public void testJoueurPerdUnBijou(){
        Wagon wagon = train.getFirstWagon();
        Bandit bandit = joueurs.getFirst();
        Butin butin = Butin.Bijou;
        bandit.getGains().add(butin);

        marshall.setNERVOSITE_MARSHALL(1);
        marshall.avance();

        assertFalse(bandit.getInterieur());
        assertFalse(bandit.possedeDesGains());
        assertTrue(wagon.butins.contains(butin));
    }

    @Test
    public void testJoueurPerdUneBourse() {
        Wagon wagon = train.getFirstWagon();
        Bandit bandit = joueurs.getFirst();
        Butin butin = Butin.Bourse;
        bandit.getGains().add(butin);

        marshall.setNERVOSITE_MARSHALL(1);
        marshall.avance();

        assertFalse(bandit.getInterieur());
        assertFalse(bandit.possedeDesGains());
        assertTrue(wagon.butins.contains(butin));
    }

    @Test
    public void testJoueurPerdUnMagot() {
        Wagon wagon = train.getFirstWagon();
        Bandit bandit = joueurs.getFirst();
        Butin butin = Butin.Magot;
        bandit.getGains().add(butin);

        marshall.setNERVOSITE_MARSHALL(1);
        marshall.avance();

        assertFalse(bandit.getInterieur());
        assertFalse(bandit.possedeDesGains());
        assertTrue(wagon.butins.contains(butin));
    }
}

