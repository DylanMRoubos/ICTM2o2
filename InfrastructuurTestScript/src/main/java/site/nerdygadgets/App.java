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

    }
}
