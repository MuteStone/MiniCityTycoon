package main.java.com.muteStone.game;

import main.java.com.muteStone.buildings.Building;
import main.java.com.muteStone.buildings.Farm;
import main.java.com.muteStone.economy.Resource;
import main.java.com.muteStone.economy.ResourceType;

import java.util.List;

public class Game {
    private List<Building> buildings;
    private int money;

    public Game() {
        System.out.println("Spiel startet mit " + money + " Geld.");

        //BeispieL: baue eine Farm
        Building farm = new Farm();
        if (money >= farm.getCost()) {
            buildings.add(farm);
            money -= farm.getCost();
            System.out.println("Farm gebaut! Verbleibendes Geld: " + money);
        }

        //Beispiel: Simuliere 5 Tage
        for (int day = 1; day <= 5; day++) {
            System.out.println("=== Tag " + day + " ===");
            for (Building b : buildings) {
                List<Resource> produced = b.produce();
                for (Resource r : produced) {
                    System.out.println("Produziert: " + r.getAmount() + " " + r. getType());
                }
            }
        }
    }
}
