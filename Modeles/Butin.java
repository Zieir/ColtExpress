package jeuColtExpress.Modeles;

import java.util.Random;

/**
 * L'énumération Butin représente les différents types de butin pouvant être obtenus dans le jeu Colt Express.
 * Chaque type de butin a un nom et une valeur associée.
 */
public enum Butin {

    /** Représente un bijou de grande valeur. */
    Bijou("Bijou", 500),

    /** Représente une bourse contenant une somme aléatoire d'argent. */
    Bourse("Bourse", (new Random()).nextInt(501)),

    /** Représente un magot d'une valeur importante. */
    Magot("Magot", 1000);

    private final int value;
    private final String name;

    /**
     * Constructeur de l'énumération Butin.
     * @param initName Le nom du butin.
     * @param initValue La valeur associée au butin.
     */
    Butin(String initName, int initValue) {
        this.value = initValue;
        this.name = initName;
    }

    /**
     * Méthode pour obtenir le nom du butin.
     * @return Le nom du butin.
     */
    public String getName() {
        return name;
    }

    /**
     * Méthode pour obtenir la valeur du butin.
     * @return La valeur du butin.
     */
    public int getValue(){
        return value;
    }
}

