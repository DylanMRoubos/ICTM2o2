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

        testScriptFrame = new TestScriptFrame();

    }
}
