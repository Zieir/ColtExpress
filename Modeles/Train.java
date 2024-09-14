package jeuColtExpress.Modeles;

/**
 * La classe Train représente un train dans le jeu Colt Express, comprenant plusieurs wagons et personnages.
 * Dans ce jeu, un train est représenté comme une liste doublement chaînée de wagons.
 */
public class Train {
    private final Wagon locomotive; // La locomotive du train
    private final Wagon firstWagon; // Le premier wagon du train

    /**
     * Constructeur de la classe Train. Initialise le train avec un nombre donné de wagons.
     *
     * @param n Le nombre de wagons à créer pour le train.
     */
    public Train(int n) {
        int NB_WAGONS_MAX = 5; // Nombre maximum de wagons dans le train
        if (n < 1 || n > NB_WAGONS_MAX) {
            n = NB_WAGONS_MAX;
        }

        // Initialisation de la locomotive avec un Marshall et des butins
        locomotive = new Locomotive(this);
        locomotive.ajouterButins();

        // Création du premier wagon
        firstWagon = new Wagon(this, 1);

        // Relier la locomotive et le premier wagon
        locomotive.setSuivant(firstWagon);
        firstWagon.setPrecedent(locomotive);

        Wagon current = firstWagon;

        // Création des wagons suivants et ajout de butins
        for (int i = 2; i < n; i++) {
            Wagon addedWagon = new Wagon(this, i);
            current.setSuivant(addedWagon);
            addedWagon.setPrecedent(current);
            current = addedWagon;
            current.ajouterButins();
        }
        current.setSuivant(locomotive);
        locomotive.setPrecedent(current);
    }

    /**
     * Renvoie le premier wagon du train.
     *
     * @return Le premier wagon du train.
     */
    public Wagon getFirstWagon() {
        return this.firstWagon;
    }

    /**
     * Renvoie la locomotive du train.
     *
     * @return La locomotive du train.
     */
    public Wagon getLocomotive() {
        return locomotive;
    }

    /**le dernier wagon la locomotive du train.
     *
     * @return Le dernier wagon du train.
     */
    public Wagon getDernierWagon (){
        return locomotive.getPrecedent();
    }
}
