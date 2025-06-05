package com.muteStone.save;

import com.muteStone.buildings.Building;
import com.muteStone.economy.ResourceType;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Reines Datenobjekt für Speicherung und Laden.
 */
public class GameState {
    private int money;
    private List<String> buildings;
    private Map<ResourceType, Integer> resources;

    public GameState() {
        this.buildings = new ArrayList<>();
        this.resources = new EnumMap<>(ResourceType.class);
        this.money = 0;
    }

    public GameState (int money, List<String> buildings, Map<ResourceType, Integer> resources) {
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

    public List<String> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<String> buildings) {
        this.buildings = buildings;
    }

    public Map<ResourceType, Integer> getResources() {
        return resources;
    }

    public void setResources(Map<ResourceType, Integer> resources) {
        this.resources = resources;
    }
}
