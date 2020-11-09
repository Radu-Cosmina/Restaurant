package model;

import javax.print.DocFlavor;

/**
 * @author Cosmina Radu
 * model class for the base product
 */
public class Product extends MenuItems {

    public  Product(String name, int price)
    {
        this.name=name;
        this.price=price;
    }

    @Override
    public int computePrice() {
        return getPrice();
    }
    public void setName(String name){this.name=name;}
    public String getName(){return this.name;}
    public void setPrice(int price){this.price=price;}
    public int getPrice(){return this.price;}

}
