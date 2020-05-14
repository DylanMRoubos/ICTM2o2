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
        //Availability van alle 3 begint op 0.00 (zodat als niks van dit aanwezig is de eindberekening de availability van het component 100% is en het geen invloed heeft)
        double firewall   = 0.00;
        double webservers = 0.00;
        double databases   = 0.00;

        for (InfrastructureComponentModel model : components) {
            for (int i = 0; i < model.getAmount(); i++) {
//                System.out.println(model.getAvailability()/100);
                switch (model.getType()) {
                    case Firewall:
                        //Als het nog niet gezet is, dan is dit de eerste dus die hoef je niet te vermedigvuldigen met de oude waarde (ander krijg je 0xiets = 0)
                        //Als dit niet de eerste is, dan vermedigvuldig de oude availability met de die van dit component
                        firewall = (firewall == 0.00) ? calculateAvailability(model.getAvailability()/100) : precisionFixedMultiply(firewall, calculateAvailability(model.getAvailability()/100));
                        break;
                    case Webserver:
                        webservers = (webservers == 0.00) ? calculateAvailability(model.getAvailability()/100) : precisionFixedMultiply(webservers, calculateAvailability(model.getAvailability()/100));
                        break;
                    case Database:
                        databases = (databases == 0.00) ? calculateAvailability(model.getAvailability()/100) : precisionFixedMultiply(databases, calculateAvailability(model.getAvailability()/100));
                        break;
                    default:
                        System.out.println("[ERROR] Modeltype does not exist");
                }
            }
        }

//De normale berekening, eerst 1-availability zodat je 0.90x0.95x0.97 krijgt ipv 0.10x0.05x0.03 (de uitkomst van bovenstaande for loopjes)
        double finalCalculation = (1-firewall) * (1-webservers) * (1-databases);
        //Om er weer een normal percentage van te maken, het weer keer 100 doen.
        double systemAvailabilityPercentage = finalCalculation * 100;

//        System.out.println("Final percentage: " + systemAvailabilityPercentage + "%");
        if(components.size() == 0){
            return 0.0;
        }else{
            return systemAvailabilityPercentage;
        }
    }
    //Gebruik hier bigdecimal, je precision hangt af van wat je wilt qua input, 99.85% = 0.9985 = 4 precisie. Als je 99.99999 wilt moet je dus 0.9999999 = 7 precisie
    private static double calculateAvailability(double availability){
        return new BigDecimal("1.0000").subtract(new BigDecimal(availability), new MathContext(4)).doubleValue();
    }

    //Ook voor vermedigvuldigen van je availibility zou ik afkappen, 10 lijkt me wel genoeg, soms is alleen bovenstaande functie anders niet genoeg.
    //10 is 99.99999999%
    private static double precisionFixedMultiply(double currentAvailability, double componentAvailability){
        return new BigDecimal(currentAvailability).multiply(new BigDecimal(componentAvailability), new MathContext(10)).doubleValue();
    }
}
