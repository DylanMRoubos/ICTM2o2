package site.nerdygadgets.functions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import site.nerdygadgets.models.ComponentModel;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

public class CalculateComponent {

    public static double calculatePrice(ArrayList<ComponentModel> components) {
        double totalPrice = 0;
        for (ComponentModel model : components) {
            totalPrice += model.getPrice();
        }
        return totalPrice;
    }

    public static double calculateAvailability(ArrayList<ComponentModel> components) {
        double firewall   = 0;
        double webservers = 0;
        double databases   = 0;
        double totalAvailability = 0;

        for (ComponentModel model : components) {
            switch (model.getType()) {
                case Firewall:
                    if (firewall > 0)
                        firewall = firewall * (1-model.getAvailability());
                    else
                        firewall += (1-model.getAvailability());
                    break;
                case Webserver:
                    if (webservers > 0)
                        webservers = webservers * (1-model.getAvailability());
                    else
                        webservers += (1-model.getAvailability());
                    break;
                case Database:
                    if (databases > 0)
                        databases = databases * (1-model.getAvailability());
                    else
                        databases += (1-model.getAvailability());
                    break;
                default:
                    System.out.println("[ERROR] Modeltype does not exist");
            }
        }
        System.out.println("Availability Firewall:   " + (1-firewall));
        System.out.println("Availability Webservers: " + (1-webservers));
        System.out.println("Availability Databases:  " + (1-databases));

        //Voor de berekening anders krijg je 0*availability
        if (firewall == 0)
            firewall = 2;
        if (webservers == 0)
            webservers = 2;
        if (databases == 0)
            databases = 2;

        totalAvailability = (1-firewall)*(1-webservers)*(1-databases);

        System.out.println("Total availability: " + totalAvailability);
        return totalAvailability;
    }
}
