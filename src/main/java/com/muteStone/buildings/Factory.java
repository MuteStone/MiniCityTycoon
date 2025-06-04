package main.java.com.muteStone.buildings;

import main.java.com.muteStone.economy.Resource;
import main.java.com.muteStone.economy.ResourceType;

import java.util.Collections;
import java.util.List;

/**
 * Factory - produziert täglich Materialien.
 */
public class Factory extends Building {

    private static final int COST = 500;
    private static final int PRODUCTION_AMOUNT = 15;

    public Factory() {
        super(COST);
    }

    /**
     * Produziert Materialien.
     * @return Liste mit produzierten Ressourcen (hier Materialien).
     */
    @Override
    public List<Resource> produce() {
        //Produziert 15 Materialien pro Tag
        return Collections.singletonList(new Resource(ResourceType.MATERIALS, PRODUCTION_AMOUNT));
    }
}
