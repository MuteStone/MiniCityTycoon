package main.java.com.muteStone.game;

import main.java.com.muteStone.buildings.Building;
import main.java.com.muteStone.economy.ResourceChange;
import main.java.com.muteStone.economy.ResourceStorage;
import main.java.com.muteStone.economy.ResourceType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Die Hauptklasse für die Spielverwaltung.
 * Hält den Geldstand, alle Gebäude und steuert die Simulation.
 */
public class Game {
    private ResourceStorage resourceStorage;
    private List<Building> buildings;
    private int money;

    public Game() {
        buildings = new ArrayList<>();
        money = 1000; //Startgeld
        resourceStorage = new ResourceStorage();
    }

    public String buildBuilding(Building building) {
        if (applyChange(new ResourceChange(ResourceType.MONEY, -building.getCost()))) {
            buildings.add(building);
            return building.getName() + " gebaut! Verbleibendes Geld: " + money;
        }
        return "Nicht genug Geld für " + building.getName() + ".";

    }

    public List<String> simulateDayAndGetLog() {
        List<String> events = new ArrayList<>();
        events.add("===================================");
        events.add("🕒 Tag beginnt: Ressourcenproduktion");
        events.add("===================================");

        // Neue Datenstrukturen für Gruppierung
        Map<String, List<ResourceChange>> groupedChanges = new HashMap<>();
        Map<String, Integer> buildingCounts = new HashMap<>();
        List<String> failedBuildings = new ArrayList<>();

        // Gebäude durchgehen und gruppiert sammeln
        for (Building b : buildings) {
            String name = b.getName();
            List<ResourceChange> changes = b.produce();

            if (canApplyAll(changes)) {
                buildingCounts.put(name, buildingCounts.getOrDefault(name, 0) + 1);
                groupedChanges.computeIfAbsent(name, k -> new ArrayList<>()).addAll(changes);
                for (ResourceChange change : changes) {
                    applyChange(change);
                }
            } else {
                failedBuildings.add(name);
            }
        }

        // Gruppierte Erfolge ausgeben
        for (String name : groupedChanges.keySet()) {
            List<ResourceChange> changes = groupedChanges.get(name);
            int count = buildingCounts.get(name);

            Map<ResourceType, Integer> summed = new EnumMap<>(ResourceType.class);
            for (ResourceChange c: changes) {
                summed.merge(c.getType(), c.getAmount(), Integer::sum);
            }

            StringBuilder line = new StringBuilder("✓ " + name + " (" + count + "x): ");
            List<String> parts = new ArrayList<>();
            for (Map.Entry<ResourceType, Integer> e : summed.entrySet()) {
                String sign = e.getValue() >= 0 ? "+" : "";
                parts.add(sign + e.getValue() + " " + e.getKey());
            }

            line.append(String.join(", ", parts));
            events.add(line.toString());
        }

        // Fehlproduktionen gruppieren und ausgeben
        Map<String, Long> failCount = failedBuildings.stream()
                        .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        for (Map.Entry<String, Long> e : failCount.entrySet()) {
            events.add("❌ " + e.getKey() + " (" + e.getValue() + "x) konnte nicht produzieren (nicht genug Ressourcen)");
        }

        events.add("===================================\n");
        return events;
    }

    public int getMoney() {
        return money;
    }

    public List<String> getBuildings() {
        List<String> names = new ArrayList<>();
        for (Building b : buildings) {
            names.add(b.getClass().getSimpleName());
        }
        return names;
    }

    public ResourceStorage getResourceStorage() {
        return resourceStorage;
    }

    public boolean applyChange(ResourceChange change) {
        if(change.getType() == ResourceType.MONEY) {
            int result = money + change.getAmount();
            if (result < 0) return false;
            money = result;
            return true;
        }
        return change.apply(resourceStorage);
    }

    public boolean canApplyAll(List<ResourceChange> changes) {
        for (ResourceChange c : changes) {
            if (c.getType() == ResourceType.MONEY) {
                if (money + c.getAmount() < 0) return false;
            } else {
                if (!c.canApply(resourceStorage)) return false;
            }
        }
        return true;
    }

    public Map<ResourceType, Map<String, Integer>> getBuildingsGroupedByOutput() {
        Map<ResourceType, Map<String, Integer>> grouped = new EnumMap<>(ResourceType.class);

        for (Building b: buildings) {
            List<ResourceChange> changes = b.produce();
            for (ResourceChange c : changes) {
                if (c.getAmount() <= 0) continue; // Nur Produktion, kein Verbrauch
                grouped
                        .computeIfAbsent(c.getType(), t -> new HashMap<>())
                        .merge(b.getName(), 1, Integer::sum);
            }
        }

        return grouped;
    }
}
