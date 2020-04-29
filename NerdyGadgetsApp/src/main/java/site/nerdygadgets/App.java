package site.nerdygadgets;

import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import site.nerdygadgets.controllers.RoutePanelController;
import site.nerdygadgets.sandbox.SSHManager;
import site.nerdygadgets.views.MainFrameView;

import javax.swing.*;

public class App {
    static MainFrameView mainFrameView;

    public static void main(String[] args) {

        SSHManager ssh = new SSHManager("student", "wd9AdEuN", "172.16.0.158");

        String cpu = ssh.runCommand("top -bn2 | grep \"Cpu(s)\" | tail -n1 | sed \"s/.*, *\\([0-9.]*\\)%* id.*/\\1/\" | awk '{print 100 - $1}'");
        String memory = ssh.runCommand("free --mega | grep 'Mem:' | awk '{print $3 \"M / \" $2 \"M\"}'");
        String diskSpace = ssh.runCommand("df -h --total | grep 'total' | awk '{print $3 \" / \" $2}'");
        String upTime = ssh.runCommand("uptime -p");

        System.out.print("cpu: " + cpu);
        System.out.print("memory: " + memory);
        System.out.print("Disk space: " + diskSpace);
        System.out.print("Uptime: " + upTime);


        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
            } catch (Exception e) {
                System.out.println("Substance failed to initialize");
            }
            // invoke frame here
            mainFrameView = new MainFrameView();
            RoutePanelController rpc = new RoutePanelController(mainFrameView);
        });
    }
}
