package main.java.com.muteStone.buildings;

import main.java.com.muteStone.economy.Resource;

import java.util.List;

public abstract class Building {
    protected int cost;

    public int getCost() {
        return cost;
    }

    public abstract List<Resource> produce();
}
