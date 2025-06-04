package main.java.com.muteStone.economy;


import java.util.EnumMap;
import java.util.Map;

/**
 * Speichert alle Ressourcen im Spiel.
 * Unterstützt sowohl positive als auch negative Änderungen,
 * aber der Bestand fällt nie unter 0.
 */
public class ResourceStorage {
    private final Map<ResourceType, Integer> storage;

    public ResourceStorage() {
        storage = new EnumMap<>(ResourceType.class);
        //Startwerte setzen
        for (ResourceType type : ResourceType.values()) {
            storage.put(type, 0);
        }
    }

    /**
     * Versucht, eine Ressourcenänderung anzuwenden.
     * Kann sowohl positive als auch negative Werte verarbeiten,
     * wobei die Mende nie unter 0 fallen darf.
     * @param resource Die Ressource mit Menge (positiv oder negativ)
     */
    public boolean add(Resource resource) {
        ResourceType type = resource.getType();
        int current = storage.getOrDefault(type, 0);
        int newAmount = current + resource.getAmount();

        if (newAmount < 0) {
            //Nicht genug Ressourcen, Änderung ablehnen
            return false;
        }

        storage.put(type, newAmount);
        return true;
    }

    //Gibt alle Ressourcen zurück (unveränderlich)
    public Map<ResourceType, Integer> getAll() {
        return Map.copyOf(storage);
    }

    //Einzelnen Ressourcenwert abrufen
    public int get(ResourceType type) {
        return storage.getOrDefault(type, 0);
    }

}
