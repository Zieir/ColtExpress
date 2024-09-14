package jeuColtExpress.Vue;

import jeuColtExpress.Modeles.Modele;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class VueDepart extends JFrame {
    public int nbJoueurs;
    BackgroundMusic music;


    public VueDepart() {
        this.setTitle("Colt Express");

        ImageIcon img = new ImageIcon("src/jeuColtExpress/Images/handgun.png");
        this.setIconImage(img.getImage());

        ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/plan.png")));

        JPanel mainPanel = new MainPanel(backgroundImage);
        mainPanel.setLayout(null);
        mainPanel.setOpaque(false);

        JLabel colt = new JLabel("Colt Express");
        colt.setFont(new Font("Arial", Font.BOLD, 24));
        colt.setForeground(Color.decode("#A0522D"));
        colt.setBounds(325, 100, 200, 100);
        mainPanel.add(colt);

        JLabel joueursLabel = new JLabel("Nombre de joueurs:");
        joueursLabel.setForeground(Color.BLACK);
        joueursLabel.setBounds(150, 200, 150, 30);
        mainPanel.add(joueursLabel);

        JTextField joueursField = new JTextField(10);
        joueursField.setBounds(300, 200, 200, 30);
        mainPanel.add(joueursField);

        JButton bouton1 = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/play.png"))));
        Dimension boutonDimension = new Dimension(150, 50);
        bouton1.setBounds(325, 250, boutonDimension.width, boutonDimension.height);
        mainPanel.add(bouton1);

        JButton boutonStop = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/MusiqueOff.png"))));
        Dimension boutonStopDimension = new Dimension(50, 50);
        boutonStop.setBounds(50, 0, boutonStopDimension.width, boutonStopDimension.height);
        mainPanel.add(boutonStop);

        JButton boutonRemettreMusique = new JButton(new ImageIcon(Objects.requireNonNull(getClass().getResource("../Images/MusiqueOn.png"))));
        Dimension boutonRemettreMusiqueDimension = new Dimension(50, 50);
        boutonRemettreMusique.setBounds(0, 0, boutonRemettreMusiqueDimension.width, boutonRemettreMusiqueDimension.height);
        mainPanel.add(boutonRemettreMusique);

        add(mainPanel, BorderLayout.CENTER);

        music = new BackgroundMusic();

        bouton1.addActionListener(e -> {
            // Récupérer le nombre de wagons et de joueurs saisis et les stocker dans les variables
            try {
                nbJoueurs = Integer.parseInt(joueursField.getText());

                // Vérifier que nbWagons est supérieur à 1 et nbJoueurs est supérieur à 2
                if (nbJoueurs > 1 && nbJoueurs < 5) {
                    System.out.println("Nombre de joueurs: " + nbJoueurs);

                    Modele modele = new Modele(5, nbJoueurs);

                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(VueDepart.this, "Le nombre de joueurs doit être supérieur à 2 et inférieur à 5.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(VueDepart.this, "Veuillez entrer des nombres valides pour le nombre de joueurs.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        boutonStop.addActionListener(e -> music.stopMusic());
        boutonRemettreMusique.addActionListener(e -> music.playMusic("src/jeuColtExpress/Images/wild-west-background-194954.wav"));
    }

    private static class MainPanel extends JPanel {
        private final ImageIcon backgroundImage;

        public MainPanel(ImageIcon backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

            Font font = new Font("Arial", Font.BOLD, 20);
            g.setFont(font);
        }
    }

    private static class BackgroundMusic {

        private Clip clip;

        public void playMusic(String filePath) {
            try {
                File audioFile = new File(filePath);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

                clip = AudioSystem.getClip();
                clip.open(audioStream);

                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println("Erreur lors de la lecture du fichier audio : " + e.getMessage());
            }
        }

        public void stopMusic() {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
        }

        public BackgroundMusic(){
            String filePath = "src/jeuColtExpress/Images/wild-west-background-194954.wav";
            playMusic(filePath);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Interruption de la musique : " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VueDepart vue = new VueDepart();
            vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            vue.setSize(800, 600);
            vue.setLocationRelativeTo(null);
            vue.setVisible(true);
        });
    }
}


