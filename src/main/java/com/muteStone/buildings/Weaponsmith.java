package com.muteStone.buildings;

import com.muteStone.economy.ResourceChange;
import com.muteStone.economy.ResourceType;

import java.util.ArrayList;
import java.util.List;

public class Weaponsmith extends Building{
    private static final int COST = 700;
    private static final int MATERIAL_INPUT = -15;
    private static final int MONEY_OUTPUT = 10;

    public Weaponsmith() {
        super(COST); //Aufruf an Building
    }

    @Override
    public List<ResourceChange> produce() {
        List<ResourceChange> changes = new ArrayList<>();

        //Erst Verbrauch (negativ)
        changes.add(new ResourceChange(ResourceType.MATERIALS, MATERIAL_INPUT));

        //Dann Produktion (positiv)
        changes.add(new ResourceChange(ResourceType.MONEY, MONEY_OUTPUT));

        return changes;
    }
}
