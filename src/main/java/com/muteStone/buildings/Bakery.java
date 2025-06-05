package main.java.com.muteStone.buildings;

import main.java.com.muteStone.economy.ResourceChange;
import main.java.com.muteStone.economy.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class Bakery extends Building {
    private static final int COST = 250;
    private static final int FOOD_INPUT = -10;
    private static final int MONEY_OUTPUT = 5;

    public Bakery() {
        super(COST); //Aufruf an Building
    }

    @Override
    public List<ResourceChange> produce() {
        List<ResourceChange> changes = new ArrayList<>();

        //Erst Verbrauch (negativ)
        changes.add(new ResourceChange(ResourceType.FOOD, FOOD_INPUT));

        //Dann Produktion (positiv)
        changes.add(new ResourceChange(ResourceType.MONEY, MONEY_OUTPUT));

        return changes;
    }
}
