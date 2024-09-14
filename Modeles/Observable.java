package jeuColtExpress.Modeles;

import jeuColtExpress.Vue.VueActionsJoueur;
import jeuColtExpress.Vue.VuePlanification;

public interface Observable {
    void addObserver(VueActionsJoueur vueActionsJoueur);

    void addObserver(VuePlanification vuePlanification);

    void notifyActionsJoueur();

    void notifyVueJoueur();

    void notifyVuePlanification();
}