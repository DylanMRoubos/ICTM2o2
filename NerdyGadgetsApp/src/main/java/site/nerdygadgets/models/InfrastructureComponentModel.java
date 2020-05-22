package site.nerdygadgets.models;

import site.nerdygadgets.functions.ComponentType;
import site.nerdygadgets.functions.Serialization;

import java.io.IOException;
import java.util.ArrayList;

/**
 * InfrastructureComponentModel class
 * Create components and set the amount
 *
 * @author Tristan Scholten & Jordy Wielaard
 * @version 1.0
 * @since 14-05-2020
 */
public class InfrastructureComponentModel {
    private String name;
    private double availability;
    private double price;
    private ComponentType type;
    private int amount;

    public InfrastructureComponentModel(String name, double availability, double price, ComponentType type, int amount) {
        this.name = name;
        this.availability = availability;
        this.price = price;
        this.type = type;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "InfrastructureComponentModel{" +
                "name='" + name + '\'' +
                ", availability=" + availability +
                ", price=" + price +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }

    public static InfrastructureComponentModel makeInfrastructureComponentModel(ComponentModel m, int amount) {
        return new InfrastructureComponentModel(m.getName(), m.getAvailability(), m.getPrice(), m.getType(), amount);
    }

    public static InfrastructureComponentModel getModel(String name, ComponentType type) {
        try {
            ArrayList<InfrastructureComponentModel> l = Serialization.deserializeInfrastructure();
            for (InfrastructureComponentModel m : l) {
                if (m.getType() == type && m.getName().equals(name)) {
                    return m;
                }
            }
        } catch (IOException e) {
        }
        return null;
    }
}
