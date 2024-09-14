package jeuColtExpress.Modeles;

import java.util.ArrayList;
import java.util.Random;

/**
 * La classe Wagon représente un wagon dans le jeu Colt Express, pouvant contenir des personnages et des butins.
 * Chaque wagon est connecté à un autre wagon par une relation de lien suivant/précédent.
 */
public class Wagon {
    private final Train train; // Le train auquel appartient le wagon
    private Wagon suivant; // Le wagon suivant dans le train
    private Wagon precedent; // Le wagon précédent dans le train
    protected ArrayList<Bandit> bandits; // Liste des bandits présents dans le wagon
    protected ArrayList<Butin> butins; // Liste des butins présents dans le wagon
    private final int numero; // Numéro du wagon

    /**
     * Constructeur de la classe Wagon.
     *
     * @param t Le train auquel appartient le wagon.
     * @param numero Le numéro du wagon.
     */
    public Wagon(Train t, int numero) {
        this.train = t;
        this.bandits = new ArrayList<>();
        this.butins = new ArrayList<>();
        this.numero = numero;
    }

    /**
     * Définit le wagon suivant dans le train.
     *
     * @param suivant Le wagon suivant dans le train.
     */
    public void setSuivant(Wagon suivant) {
        this.suivant = suivant;
    }

    /**
     * Renvoie le wagon suivant dans le train.
     *
     * @return Le wagon suivant dans le train.
     */
    public Wagon getSuivant() {
        return this.suivant;
    }

    /**
     * Renvoie le wagon précédent dans le train.
     *
     * @return Le wagon précédent dans le train.
     */
    public Wagon getPrecedent() {
        return this.precedent;
    }

    /**
     * Définit le wagon précédent dans le train.
     *
     * @param precedent Le wagon précédent dans le train.
     */
    public void setPrecedent(Wagon precedent) {
        this.precedent = precedent;
    }

    /**
     * Renvoie le numéro du wagon.
     *
     * @return Le numéro du wagon.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Renvoie le train.
     *
     * @return Le train.
     */
    public Train getTrain() {
        return this.train;
    }

    /**
     * Vérifie si ce wagon est le dernier wagon du train.
     *
     * @return true si c'est le dernier wagon, false sinon.
     */
    public boolean estDernierWagon() {
        return this.suivant == train.getLocomotive();
    }

    /**
     * Vérifie si ce wagon est une locomotive.
     *
     * @return true si c'est une locomotive, false sinon.
     */
    public boolean isLocomotive() {
        return false;
    }


    /**
     * Obtient un butin aléatoire parmi ceux présents dans la liste de butins.
     * Si la liste de butins est vide, retourne null.
     *
     * @return Un butin aléatoire ou null si la liste de butins est vide.
     */
    public Butin getButinAleatoireWagon() {
        Random random = new Random();
        int randomIndex = random.nextInt(butins.size());

        return butins.get(randomIndex);
    }

    /**
     * Récupère la liste des butins présents dans le wagon.
     *
     * @return La liste des butins présents dans le wagon.
     */
    public ArrayList<Butin> getButins(){
        return this.butins;
    }

    /**
     * Efface tous les butins du wagon.
     */
    public void modifierWagonButins(Butin butin) {
        this.butins.remove(butin);
    }

    /**
     * Vérifie s'il y a des butins dans le wagon.
     *
     * @return true s'il y a des butins, false sinon.
     */
    public boolean thereIsButin() {
        return !this.butins.isEmpty();
    }

    /**
     * Ajoute aléatoirement des butins au wagon.
     */
    public void ajouterButins() {
        Random random = new Random();
        int r = random.nextInt(5); // Choix aléatoire du nombre de butins à ajouter (entre 0 et 4)
        for (int i = 0; i < r; i++) {
            if (random.nextBoolean()) {
                System.out.println("Un bijou a été ajouté au wagon" + " " + this.getNumero());
                this.butins.add(Butin.Bijou);
            } else {
                System.out.println("Une bourse a été ajouté au wagon" + " " + this.getNumero());
                this.butins.add(Butin.Bourse);
            }
        }
    }

    /**
     * Ajoute un personnage (bandit) au wagon.
     *
     * @param b Le bandit à ajouter.
     */
    public void ajouterPersonnages(Bandit b) {
        this.bandits.add(b);
    }

