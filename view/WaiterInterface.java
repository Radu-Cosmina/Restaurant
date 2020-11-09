package view;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


import model.GenerateID;
import process.IRestaurantProcessing;
import model.MenuItems;
import model.Order;
import controller.Restaurant;
/**
 * @author Cosmina Radu
 * the graphical user interface for the waiter
 */
public class WaiterInterface extends JFrame implements IRestaurantProcessing {

    private JLabel titleLabel;
    private JButton backButton;
    private JButton createOrderButton;
    private JButton addMenuItemButton;
    private JButton showMenuButton;
    private JButton showOrdersButton;
    private JButton generateBillButton;

   private ArrayList<MenuItems> orderedItems = new ArrayList<MenuItems>();
    ArrayList<Order> orderList = new ArrayList<Order>();

    private JLabel orderIDLabel;
    private JTextField orderIDField;
    private JLabel orderTableLabel;
    private JTextField orderTableField;
    private JLabel orderDateLabel;
    private JTextField orderDateField;

    private JLabel chosenItemsLabel;
    private JTextArea chosenItems;

    private JComboBox<MenuItems> menu;

    private Restaurant restaurant;

    public WaiterInterface(UserInterface userInterface, Restaurant restaurant) {
        this.restaurant = restaurant;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 50, 1175, 600);
        this.getContentPane().setLayout(null);


        titleLabel = new JLabel("Waiter operations ");
        titleLabel.setBounds(300, 50, 450, 50);
        getContentPane().add(titleLabel);

        orderIDLabel = new JLabel("OrderID:");
        orderIDLabel.setBounds(50, 110, 80, 30);
        getContentPane().add(orderIDLabel);

        orderIDField = new JTextField();
        orderIDField.setBounds(120, 110, 50, 30);
        getContentPane().add(orderIDField);

        orderTableLabel = new JLabel("Table:");
        orderTableLabel.setBounds(50, 150, 50, 30);
        getContentPane().add(orderTableLabel);

        orderTableField = new JTextField();
        orderTableField.setBounds(100, 150, 50, 30);
        getContentPane().add(orderTableField);

        orderDateLabel = new JLabel("Date:");
        orderDateLabel.setBounds(50, 200, 50, 30);
        getContentPane().add(orderDateLabel);

        orderDateField = new JTextField();
        orderDateField.setBounds(100, 200, 100, 30);
        getContentPane().add(orderDateField);

        menu = new JComboBox<MenuItems>();
        menu.setBounds(50, 250, 150, 50);
        getContentPane().add(menu);

        chosenItemsLabel = new JLabel("Chosen items");
        chosenItemsLabel.setBounds(230, 130, 100, 20);
        getContentPane().add(chosenItemsLabel);

        chosenItems = new JTextArea();
        chosenItems.setBounds(230, 150, 100, 200);
        getContentPane().add(chosenItems);

