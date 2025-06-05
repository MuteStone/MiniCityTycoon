package com.muteStone.economy;

public enum ResourceType {
    FOOD("Nahrung"),
    MATERIALS("Material"),
    MONEY("Geld");

    private final String displayName;

    ResourceType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