    /**
     * Vérifie s'il y a d'autres joueurs que le bandit actuel dans une certaine partie du wagon.
     *
     * @param underRoof true pour vérifier sur le toit, false pour vérifier à l'intérieur du wagon.
     * @return true s'il y a d'autres joueurs que le bandit actuel dans la partie spécifiée du wagon, sinon false.
     */
    public boolean presenceJoueur(boolean underRoof) {
        for (Bandit bandit : this.bandits) {
            if (bandit.getInterieur() == underRoof) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie s'il y a d'autres joueurs sur le toit que le bandit actuel  sur le toit.
     * Le bandit actuel se trouve à l'intérieur et veut tirer vers le haut
     *
     * @return true s'il y a d'autres joueurs que le bandit actuel sur le toit, sinon false.
     */
    public boolean presenceJoueurSurLeToit() {
        return presenceJoueur(false);
    }

    /**
     * Vérifie s'il y a d'autres joueurs que le bandit actuel à l'intérieur du wagon.
     * Le bandit actuel se trouve sur le toit et veut tirer vers le bas
     *
     * @return true s'il y a d'autres joueurs que le bandit actuel à l'intérieur du wagon, sinon false.
     */
    public boolean presenceJoueurInterieur() {
        return presenceJoueur(true);
    }

    /**
     * Vérifie s'il y a d'autres joueurs que le bandit actuel dans un sens donné du wagon.
     *
     * @param forward true pour vérifier vers l'avant du train, false pour vérifier vers l'arrière du train.
     * @return true s'il y a d'autres joueurs que le bandit actuel dans le sens spécifié du wagon, sinon false.
     */
    public boolean presenceJoueurDirectionInterieur(boolean forward) {
        // Détermine le wagon suivant ou précédent selon la direction spécifiée
        Wagon currentWagon = forward ? this.getSuivant() : this.getPrecedent();
        // Détermine la limite du parcours selon la direction spécifiée
        Wagon limite = forward ? train.getLocomotive() : train.getDernierWagon();

        // Parcours des wagons dans la direction spécifiée jusqu'à atteindre la limite
        while (currentWagon != limite) {
            if (currentWagon.presenceJoueurInterieur()) {
                return true;
            }
            currentWagon = forward ? currentWagon.getSuivant() : currentWagon.getPrecedent();
        }
        return false;
    }

    /**
     * Vérifie s'il y a d'autres joueurs que le bandit actuel à droite du wagon à l'intérieur.
     *
     * @return true s'il y a d'autres joueurs que le bandit actuel à droite du wagon à l'intérieur, sinon false.
     */
    public boolean presenceJoueurDroiteInterieur() {
        // Appelle la méthode presenceJoueurInterieur en spécifiant la direction vers la droite
        return presenceJoueurDirectionInterieur(true);
    }

    /**
     * Vérifie s'il y a d'autres joueurs que le bandit actuel à gauche du wagon à l'intérieur.
     *
     * @return true s'il y a d'autres joueurs que le bandit actuel à gauche du wagon à l'intérieur, sinon false.
     */
    public boolean presenceJoueurGaucheInterieur() {
        // Appelle la méthode presenceJoueurInterieur en spécifiant la direction vers la gauche
        return presenceJoueurDirectionInterieur(false);
    }


    /**
     * Vérifie s'il y a d'autres joueurs que le bandit actuel dans une certaine direction et sur le toit.
     *
     * @param forward true pour vérifier vers l'avant du train (droite), false pour vérifier vers l'arrière du train (gauche).
     * @return true s'il y a d'autres joueurs que le bandit actuel dans la direction spécifiée et sur le toit, sinon false.
     */
    public boolean presenceJoueurSurLeToitDirection(boolean forward) {
        Wagon currentWagon = forward ? this.getSuivant() : this.getPrecedent();
        Wagon limite = forward ? train.getLocomotive() : train.getDernierWagon();

        while (!currentWagon.equals(limite)) {
            if (currentWagon.presenceJoueurSurLeToit()) {
                return true;
            }
            currentWagon = forward ? currentWagon.getSuivant() : currentWagon.getPrecedent();
        }
        return false;
    }

    /**
     * Vérifie s'il y a d'autres joueurs que le bandit actuel à gauche du train sur le toit.
     *
     * @return true s'il y a d'autres joueurs que le bandit actuel à gauche du wagon et sur le toit, sinon false.
     */
    public boolean presenceJoueurGaucheToit() {
        return presenceJoueurSurLeToitDirection(false);
    }

    /**
     * Vérifie s'il y a d'autres joueurs que le bandit actuel à droite du train sur le toit.
     *
     * @return true s'il y a d'autres joueurs que le bandit actuel à droite du wagon et sur le toit, sinon false.
     */
    public boolean presenceJoueurDroiteToit() {
        return presenceJoueurSurLeToitDirection(true);
    }

    /**
     * Sélectionne aléatoirement un bandit en fonction de son emplacement et de la direction spécifiée.
     *
     * @param underRoof true pour sélectionner un bandit sur le toit, false pour sélectionner un bandit à l'intérieur.
     * @return Le bandit sélectionné aléatoirement, ou null s'il n'y en a aucun.
     */
    public Bandit getBanditAleatoireDroiteDirection(boolean underRoof) {
        ArrayList<Bandit> listBandits = new ArrayList<>();
        Wagon currentWagon = this.getSuivant();

        while (currentWagon != train.getLocomotive()) {
            for(Bandit bandit : currentWagon.bandits)
                if (!(bandit instanceof Marshall) && bandit.getInterieur() == underRoof) {
                    System.out.println(bandit.name + " a été ajouté à la liste des cibles");
                    listBandits.add(bandit);
                }
            currentWagon = currentWagon.getSuivant();
        }

        // Vérifie s'il y a des bandits dans la direction spécifiée
        if (!listBandits.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(listBandits.size());

            return listBandits.get(randomIndex);
        } else {
            return null;
        }
    }


    /**
     * Sélectionne aléatoirement un bandit se trouvant sur le toit parmi les bandits des wagons à droite.
     * Si aucun bandit sur le toit n'est trouvé à droite, retourne null.
     *
     * @return Le bandit sélectionné aléatoirement sur le toit à droite, ou null s'il n'y en a aucun.
     */
    public Bandit getBanditAleatoireDroiteToit() {
        return getBanditAleatoireDroiteDirection(false);
    }

    /**
     * Sélectionne aléatoirement un bandit se trouvant à l'intérieur parmi les bandits des wagons à droite.
     * Si aucun bandit sur le toit n'est trouvé à droite, retourne null.
     *
     * @return Le bandit sélectionné aléatoirement à l'intérieur à droite, ou null s'il n'y en a aucun.
     */
    public Bandit getBanditAleatoireDroiteInterieur() {
        return getBanditAleatoireDroiteDirection(true);
    }

    /**
     * Sélectionne aléatoirement un bandit en fonction de son emplacement et de la direction spécifiée.
     *
     * @param underRoof true pour sélectionner un bandit sur le toit, false pour sélectionner un bandit à l'intérieur.
     * @return Le bandit sélectionné aléatoirement, ou null s'il n'y en a aucun.
     */
    public Bandit getBanditAleatoireGaucheDirection(boolean underRoof) {
        ArrayList<Bandit> listBandits = new ArrayList<>();
        Wagon currentWagon = this.getPrecedent();

        while (currentWagon != train.getDernierWagon()) {
            for(Bandit bandit : this.bandits)
                if(!(bandit instanceof Marshall) &&bandit.getInterieur() == underRoof) {
                    System.out.println(bandit.name + " a été ajouté à la liste des cibles");
                    listBandits.add(bandit);
                }
            currentWagon = currentWagon.getPrecedent();
        }

        // Vérifie s'il y a des bandits dans la direction spécifiée
        if (!listBandits.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(listBandits.size());

            return listBandits.get(randomIndex);
        } else {
            return null;
        }
    }

    /**
     * Sélectionne aléatoirement un bandit se trouvant à l'intérieur parmi les bandits des wagons à gauche.
     * Si aucun bandit sur le toit n'est trouvé à droite, retourne null.
     *
     * @return Le bandit sélectionné aléatoirement à l'intérieur à gauche, ou null s'il n'y en a aucun.
     */
    public Bandit getBanditAleatoireGaucheInterieur() {
        return getBanditAleatoireGaucheDirection(true);
    }

    /**
     * Sélectionne aléatoirement un bandit se trouvant sur le toit parmi les bandits des wagons à gauche.
     * Si aucun bandit sur le toit n'est trouvé à droite, retourne null.
     *
     * @return Le bandit sélectionné aléatoirement sur le toit à gauche, ou null s'il n'y en a aucun.
     */
    public Bandit getBanditAleatoireGaucheToit() {
        return getBanditAleatoireGaucheDirection(false);
    }

    /**
     * Vérifie si le Marshal est présent parmi les bandits.
     * @return true si un Marshal est présent, sinon false.
     */
    public boolean marshallPresent(){
        for(Bandit bandit : this.bandits){
            if(bandit instanceof Marshall){
                return true;
            }
        }
        return false;
    }

    /**
     * Sélectionne aléatoirement un bandit en fonction de son emplacement et de la direction spécifiée.
     *
     * @param underRoof true pour sélectionner un bandit sous le toit, false pour sélectionner un bandit à l'intérieur.
     * @return Le bandit sélectionné aléatoirement, ou null s'il n'y en a aucun.
     */
    public Bandit getBanditAleatoire(boolean underRoof) {
        ArrayList<Bandit> listBandits = new ArrayList<>();

        for (Bandit bandit : this.bandits) {
            if (!(bandit instanceof Marshall) && bandit.getInterieur() == underRoof) {
                System.out.println(bandit.name + " a été ajouté à la liste des cibles");
                listBandits.add(bandit);
            }
        }

        // Vérifie s'il y a des bandits dans la direction spécifiée
        if (!listBandits.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(listBandits.size());

            return listBandits.get(randomIndex);
        } else {
            return null;
        }
    }

    /**
     * Sélectionne aléatoirement un bandit se trouvant sous le toit parmi les bandits
     * Si aucun bandit sous le toit n'est trouvé à droite, retourne null.
     * Le tireur se trouve sur le toit
     * @return Le bandit sélectionné aléatoirement sous le toit , ou null s'il n'y en a aucun.
     */
    public Bandit getBanditAleatoireSousLeToit() {
        return getBanditAleatoire(true);
    }

    /**
     * Sélectionne aléatoirement un bandit se trouvant sous le toit parmi les bandits des wagons à gauche.
     * Si aucun bandit sous le toit n'est trouvé à gauche, retourne null.
     * Le tireur se trouve sous le toit
     * @return Le bandit sélectionné aléatoirement sur le toit, ou null s'il n'y en a aucun.
     */
    public Bandit getBanditAleatoireSurLeToit() {
        return getBanditAleatoire(false);
    }
}



