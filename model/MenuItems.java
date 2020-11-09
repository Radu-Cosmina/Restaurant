package model;

/**
 * @author Cosmina Radu
 * abstract class used por the base products and the composite products
 */
public abstract class MenuItems implements java.io.Serializable{
    protected String name;
    protected int price;

  /*  public MenuItems(String name, int price )
    {
        this.name=name;
        this.price=price;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String toString()
    {
        return this.name + " "+ this.price;

    }
    public abstract int computePrice();
}
