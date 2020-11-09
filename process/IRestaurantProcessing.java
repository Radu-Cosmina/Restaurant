package process;

import model.MenuItems;
import model.Order;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Cosmina Radu
 * interface for the processing of the restaurant operations
 */
public interface IRestaurantProcessing {
    //for the menu items:

    /**
     * pre item != null
     * post  prelist.size+1=postlist.size
     * @param item
     */
    public void createMenuItem(MenuItems item);

    /**
     * pre item!=null
     * post postlist.size = prelist.size +1
     * @param item
     */
    public void deleteMenuItem(MenuItems item);

    /**
     * pre item!=null
     * post item@pre.price != item@post.price
     * @param item
     */
    public void editMenuItem(MenuItems item);
    //for the orders:

    /**
     * pre order != null
     * pre menuItem != null
     * @param order
     * @param item
     */
    public void createOrder(Order order, ArrayList<MenuItems> item);

    /**
     * pre order!=null
     * post price!=0
     * @param order
     * @return
     */
    public int priceOrder(Order order);

    /**
     * pre bill!=0
     * @param bill
     * @param id
     * @throws IOException
     */
    public void generateBill(String bill, int id) throws IOException;
}
