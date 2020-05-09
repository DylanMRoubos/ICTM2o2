package site.nerdygadgets.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Algoritme {

    // tijdelijke beschikbaarheid
    private double bescikbaarheid = 99.99;

    public static void main(String[] args) throws IOException {
        List<String[]> componenten = new ArrayList<String[]>();

        String[] array = {"HAL90001DB", "90", "5100", "0", "1"};
        String[] array1 = {"HAL9002DB", "95", "7700", "0", "2"};
        String[] array2 = {"HAL9003DB", "98", "12200", "0", "3"};
        String[] array3 = {"HAL90001W", "80", "2200", "1", "1"};
        String[] array4 = {"HAL9002DW", "90", "3200", "1", "2"};
        String[] array5 = {"HAL9003DW", "95", "5100", "1", "3"};
        String[] array6 = {"firewall pfSense", "99.998", "4000", "2"};
        String[] array7 = {"Null", "", "", "0", "0"};
        String[] array8 = {"Null", "", "", "1", "0"};

        // toevoegen van de componenten aan de array
        componenten.add(array6);
        componenten.add(array7);
        componenten.add(array);
        componenten.add(array1);
        componenten.add(array2);
        componenten.add(array8);
        componenten.add(array3);
        componenten.add(array4);
        componenten.add(array5);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String amountStr = reader.readLine();
        int amount = Integer.parseInt(amountStr);

        if (amount <= 0) {
            System.out.println("Prank");
            amount = 5;
        }

        int[] dbArr = new int[4];
        int x = 0;

        int[] webArr = new int[4];
        int y = 0;

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

        CombinationRepetition(webArr, k, l);


        /*for (String[] strArr : componenten) {
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

        }*/
    }

    static void CombinationRepetition(int[] arr, int n, int r) {
        // Allocate memory
        int[] chosen = new int[r + 1];

        // Call the recursice function
        CombinationRepetitionUtil(chosen, arr, 0, r, 0, n - 1);
    }

    static void CombinationRepetitionUtil(int[] chosen, int[] arr,
                                          int index, int r, int start, int end) {
        // Since index has become r, current combination is
        // ready to be printed, print
        int[] array = new int[r];

        if (index == r) {
            for (int i = 0; i < r; i++) {
                System.out.printf("%d", arr[chosen[i]]);
                //array[i] = arr[chosen[i]];
            }
            //arg.add(String.valueOf(array));
            System.out.printf("\n");
            return;
        }

        // One by one choose all elements (without considering
        // the fact whether element is already chosen or not)
        // and recur
        for (int i = start; i <= end; i++) {
            chosen[index] = i;
            CombinationRepetitionUtil(chosen, arr, index + 1, r, i, end);
        }
        return;
    }


}
