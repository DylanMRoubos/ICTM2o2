package site.nerdygadgets.models;

import site.nerdygadgets.functions.ComponentType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Algorithm {

    // arraylist for db solutions and web solutions
    private List<String> dbSolutions = new ArrayList<>();
    private List<String> webSolutions = new ArrayList<>();

    private ArrayList<InfrastructureComponentModel> bestList = new ArrayList<>();

    private String bestSolution;

    // temporary availabilty and amount of dbservers and webservers
    private double availabilty;
    private int amount = 5;

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

    // TODO: constructor for given availabilty and given components / all components
    public Algorithm(double availabilty, ArrayList<ComponentModel> chosenComponents, ArrayList<ComponentModel> components ) {
        this.availabilty = availabilty;
        this.components = components;

        // Add components to arraylist components
        AddComponents();

        // Add servers to array
        AddServers();

        // current highest webserver
        HighestWebServer();

        // all solutions for dbservers
        int n = dbArr.length;
        int r = amount;
        int db = 0;
        CombinationRepetition(dbArr, n, r, db);

        // all solutions for webservers
        int l = amount;
        int k = webArr.length;
        int web = 1;
        CombinationRepetition(webArr, k, l, web);

        // for the best solution
        Algoritm();

        // return arraylist with best solution
        createList();

    }

    // constructor for given availabilty and all components
    public Algorithm(double availabilty, ArrayList<ComponentModel> components) {
        this.availabilty = availabilty;
        this.components = components;

        algorithmComponents = new ArrayList<>();

        // Add components to arraylist components
        AddComponents();

        // Add servers to array
        AddServers();

        // current highest webserver
        HighestWebServer();

        // all solutions for dbservers
        int n = dbArr.length;
        int r = amount;
        int db = 0;
        CombinationRepetition(dbArr, n, r, db);
        System.out.println(Arrays.toString(dbArr));

        // all solutions for webservers
        int l = amount;
        int k = webArr.length;
        int web = 1;
        CombinationRepetition(webArr, k, l, web);
        System.out.println(Arrays.toString(webArr));



        // for the best solution
        Algoritm();

        // return arraylist with best solution
        createList();
    }

    public void AddComponents() {
        int componentCounter = 2;

        // 0
        algorithmComponents.add(dbnull);

        // 1
        algorithmComponents.add(webnull);

        // TODO : if chosenComponents exist

        for (ComponentModel test : components) {

            if (test.getType().equals(ComponentType.Database)) {
                String[] add = {test.getName(), String.valueOf(test.getAvailability()), String.valueOf(test.getPrice()), String.valueOf(test.getType()), String.valueOf(componentCounter)};
                algorithmComponents.add(add);
                dbCounter++;

            } else if (test.getType().equals(ComponentType.Webserver)) {
                String[] add = {test.getName(), String.valueOf(test.getAvailability()), String.valueOf(test.getPrice()), String.valueOf(test.getType()), String.valueOf(componentCounter)};
                algorithmComponents.add(add);
                webCounter++;

            } else if (test.getType().equals(ComponentType.Firewall)){
                String[] add = {test.getName(), String.valueOf(test.getAvailability()), String.valueOf(test.getPrice()), String.valueOf(test.getType()), String.valueOf(componentCounter)};
                algorithmComponents.add(add);
            }

            componentCounter++;
        }
    }

    public void AddServers() {
        webArr = new int[webCounter];
        dbArr = new int[dbCounter];

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

    public void CombinationRepetition(int[] arr, int n, int r, int q) {
        // Allocate memory
        int[] chosen = new int[r + 1];

        // Call the recursice function
        CombinationRepetitionUtil(chosen, arr, 0, r, 0, n - 1, q);
    }

    public void CombinationRepetitionUtil(int[] chosen, int[] arr, int index, int r, int start, int end, int q) {
        // Since index has become r, current combination is
        // ready to be printed, print
        if (index == r) {
            String tijdelijke = "";

            for (int i = 0; i <= r-1; i++) {
                String test = Integer.toString(arr[chosen[i]]);
                if (tijdelijke.equals("")) {
                    tijdelijke = tijdelijke + test;
                }else if(i == r-1) {
                    tijdelijke = tijdelijke + "-" + test + "-";
                }else{
                    tijdelijke = tijdelijke + "-" + test;
                }
            }

            if (q == 1) {
                webSolutions.add(tijdelijke);
            } else if (q == 0) {
                dbSolutions.add(tijdelijke);
            }
            return;
        }

        // One by one choose all elements (without considering
        // the fact whether element is already chosen or not)
        // and recur
        for (int i = start; i <= end; i++) {
            chosen[index] = i;
            CombinationRepetitionUtil(chosen, arr, index + 1, r, i, end, q);
        }

    }

    public void HighestWebServer() {
        double tijdelijk = 1;
        for (String[] strInt : algorithmComponents) {
            if (strInt[3].equals("Webserver") && !strInt[4].equals("1")) {
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
            int webServerNumber;
            int x;

            for (x = 0; x < dbsolution.length(); x++) {
                if (dbsolution.charAt(x) == '-'){
                    continue;
                }else if (!(dbsolution.charAt(x+1) == '-')){
                    dbServerNumber = Character.getNumericValue(dbsolution.charAt(x)) + Character.getNumericValue(dbsolution.charAt(x+1));
                    x++;

                    for (String[] strInt : algorithmComponents) {
                        componentNumber = Integer.parseInt(strInt[4]);

                        if (dbServerNumber == componentNumber) {
                            dbTestPercentage = dbTestPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                        }
                    }

                }else{
                    dbServerNumber = Character.getNumericValue(dbsolution.charAt(x));

                    for (String[] strInt : algorithmComponents) {
                        componentNumber = Integer.parseInt(strInt[4]);

                        if (dbServerNumber == componentNumber) {
                            dbTestPercentage = dbTestPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                        }
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
                    if (dbsolution.charAt(x) == '-'){
                        continue;
                    }else if (!(dbsolution.charAt(x+1) == '-')){
                        dbServerNumber = Character.getNumericValue(dbsolution.charAt(x)) + Character.getNumericValue(dbsolution.charAt(x+1));
                        x++;

                        for (String[] strInt : algorithmComponents) {
                            componentNumber = Integer.parseInt(strInt[4]);

                            if (dbServerNumber == componentNumber) {
                                dbPrice += Double.parseDouble(strInt[2]);
                                dbPercentage = dbPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                            }
                        }

                    }else{
                        dbServerNumber = Character.getNumericValue(dbsolution.charAt(x));

                        for (String[] strInt : algorithmComponents) {
                            componentNumber = Integer.parseInt(strInt[4]);

                            if (dbServerNumber == componentNumber) {
                                dbPrice += Double.parseDouble(strInt[2]);
                                dbPercentage = dbPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                            }
                        }

                    }
                }

                for (x = 0; x < websolution.length(); x++) {
                    if (websolution.charAt(x) == '-'){
                        continue;
                    }else if (!(websolution.charAt(x+1) == '-')){
                        webServerNumber = Character.getNumericValue(websolution.charAt(x)) + Character.getNumericValue(websolution.charAt(x + 1));
                        x++;

                        for (String[] strInt : algorithmComponents) {
                            componentNumber = Integer.parseInt(strInt[4]);

                            if (webServerNumber == componentNumber) {
                                webPrice += Double.parseDouble(strInt[2]);
                                webPercentage = webPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                            }
                        }

                    }else{
                        webServerNumber = Character.getNumericValue(websolution.charAt(x));

                        for (String[] strInt : algorithmComponents) {
                            componentNumber = Integer.parseInt(strInt[4]);

                            if (webServerNumber == componentNumber) {
                                webPrice += Double.parseDouble(strInt[2]);
                                webPercentage = webPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                            }
                        }

                    }
                }


                webPercentage = (1 - webPercentage);
                dbPercentage = (1 - dbPercentage);

                // TODO : pfsene beschikbaarheid en prijs fixxen
                double totalPercentage = (webPercentage * dbPercentage * 0.99998) * 100;
                double totalPrice = webPrice + dbPrice + 4000;

                if (totalPercentage >= availabilty) {
                    // TODO: arraylist with the best solution
                    if (bestSolutionPrice == 0) {
                        bestSolutionPrice = totalPrice;
                    } else if (totalPrice < bestSolutionPrice) {
                        bestSolutionPrice = totalPrice;
                        bestSolutionAvailabilty = totalPercentage;
                        System.out.println(websolution + dbsolution);
                        bestSolution = websolution + dbsolution + (algorithmComponents.size()-1) + "-";
                    }
                }

                webPercentage = 1;
                webPrice = 0;
                dbPrice = 0;
                dbPercentage = 1;
            }
        }
    }

    public void createList() {
        
        int bestSolutionNumber;
        int componentNumber;
        InfrastructureComponentModel bestInfrastructure;
        
        for (int p = 0; p < bestSolution.length(); p++) {
            if (bestSolution.charAt(p) == '-' || (bestSolution.charAt(p) == '0' && bestSolution.charAt(p + 1) == '-') || ( bestSolution.charAt(p) == '1' && bestSolution.charAt(p + 1) == '-' )){
                continue;
            }else if (!(bestSolution.charAt(p + 1) == '-')){
                bestSolutionNumber = Character.getNumericValue(bestSolution.charAt(p)) + Character.getNumericValue(bestSolution.charAt(p + 1));
                p++;
                for (String[] strInt : algorithmComponents) {
                    componentNumber = Integer.parseInt(strInt[4]);

                    if (bestSolutionNumber == componentNumber) {
                        bestInfrastructure = new InfrastructureComponentModel(strInt[0], Double.parseDouble(strInt[1]), Double.parseDouble(strInt[2]), ComponentType.valueOf(strInt[3]), 1);
                        bestList.add(bestInfrastructure);
                    }
                }

            }else{
                bestSolutionNumber = Character.getNumericValue(bestSolution.charAt(p));

                for (String[] strInt : algorithmComponents) {
                    componentNumber = Integer.parseInt(strInt[4]);
                    if (bestSolutionNumber == componentNumber) {
                        bestInfrastructure = new InfrastructureComponentModel(strInt[0], Double.parseDouble(strInt[1]), Double.parseDouble(strInt[2]), ComponentType.valueOf(strInt[3]), 1);
                        bestList.add(bestInfrastructure);
                    }
                }
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

    public ArrayList<InfrastructureComponentModel> getList() {
        return bestList;
    }
}

