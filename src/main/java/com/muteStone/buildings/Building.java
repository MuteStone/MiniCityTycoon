package main.java.com.muteStone.buildings;

import main.java.com.muteStone.economy.Resource;

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

    public abstract List<Resource> produce();

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
