package model;

/**
 * @author Cosmina Radu
 * class that generates the id for the order
 */
public class GenerateID {
    public static int count=1;
    public static int getNextID(){
        return count++;
    }
    public GenerateID(){}
}
