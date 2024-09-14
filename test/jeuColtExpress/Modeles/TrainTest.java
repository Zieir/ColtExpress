package jeuColtExpress.Modeles;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TrainTest {

    @Test
    public void testCreationTrainAvecNombreDeWagons() {
        int nombreDeWagons = 5;
        Train train = new Train(nombreDeWagons);

        assertNotNull(train.getFirstWagon());

        assertNotNull(train.getLocomotive());

        assertNotNull(train.getDernierWagon());

        int countWagons = 1;
        Wagon currentWagon = train.getFirstWagon();
        while (currentWagon.getSuivant() != train.getFirstWagon()) {
            countWagons++;
            currentWagon = currentWagon.getSuivant();
        }
        assertEquals(nombreDeWagons, countWagons);
    }

    @Test
    public void testCreationTrainAvecNombreNegatifDeWagons() {
        int nombreDeWagonsNegatif = -3;
        Train train = new Train(nombreDeWagonsNegatif);

        int countWagons = 1;
        Wagon currentWagon = train.getFirstWagon();
        while (currentWagon.getSuivant() != train.getFirstWagon()) {
            countWagons++;
            currentWagon = currentWagon.getSuivant();
        }
        assertEquals(5, countWagons);
    }

    @Test
    public void testCreationTrainAvecNombreSuperieurMaxDeWagons() {
        int nombreDeWagonsTropGrand = 10;
        Train train = new Train(nombreDeWagonsTropGrand);

        int countWagons = 1;
        Wagon currentWagon = train.getFirstWagon();
        while (currentWagon.getSuivant() != train.getFirstWagon()) {
            countWagons++;
            currentWagon = currentWagon.getSuivant();
        }
        assertEquals(5, countWagons);
    }
}


