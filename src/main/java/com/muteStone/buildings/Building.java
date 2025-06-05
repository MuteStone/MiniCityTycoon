package main.java.com.muteStone.buildings;

import main.java.com.muteStone.economy.ResourceChange;

import java.util.List;

public abstract class Building {
    protected int cost;

    //Konstruktor für Kostenübergabe
    protected Building(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public abstract List<ResourceChange> produce();

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
