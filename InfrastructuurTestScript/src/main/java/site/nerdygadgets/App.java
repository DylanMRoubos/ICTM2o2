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
        //web1
        SSHManager ssh = new SSHManager("student", "KHxd4gu7", "172.16.0.190");

        ssh.startSession();
        String joe = ssh.runCommandSudo("ls");
        String joe2 = ssh.runCommandSudo("ls");
        String jo3 = ssh.runCommandSudo("ls");

        testScriptFrame.getConsoleJP().appendConsoleText(joe);
        testScriptFrame.getConsoleJP().appendConsoleText(jo3);
        testScriptFrame.getConsoleJP().appendConsoleText(joe2);

    }
}
