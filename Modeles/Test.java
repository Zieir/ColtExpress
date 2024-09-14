package jeuColtExpress.Modeles;

import javax.swing.*;
import java.awt.*;
public class Test
{
    public Test()
    { JFrame f = new JFrame("Hello World!!");
        f.setVisible(true);
        f.setSize(new Dimension(200,200));
        JPanel pan = new JPanel();
        BoxLayout bl=new BoxLayout(
                pan,BoxLayout.Y_AXIS);
        pan.setLayout(bl);
        JLabel lab=new JLabel("Bonjour le monde!!");
        pan.add(lab);
        JTextField tf=new JTextField("Editez !");
        pan.add(tf);
        JPanel pan2=new JPanel();
        bl=new BoxLayout(pan2,BoxLayout.X_AXIS);
        pan2.setLayout(bl);
        lab=new JLabel("ComboBox:");
        pan2.add(lab);
        String c[] ={"Un","Deux",
                "Trois","Z´ero !"};
        JComboBox cb=new JComboBox(c);
        pan2.add(cb);
//ajoute le panel 2 dans le panel 1!
        pan.add(pan2);
//un dernier composantpour la route...
        JButton but=new JButton(
                "C’est un bouton !!");
        pan.add(but);
        f.setContentPane(pan);
        f.setVisible(true);
        f.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String args[])
    { new Test(); }}