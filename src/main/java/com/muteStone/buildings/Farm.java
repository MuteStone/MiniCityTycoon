package main.java.com.muteStone.buildings;

import main.java.com.muteStone.economy.Resource;
import main.java.com.muteStone.economy.ResourceType;

import java.util.Collections;
import java.util.List;

/**
 * Farm - produziert täglich eine feste Menge Nahrung.
 */
public class Farm extends Building {

    private static final int COST = 100;
    private static final int PRODUCTION_AMOUNT = 10;

    public Farm() {
        super(COST); //Baukosten setzen
    }

    /**
     * Produziert täglich Nahrung.
     * @return Liste mit produzierten Ressourcen (hier nur Nahrung).
     */
    @Override
    public List<Resource> produce() {
        //Eine Farm produziert 10 nahrung pro Tag
        return Collections.singletonList(new Resource(ResourceType.FOOD, PRODUCTION_AMOUNT));
    }

}
