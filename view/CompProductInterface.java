package view;

import controller.RestaurantSerializator;
import model.CompositeProduct;
import model.MenuItems;
import controller.Restaurant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CompProductInterface  extends JFrame{
    private Restaurant restaurant;
    private ArrayList<MenuItems> compProds;
    private JComboBox<MenuItems> menuItemsBox;
    private JLabel title;
    private JButton addItemButton;
    private JButton finishItemButton;
    private JTextArea addedItems;
    private JLabel addedLabel;
    /**
     * @author Cosmina Radu
     * the graphical user interface for the composite products
     */
    public CompProductInterface(Restaurant restaurant)
    {
        this.restaurant=restaurant;
        this.compProds=new ArrayList<MenuItems>();
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.setBounds(300, 30, 900, 700);
        this.getContentPane().setLayout(null);

        title=new JLabel("Composite product options");
        title.setBounds(300, 50, 450, 50);
        getContentPane().add(title);
        menuItemsBox=new JComboBox<MenuItems>();
        menuItemsBox.setBounds(100, 150, 250, 50);
        getContentPane().add(menuItemsBox);
        addItemButton=new JButton("Add item");
        addItemButton.setBounds(50, 550, 125, 50);
        getContentPane().add(addItemButton);
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuItems chosenItem=(MenuItems)menuItemsBox.getSelectedItem();
                compProds.add(chosenItem);
                addedItems.append(chosenItem.toString());
                addedItems.append("\n");
            }
        });
        finishItemButton=new JButton("Finish composite product");
        finishItemButton.setBounds(250, 550, 200, 50);
        getContentPane().add(finishItemButton);
        finishItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restaurant.setCompProducts(finishItem());
                ArrayList<MenuItems> comp = restaurant.getCompProducts();
                String iname = restaurant.getCompName();

                CompositeProduct cp = new CompositeProduct(iname, comp);
                int finalPrice=cp.computePrice();
                cp.setPrice(finalPrice);

                restaurant.createMenuItem(cp);
                try {
                    RestaurantSerializator.serialize(restaurant);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                addedItems.setText("");
                JOptionPane.showMessageDialog(null, "Composite product created successfully");

            }
        });
        addedLabel = new JLabel("Added Items");
        addedLabel.setBounds(400,100,100,50);
        getContentPane().add(addedLabel);

        addedItems = new JTextArea();
        addedItems.setBounds(400,150,400,300);
        addedItems.setEditable(false);
        getContentPane().add(addedItems);

    }
    public void fillBox()
    {
        ArrayList<MenuItems> list = restaurant.getMenu();
        Iterator<MenuItems> it = list.iterator();
        while(it.hasNext())
        {
            MenuItems currentItem = it.next();
            menuItemsBox.addItem(currentItem);

            System.out.println(currentItem.getName());
        }
    }

    public ArrayList<MenuItems> finishItem()
    {
        return compProds;
    }


}
