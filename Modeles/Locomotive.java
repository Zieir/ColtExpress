package jeuColtExpress.Modeles;

/**
 * La classe Locomotive représente le wagon de locomotive dans le jeu Colt Express.
 * Il contient un Marshall comme personnage par défaut et un butin spécial, le Magot.
 */
public class Locomotive extends Wagon {
    /**
     * Constructeur de la classe Locomotive.
     *
     * @param t Le train auquel appartient la locomotive.
     */
    public Locomotive(Train t) {
        super(t, 0); // La locomotive est le premier wagon (indice 0)
    }

    /**
     * Vérifie si ce wagon est la locomotive.
     *
     * @return true si c'est la locomotive, false sinon.
     */
    @Override
    public boolean isLocomotive() {
        return true;
    }

    /**
     * Ajoute un butin spécial, le Magot, à la locomotive.
     * Override de la méthode de la classe parent Wagon.
     */
    @Override
    public void ajouterButins() {
        this.butins.add(Butin.Magot);
    }
}

