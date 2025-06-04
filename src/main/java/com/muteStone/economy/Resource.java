package main.java.com.muteStone.economy;

public class Resource {
    private final ResourceType type;
    private final int amount;

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

    @Override
    public String toString() {
        return amount + " " + type;
    }
}
