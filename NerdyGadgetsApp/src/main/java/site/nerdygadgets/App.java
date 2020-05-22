package site.nerdygadgets;

import org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel;
import site.nerdygadgets.controllers.ComponentsController;
import site.nerdygadgets.controllers.CurrentInfrastructureController;
import site.nerdygadgets.controllers.DesignController;
import site.nerdygadgets.controllers.RoutePanelController;
import site.nerdygadgets.functions.Serialization;
import site.nerdygadgets.models.ComponentsModel;
import site.nerdygadgets.models.CurrentInfrastructureComponentModel;
import site.nerdygadgets.models.DesignModel;
import site.nerdygadgets.scraper.Database;
import site.nerdygadgets.scraper.ServerManager;
import site.nerdygadgets.views.MainFrameView;

import javax.swing.*;
import java.util.ArrayList;

public class App {
    static MainFrameView mainFrameView;

    public static void main(String[] args) throws InterruptedException {
        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new SubstanceBusinessBlackSteelLookAndFeel());
            } catch (Exception e) {
                System.out.println("Substance failed to initialize");
            }
            // invoke frame here
            mainFrameView = new MainFrameView();
            ComponentsController cc = new ComponentsController(mainFrameView.getComponentManagementPanel(), new ComponentsModel(), mainFrameView);
            DesignController dc = new DesignController(mainFrameView.getDesignPanel(), new DesignModel(), mainFrameView);
            RoutePanelController rpc = new RoutePanelController(mainFrameView);

            ArrayList<String> defaultConnection = new ArrayList<String>();
            defaultConnection.add("mongodb+srv://admin:admin@cluster0-gzerr.mongodb.net/test?retryWrites=true&w=majority");
            defaultConnection.add("nerdyGadgets");
            defaultConnection.add("serverStatus");
            Serialization.serializeConnection(defaultConnection);

            //Create database instance to get data for the current infrastrucutre models
            Database db = new Database(Serialization.deserializeConnection());

            new ServerManager(db); // start data scraper.

            //Create current infrastructure models to save data from a currentComponent in a object

            CurrentInfrastructureComponentModel web1Model  = new CurrentInfrastructureComponentModel(1, db, "Web1");
            CurrentInfrastructureComponentModel web2Model  = new CurrentInfrastructureComponentModel(2, db, "Web2");
            CurrentInfrastructureComponentModel db1Model  = new CurrentInfrastructureComponentModel(3, db, "DB1");
            CurrentInfrastructureComponentModel db2Model  = new CurrentInfrastructureComponentModel(4, db, "DB2");
            CurrentInfrastructureComponentModel pfSenseModel  = new CurrentInfrastructureComponentModel(5, db, "PFsense");

            //Create current infrastructure controllers to add the panels to the JFrame filled with data from the model.
            CurrentInfrastructureController web1Controller = new CurrentInfrastructureController(web1Model, mainFrameView.getCurrentInfrastructurePanel());
            CurrentInfrastructureController web2Controller = new CurrentInfrastructureController(web2Model, mainFrameView.getCurrentInfrastructurePanel());
            CurrentInfrastructureController db1Controller = new CurrentInfrastructureController(db1Model, mainFrameView.getCurrentInfrastructurePanel());
            CurrentInfrastructureController db2Controller = new CurrentInfrastructureController(db2Model, mainFrameView.getCurrentInfrastructurePanel());
            CurrentInfrastructureController pfSenseController = new CurrentInfrastructureController(pfSenseModel, mainFrameView.getCurrentInfrastructurePanel());

        });
    }
}
