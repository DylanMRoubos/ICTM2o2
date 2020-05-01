package site.nerdygadgets.controllers;

import site.nerdygadgets.models.CurrentInfrastructureComponentModel;
import site.nerdygadgets.views.CurrentInfrastructureComponentPanel;
import site.nerdygadgets.views.CurrentInfrastructurePanel;
/**
 * CurrentInfrastructureController class
 * Gets data from model and puts in in the JPanel
 *
 * @author Mike Thomas & Dylan Roubos
 * @version 1.0
 * @since 01-05-2020
 */
public class CurrentInfrastructureController {
    private CurrentInfrastructureComponentModel model;
    private CurrentInfrastructurePanel panel;
    private CurrentInfrastructureComponentPanel componentPanel;

    public CurrentInfrastructureController(CurrentInfrastructureComponentModel model, CurrentInfrastructurePanel panel) {
        this.model = model;
        this.panel = panel;

        componentPanel = new CurrentInfrastructureComponentPanel(model.getName());

        panel.add(componentPanel);

        componentPanel.getNameValue().setText(model.getName());

        update();
    }
    //Update the data from the model
    public void update() {
        model.getData();

        componentPanel.getOnlineValue().setText(String.valueOf(model.getOnline()));
        componentPanel.getUptimeValue().setText(model.getUptime());
        componentPanel.getCpuValue().setText(model.getCpu());
        componentPanel.getMemoryValue().setText(model.getMemory());
        componentPanel.getDiskValue().setText(model.getDisk());

    }
}
