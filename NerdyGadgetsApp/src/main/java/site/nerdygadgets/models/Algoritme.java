package site.nerdygadgets.models;

import site.nerdygadgets.functions.ComponentType;

import java.util.ArrayList;
import java.util.List;

public class Algoritme {

    // arraylist for db solutions and web solutions
    private List<String> dbSolutions = new ArrayList<>();
    private List<String> webSolutions = new ArrayList<>();

    // temporary availabilty and amount of dbservers and webservers
    private double availabilty;
    private int amount = 5;

    // arraylist for components
    private List<String[]> algorithmComponents = new ArrayList<>();

    // Null Components
    private String[] dbnull = {"Null", "0", "0", "Database", "0"};
    private String[] webnull = {"Null", "0", "0", "Webserver", "1"};

    // array for dbservers, dbserver null + amount dbservers = 4
    // TODO: method to count the amount of dbservers or to count the chosen dbservers
    private int[] dbArr = new int[4];
    private int x = 0;

    // TODO: method to count the amount of webservers or to count the chosen webservers
    // array for webervers, webserver null + amount webservers = 4
    private int[] webArr = new int[4];
    private int y = 0;

    private int n = dbArr.length;
    private int r = amount;

    private int k = webArr.length;
    private int l = amount;

    // defaults
    private double highestWebServer = 1;

    private double dbPrice = 0;
    private double dbPercentage = 1;

    private double webPrice = 0;
    private double webPercentage = 1;

    private double bestSolutionPrice = 0;
    private double bestSolutionAvailabilty = 0;

    private double dbTestPercentage = 1;

    private ArrayList<ComponentModel> test123;

    // TODO: constructor for given availabilty and given components / all components
    public Algoritme(double availabilty, List components , boolean ownChoice) {

    }

    public Algoritme(double availabilty, ArrayList<ComponentModel> components) {
        this.availabilty = availabilty;
        this.test123 = components;

        // Add components to arraylist components
        AddComponents();

        // Add servers to array
        AddServers();

        // current highest webserver
        HighestWebServer();

        // all solutions for dbservers
        CombinationRepetition(dbArr, n, r);

        // all solutions for webservers
        CombinationRepetition(webArr, k, l);

        // for the best solution
        Algoritm();

    }

    public void AddComponents() {
        int componentCounter = 2;

        algorithmComponents.add(dbnull);
        algorithmComponents.add(webnull);

        for (ComponentModel test : test123) {
            System.out.println(test);

            if (test.getType().equals(ComponentType.Database)) {
                String[] add = {test.getName(), String.valueOf(test.getAvailability()), String.valueOf(test.getPrice()), String.valueOf(test.getType()), String.valueOf(componentCounter)};
                algorithmComponents.add(add);

            } else if (test.getType().equals(ComponentType.Webserver)) {
                String[] add = {test.getName(), String.valueOf(test.getAvailability()), String.valueOf(test.getPrice()), String.valueOf(test.getType()), String.valueOf(componentCounter)};
                algorithmComponents.add(add);

            } else if (test.getType().equals(ComponentType.Firewall)){
                String[] add = {test.getName(), String.valueOf(test.getAvailability()), String.valueOf(test.getPrice()), String.valueOf(test.getType()), String.valueOf(componentCounter)};
                algorithmComponents.add(add);
            }

            componentCounter++;
        }

        for (String[] strInt : algorithmComponents) {
            System.out.println(strInt[4] + strInt[0]);
        }

    }

    public void AddServers() {
        // check if component is a webserver or a dbserver and then adds the componentnumber to the array
        for (String[] strInt : algorithmComponents) {

            if (strInt[3].equals("Database")) {
                dbArr[x] = Integer.parseInt(strInt[4]);
                x++;

            } else if (strInt[3].equals("Webserver")) {
                webArr[y] = Integer.parseInt(strInt[4]);
                y++;
            }
        }
    }

