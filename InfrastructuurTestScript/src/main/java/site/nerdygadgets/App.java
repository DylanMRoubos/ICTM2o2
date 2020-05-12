package site.nerdygadgets;

import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;

import javax.swing.*;

/**
 * Hello world!
 */
public class App {
    static TestScriptFrame testScriptFrame;

    public static void main(String[] args) {

        System.out.println("Hello World!");


        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
            } catch (Exception e) {
                System.out.println("Substance failed to initialize");
            }
            testScriptFrame = new TestScriptFrame();
        });

        //web1
        SSHManager ssh = new SSHManager("student", "KHxd4gu7", "172.16.0.190");

        ssh.startSession();
        String joe = ssh.runCommandSudo("ls");
        String joe2 = ssh.runCommandSudo("ls");
        String jo3 = ssh.runCommandSudo("ls");

        testScriptFrame.getConsoleJP().setConsoleText(joe);
        testScriptFrame.getConsoleJP().setConsoleText(jo3);
        testScriptFrame.getConsoleJP().setConsoleText(joe2);

    }
}
