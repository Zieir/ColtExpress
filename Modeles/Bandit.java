package jeuColtExpress.Modeles;

import jeuColtExpress.Vue.VueActionsJoueur;
import jeuColtExpress.Vue.VueJoueur;
import jeuColtExpress.Vue.VuePlanification;

import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.Random;

/**
 * Représente un bandit dans le jeu Colt Express.
 * Les bandits sont des personnages capables de se déplacer à l'intérieur du train,
 * de monter et de descendre du toit, ainsi que de tirer et de braquer.
 */
public class Bandit implements Observable {

    protected String name;

    protected Wagon wagon;
    private ArrayList<jeuColtExpress.Modeles.Action> actions;
    protected ImageIcon image;
    protected ImageIcon imageMort;
    private boolean interieur = true; // Pour savoir s'il est sur le toit ou dans le wagon
    protected ArrayList<Butin> gains;
    private VueActionsJoueur vueActionsJoueur;
    private VueJoueur vueJoueur;
    private VuePlanification vuePlanification;
    int score;
    int NB_BALLES;
    int coeursRestants;

    /**
     * Constructeur de la classe Bandit.
     *
     * @param wagon Le wagon où se trouve le bandit.
     * @param name  Le nom du bandit.
     * @param image L'image représentant le bandit.
     * @param imageMort L'image représentant le bandit mort
     */
    public Bandit(Wagon wagon, String name, ImageIcon image, ImageIcon imageMort) {
        this.wagon = wagon;
        this.name = name;
        this.gains = new ArrayList<>();
        this.actions = new ArrayList<>();
        this.image = image;
        this.NB_BALLES = 6;
        this.score = 0;
        this.coeursRestants = 1;
        this.imageMort = imageMort;
    }

    /**
     * Définit l'image associée à cet objet.
     *
     * @param image L'icône d'image à associer à cet objet.
     */
    public void setImage(ImageIcon image) {
        this.image = image;
    }

    /**
     * Vérifie si le personnage est mort en fonction du nombre de cœurs restants.
     *
     * @return true si le personnage est mort (0 cœur restant), sinon false
     */
    public boolean encoreVivant() {
        return this.coeursRestants > 0;
    }

    /**
     * Vérifie si le joueur est mort et effectue les actions appropriées si tel est le cas.
     * Si le joueur est mort, son image est définie sur une image de mort, son score est 0,
     * et tous les gains qu'il a accumulés sont transférés au butin du wagon. Enfin, les actions et gains du joueur
     * sont effacés, et les observateurs sont notifiés des changements.
     *
     */
    public void testDevie() {
        if (!encoreVivant()) {
            setImage(imageMort);
            setScore(-this.getScore());
            getWagon().getButins().addAll(this.getGains());
            getGains().clear();
            this.wagon.bandits.remove(this);
            getActions().clear();
            notifyActionsJoueur();
            notifyVueJoueur();
        }
    }


    /**
     * Renvoie le nombre de cœurs restants.
     *
     * @return le nombre de cœurs restants
     */
    public int getCoeursRestants() {
        return this.coeursRestants;
    }

    /**
     * Définit le nombre de cœurs restants.
     *
     */
    public void setCoeursRestants() {
        if(coeursRestants > 0)
            this.coeursRestants--;
    }

    /**
     * Renvoie l'image représentant le personnage.
     *
     * @return L'image représentant le personnage.
     */
    public ImageIcon getImagePersonnage() {
        return image;
    }

    /**
     * Définit si le personnage est à l'intérieur du wagon ou sur le toit.
     *
     */
    public void setInterieur() {
        this.interieur = !interieur;
    }

    /**
     * Obtient l'état du personnage, s'il est à l'intérieur du wagon ou sur le toit.
     *
     * @return true si le personnage est à l'intérieur du wagon, false sinon.
     */
    public boolean getInterieur() {
        return this.interieur;
    }

    /**
     * Vérifie si le bandit possède des gains.
     *
     * @return true si le bandit possède des gains, sinon false.
     */
    public boolean possedeDesGains() {
        return !this.gains.isEmpty();
    }

