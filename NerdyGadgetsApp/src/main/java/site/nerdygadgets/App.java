package site.nerdygadgets;

import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import site.nerdygadgets.controllers.RoutePanelController;
import site.nerdygadgets.views.MainFrameView;

import javax.swing.*;

public class App 
{
    static MainFrameView mainFrameView;

    public static void main( String[] args )
    {
        System.out.println( "joe simpels joe!" );

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
