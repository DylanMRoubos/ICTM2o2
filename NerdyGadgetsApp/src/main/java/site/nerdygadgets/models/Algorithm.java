package site.nerdygadgets.models;

import site.nerdygadgets.functions.ComponentType;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Algorithm class
 * Class to calculate the best solution for the given components
 *
 * @author Ade Wattimena & Ruben Oosting & Dylan Roubos
 * @version 1.0
 * @since 19-05-2020
 */
public class Algorithm {

    // arraylist for db solutions and web solutions formatted in ArrayString for easy acces
    private List<String> dbSolutions = new ArrayList<>();
    private List<String> webSolutions = new ArrayList<>();

    // Arraylist filled by algorithm method returns best possible solution
    private ArrayList<InfrastructureComponentModel> bestList = new ArrayList<>();

    //Best solution displayed with numbers
    private String bestSolution;

    // temporary availabilty and amount of dbservers and webservers
    private double availabilty;
    private int amount = 12;

    // arraylist for components
    private List<String[]> algorithmComponents;

    // Null Components
    private String[] dbnull = {"Null", "0", "0", "Database", "0"};
    private String[] webnull = {"Null", "0", "0", "Webserver", "1"};

    // array for dbservers, dbserver null + amount dbservers
    private int[] dbArr;
    private int x = 0;

    // array for webervers, webserver null + amount webservers
    private int[] webArr;
    private int y = 0;

    // defaults
    private double highestWebServer = 1;

    private double dbPrice = 0;
    private double dbPercentage = 1;

    private double webPrice = 0;
    private double webPercentage = 1;

    private double bestSolutionPrice = 0;
    private double bestSolutionAvailabilty = 0;

    private double dbTestPercentage = 1;

    private int dbCounter = 1;
    private int webCounter = 1;

    private ArrayList<ComponentModel> components;