    /**
     * Permet au bandit de monter sur le toit du train.
     * Si le bandit est déjà à l'extérieur du wagon, aucune action n'est effectuée.
     * Notifie également les observateurs après le changement.
     */
    public void monter() {
        System.out.println(this.name + " est monté sur le toit");
        if (this.getInterieur()) {
            this.setInterieur();
            this.notifyActionsJoueur();
        }
    }

    /**
     * Renvoie le score actuel.
     *
     * @return le score actuel
     */
    public int getScore() {
        return score;
    }

    /**
     * Met à jour le score en ajoutant la valeur du butin spécifié.
     *
     * @param butinValeur la valeur du butin à ajouter au score
     */
    public void setScore(int butinValeur) {
        score += butinValeur;
    }

    /**
     * Fait reculer le personnage d'un wagon s'il n'est pas dans la locomotive.
     * Notifie également les observateurs après le changement.
     */
    public void recule() {
        System.out.println(this.name + " a reculé");
        if (!wagon.isLocomotive()) {
            this.wagon.bandits.remove(this);
            this.wagon = this.wagon.getPrecedent();
            this.wagon.ajouterPersonnages(this);
            if(wagon.marshallPresent())
                this.setInterieur();
            this.notifyActionsJoueur();
        }
    }

    /**
     * Fait avancer le personnage d'un wagon s'il n'est pas le dernier wagon
     * Notifie également les observateurs après le changement.
     */
    public void avance() {
        System.out.println(this.name +" a avancé");
        if (!this.wagon.estDernierWagon()) {
            this.wagon.bandits.remove(this);
            this.wagon = this.wagon.getSuivant();
            this.wagon.ajouterPersonnages(this);
            if(wagon.marshallPresent())
                this.setInterieur();
            this.notifyActionsJoueur();
        }
    }

    /**
     * Permet au bandit de descendre du toit du train.
     * Si le bandit est déjà à l'intérieur du wagon, aucune action n'est effectuée.
     * Notifie également les observateurs après le changement.
     */
    public void descendre() {
        System.out.println(this.name +" est descendu du toit");
        if (!this.getInterieur() && !wagon.marshallPresent()) {
            this.setInterieur();
            this.notifyActionsJoueur();
        }
    }

    /**
     * Permet au personnage de braquer le wagon et de récupérer un butin aléatoire s'il y en a.
     * Notifie également les observateurs après le changement.
     */
    public void braquer() {
        System.out.println(this.name +" a braqué le wagon");
        if (this.wagon.thereIsButin() && this.getInterieur()) {
            Butin butin = this.wagon.getButinAleatoireWagon();
            this.gains.add(butin);
            this.setScore(butin.getValue());
            System.out.println(this.name + " a gagné un " + butin.getName() + " de valeur " + butin.getValue());
            this.wagon.modifierWagonButins(butin);
            this.notifyActionsJoueur();
            this.notifyVueJoueur();
        }
    }

    /**
     * Renvoie la liste des actions planifiées pour ce bandit.
     *
     * @return La liste des actions planifiées pour ce bandit.
     */
    public ArrayList<jeuColtExpress.Modeles.Action> getActions() {
        return this.actions;
    }

