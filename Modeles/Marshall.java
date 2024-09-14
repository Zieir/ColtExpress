package jeuColtExpress.Modeles;

import javax.swing.ImageIcon;
import java.util.Random;

/**
 * La classe Marshall représente le personnage du Marshall dans le jeu Colt Express.
 * Le Marshall est un type spécial de personnage qui assure l'ordre dans le train.
 * Il hérite de la classe Bandit.
 */
public class Marshall extends Bandit {
    private double NERVOSITE_MARSHALL = 0.3;

    /**
     * Constructeur de la classe Marshall.
     *
     * @param wagon Le wagon dans lequel le Marshall se trouve.
     * @param image L'image représentant le Marshall.
     */
    public Marshall(Wagon wagon, ImageIcon image) {
        super(wagon, "Marshall", image, image);
    }

    /**
     * Recule le Marshall vers le wagon précédent avec une probabilité définie par sa nervosité.
     * S'il recule, les bandits dans le wagon précédant montent sur le toit et lâchent un
     * des butins s'ils en possèdent.
     * Notifie les observateurs après le déplacement.
     */
    @Override
    public void recule() {
        deplacer(false);
    }

    /**
     * Déplace le Marshall vers le wagon suivant avec une probabilité définie par sa nervosité.
     * S'il se déplace, les bandits dans le wagon suivant montent sur le toit et lâchent un
     * des butins s'ils en possèdent.
     * Notifie les observateurs après le déplacement.
     */
    @Override
    public void avance() {
        deplacer(true);
    }

    /**
     * Deplace le Marshall selon une probabilite definie.
     *
     * @param versAvant true si le deplacement doit se faire vers l'avant, false sinon.
     */
    private void deplacer(boolean versAvant) {
        Random random = new Random();
        double probabilite = random.nextDouble();

        // Verifie si le Marshall se deplace avec la probabilite definie
        if (probabilite <= NERVOSITE_MARSHALL) {
            if (peutSeDeplacer(versAvant)) {
                Wagon wagonDestination = versAvant ? wagon.getSuivant() : wagon.getPrecedent();
                System.out.println("Deplacement du Marshall vers le wagon numero " + wagonDestination.getNumero());
                System.out.println("Le Marshall tire !");
                transfererBanditsEtGains(wagonDestination);
                actualiserPosition(wagonDestination);
                notifierObservateurs();
            } else {
                System.out.println("Le Marshall ne peut pas se deplacer dans cette direction.");
            }
        } else {
            System.out.println("Le Marshall reste ou il est.");
        }
    }

    /**
     * Transferer les bandits vers un nouveau wagon et faire tomber un gain aléatoire si ils en possèdent
     *
     * @param wagonDestination Le wagon de destination.
     */
    private void transfererBanditsEtGains(Wagon wagonDestination) {
        for (Bandit bandit : wagonDestination.bandits) {
            if (!(bandit instanceof Marshall)) {
                bandit.monter();
                transfererGainsSiNecessaire(bandit, wagonDestination);
            }
        }
    }

    /**
     * Transferer les gains d'un bandit vers un wagon s'il en possede.
     *
     * @param bandit           Le bandit concerne.
     * @param wagonDestination Le wagon de destination.
     */
    private void transfererGainsSiNecessaire(Bandit bandit, Wagon wagonDestination) {
        if (bandit.possedeDesGains()) {
            Butin butin = bandit.removeGainsAleatoire();
            wagonDestination.butins.add(butin);
        }
    }

    /**
     * Actualiser la position du Marshall vers un nouveau wagon.
     *
     * @param nouvellePosition La nouvelle position du Marshall.
     */
    private void actualiserPosition(Wagon nouvellePosition) {
        this.wagon.bandits.remove(this);
        this.wagon = nouvellePosition;
    }

    /**
     * Notifier les observateurs apres le deplacement du Marshall.
     */
    private void notifierObservateurs() {
        notifyActionsJoueur();
        notifyVueJoueur();
    }

    /**
     * Définit la nervosité du Marshall.
     *
     * @param nervositeMarshall La valeur de nervosité du Marshall.
     */
    public void setNERVOSITE_MARSHALL(double nervositeMarshall) {
        this.NERVOSITE_MARSHALL = nervositeMarshall;
    }

    /**
     * Obtient le niveau de nervosité actuel du Marshall.
     *
     * @return Le niveau de nervosité du Marshall.
     */
    public double getNERVOSITE_MARSHALL(){
        return NERVOSITE_MARSHALL;
    }


    /**
     * Vérifie si le Marshall peut se déplacer dans la direction spécifiée.
     *
     * @param versAvant Indique si le déplacement est vers l'avant (true) ou vers l'arrière (false) du train.
     * @return true si le déplacement est possible, sinon false.
     */
    private boolean peutSeDeplacer(boolean versAvant) {
        if (versAvant)
            return !this.wagon.getSuivant().isLocomotive();
        return !this.wagon.getPrecedent().estDernierWagon();
    }

    /**
     * @return true Indique que Marshall est toujours à l'intérieur
     */
    @Override
    public boolean getInterieur() {
        return true;
    }
}



