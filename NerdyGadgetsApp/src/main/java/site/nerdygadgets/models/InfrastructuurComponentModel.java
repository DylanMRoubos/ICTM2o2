package site.nerdygadgets.models;

import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.functions.Serialization;

import java.io.IOException;
import java.util.ArrayList;


public class InfrastructuurComponentModel {
    private String name;
    private double availability;
    private double price;
    private ComponentType type;
    private int aantal;

    public InfrastructuurComponentModel(String name, double availability, double price, ComponentType type, int aantal) {
        this.name = name;
        this.availability = availability;
        this.price = price;
        this.type = type;
        this.aantal = aantal;
    }

    public ComponentType getType() {
        return this.type;
    }

    public double getPrice() {
        return this.price;
    }

    public double getAvailability() {
        return this.availability;
    }

    public String getName() {
        return this.name;
    }

    public int getAantal() {
        return aantal;
    }

    @Override
    public String toString() {
        return "InfrastructuurComponentModel{" +
                "name='" + name + '\'' +
                ", availability=" + availability +
                ", price=" + price +
                ", type=" + type +
                ", aantal=" + aantal +
                '}';
    }

    public static InfrastructuurComponentModel makeInfrastructuurComponentModel(ComponentModel m, int amount) {
        return new InfrastructuurComponentModel(m.getName(), m.getAvailability(), m.getPrice(), m.getType(), amount);
    }

    public static InfrastructuurComponentModel getModel(String name, ComponentType type) {
        try {
            ArrayList<InfrastructuurComponentModel> l = Serialization.deserializeInfrastructuur();
            for (InfrastructuurComponentModel m : l) {
                if (m.getType() == type && m.getName().equals(name)) {
                    return m;
                }
            }
        }
        catch (IOException e) { }
        return null;
    }
}
