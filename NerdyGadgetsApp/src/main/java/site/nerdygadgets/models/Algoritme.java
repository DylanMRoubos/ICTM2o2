package site.nerdygadgets.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Algoritme {

    private double bescikbaarheid = 99.99;

    private double pfSensebeschikbaarheid = 99.998;
    private int pfSenseprijs = 4000;

    public static void main(String[] args) {
        List<String[]> componenten = new ArrayList<String[]>();

        String[] array = {"HAL90001DB", "90", "5100", "0"};
        String[] array1 = {"HAL9002DB", "95", "7700", "0"};
        String[] array2 = {"HAL9003DB", "98", "12200", "0"};
        String[] array3 = {"HAL90001W", "80", "2200", "1"};
        String[] array4 = {"HAL9002DW", "90", "3200", "1"};
        String[] array5 = {"HAL9003DW", "95", "5100", "1"};
        String[] array6 = {"firewall pfSens", "99.998", "4000", "2"};


        componenten.add(array);
        componenten.add(array1);
        componenten.add(array2);
        componenten.add(array3);
        componenten.add(array4);
        componenten.add(array5);
        componenten.add(array6);


        for (String[] strArr : componenten) {
            if (strArr[3].equals("0")) {

                System.out.println("databaseserver " + strArr[0]);
            } else if (strArr[3].equals("1")){

                System.out.println("webserver " + strArr[0]);
            } else {
                System.out.println("pfSense " + strArr[0]);
            }

            // 1 - (1-webserver) * (1-webserver2) = double
            // 1 - (1-dbserver) * (1-dbserver2) = double2
            // pfSense = 0.99998 = double 3
            // double * double 2 * double 3 = beschikbaarheid

        }
    }



}
