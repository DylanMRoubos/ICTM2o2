package site.nerdygadgets;

import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import site.nerdygadgets.controllers.RoutePanelController;
//import site.nerdygadgets.functions.Serialization;
import site.nerdygadgets.functions.CalculateComponent;
import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.functions.Serialization;
import site.nerdygadgets.models.ComponentModel;
import site.nerdygadgets.sandbox.SSHManager;
import site.nerdygadgets.views.MainFrameView;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class App {
    static MainFrameView mainFrameView;

    public static void main(String[] args) {

//        SSHManager ssh = new SSHManager();
        ArrayList<ComponentModel> list = new ArrayList<ComponentModel>();
        list.add(new ComponentModel("MijnVuurmuur", 99.9, 5000.0, ComponentType.Firewall));
        list.add(new ComponentModel("MijnWebservor", 80.5, 1200.0, ComponentType.Webserver));
        list.add(new ComponentModel("MijnDaatbees", 94.0, 1500.0, ComponentType.Database));
        try {
            Serialization.serializeComponents(list);
        } catch (IOException e) {System.out.println("Unable to serialize components");}


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
