package jeuColtExpress.Vue;

import jeuColtExpress.Controleur.Controleur;
import jeuColtExpress.Modeles.Observer;
import jeuColtExpress.Modeles.Bandit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class VuePlanification extends JFrame implements Observer {
    private final ArrayList<Bandit> bandits;
    private int indiceCurrentBandits = 1;
    public VuePlanification(ArrayList<Bandit> listBandits) {
        this.bandits = listBandits;
        ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/plan.png")));
        setLayout(new BorderLayout());

        JPanel mainPanel = new MainPanel(backgroundImage);
        mainPanel.setOpaque(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton bouton1 = new JButton("Avancer", new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/CarteReculer.png"))));
        JButton bouton2 = new JButton("Braquer", new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/CarteBraquer.png"))));
        JButton bouton3 = new JButton("Monter", new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/CarteMonter.png"))));
        JButton bouton4 = new JButton("Tirer", new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/CarteTirer.png"))));
        JButton bouton5 = new JButton("Reculer", new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/CarteAvancer.png"))));
        JButton bouton6 = new JButton("Descendre", new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/CarteDescendre.png"))));
        JButton boutonAction = new JButton("Action", new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/icon.png"))));

        bouton1.setBorderPainted(false);
        bouton2.setBorderPainted(false);
        bouton3.setBorderPainted(false);
        bouton4.setBorderPainted(false);
        bouton5.setBorderPainted(false);
        bouton6.setBorderPainted(false);
        boutonAction.setBorderPainted(false);

        Controleur controleur = new Controleur(listBandits);
        bouton1.addActionListener(controleur);
        bouton2.addActionListener(controleur);
        bouton3.addActionListener(controleur);
        bouton4.addActionListener(controleur);
        bouton5.addActionListener(controleur);
        bouton6.addActionListener(controleur);
        boutonAction.addActionListener(controleur);

        Dimension boutonDimension = new Dimension(150, 200);
        bouton1.setPreferredSize(boutonDimension);
        bouton2.setPreferredSize(boutonDimension);
        bouton3.setPreferredSize(boutonDimension);
        bouton4.setPreferredSize(boutonDimension);
        bouton5.setPreferredSize(boutonDimension);
        bouton6.setPreferredSize(boutonDimension);
        boutonAction.setPreferredSize(boutonDimension);

        buttonPanel.add(bouton1);
        buttonPanel.add(bouton2);
        buttonPanel.add(bouton3);
        buttonPanel.add(bouton4);
        buttonPanel.add(bouton5);
        buttonPanel.add(bouton6);
        buttonPanel.add(boutonAction);

        mainPanel.add(buttonPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Méthode appelée lorsqu'un observable (dans ce cas, la VueActionsJoueur elle-même) est mis à jour.
     * Elle récupère le panneau principal et le redessine.
     *
     * @param o   L'observable mis à jour.
     * @param arg L'argument passé lors de la mise à jour.
     */
    @Override
    public void update(VueActionsJoueur o, Object arg) {
        MainPanel mainPanel;
        mainPanel = (MainPanel) getContentPane().getComponent(0);
        mainPanel.repaint();
    }

    @Override
    public void update1(VueJoueur o, Object arg) {
        MainPanel mainPanel = (MainPanel) getContentPane().getComponent(0);
        mainPanel.repaint();
    }

    @Override
    public void update2(VuePlanification o, Object args){
        if(indiceCurrentBandits >= bandits.size() - 1){
            indiceCurrentBandits = 0;
        }
        indiceCurrentBandits++;
        System.out.println(indiceCurrentBandits);
        MainPanel mainPanel = (MainPanel) getContentPane().getComponent(0);
        mainPanel.repaint();
    }

    private class MainPanel extends JPanel {
        private final ImageIcon backgroundImage;

        public MainPanel(ImageIcon backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Bandit bandit = bandits.get(indiceCurrentBandits);
            super.paintComponent(g);
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

            Font font = new Font("Arial", Font.BOLD, 20);
            g.setFont(font);
            String message = bandit.getName() + " sélectionne tes 5 cartes";
            FontMetrics fontMetrics = g.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth(message);
            int stringHeight = fontMetrics.getHeight();
            g.drawString(message, (getWidth() - stringWidth) / 2, (getHeight() - stringHeight));
        }
    }
}


