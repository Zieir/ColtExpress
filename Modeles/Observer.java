package jeuColtExpress.Modeles;

import jeuColtExpress.Vue.VueActionsJoueur;
import jeuColtExpress.Vue.VueJoueur;
import jeuColtExpress.Vue.VuePlanification;

public interface Observer {

    void update(VueActionsJoueur o, Object arg);

    void update1(VueJoueur o, Object arg);

    void update2(VuePlanification o, Object arg);
}