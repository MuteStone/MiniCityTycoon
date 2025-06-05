package com.muteStone.save;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.muteStone.buildings.*;
import com.muteStone.economy.ResourceStorage;
import com.muteStone.economy.ResourceType;
import com.muteStone.game.Game;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameSaveManager {

    private static final ObjectMapper mapper = new ObjectMapper();

    //Game -> GameState
    public static GameState toGameState(Game game) {
        Map<String, Integer> buildingCounts = new HashMap<>();
        for (String name : game.getBuildings()) {
            buildingCounts.merge(name, 1, Integer::sum);
        }

        Map<String, Integer> resources = new HashMap<>();
        game.getResourceStorage().getAll().forEach((type, amount) ->
                resources.put(type.name(), amount));

        return new GameState(game.getMoney(), buildingCounts, resources);
    }

    //GameState -> Game
    public static Game fromGameState(GameState state) {
        Game game = new Game();

        game.applyChange(new com.muteStone.economy.ResourceChange(ResourceType.MONEY, state.getMoney() - game.getMoney()
        ));

        //Ressourcen laden
        state.getResources().forEach((typeStr, amount) -> {
            ResourceType type = ResourceType.valueOf(typeStr);
            game.getResourceStorage().modify(type, amount);
        });

        //Gebäude rekonsturieren
        state.getBuildings().forEach((name, count) -> {
            for (int i = 0; i < count; i++) {
                game.buildBuilding(createBuildingFromName(name));
            }
        });

        return game;
    }

    //Hilfsmethode zur Gebäude-Erzeugung
    private static com.muteStone.buildings.Building createBuildingFromName(String name) {
        return switch (name) {
            case "Farm" -> new Farm();
            case "Factory" -> new Factory();
            case "Bakery" -> new Bakery();
            case "WeaponSmith" -> new Weaponsmith();
            default -> throw new IllegalArgumentException("Unbekannter Gebäudetyp: " + name);
        };
    }

    //Speichern als JSON
    public static void save(Game game, File file) throws IOException {
        GameState state = toGameState(game);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, state);
    }

    //Laden aus JSON
    public static Game load(File file) throws IOException {
        GameState state = mapper.readValue(file, GameState.class);
        return fromGameState(state);
    }
}
