package model;

/**
 * @author Cosmina Radu
 * model class for the order
 */
public class Order {
    private int OrderID;
    private String date;
    private int table;

    public Order(int OrderID, String date, int table)
    {
        this.OrderID=OrderID;
        this.date=date;
        this.table=table;
    }
    public int getOrderID() {
        return OrderID;
    }
    public int getTable() {
        return table;
    }
    public void setOrderID(int id) {
        this.OrderID=id;
    }
    public void setTable(int table) {
        this.table=table;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date)
    {
        this.date=date;
    }

    @Override
    public int hashCode()
    {
        int hashcode = 5;
        hashcode += hashcode*7 + 17*OrderID;
        return hashcode;

    }

}
