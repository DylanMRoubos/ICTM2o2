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

    public boolean addComponentModel(ComponentModel model) {
        try {
            ArrayList<ComponentModel> l = Serialization.deserializeComponents();
            for (ComponentModel m : l) {
                if (m.toString().equals(model.toString())) {
                    return false;
                }
            }
            componentModels.add(model);
            Serialization.serializeComponents(componentModels);
            return true;
        }catch (IOException e){
            System.out.println("Error ding");
            return false;
        }
    }

    public void reloadComponentModel() {
        try {
            this.componentModels = Serialization.deserializeComponents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeAt(int index) {
        componentModels.remove(index);
        try {
            Serialization.serializeComponents(componentModels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printComponenten(){
        for(ComponentModel m: componentModels){
            System.out.println(m);
        }
    }
}
