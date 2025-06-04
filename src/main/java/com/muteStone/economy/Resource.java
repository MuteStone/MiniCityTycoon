package main.java.com.muteStone.economy;

public class Resource {
    private ResourceType type;
    private int amount;

    public Resource(ResourceType type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public ResourceType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }
}
