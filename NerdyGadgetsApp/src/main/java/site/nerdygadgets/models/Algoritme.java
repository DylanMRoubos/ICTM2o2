package site.nerdygadgets.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Algoritme {

    static List<String> dboplossingen = new ArrayList<>();
    static List<String> weboplossingen = new ArrayList<>();

    public static void main(String[] args) {

        // tijdelijke beschikbaarheid
        double bescikbaarheid = 99.99;

        // arraylist voor de componenten
        List<String[]> componenten = new ArrayList<>();

        // componenten
        String[] db1 = {"HAL90001DB", "90", "5100", "0", "1"};
        String[] db2 = {"HAL9002DB", "95", "7700", "0", "2"};
        String[] db3 = {"HAL9003DB", "98", "12200", "0", "3"};
        String[] web1 = {"HAL90001W", "80", "2200", "1", "4"};
        String[] web2 = {"HAL9002DW", "90", "3200", "1", "5"};
        String[] web3 = {"HAL9003DW", "95", "5100", "1", "6"};
        String[] pf1 = {"firewall pfSense", "99.998", "4000", "2", "7"};
        String[] dbnull = {"Null", "0", "0", "0", "0"};
        String[] webnull = {"Null", "0", "0", "1", "8"};

        // toevoegen van de componenten aan de array
        componenten.add(pf1);
        componenten.add(dbnull);
        componenten.add(db1);
        componenten.add(db2);
        componenten.add(db3);
        componenten.add(webnull);
        componenten.add(web1);
        componenten.add(web2);
        componenten.add(web3);

        // max aantal servers
        int amount = 4;

        // array voor de database servers null + aantal db servers
        int[] dbArr = new int[4];
        int x = 0;

        // array voor de webservers null + aantal webservers
        int[] webArr = new int[4];
        int y = 0;

        // voegt de verschillende web en databaseservers toe
        for (String[] strInt : componenten){
            if (strInt[3].equals("0")) {
                dbArr[x] = Integer.parseInt(strInt[4]);
                x++;
            } else if (strInt[3].equals("1")){
                webArr[y] = Integer.parseInt(strInt[4]);
                y++;
            }

        }

        int n = dbArr.length;
        int r = amount;

        CombinationRepetition(dbArr, n, r);

        int k = webArr.length;
        int l = amount;

        System.out.println();

        CombinationRepetition(webArr, k, l);

        int test2;
        int test3;
        int dbPrijs = 0;
        double dbPercentage = 1;

        int test4;
        int test5;
        int webPrijs = 0;
        double webPercentage = 1;

        int totaalPrijs = 0;
        double totaalPercentage;
        int goedkoop = 0;

        for (String test : dboplossingen) {
            for (String webTest : weboplossingen) {

                for (x = 0; x < amount; x++) {
                    test2 = Character.getNumericValue(test.charAt(x));
                    test4 = Character.getNumericValue(webTest.charAt(x));

                    for (String[] strInt : componenten) {
                        test3 = Integer.parseInt(strInt[4]);
                        test5 = Integer.parseInt(strInt[4]);

                        if (test2 == test3) {
                            dbPrijs += Integer.parseInt(strInt[2]);
                            //System.out.println(1 - (Double.parseDouble(strInt[1]) / 100));
                            dbPercentage = dbPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                        }

                        if (test4 == test5) {
                            webPrijs += Integer.parseInt(strInt[2]);
                            //System.out.println(1 - (Double.parseDouble(strInt[1]) / 100));
                            webPercentage = webPercentage * (1 - (Double.parseDouble(strInt[1]) / 100));
                        }
                    }

                }
                webPercentage = (1 - webPercentage);
                dbPercentage = (1 - dbPercentage);


                totaalPercentage = (webPercentage * dbPercentage * 0.99998) * 100;
                totaalPrijs = webPrijs + dbPrijs + 4000;

                if (totaalPercentage >= bescikbaarheid){
                    if (goedkoop == 0) {
                        System.out.println(totaalPrijs);
                        System.out.println(totaalPercentage);
                        System.out.println(webTest + test);
                        goedkoop = totaalPrijs;
                        System.out.println();

                    }
                    else if (totaalPrijs < goedkoop ){
                        goedkoop = totaalPrijs;
                        System.out.println(totaalPrijs);
                        System.out.println(totaalPercentage);
                        System.out.println(webTest + test);
                        System.out.println();
                    }
                }

                webPercentage = 1;
                webPrijs = 0;
                dbPrijs = 0;
                dbPercentage = 1;

            }
        }
    }


    static void CombinationRepetition(int[] arr, int n, int r) {
        // Allocate memory
        int[] chosen = new int[r + 1];



        // Call the recursice function
        CombinationRepetitionUtil(chosen, arr, 0, r, 0, n - 1);
    }

    static void CombinationRepetitionUtil(int[] chosen, int[] arr, int index, int r, int start, int end) {
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
                dboplossingen.add(tijdelijke);
            } else {
                weboplossingen.add(tijdelijke);
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
}
