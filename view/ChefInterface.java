package view;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;


import controller.Restaurant;

/**
 * @author Cosmina Radu
 * the graphical user interface for the chef
 */
public class ChefInterface extends JFrame implements Observer  {

    private JLabel title;
    private JButton goBackButton;
    private Restaurant restaurant;
    private JButton showButton;

    public ChefInterface(UserInterface userInterface, Restaurant restaurant) {

        this.restaurant = restaurant;
        restaurant.addObserver(this);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setBounds(150, 50, 850, 600);
        this.getContentPane().setLayout(null);

        title = new JLabel("The Chef: Processing Commands");
        title.setBounds(300, 50, 450, 50);
        getContentPane().add(title);

        goBackButton = new JButton("Back");
        goBackButton.setBounds(650, 450, 100, 50);
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                userInterface.setVisible(true);

            }
        });
        getContentPane().add(goBackButton);





    }

    @Override
    public void update(Observable obs, Object obj) {

        this.setVisible(true);
        System.out.println(obj.toString());
        int test = JOptionPane.showConfirmDialog(null, obj, "Notification:", 2);
        if (test == 0) {
            System.out.println("Processed command\n");
            this.setVisible(false);
        } else {
            System.out.println("Unprocessed command. Must wait\n");
            this.setVisible(false);
        }


    }
}