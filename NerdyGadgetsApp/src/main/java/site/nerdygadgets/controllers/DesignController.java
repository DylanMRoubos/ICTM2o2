package site.nerdygadgets.controllers;

import site.nerdygadgets.models.DesignModel;
import site.nerdygadgets.views.DesignPanel;

public class DesignController {
    private DesignPanel panel;
    private DesignModel model;

    public DesignController(DesignPanel panel, DesignModel model){
        this.panel = panel;
        this.model = model;
    }

}
