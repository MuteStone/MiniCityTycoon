package main.java.com.muteStone.buildings;

import main.java.com.muteStone.economy.Resource;
import main.java.com.muteStone.economy.ResourceType;

import java.util.Collections;
import java.util.List;

public class Factory extends Building {

    public Factory() {
        this.cost = 500;
    }

    @Override
    public List<Resource> produce() {
        //Produziert 15 Materialien pro Tag
        return Collections.singletonList(new Resource(ResourceType.MATERIALS, 15));
    }
}
