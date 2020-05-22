package site.nerdygadgets.functions;

import site.nerdygadgets.models.InfrastructureComponentModel;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

/**
 * CalculateComponent class
 * Calulates the total price and total availability of a Design
 *
 * @author Tristan Scholten & Jordy Wielaard
 * @version 1.0
 * @since 14-05-2020
 */
public class CalculateComponent {
    // Calculates total price design
    public static double calculatePrice(ArrayList<InfrastructureComponentModel> components) {
        double totalPrice = 0;
        for (InfrastructureComponentModel model : components) {
            for (int i = 0; i < model.getAmount(); i++)
                totalPrice += model.getPrice();
        }
        return totalPrice;
    }

    // Calculates total availability design
    public static double calculateAvailability(ArrayList<InfrastructureComponentModel> components) {
        //Availability for all start with 0.00 (so nothing that is available will be used in the calculation);
        double firewall = 0.00;
        double webservers = 0.00;
        double databases = 0.00;

        for (InfrastructureComponentModel model : components) {
            for (int i = 0; i < model.getAmount(); i++) {
                switch (model.getType()) {
                    case Firewall:
                        // If it's not set yet, this is the first one so you don't have to multiply it by the old value (otherwise you get 0xiets = 0)
                        // If this is not the first, multiply the old availability by the one of this component
                        firewall = (firewall == 0.00) ? calculateAvailability(model.getAvailability() / 100) : precisionFixedMultiply(firewall, calculateAvailability(model.getAvailability() / 100));
                        break;
                    case Webserver:
                        webservers = (webservers == 0.00) ? calculateAvailability(model.getAvailability() / 100) : precisionFixedMultiply(webservers, calculateAvailability(model.getAvailability() / 100));
                        break;
                    case Database:
                        databases = (databases == 0.00) ? calculateAvailability(model.getAvailability() / 100) : precisionFixedMultiply(databases, calculateAvailability(model.getAvailability() / 100));
                        break;
                    default:
                        System.out.println("[ERROR] Modeltype does not exist");
                }
            }
        }


        // The normal calculation, first 1-availability so that you get 0.90x0.95x0.97 instead of 0.10x0.05x0.03 (the result of the above for loops)
        double finalCalculation = (1 - firewall) * (1 - webservers) * (1 - databases);
        // To make it a normal percentage again, do it again 100 times.
        double systemAvailabilityPercentage = finalCalculation * 100;

        if (components.size() == 0) {
            return 0.0;
        } else {
            return systemAvailabilityPercentage;
        }
    }

    // Use bigdecimal here, your precision depends on what you want in terms of input, 99.85% = 0.9985 = 4 precision. So if you want 99.99999 you need 0.9999999 = 7 precision
    private static double calculateAvailability(double availability) {
        return new BigDecimal("1.0000").subtract(new BigDecimal(availability), new MathContext(4)).doubleValue();
    }


    // I would also truncate for increasing your availibility, 10 seems enough, sometimes just the above function is not enough otherwise.
    // 10 is 99.99999999%
    private static double precisionFixedMultiply(double currentAvailability, double componentAvailability) {
        return new BigDecimal(currentAvailability).multiply(new BigDecimal(componentAvailability), new MathContext(10)).doubleValue();
    }
}
