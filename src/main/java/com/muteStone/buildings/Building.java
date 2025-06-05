package com.muteStone.buildings;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.muteStone.economy.ResourceChange;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Farm.class, name = "Farm"),
        @JsonSubTypes.Type(value = Factory.class, name = "Factory"),
        @JsonSubTypes.Type(value = Bakery.class, name = "Bakery"),
        @JsonSubTypes.Type(value = Weaponsmith.class, name = "Weaponsmith")
})
public abstract class Building {
    protected int cost;

    //Konstruktor für Kostenübergabe
    protected Building(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public abstract List<ResourceChange> produce();

    public String getName() {
        return this.getClass().getSimpleName();
    }
}
