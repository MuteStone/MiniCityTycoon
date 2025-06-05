package com.muteStone.save;

import java.util.Map;

/**
 * Reines Datenobjekt für Speicherung und Laden.
 */
public class GameState {
    private int money;
    private Map<String, Integer> buildings;
    private Map<String, Integer> resources;

    public GameState() {
        //Leerer Konstruktor für Jackson
    }

    public GameState (int money, Map<String, Integer> buildings, Map<String, Integer> resources) {
        this.money = money;
        this.buildings = buildings;
        this.resources = resources;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Map<String, Integer> getBuildings() {
        return buildings;
    }

    public void setBuildings(Map<String, Integer> buildings) {
        this.buildings = buildings;
    }

    public Map<String, Integer> getResources() {
        return resources;
    }

    public void setResources(Map<String, Integer> resources) {
        this.resources = resources;
    }
}