        backButton = new JButton("Back");
        backButton.setBounds(800, 500, 100, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setVisible(false);
                userInterface.setVisible(true);

            }
        });
        getContentPane().add(backButton);

        showMenuButton = new JButton("Show menu");
        showMenuButton.setBounds(20, 320, 110, 30);
        getContentPane().add(showMenuButton);
        showMenuButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                fillMenu();
            }
        });

        addMenuItemButton = new JButton("Add");
        addMenuItemButton.setBounds(140, 320, 80, 30);
        getContentPane().add(addMenuItemButton);
        addMenuItemButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                MenuItems myItem = (MenuItems) menu.getSelectedItem();
                chosenItems.append(myItem.toString());
                chosenItems.append("\n");
                orderedItems.add(myItem);
                System.out.println(myItem.getName());

            }
        });

        createOrderButton = new JButton("Create Order");
        createOrderButton.setBounds(50, 500, 150, 50);
        getContentPane().add(createOrderButton);
        createOrderButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                ArrayList<MenuItems> copyList =null;

                try {
                    int ordID = GenerateID.getNextID();
                    int table = Integer.parseInt(orderTableField.getText());
                    String date = orderDateField.getText();
                    System.out.println(ordID + " " + table + " " + date);
                    Order myOrder = new Order(ordID, date, table);
                    copyList = new ArrayList<MenuItems>();
                    Iterator<MenuItems> it = orderedItems.iterator();

                    int i=0;
                    while (it.hasNext()) {
                        i++;
                        copyList.add(it.next());
                    }

                    if(i>0)
                    {
                        createOrder(myOrder, copyList);
                        orderedItems.removeAll(orderedItems);
                        chosenItems.setText("");
                        orderList.add(myOrder);
                        restaurant.addOrder(myOrder);
                    }
                    else
                        JOptionPane.showMessageDialog(null, "No order for the chef");
                }catch (Exception e1) {
                    System.out.println(e1);
                    JOptionPane.showMessageDialog(null, "Error input");
                }
                JOptionPane.showMessageDialog(null, "Order created successfully ");
            }
        });

        showOrdersButton = new JButton("Show Orders");
        showOrdersButton.setBounds(550, 500, 150, 50);
        getContentPane().add(showOrdersButton);
        showOrdersButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Order, ArrayList<MenuItems>> myMap = restaurant.getOrderMap();
                String columns[] = { "OrderID", "Date", "Table", "OrderedItems" };
                DefaultTableModel myModel = new DefaultTableModel();
                myModel.setColumnIdentifiers(columns);
                Object[] obj = new Object[4];
                Iterator<Order> it = orderList.iterator();
                while (it.hasNext()) {
                    Order curentOrder = it.next();
                    obj[0] = curentOrder.getOrderID();
                    String date = curentOrder.getDate();
                    obj[1] = date;
                    obj[2] = curentOrder.getTable();
                    ArrayList<MenuItems> listM = myMap.get(curentOrder);
                    StringBuilder sb = new StringBuilder();
                    Iterator<MenuItems> itt = listM.iterator();
                    while (itt.hasNext()) {
                        sb.append(itt.next().toString());
                        if (itt.hasNext())
                            sb.append(" , ");
                    }
                    obj[3] = sb.toString();
                    myModel.addRow(obj);
                }
                JTable myTable = new JTable(myModel);
                JScrollPane myScrollPane = new JScrollPane();
                myScrollPane.setBounds(350, 100, 775, 350);
                myScrollPane.setViewportView(myTable);
                myScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                myScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                getContentPane().add(myScrollPane);

            }
        });
        generateBillButton = new JButton("Generate Bill !");
        generateBillButton.setBounds(300, 500, 200, 50);
        getContentPane().add(generateBillButton);
        generateBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ordID = Integer.parseInt(orderIDField.getText());
                Order myOrder = findOrderByID(ordID);
                if (myOrder == null)
                    JOptionPane.showMessageDialog(null, "Order not found!");
                else {

                    StringBuilder sBuilder = new StringBuilder();
                    sBuilder.append("Order ID : " + ordID + "\n");
                    sBuilder.append("Date : " + myOrder.getDate()+ "\n");
                    sBuilder.append("Table : " + myOrder.getTable() + "\n");
                    sBuilder.append("Ordered Items : " + "\n");
                    Map<Order, ArrayList<MenuItems>> myMap = restaurant.getOrderMap();
                    ArrayList<MenuItems> list = myMap.get(myOrder);
                    Iterator<MenuItems> it = list.iterator();
                    while (it.hasNext()) {
                        sBuilder.append(it.next().toString());
                        sBuilder.append("\n");
                    }
                    sBuilder.append("Total price : " + priceOrder(myOrder));
                    try {
                        generateBill(sBuilder.toString(), ordID);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Bill generated successfully !");
                }
            }
        });
    }

    @Override
    public void createMenuItem(MenuItems item) {
        System.out.println("can't create items");

    }

    @Override
    public void deleteMenuItem(MenuItems item) {
        System.out.println("can't delete items");

    }

    @Override
    public void editMenuItem(MenuItems item) {
        System.out.println("can't edit items");

    }

    @Override
    public void createOrder(Order order, ArrayList<MenuItems> menuItem) {
        restaurant.createOrder(order, menuItem);

    }


    @Override
    public int priceOrder(Order order) {

        return restaurant.priceOrder(order);
    }

    @Override
    public void generateBill(String whatToPrint, int id) throws IOException {
        restaurant.generateBill(whatToPrint, id);

    }

    public void fillMenu() {
        menu.removeAllItems();
        ArrayList<MenuItems> list = restaurant.getMenu();
        Iterator<MenuItems> it = list.iterator();
        while (it.hasNext()) {
            MenuItems currentItem = it.next();
            menu.addItem(currentItem);
        }
    }

    public Order findOrderByID(int id) {
        Order myOrder = null;
        Iterator<Order> it = orderList.iterator();
        while (it.hasNext()) {
            Order curOrder = it.next();
            if (curOrder.getOrderID() == id)
                myOrder = curOrder;
        }
        return myOrder;
    }

    public ArrayList<Order> getOrderList()
    {
        return this.orderList;
    }

}
