package model;

import java.util.ArrayList;
import java.awt.*;

/**
 * @author Cosmina Radu
 * model class for the composite product
 */
public class CompositeProduct extends MenuItems{
    private ArrayList<MenuItems> compositeProducts;
    public CompositeProduct(String name, ArrayList<MenuItems> compositeProducts)
    {
        this.name=name;
        this.compositeProducts=compositeProducts;
    }
    public int computePrice()
    {
        int price=0;
        for(MenuItems m: compositeProducts)
        {
            MenuItems currentItem=m;
            price+=currentItem.getPrice();
        }
        return price;
    }


}

