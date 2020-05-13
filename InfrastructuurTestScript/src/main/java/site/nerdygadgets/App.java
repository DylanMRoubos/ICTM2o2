package site.nerdygadgets;

/**
 * Main class
 * Creates JFrame
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 12-05-2020
 */
public class App {
    static TestScriptFrame testScriptFrame;
    static boolean scriptRunning = false;

    public static void main(String[] args) {

        //Set SubstanceBusinessBlackSteel look and feel tot the JFrame
//        JFrame.setDefaultLookAndFeelDecorated(true);
//        SwingUtilities.invokeLater(() -> {
//            try {
////                UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
//            } catch (Exception e) {
//                System.out.println("Substance failed to initialize");
//            }
//        });

        testScriptFrame = new TestScriptFrame();

        // test dbconnection
        DbConnection conn1 = new DbConnection(1,"172.16.0.190", "root", "ArshhU8K", "wideworldimporters");
        System.out.println(conn1.getLastRowFromTestTable());
        System.out.println(conn1.updateTestTable());
        System.out.println(conn1.getLastRowFromTestTable());

        DbConnection conn2 = new DbConnection(2,"172.16.0.191", "root", "ArshhU8K", "wideworldimporters");
        System.out.println(conn2.getLastRowFromTestTable());
        System.out.println(conn2.updateTestTable());
        System.out.println(conn2.getLastRowFromTestTable());
    }
}
