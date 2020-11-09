package controller;

import model.MenuItems;
import model.Order;
import view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Cosmina Radu
 * main control class
 */
public class MainController {

    private Restaurant restaurant;
    private UserInterface userInterface;
    private WaiterInterface waiterInterface;
    private ChefInterface chefInterface;
    private AdminInterface adminInterface;
    private CompProductInterface compProductInterface;

    public void start() throws IOException, ClassNotFoundException {
       // restaurant=new Restaurant();//when we use a new file, first we must serialize it tha deserialize it
        //RestaurantSerializator.serialize(restaurant);
        ////restaurant=new Restaurant();
       restaurant=RestaurantSerializator.deSerialize();
        userInterface=new UserInterface();
        waiterInterface=new WaiterInterface(userInterface, restaurant);
        chefInterface=new ChefInterface(userInterface, restaurant);
        compProductInterface=new CompProductInterface(restaurant);
        adminInterface=new AdminInterface(userInterface, restaurant, compProductInterface);
        userInterface.setVisible(true);

        userInterface.addToChefButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // userInterface.setVisible(false);
                chefInterface.setVisible(true);
            }
        });

        userInterface.addToWaiterButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //userInterface.setVisible(false);
                waiterInterface.setVisible(true);
            }
        });

        userInterface.addToAdminButtonActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               // userInterface.setVisible(false);
                adminInterface.setVisible(true);
            }
        });
    }
//controller method to create the menu items
    public void properCreateMenuItem(MenuItems item){restaurant.createMenuItem(item);}
    //controller method to delete menu items
    public void properDeleteMenuItem(MenuItems item){restaurant.deleteMenuItem(item);}
    //controller method do edit menu items
    public void properEditMenuItem(MenuItems item){restaurant.editMenuItem(item);}
    //controller method to create order
    public void properCreateOrder(Order order, ArrayList<MenuItems> item){restaurant.createOrder(order, item);}
    //controller method to add order to chef
    public void properAddOrder(Order order){restaurant.addOrder(order);}
    //controller method to compute the price of an order
    public int properPriceOrder(Order order){return restaurant.priceOrder(order);}
    //controller method to generate the bill for an order
    public void properGenerateBill(String bill, int id) throws IOException {restaurant.generateBill(bill, id);}

    }
