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
        List<String> buildingNames = new ArrayList<>();
        for (Building b : game.getRawBuildings()) {
            buildingNames.add(b.getClass().getSimpleName());
        }

        Map<ResourceType, Integer> resources = new EnumMap<>(ResourceType.class);
        game.getResourceStorage().getAll().forEach(resources::put);

        return new GameState(game.getMoney(), buildingNames, resources);

    }

    //GameState -> Game
    public static Game fromGameState(GameState state) {
        Game game = new Game();

        game.applyChange(new com.muteStone.economy.ResourceChange(ResourceType.MONEY, state.getMoney() - game.getMoney()
        ));

        //Ressourcen laden
        state.getResources().forEach(game.getResourceStorage()::modify);

        //Gebäude rekonsturieren
        state.getBuildings().forEach(name -> {
            game.buildBuildingDirect(createBuildingFromName(name));
        });

        return game;
    }

    //Hilfsmethode zur Gebäude-Erzeugung
    private static com.muteStone.buildings.Building createBuildingFromName(String name) {
        return switch (name) {
            case "Farm" -> new Farm();
            case "Factory" -> new Factory();
            case "Bakery" -> new Bakery();
            case "Weaponsmith" -> new Weaponsmith();
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
