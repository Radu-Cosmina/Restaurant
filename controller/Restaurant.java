package controller;

import model.MenuItems;
import model.Order;
import process.IRestaurantProcessing;

import java.io.*;
import java.util.*;

/**
 * @author Comsina Radu
 * class with the main methods for a restaurant
 */
public class Restaurant extends Observable implements IRestaurantProcessing,java.io.Serializable{

    private ArrayList<MenuItems> menu;
    private ArrayList<MenuItems> compProducts=new ArrayList<MenuItems>();
    private String compName;
    private ArrayList<Order> orders;
    private Map<Order, ArrayList<MenuItems>> orderMap;

    public Restaurant()
    {
        this.menu=new ArrayList<MenuItems>();
       // this.compProducts=new ArrayList<MenuItems>();
        //this.compName=null;
        this.orders=new ArrayList<Order>();
        this.orderMap=new HashMap<Order, ArrayList<MenuItems>>();
    }

    public ArrayList<MenuItems> getCompProducts() {
        return compProducts;
    }

    public void setCompProducts(ArrayList<MenuItems> compProducts) {
        this.compProducts = compProducts;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public Map<Order, ArrayList<MenuItems>> getOrderMap() {
        return orderMap;
    }

    public void setOrderMap(Map<Order, ArrayList<MenuItems>> orderMap) {
        this.orderMap = orderMap;
    }

    public ArrayList<MenuItems> getMenu() {
        return this.menu;
    }
    public void setMenu(ArrayList<MenuItems> items) {
        this.menu=items;
    }

    /**
     * createMenuItem
     * generates the menu item written by the administrator
     * @param item
     */
    public void createMenuItem(MenuItems item)//administrator creates the menu item
    {
        assert item != null;
        int size=menu.size();
        menu.add(item);
        assert size+1==menu.size();

    }

    /**
     * deleteMenuItem
     * deletes the item with the name given by the administrator
     * @param item
     */
    public void deleteMenuItem(MenuItems item)//administrator deletes the menu item
    {
        assert item != null;
        int size=menu.size();
        menu.remove(item);
        assert size-1==menu.size();
    }

    /**
     * editMenuItem
     * edits the price of the item with the name given by the administrator
     * @param item
     */
    public void editMenuItem(MenuItems item)//administrator deletes the menu item
    {
        assert item != null;
        int post=item.getPrice();
        int pre=0;
        String name=item.getName();
        int price=item.getPrice();
        for(MenuItems m: menu)
        {
            MenuItems currentItem=m;
            if(currentItem.getName()==name)
            {
                currentItem.setPrice(price);
                pre=currentItem.getPrice();
            }
        }
        assert pre==post;

    }

    /**
     * createOrder
     * method done by the waiter
     * @param order
     * @param item
     */
    public void createOrder(Order order, ArrayList<MenuItems> item)//the waiter creates the order
    {
       // if(order!=null && item!=null)
        assert order!=null;
        assert item!=null;
            orderMap.put(order, item);
    }

    /**
     * addOrder
     * add the order done by the waiter to the chef interface
     * @param order
     */
    public void addOrder(Order order)//the created order is sent to the chef
    {
        assert order!=null;
        orders.add(order);
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("New order: "+"\n");
        Map<Order, ArrayList<MenuItems>> map=getOrderMap();
        ArrayList<MenuItems> list=map.get(order);
        Iterator<MenuItems> item=list.iterator();
        while(item.hasNext())
        {
            stringBuilder.append(item.next().toString());
            stringBuilder.append("\n");
        }
        setChanged();
        notifyObservers(stringBuilder.toString());
    }


    /**
     * priceOrder
     * generates the final price for an order
     * @param order
     * @return
     */
    public int priceOrder(Order order)//computes the final price of the order
    {
        assert order!=null;
        int pre=0;
        int finalPrice=0;
        if(order!=null)
        {
            if(this.orders.contains(order))
            {
                for(MenuItems m: orderMap.get(order))
                {
                    finalPrice+=m.getPrice();
                }
            }

        }
        assert finalPrice!=pre;
        return finalPrice;

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

    /**
     * generateBill
     * generates a txt file with the order data: id, table, items and final price
     * @param bill
     * @param id
     * @throws IOException
     */
    public void generateBill(String bill, int id) throws IOException {//generates the bill for the chosen order
        assert bill!=null;
        BufferedWriter buff=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("bill"+id+".txt")));
        buff.write(bill);
        buff.close();
    }

//    public MenuItems findByName(String name)
//    {
//        ArrayList<MenuItems> items = restaurant.getMenu();
//        Iterator<MenuItems> it = items.iterator();
//        while(it.hasNext())
//        {
//            MenuItem curentItem = it.next();
//            if(curentItem.getName().compareTo(name)==0) return curentItem;
//        }
//        return null;
//    }

}
