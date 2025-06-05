package com.muteStone.economy;


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

    //Gibt alle Ressourcen zurück (unveränderlich)
    public Map<ResourceType, Integer> getAll() {
        return Map.copyOf(storage);
    }

    //Einzelnen Ressourcenwert abrufen
    public int get(ResourceType type) {
        return storage.getOrDefault(type, 0);
    }

    //Setzt den neuen Wert für einen bestimmten Ressourcentyp
    public void modify(ResourceType type, int newAmount) {
        storage.put(type, Math.max(0, newAmount)); //Fallback-Sicherung
    }

}
