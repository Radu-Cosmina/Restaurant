package view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 * @author Cosmina Radu
 * the main graphical user interface
 */
public class UserInterface extends JFrame {
    private JButton toAdministrator;
    private JButton toWaiter;
    private JButton toChef;
    private JLabel title;

    public UserInterface() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);
        //construct components
        toAdministrator = new JButton("Administrator");
        getContentPane().add(toAdministrator);
        toWaiter = new JButton("Waiter");
        getContentPane().add(toWaiter);
        toChef = new JButton("Chef");
        getContentPane().add(toChef);
        title = new JLabel("User Options");
        getContentPane().add(title);

        //set components properties
        toAdministrator.setToolTipText("ActionListener");

        //adjust size and set layout
       // setPreferredSize(new Dimension(1000, 800));
        setBounds(400, 100, 600, 300);
        setLayout(null);

        //add components
        add(toAdministrator);
        add(toWaiter);
        add(toChef);
        add(title);

        //set component bounds (only needed by Absolute Positioning)
        toAdministrator.setBounds(290, 190, 125, 50);
        toWaiter.setBounds(290, 120, 125, 50);
        toChef.setBounds(290, 55, 125, 50);
        title.setBounds(180, 120, 200, 35);
    }
    public void addToWaiterButtonActionListener(ActionListener actionListener)
    {
        toWaiter.addActionListener(actionListener);
    }

    public void addToAdminButtonActionListener(ActionListener actionListener)
    {
        toAdministrator.addActionListener(actionListener);
    }

    public void addToChefButtonActionListener(ActionListener actionListener)
    {
        toChef.addActionListener(actionListener);
    }
}