package main.java.com.muteStone.economy;


import java.util.EnumMap;
import java.util.Map;

/**
 * Speichert alle Ressourcen im Spiel.
 */
public class ResourceStorage {
    private final Map<ResourceType, Integer> storage;

    public ResourceStorage() {
        storage = new EnumMap<>(ResourceType.class);
        //Optional: Startwerte setzen
        for (ResourceType type : ResourceType.values()) {
            storage.put(type, 0);
        }
    }

    //Fügt Ressourcen hinzu
    public void add(Resource resource) {
        ResourceType type = resource.getType();
        int current = storage.getOrDefault(type, 0);
        storage.put(type, current + resource.getAmount());
    }

    //Gibt alle Ressourcen zurück
    public Map<ResourceType, Integer> getAll() {
        return storage;
    }

    //Für hübsche Anzeige
    public void printStatus() {
        System.out.println("=== Ressourcenlager ===");
        for (Map.Entry<ResourceType, Integer> entry : storage.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
