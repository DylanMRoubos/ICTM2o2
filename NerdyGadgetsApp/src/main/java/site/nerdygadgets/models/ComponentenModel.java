package site.nerdygadgets.models;

import site.nerdygadgets.functions.Serialization;

import java.io.IOException;
import java.util.ArrayList;

public class ComponentenModel {
    private ArrayList<ComponentModel> componentModels;

    public ComponentenModel(){
        componentModels = new ArrayList<ComponentModel>();
    }

    public ArrayList<ComponentModel> getComponentModels() {
        return componentModels;
    }

    public void setComponentModels(ArrayList<ComponentModel> componentModels) {
        this.componentModels = componentModels;
    }

    public void addComponentModel(ComponentModel model) {
        try {
            componentModels.add(model);
            Serialization.serializeComponents(componentModels);
        }catch (IOException e){
            System.out.println("Error ding");
        }

    }

    public void printComponenten(){
        for(ComponentModel m: componentModels){
            System.out.println(m);
        }
    }
}