    /**
     * Supprime un butin aléatoire de la liste des gains du bandit et le retourne.
     * Si la liste des gains est vide, retourne null.
     *
     * @return Le butin aléatoire retiré des gains du bandit, ou null si la liste est vide.
     */
    public Butin removeGainsAleatoire() {
        // Vérifie si la liste des gains est vide
        if (this.gains.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(this.gains.size());
        Butin butin = this.gains.remove(randomIndex);
        this.setScore(-butin.getValue());
        return butin;
    }

    /**
     * Permet au personnage de tirer sur un autre bandit présent dans le train.
     * Notifie également les observateurs après le changement.
     */
    public void tirer() {
        if (NB_BALLES > 0) {
            // On réduit le nombre de balles du joueur.
            NB_BALLES -= 1;

            //Choisir la direction du tir aléatoirement
            Random random = new Random();
            Direction direction = Direction.values()[random.nextInt(Direction.values().length)];
            if (this.getInterieur()) {
                System.out.println(this.name + " est à l'intérieur du wagon " + wagon.getNumero() + " a tiré vers " + direction);
                System.out.println(this.name + " lui reste " + NB_BALLES + "balles");
            } else {
                System.out.println(this.name + " est sur le toit du wagon " + wagon.getNumero() + " a tiré vers " + direction);
                System.out.println(this.name + " lui reste " + NB_BALLES);
            }
            Bandit banditCible = null;
            switch (direction) {
                case HAUT:
                    if (this.getInterieur() && this.wagon.presenceJoueurSurLeToit()) {
                        banditCible = this.wagon.getBanditAleatoireSurLeToit();
                    }
                    break;
                case BAS:
                    if (!this.getInterieur() && this.wagon.presenceJoueurInterieur()) {
                        banditCible = this.wagon.getBanditAleatoireSousLeToit();
                    }
                    break;
                case DROITE:
                    if (!this.getInterieur() && this.wagon.presenceJoueurDroiteToit()) {
                        banditCible = this.wagon.getBanditAleatoireDroiteToit();
                    } else if (this.getInterieur() && this.wagon.presenceJoueurDroiteInterieur()) {
                        banditCible = this.wagon.getBanditAleatoireDroiteInterieur();
                    }
                    break;
                case GAUCHE:
                    if (!this.getInterieur() && this.wagon.presenceJoueurGaucheToit()) {
                        banditCible = this.wagon.getBanditAleatoireGaucheToit();
                    } else if (this.getInterieur() && this.wagon.presenceJoueurGaucheInterieur()) {
                        banditCible = this.wagon.getBanditAleatoireGaucheInterieur();
                    }
                    break;
                default:
                    break;
            }

            if (banditCible != null) {
                banditCible.setCoeursRestants();
                banditCible.testDevie();
                System.out.println(banditCible.name + " a reçu une balle");
                if (banditCible.possedeDesGains()) {
                    Butin butin = banditCible.removeGainsAleatoire();
                    System.out.println("Il a lâché un " + butin.getName());
                    banditCible.wagon.butins.add(butin);
                }
                banditCible.notifyActionsJoueur();
                banditCible.notifyVueJoueur();
            }
        } else {
            System.out.println(this.name + " n'a plus de balle !");
        }
    }

    /**
     * Définit les actions planifiées pour ce bandit.
     *
     * @param actions La liste des actions planifiées pour ce bandit.
     */
    public void setActions(ArrayList<jeuColtExpress.Modeles.Action> actions) {
        this.actions = actions;
    }

    /**
     * Récupère le wagon dans lequel le personnage se trouve.
     *
     * @return Le wagon dans lequel le personnage se trouve.
     */
    public Wagon getWagon(){
        return wagon;
    }

    /**
     * Récupère la liste des gains possédés par le bandit.
     *
     * @return La liste des gains possédés par le bandit.
     */
    public ArrayList<Butin> getGains(){
        return this.gains;
    }

    /**
     * Récupère le nom du bandit.
     *
     * @return Le nom du bandit.
     */
    public String getName(){
        return name;
    }

    /**
     * Définit le nombre de balles disponibles pour le bandit.
     *
     * @param NB_BALLES Le nombre de balles disponibles.
     */
    public void setNB_BALLES(int NB_BALLES) {
        this.NB_BALLES = NB_BALLES;
    }

    @Override
    public void addObserver(VueActionsJoueur vueActionsJoueur) {
        this.vueActionsJoueur = vueActionsJoueur;
    }

    /**
     * Ajoute une vue du joueur à observer.
     *
     * @param vueJoueur La vue du joueur à observer.
     */
    public void addObserver(VueJoueur vueJoueur) {
        this.vueJoueur = vueJoueur;
    }

    /**
     * Ajoute une vue de la planification à observer.
     *
     * @param vuePlanification La vue de la planification à observer.
     */
    public void addObserver(VuePlanification vuePlanification){
        this.vuePlanification = vuePlanification;
    }

    /**
     * Notifie la vue des actions du joueur.
     */
    public void notifyActionsJoueur() {
        this.vueActionsJoueur.update(vueActionsJoueur, this);
    }

    /**
     * Notifie la vue du joueur.
     */
    public void notifyVueJoueur(){
        this.vueJoueur.update1(vueJoueur, this);
    }

    /**
     * Notifie la vue de la planification.
     */
    public void notifyVuePlanification(){
        this.vuePlanification.update2(vuePlanification, this);
    }
}
