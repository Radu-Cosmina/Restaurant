package view;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import process.IRestaurantProcessing;
import controller.RestaurantSerializator;
import model.Product;
import model.MenuItems;
import model.Order;
import controller.Restaurant;

/**
 * @author Cosmina Radu
 * the graphical use interface for the administrator
 */
public class AdminInterface extends JFrame implements IRestaurantProcessing {

    private JLabel title;
    private Restaurant restaurant;
    private CompProductInterface compositeItemGUI;

    private JButton backButton;
    private JButton createItemButton;
    private JButton deleteItemButton;
    private JButton editItemButton;
    private JButton showItemsButton;
    private JButton compositeButton;
    private JLabel itemNameLabel;
    private JTextField itemNameField;
    private JLabel itemPriceLabel;
    private JTextField itemPriceField;

    public AdminInterface(UserInterface userInterface, Restaurant restaurant,CompProductInterface compositeItemGUI) {

        this.restaurant = restaurant;
        this.compositeItemGUI = compositeItemGUI;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(300, 100, 900, 600);
        this.getContentPane().setLayout(null);

        title = new JLabel("Administrator options");
        title.setBounds(300, 50, 450, 50);
        getContentPane().add(title);

        backButton = new JButton("Back");
        backButton.setBounds(750, 450, 100, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                userInterface.setVisible(true);

            }
        });
        getContentPane().add(backButton);

        compositeButton=new JButton("New Composite Product");
        compositeButton.setBounds(50, 350, 200, 50);
        getContentPane().add(compositeButton);
        compositeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = getNameField();
                String itemPrice = getPriceField();
                if(itemPrice.equals("")) {
                    System.out.println("Composite !");
                    compositeItemGUI.setVisible(true);
                    compositeItemGUI.fillBox();
                    restaurant.setCompName(itemName);
                }
                else  JOptionPane.showMessageDialog(null, "Error:Can't set price for a composite product");
            }
        });

        createItemButton = new JButton("Create item");
        createItemButton.setBounds(50, 450, 125, 50);
        getContentPane().add(createItemButton);
        createItemButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = getNameField();
                String itemPrice = getPriceField();
                if (itemName.compareTo("composite") == 0 &&itemPrice.equals(" ")) {
                    System.out.println("Composite Item");
                    compositeItemGUI.setVisible(true);
                    compositeItemGUI.fillBox();
                    restaurant.setCompName(itemName);

                } else {

                    int itemP = 0;
                    try {
                        itemP = Integer.parseInt(itemPrice);
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null, "Error:Invalid price");
                    }

                    Product newBaseProduct = new Product(itemName, itemP);
                    createMenuItem(newBaseProduct);
                    try {
                        RestaurantSerializator.serialize(restaurant);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Item added");
                }

            }
        });

        editItemButton = new JButton("Edit item");
        editItemButton.setBounds(350, 450, 125, 50);
        getContentPane().add(editItemButton);
        editItemButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String iname = getNameField();
                String iprice = getPriceField();
                int price=0;
                try {
                    price = Integer.parseInt(iprice);

                    Product newProduct = new Product(iname, price);
                    MenuItems oldProduct = findByName(iname);
                    if(oldProduct instanceof Product)
                    {
                        oldProduct.setPrice(price);
                        JOptionPane.showMessageDialog(null, "Item edited");
                        editMenuItem(oldProduct);
                        RestaurantSerializator.serialize(restaurant);

                    }
                    else JOptionPane.showMessageDialog(null, "Can't edit composite products");

                }catch (Exception e2) {
                    JOptionPane.showMessageDialog(null, "Error: invalid price");
                }

            }
        });



        deleteItemButton = new JButton("Delete item");
        deleteItemButton.setBounds(200, 450, 125, 50);
        getContentPane().add(deleteItemButton);
        deleteItemButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String iname = getNameField();
                restaurant.deleteMenuItem(findByName(iname));
                try {
                    RestaurantSerializator.serialize(restaurant);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Item deleted");

            }
        });


        showItemsButton = new JButton("Show items");
        showItemsButton.setBounds(550, 450, 125, 50);
        getContentPane().add(showItemsButton);
        showItemsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String columns[] = { "Item Name", "Item Price" };
                ArrayList<MenuItems> list = restaurant.getMenu();
                DefaultTableModel myModel = new DefaultTableModel();
                myModel.setColumnIdentifiers(columns);
                Object[] obj = new Object[2];
                Iterator<MenuItems> it = list.iterator();
                while (it.hasNext()) {
                    MenuItems curentItem = it.next();
                    System.out.println(curentItem.getName() + " " + curentItem.getPrice());

                    obj[0] = curentItem.getName();
                    obj[1] = curentItem.getPrice();
                    myModel.addRow(obj);
                }

                JTable myTable = new JTable(myModel);
                JScrollPane myScrollPane = new JScrollPane();
                myScrollPane.setBounds(250, 100, 600, 300);
                myScrollPane.setViewportView(myTable);
                getContentPane().add(myScrollPane);

            }
        });

        itemNameLabel = new JLabel("Product NAME");
        itemNameLabel.setBounds(20, 150, 100, 40);
        getContentPane().add(itemNameLabel);

        itemNameField = new JTextField();
        itemNameField.setBounds(120, 150, 100, 30);
        getContentPane().add(itemNameField);

        itemPriceLabel = new JLabel("Product PRICE");
        itemPriceLabel.setBounds(20, 200, 100, 40);
        getContentPane().add(itemPriceLabel);

        itemPriceField = new JTextField();
        itemPriceField.setBounds(120, 200, 100, 30);
        getContentPane().add(itemPriceField);

    }

    public String getPriceField() {
        return this.itemPriceField.getText();
    }

    public String getNameField() {
        return this.itemNameField.getText();
    }

    @Override
    public void createOrder(Order order, ArrayList<MenuItems> menuItem) {
        System.out.println("can't create orders");

    }

    @Override
    public int priceOrder(Order order) {
        System.out.println("can't compute price");
        return 0;

    }

    @Override
    public void generateBill(String whatToPrint, int id) {
        System.out.println("Can't generate bills");

    }

    @Override
    public void createMenuItem(MenuItems item) {
        restaurant.createMenuItem(item);

    }

    @Override
    public void deleteMenuItem(MenuItems item) {
        restaurant.deleteMenuItem(item);

    }

    @Override
    public void editMenuItem(MenuItems item) {
        restaurant.editMenuItem(item);

    }

    public MenuItems findByName(String name)
    {
        ArrayList<MenuItems> items = restaurant.getMenu();
        Iterator<MenuItems> it = items.iterator();
        while(it.hasNext())
        {
            MenuItems currentItem = it.next();
            if(currentItem.getName().compareTo(name)==0) return currentItem;
        }
        return null;
    }
    //    public MenuItems findById(int id)
//    {
//        ArrayList<Orders> orders = restaurant.getOrders();
//        Iterator<MenuItems> it = items.iterator();
//        while(it.hasNext())
//        {
//           Order currentOrder = it.next();
//            if(currentOrder.getID().compareTo(id)==0) return currentOrder;
//        }
//        return null;
//    }
}
