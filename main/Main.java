package main;

import controller.MainController;

import java.io.IOException;

/**
 * @author Cosmina Radu
 * starts the application
 */
public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MainController ctrl=new MainController();
        ctrl.start();
    }

}