    // Set default values
    public Algorithm(double availabilty, ArrayList<ComponentModel> components) {
        this.availabilty = availabilty;
        this.components = components;
        algorithmComponents = new ArrayList<>();

        // Add components to arraylist components as String
        addComponentsToArrayListAsString();
        // Add Webservers and DBservers to indivual arrays
        addServersToIndividualArrays();
        // current highest webserver
        calculateHighestAvailableWebServer();
        // all solutions for dbservers
        createPossibilitiesArray(dbArr, amount, ComponentType.Database);
        // all solutions for webservers
        createPossibilitiesArray(webArr, amount, ComponentType.Webserver);

        try {
            // for the best solution
            calculateBestSolution();
            // return arraylist with best solution
            createList();
        } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Geen oplossing mogelijk");
        }

    }

    //Transfer ComponentModel araylist components into String arralist with index
    public void addComponentsToArrayListAsString() {
        int componentCounter = 2;

        // add dbnull components to componentlist
        algorithmComponents.add(dbnull);
        // add webnull components to componentlist
        algorithmComponents.add(webnull);

        //Loop through component and add to list with the correct data based on type
        for (ComponentModel component : components) {

            //Add component to list
            String[] add = {component.getName(), String.valueOf(component.getAvailability()), String.valueOf(component.getPrice()), String.valueOf(component.getType()), String.valueOf(componentCounter)};
            algorithmComponents.add(add);

            //Check the component type
            switch (component.getType()) {
                case Webserver:
                    webCounter++;
                    break;
                case Database:
                    dbCounter++;
                    break;
            }
            componentCounter++;
        }
    }

    //Create 2 arraylists 1 for webservers and 1 for dbservers
    public void addServersToIndividualArrays() {
        webArr = new int[webCounter];
        dbArr = new int[dbCounter];

        // check if component is a webserver or a dbserver and then adds the componentnumber to the array
        for (String[] strInt : algorithmComponents) {

            //add dbarray components
            if (strInt[3].equals("Database")) {
                dbArr[x] = Integer.parseInt(strInt[4]);
                x++;
                //add webarray components
            } else if (strInt[3].equals("Webserver")) {
                webArr[y] = Integer.parseInt(strInt[4]);
                y++;
            }
        }
    }

    public void createPossibilitiesArray(int[] componentArray, int amount, ComponentType componentType) {
        // Allocate memory
        int[] possibilityArray = new int[amount];

        // Call the recursice function
        fillPossibilietsArray(possibilityArray, componentArray, 0, amount, 0, componentArray.length - 1, componentType);
    }

    public void fillPossibilietsArray(int[] possibilityArray, int[] componentArray, int index, int amount, int start, int end, ComponentType componentType) {

        //Check if array is full
        if (index == amount) {
            String serverCombination = "";

            for (int i = 0; i < amount; i++) {
                String serverNumber = Integer.toString(componentArray[possibilityArray[i]]);
                if (serverCombination.equals("")) {
                    serverCombination += serverNumber;
                } else if (i == amount - 1) {
                    serverCombination += "-" + serverNumber + "-";
                } else {
                    serverCombination += "-" + serverNumber;
                }
            }

            if (componentType.equals(ComponentType.Webserver)) {
                webSolutions.add(serverCombination);
            } else if (componentType.equals(ComponentType.Database)) {
                dbSolutions.add(serverCombination);
            }

            System.out.println(serverCombination);
            return;
        }

        // One by one choose all elements (without considering
        // the fact whether element is already chosen or not)
        // and recur
        for (int i = start; i <= end; i++) {
            possibilityArray[index] = i;
            fillPossibilietsArray(possibilityArray, componentArray, index + 1, amount, i, end, componentType);
        }

    }

    public void calculateHighestAvailableWebServer() {
        boolean firstServer = true;
        double serverPercentage = 0.0;

        //Loop through components for webservers and find the webserver with the highest availability and save the availability percentage in the variable
        for (String[] strInt : algorithmComponents) {
            if (strInt[3].equals("Webserver") && !strInt[4].equals("1")) {
                if (firstServer) {
                    serverPercentage = Double.parseDouble(strInt[1]);
                    firstServer = false;
                } else if (serverPercentage < Double.parseDouble(strInt[1])) {
                    serverPercentage = Double.parseDouble(strInt[1]);
                }
            }
        }


        //Calculate the best possibilitie with the maximun number of the highest percentage webserver.
        for (int z = 0; z < amount; z++) {
            highestWebServer *= (1 - (serverPercentage / 100));
        }

        highestWebServer = (1 - highestWebServer);
    }

    //Calculate the best possible solution with the given percentage and components
    public void calculateBestSolution() {

        // foreach dbsolotion try (almost) all websolutions
        for (String dbsolution : dbSolutions) {
            int dbServerNumber;
            int componentNumber;
            int webServerNumber;

            for (int x = 0; x < dbsolution.length(); x++) {
                if (dbsolution.charAt(x) == '-') {
                    continue;
                    //Double digit in String
                } else if (!(dbsolution.charAt(x + 1) == '-')) {
                    //Get both digits
                    dbServerNumber = Character.getNumericValue(dbsolution.charAt(x) + dbsolution.charAt(x + 1));
                    x++;
                    //Single digit in String
                } else {
                    //get digit
                    dbServerNumber = Character.getNumericValue(dbsolution.charAt(x));
                }
                //Calculate db webpercentage
                for (String[] strInt : algorithmComponents) {
                    componentNumber = Integer.parseInt(strInt[4]);

                    if (dbServerNumber == componentNumber) {
                        dbTestPercentage = dbTestPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                    }
                }
            }

            dbTestPercentage = (1 - dbTestPercentage);
            double testTotalPercentage = (dbTestPercentage * highestWebServer * 0.99998) * 100;
            dbTestPercentage = 1;

            // foreach websolution calculate the percentage and price
            for (String websolution : webSolutions) {

                // if the percentage of the highest websolution is lower with the current dbsolution break and go to the next dbsolution
                if (testTotalPercentage < availabilty) {
                    break;
                }

                for (x = 0; x < dbsolution.length(); x++) {
                    if (dbsolution.charAt(x) == '-') {
                        continue;
                        //2 characters
                    } else if (!(dbsolution.charAt(x + 1) == '-')) {
                        dbServerNumber = Character.getNumericValue(dbsolution.charAt(x) + dbsolution.charAt(x + 1));
                        x++;
                        //1 character
                    } else {
                        dbServerNumber = Character.getNumericValue(dbsolution.charAt(x));
                    }
                    //calculate db percentage + price
                    for (String[] strInt : algorithmComponents) {
                        componentNumber = Integer.parseInt(strInt[4]);

                        if (dbServerNumber == componentNumber) {
                            dbPrice += Double.parseDouble(strInt[2]);
                            dbPercentage = dbPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                        }
                    }
                }

                for (x = 0; x < websolution.length(); x++) {
                    if (websolution.charAt(x) == '-') {
                        continue;
                    } else if (!(websolution.charAt(x + 1) == '-')) {
                        webServerNumber = Character.getNumericValue(websolution.charAt(x) + websolution.charAt(x + 1));

                        x++;

                    } else {
                        webServerNumber = Character.getNumericValue(websolution.charAt(x));

                    }
                    for (String[] strInt : algorithmComponents) {
                        componentNumber = Integer.parseInt(strInt[4]);

                        if (webServerNumber == componentNumber) {
                            webPrice += Double.parseDouble(strInt[2]);
                            webPercentage = webPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                        }
                    }
                }


                webPercentage = (1 - webPercentage);
                dbPercentage = (1 - dbPercentage);

                //hardcoded default PFsense
                double totalPercentage = (webPercentage * dbPercentage * 0.99998) * 100;
                double totalPrice = webPrice + dbPrice + 4000;

                System.out.println(totalPercentage);
                System.out.println(availabilty);

                if (totalPercentage >= availabilty) {
                    if (bestSolutionPrice == 0) {
                        bestSolutionPrice = totalPrice;
                        bestSolutionAvailabilty = totalPercentage;
                        bestSolution = websolution + dbsolution + getPfSenseFromArray() + "-";
                    } else if (totalPrice < bestSolutionPrice) {
                        bestSolutionPrice = totalPrice;
                        bestSolutionAvailabilty = totalPercentage;
                        System.out.println(websolution + dbsolution);
                        bestSolution = websolution + dbsolution + getPfSenseFromArray() + "-";
                    }
                }

                webPercentage = 1;
                webPrice = 0;
                dbPrice = 0;
                dbPercentage = 1;
            }
        }
    }

    //Get the first PFsense from the arraylist
    public int getPfSenseFromArray() {

        for (String[] strInt : algorithmComponents) {

            if (strInt[3].equals("Firewall")) {
                return Integer.parseInt(strInt[4]);
            }
        }
        return 0;
    }

    public void createList() {

        int bestSolutionNumber;
        int componentNumber;
        InfrastructureComponentModel bestInfrastructure;

        for (int p = 0; p < bestSolution.length(); p++) {
            if (bestSolution.charAt(p) == '-' || (bestSolution.charAt(p) == '0' && bestSolution.charAt(p + 1) == '-') || (bestSolution.charAt(p) == '1' && bestSolution.charAt(p + 1) == '-')) {
                continue;
            } else if (!(bestSolution.charAt(p + 1) == '-')) {
                bestSolutionNumber = Character.getNumericValue(bestSolution.charAt(p) + bestSolution.charAt(p + 1));

                for (String[] strInt : algorithmComponents) {
                    componentNumber = Integer.parseInt(strInt[4]);

                    if (bestSolutionNumber == componentNumber) {
                        if (bestList.isEmpty()) {
                            bestInfrastructure = new InfrastructureComponentModel(strInt[0], Double.parseDouble(strInt[1]), Double.parseDouble(strInt[2]), ComponentType.valueOf(strInt[3]), 1);
                            bestList.add(bestInfrastructure);
                        } else {
                            // checken of element al bestaat
                            boolean newCompoment = false;
                            int objectPosition = 0;
                            int currentAmount = 0;
                            for (InfrastructureComponentModel icm : bestList) {
                                // zoja verhoog amount
                                if (icm.getName().equals(strInt[0]) && icm.getType().equals(ComponentType.valueOf(strInt[3]))) {

                                    newCompoment = false;
                                    currentAmount = icm.getAmount();
                                    break;
                                    //zo nee maak nieuw element aan
                                } else {
                                    newCompoment = true;
                                }
                                objectPosition++;
                            }
                            if (newCompoment) {
                                bestInfrastructure = new InfrastructureComponentModel(strInt[0], Double.parseDouble(strInt[1]), Double.parseDouble(strInt[2]), ComponentType.valueOf(strInt[3]), 1);
                                bestList.add(bestInfrastructure);
                                System.out.println("new component");
                            } else {
                                bestList.set(objectPosition, new InfrastructureComponentModel(strInt[0], Double.parseDouble(strInt[1]), Double.parseDouble(strInt[2]), ComponentType.valueOf(strInt[3]), currentAmount + 1));
                                System.out.println(bestList.get(objectPosition));
                            }
                        }
                    }
                }
                p++;

            } else {
                bestSolutionNumber = Character.getNumericValue(bestSolution.charAt(p));

                for (String[] strInt : algorithmComponents) {
                    componentNumber = Integer.parseInt(strInt[4]);
                    if (bestSolutionNumber == componentNumber) {
                        if (bestList.isEmpty()) {
                            bestInfrastructure = new InfrastructureComponentModel(strInt[0], Double.parseDouble(strInt[1]), Double.parseDouble(strInt[2]), ComponentType.valueOf(strInt[3]), 1);
                            bestList.add(bestInfrastructure);
                        } else {
                            // checken of element al bestaat
                            boolean newCompoment = false;
                            int objectPosition = 0;
                            int currentAmount = 0;
                            for (InfrastructureComponentModel icm : bestList) {
                                // zoja verhoog amount
                                if (icm.getName().equals(strInt[0]) && icm.getType().equals(ComponentType.valueOf(strInt[3]))) {

                                    newCompoment = false;
                                    currentAmount = icm.getAmount();
                                    break;
                                    //zo nee maak nieuw element aan
                                } else {
                                    newCompoment = true;
                                }
                                objectPosition++;
                            }
                            if (newCompoment) {
                                bestInfrastructure = new InfrastructureComponentModel(strInt[0], Double.parseDouble(strInt[1]), Double.parseDouble(strInt[2]), ComponentType.valueOf(strInt[3]), 1);
                                bestList.add(bestInfrastructure);
                                System.out.println("new component");
                            } else {
                                bestList.set(objectPosition, new InfrastructureComponentModel(strInt[0], Double.parseDouble(strInt[1]), Double.parseDouble(strInt[2]), ComponentType.valueOf(strInt[3]), currentAmount + 1));
                                System.out.println(bestList.get(objectPosition));
                            }
                        }
                    }
                }
            }
        }
    }

    public double getBestSolutionAvailabilty() {
        return bestSolutionAvailabilty;
    }

    public double getBestSolutionPrice() {
        return bestSolutionPrice;
    }

    public ArrayList<InfrastructureComponentModel> getList() {
        return bestList;
    }
}

