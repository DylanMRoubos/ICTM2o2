package site.nerdygadgets;

import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import site.nerdygadgets.controllers.RoutePanelController;
//import site.nerdygadgets.functions.Serialization;
import site.nerdygadgets.functions.CalculateComponent;
import site.nerdygadgets.functions.ComponentType;
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

        /*
        ArrayList<Object> m = new ArrayList<Object>();
        m.add(new ComponentModel("MijnComponent1", 1000));
        m.add(new ComponentModel("MijnComponent2", 4000));

        try {
            Serialization.serialize(m, "mepm.json");
            System.out.println(m);
            System.out.println("COMPONENTS SERIALIZED");
            m = Serialization.deserialize("mepm.json");
            for (Object o : m) {
                ComponentModel cm = (ComponentModel) o;
                System.out.println(cm);
            }
            System.out.println("COMPONENTS DESERIALIZED");
            System.out.println(m);
        }
        catch (IOException e) {

        }*/

        /*
        ArrayList<ComponentModel> models = new ArrayList<ComponentModel>();
        models.add(new ComponentModel("Webservernaampje", 0.95, 1950.8, ComponentType.Webserver));
        models.add(new ComponentModel("Webservernaampje2", 0.90, 999.99, ComponentType.Webserver));

        models.add(new ComponentModel("firewallnaampje", 0.9998, 4999.99, ComponentType.Firewall));

        models.add(new ComponentModel("DBnaampje", 0.98, 2000.0, ComponentType.Database));
        models.add(new ComponentModel("DBnaampje2", 0.85, 500.0, ComponentType.Database));

        System.out.println("Availability: " + CalculateComponent.calculateAvailability(models) * 100);
        System.out.println("Price: " + CalculateComponent.calculatePrice(models));

        System.out.println("joe simpels joe!");*/


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
