package controller;

import java.io.*;

/**
 * @author Cosmina Radu
 * serialization class
 */
public class RestaurantSerializator {

    public static void serialize(Restaurant restaurant) throws IOException {
        FileOutputStream outFile=new FileOutputStream("Restaurant.ser");
        ObjectOutputStream outObj=new ObjectOutputStream(outFile);
        outObj.writeObject(restaurant);
        outObj.close();
        outFile.close();
        System.out.println("Saved Data\n");
    }//method to save the data in the file

    public static Restaurant deSerialize() throws IOException, ClassNotFoundException {
        FileInputStream inFile=new FileInputStream("Restaurant.ser");
        ObjectInputStream inObj=new ObjectInputStream(inFile);
        Restaurant restaurant=(Restaurant) inObj.readObject();
        inObj.close();
        inFile.close();

        System.out.println("Retrieved Data\n");
        return restaurant;
    }//method to retrieve the data from the file

}
