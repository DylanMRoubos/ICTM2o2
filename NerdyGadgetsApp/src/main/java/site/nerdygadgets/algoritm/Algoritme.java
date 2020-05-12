package site.nerdygadgets.algoritm;

import java.util.ArrayList;
import java.util.List;

public class Algoritme {

    // arraylist for db solutions and web solutions
    private List<String> dbSolutions = new ArrayList<>();
    private List<String> webSolutions = new ArrayList<>();

    // temporary availabilty and amount of dbservers and webservers
    private double availabilty = 99.99;
    private int amount = 5;

    // arraylist for components
    private List<String[]> components = new ArrayList<>();

    // Components
    private String[] db1 = {"HAL90001DB", "90", "5100", "0", "1"};
    private String[] db2 = {"HAL9002DB", "95", "7700", "0", "2"};
    private String[] db3 = {"HAL9003DB", "98", "12200", "0", "3"};
    private String[] web1 = {"HAL90001W", "80", "2200", "1", "4"};
    private String[] web2 = {"HAL9002DW", "90", "3200", "1", "5"};
    private String[] web3 = {"HAL9003DW", "95", "5100", "1", "6"};
    private String[] pf1 = {"firewall pfSense", "99.998", "4000", "2", "7"};
    private String[] dbnull = {"Null", "0", "0", "0", "0"};
    private String[] webnull = {"Null", "0", "0", "1", "8"};

    // array for dbservers, dbserver null + amount dbservers = 4
    // ! method to count the amount of dbservers or to count the chosen dbservers !
    private int[] dbArr = new int[4];
    private int x = 0;

    // ! method to count the amount of webservers or to count the chosen webservers !
    // array for webervers, webserver null + amount webservers = 4
    private int[] webArr = new int[4];
    private int y = 0;

    private int n = dbArr.length;
    private int r = amount;

    private int k = webArr.length;
    private int l = amount;

    // default values
    private int dbServerNumber;
    private int webServerNumber;
    private int componentNumber;

    private double highestWebServer = 1;

    private int dbPrice = 0;
    private double dbPercentage = 1;

    private int webPrice = 0;
    private double webPercentage = 1;

    private int bestSolution = 0;

    private double dbTestPercentage = 1;

    // default constructor
    public Algoritme() {

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

        // site.nerdygadgets.algoritm for the best solution
        Algoritm();
    }

    public void AddComponents() {
        // lowest availabilty dbservers first
        // highest availabilty webservers first
        components.add(pf1);
        components.add(dbnull);
        components.add(db1);
        components.add(db2);
        components.add(db3);
        components.add(web3);
        components.add(web2);
        components.add(web1);
        components.add(webnull);
    }

    public void AddServers() {
        // check if component is a webserver or a dbserver and then adds the componentnumber to the array
        for (String[] strInt : components) {

            if (strInt[3].equals("0")) {
                dbArr[x] = Integer.parseInt(strInt[4]);
                x++;

            } else if (strInt[3].equals("1")) {
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

                if (arr[chosen[i]] <= 3) {
                    String test = Integer.toString(arr[chosen[i]]);
                    tijdelijke = tijdelijke + test;

                } else {
                    String test = Integer.toString(arr[chosen[i]]);
                    tijdelijke = tijdelijke + test;
                }
            }

            if (tijdelijke.contains("1") || tijdelijke.contains("2") || tijdelijke.contains("3") || tijdelijke.contains("0")) {
                dbSolutions.add(tijdelijke);

            } else {
                webSolutions.add(tijdelijke);
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
        for (String[] strInt : components) {
            if (strInt[3].equals("1")) {
                if (tijdelijk == 1) {
                    tijdelijk = Double.parseDouble(strInt[1]);
                } else if (tijdelijk < Integer.parseInt(strInt[1])) {
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

            for (x = 0; x < amount; x++) {
                dbServerNumber = Character.getNumericValue(dbsolution.charAt(x));

                for (String[] strInt : components) {
                    componentNumber = Integer.parseInt(strInt[4]);

                    if (dbServerNumber == componentNumber) {
                        dbTestPercentage = dbTestPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                    }
                }
            }

            dbTestPercentage = (1 - dbTestPercentage);

            double testTotalPercentage = (dbTestPercentage * highestWebServer * 0.99998) * 100;

            // if the percentage of the highest websolution is lower with the current dbsolution break and go to the next dbsolution
            if (testTotalPercentage < availabilty) {
                dbTestPercentage = 1;
                break;
            }


            // foreach websolution calculate the percentage and price
            for (String websolution : webSolutions) {

                for (x = 0; x < amount; x++) {
                    dbServerNumber = Character.getNumericValue(dbsolution.charAt(x));
                    webServerNumber = Character.getNumericValue(websolution.charAt(x));

                    for (String[] strInt : components) {
                        componentNumber = Integer.parseInt(strInt[4]);

                        if (dbServerNumber == componentNumber) {
                            dbPrice += Integer.parseInt(strInt[2]);
                            dbPercentage = dbPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                        }

                        if (webServerNumber == componentNumber) {
                            webPrice += Integer.parseInt(strInt[2]);
                            webPercentage = webPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                        }
                    }

                }
                webPercentage = (1 - webPercentage);
                dbPercentage = (1 - dbPercentage);

                double totalPercentage = (webPercentage * dbPercentage * 0.99998) * 100;
                int totalPrice = webPrice + dbPrice + 4000;

                if (totalPercentage >= availabilty) {
                    if (bestSolution == 0) {
                        System.out.println(totalPrice);
                        System.out.println(totalPercentage);
                        System.out.println(websolution + dbsolution);
                        bestSolution = totalPrice;
                        System.out.println();
                    } else if (totalPrice < bestSolution) {
                        bestSolution = totalPrice;
                        System.out.println(totalPrice);
                        System.out.println(totalPercentage);
                        System.out.println(websolution + dbsolution);
                        System.out.println();
                    }
                }

                webPercentage = 1;
                webPrice = 0;
                dbPrice = 0;
                dbPercentage = 1;
            }
        }
    }
}

