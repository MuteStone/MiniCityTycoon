package main.java.com.muteStone.buildings;

import main.java.com.muteStone.economy.Resource;
import main.java.com.muteStone.economy.ResourceType;

import java.util.Collections;
import java.util.List;

public class Farm extends Building {

    public Farm() {
        this.cost = 100;
    }

    @Override
    public List<Resource> produce() {
        //Eine Farm produziert 10 nahrung pro Tag
        return Collections.singletonList(new Resource(ResourceType.FOOD, 10));
    }

}
