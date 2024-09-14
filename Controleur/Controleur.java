package jeuColtExpress.Controleur;

import jeuColtExpress.Modeles.Action;
import jeuColtExpress.Modeles.Bandit;


import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Le contrôleur pour gérer les actions des bandits dans le jeu.
 */
public class Controleur implements ActionListener {
    private final ArrayList<Bandit> list; // La liste des bandits dans le jeu
    private Bandit currentBandit; // Le bandit en cours de sélection d'actions
    private int indexCurrentBandit; // Index du bandit en cours de sélection
    private int currentActionIndex; // Index de l'action en cours pour le bandit en cours
    private int indexCurrentBanditFin; // Index du bandit en cours d'exécution des actions
    private boolean finPhaseSelection; // Indique si la phase de sélection des actions est terminée

    private boolean haut;
    private int wagon;

    /**
     * Constructeur de la classe Controleur.
     *
     * @param listBandits  La liste des bandits dans le jeu.
     */
    public Controleur(ArrayList<Bandit> listBandits) {
        this.list = listBandits; // Le train dans le jeu

        //Variable pour la phase de sélection des actions des bandits
        this.indexCurrentBandit = 1;
        this.currentBandit = listBandits.get(1);
        this.currentActionIndex = 0;

        //Variable pour la partie d'excution d'action
        this.indexCurrentBanditFin = 0;
        this.finPhaseSelection = false;

        haut= false;
        wagon=1;
    }

    /**
     * Méthode invoquée lorsqu'un événement est déclenché.
     *
     * @param e L'événement associé à l'action.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String buttonText = button.getText();

        // Exécution des actions des bandits après la phase de sélection
        if (finPhaseSelection && indexCurrentBanditFin < list.size()) {
            if (buttonText.equals("Action")) {

                executeAction(list.get(indexCurrentBanditFin));
                indexCurrentBanditFin++;
                if (indexCurrentBanditFin == list.size()) {
                    indexCurrentBanditFin = 0;
                }
            }
        }
        // Phase de sélection des actions des bandits
        if (currentActionIndex < 5) {
            switch (buttonText) {
                case "Avancer":
                    if(wagon == 4){
                        System.out.println("Vous êtes dans le dernier wagon");
                        currentActionIndex--;
                    }else {
                        currentBandit.getActions().add(Action.Avance);
                        System.out.println("Action avancer");
                        wagon++;
                    }
                    break;
                case "Braquer":
                    currentBandit.getActions().add(Action.Braquer);
                    System.out.println("Action braquer");
                    break;
                case "Monter":
                    if(!haut) {
                        currentBandit.getActions().add(Action.Monter);
                        System.out.println("Action monter");
                        haut= true;
                    }else{
                        System.out.println("Déjà en haut");
                        currentActionIndex--;
                    }
                    break;
                case "Tirer":
                    currentBandit.getActions().add(Action.Tirer);
                    System.out.println("Action tirer");
                    break;
                case "Reculer":
                    if(wagon == 0){
                        System.out.println("Vous êtes dans la locomotive");
                        currentActionIndex--;
                    }else {
                        currentBandit.getActions().add(Action.Recule);
                        System.out.println("Action reculer");
                        wagon--;
                    }
                    break;
                case "Descendre":
                    if(!haut) {
                        System.out.println("Vous êtes dans le train");
                        currentActionIndex--;
                    }else {
                        currentBandit.getActions().add(Action.Descendre);
                        System.out.println("Action descendre");
                        haut = false;
                    }
                    break;
                case "Action":
                    currentActionIndex--;
                    break;
                default:
                    break;
            }
            currentActionIndex++;
        } else {
            haut = false; wagon =1;
            if (indexCurrentBandit < list.size() - 1) {
                indexCurrentBandit++;
                currentBandit.notifyVuePlanification();
                currentBandit = list.get(indexCurrentBandit);
                currentActionIndex = 0; // Réinitialisez l'index d'action pour le nouveau bandit
            } else {
                finPhaseSelection = true;
            }
        }
    }

    /**
     * Exécute l'action du bandit spécifié.
     *
     * @param currentBandit Le bandit dont l'action doit être exécutée.
     */
    private void executeAction(Bandit currentBandit) {
        if (currentBandit.getActions() != null && !currentBandit.getActions().isEmpty()) {
            Action action = currentBandit.getActions().getFirst();
            switch (action) {
                case Tirer:
                    currentBandit.tirer();
                    break;
                case Braquer:
                    currentBandit.braquer();
                    break;
                case Monter:
                    currentBandit.monter();
                    break;
                case Descendre:
                    currentBandit.descendre();
                    break;
                case Avance:
                    currentBandit.avance();
                    break;
                case Recule:
                    currentBandit.recule();
                    break;
                default:
                    break;
            }
            clearActions(currentBandit);
        }
    }

    /**
     * Efface l'action du bandit spécifié après son exécution.
     *
     * @param bandit Le bandit dont l'action doit être effacée.
     */
    private void clearActions(Bandit bandit) {
        bandit.getActions().removeFirst();
    }
}






