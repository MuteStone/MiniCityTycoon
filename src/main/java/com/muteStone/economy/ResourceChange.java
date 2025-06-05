package main.java.com.muteStone.economy;

/**
 * Beschreibt eine Änderung an einer Ressource (positiv oder negativ).
 * Wird für Produktion, Konsum oder Events verwendet.
 */
public class ResourceChange {
    private final ResourceType type;
    private final int amount;

    public ResourceChange(ResourceType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public ResourceType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    /**
     * Wendet die Änderungen auf das angegebene Lager an.
     * Negative Werte werden nur angewendet, wenn genug vorhanden ist.
     * @param storage das Ziel-Lager
     * @return true, wenn erfolgreich angewendet, false bei unzureichendem Bestand
     */
    public boolean apply(ResourceStorage storage) {
        int current = storage.get(type);

        //Wenn negativ: nur wenn genug vorhanden
        if (amount < 0 && current + amount < 0) {
            return false;
        }

        storage.modify(type, current + amount);
        return true;
    }

    public boolean canApply(ResourceStorage storage) {
        int current = storage.get(type);
        return !(amount < 0 && current + amount < 0);
    }
}