    public void CombinationRepetition(int[] arr, int n, int r) {
        // Allocate memory
        int[] chosen = new int[r + 1];

        // Call the recursice function
        CombinationRepetitionUtil(chosen, arr, 0, r, 0, n - 1);
    }

    public void CombinationRepetitionUtil(int[] chosen, int[] arr, int index, int r, int start, int end) {
        // Since index has become r, current combination is
        // ready to be printed, print
        if (index == r) {
            String tijdelijke = "";

            for (int i = 0; i < r; i++) {
                String test = Integer.toString(arr[chosen[i]]);
                tijdelijke = tijdelijke + test;
            }
            for (String[] strInt : algorithmComponents) {
                if (tijdelijke.contains(strInt[4]) && strInt[3].equals("Database")) {
                    dbSolutions.add(tijdelijke);
                } else if (tijdelijke.contains(strInt[4]) && strInt[3].equals("Webserver")) {
                    webSolutions.add(tijdelijke);
                }
            }

            return;
        }

        // One by one choose all elements (without considering
        // the fact whether element is already chosen or not)
        // and recur
        for (int i = start; i <= end; i++) {
            chosen[index] = i;
            CombinationRepetitionUtil(chosen, arr, index + 1, r, i, end);
        }

    }

    public void HighestWebServer() {
        double tijdelijk = 1;
        for (String[] strInt : algorithmComponents) {
            if (strInt[3].equals("Webserver")) {
                if (tijdelijk == 1) {
                    tijdelijk = Double.parseDouble(strInt[1]);
                } else if (tijdelijk < Double.parseDouble(strInt[1])) {
                    tijdelijk = Double.parseDouble(strInt[1]);
                }
            }
        }
        int z ;

        for (z = 0; z < amount; z++) {
            highestWebServer = highestWebServer * (1 - (tijdelijk / 100));
        }

        highestWebServer = ( 1 - highestWebServer);

    }

    public void Algoritm() {

        // foreach dbsolotion try (almost) all websolutions
        for (String dbsolution : dbSolutions) {

            int dbServerNumber;
            int componentNumber;

            for (x = 0; x < amount; x++) {
                dbServerNumber = Character.getNumericValue(dbsolution.charAt(x));

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

                for (x = 0; x < amount; x++) {
                    dbServerNumber = Character.getNumericValue(dbsolution.charAt(x));
                    int webServerNumber = Character.getNumericValue(websolution.charAt(x));

                    for (String[] strInt : algorithmComponents) {
                        componentNumber = Integer.parseInt(strInt[4]);

                        if (dbServerNumber == componentNumber) {
                            dbPrice += Double.parseDouble(strInt[2]);
                            dbPercentage = dbPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                        }

                        if (webServerNumber == componentNumber) {
                            webPrice += Double.parseDouble(strInt[2]);
                            webPercentage = webPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                        }
                    }

                }
                webPercentage = (1 - webPercentage);
                dbPercentage = (1 - dbPercentage);

                double totalPercentage = (webPercentage * dbPercentage * 0.99998) * 100;
                double totalPrice = webPrice + dbPrice + 4000;

                if (totalPercentage >= availabilty) {
                    // TODO: arraylist with the best solution
                    if (bestSolutionPrice == 0) {
                        bestSolutionPrice = totalPrice;
                    } else if (totalPrice < bestSolutionPrice) {
                        bestSolutionPrice = totalPrice;
                        bestSolutionAvailabilty = totalPercentage;
                        // System.out.println(bestSolutionPrice);
                        // System.out.println(totalPercentage);
                        // System.out.println(websolution + dbsolution);
                    }
                }

                webPercentage = 1;
                webPrice = 0;
                dbPrice = 0;
                dbPercentage = 1;
            }
        }
    }

    // TODO : return a arraylist with the solution naam beschikbaarheid prijs type (nummer) amount
    // TODO : + totale beschikbaarheid totale prijs
    public double getBestSolutionAvailabilty() {
        return bestSolutionAvailabilty;
    }

    public double getBestSolutionPrice() {
        return bestSolutionPrice;
    }
}

