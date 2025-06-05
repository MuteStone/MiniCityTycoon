package main.java.com.muteStone.gui;

import main.java.com.muteStone.buildings.Bakery;
import main.java.com.muteStone.buildings.Factory;
import main.java.com.muteStone.buildings.Farm;
import main.java.com.muteStone.economy.ResourceStorage;
import main.java.com.muteStone.economy.ResourceType;
import main.java.com.muteStone.game.Game;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class GameGUI extends JFrame {
    private final Game game;
    private final JTextArea logArea;
    private final JLabel moneyLabel;
    private final JTextArea resourceArea;
    private final JTextArea buildingArea;

    public GameGUI() {
        super("MiniCityTycoon");

        this.game = new Game(); //Spiel-Backend

        //Layout
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Info Panel (oben)
        JPanel infoPanel = new JPanel(new GridLayout(1, 2));
        moneyLabel = new JLabel("Geld: " + game.getMoney());
        infoPanel.add(moneyLabel);

        resourceArea = new JTextArea();
        resourceArea.setEditable(false);
        resourceArea.setBorder(BorderFactory.createTitledBorder("Ressourcen"));
        infoPanel.add(resourceArea);

        add(infoPanel, BorderLayout.NORTH);

        //Log + Gebäudeanzeige (Mitte)
        JPanel centerPanel = new JPanel((new GridLayout(1, 2)));

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setBorder(BorderFactory.createTitledBorder("Gebäude"));
        centerPanel.add(new JScrollPane(logArea));

        buildingArea = new JTextArea();
        buildingArea.setEditable(false);
        buildingArea.setBorder(BorderFactory.createTitledBorder("Gebäude"));
        centerPanel.add(new JScrollPane(buildingArea));

        add(centerPanel, BorderLayout.CENTER);

        //Steuerung Panel (unten)
        JPanel controlPanel = new JPanel();

        JButton buildFarmBtn = new JButton("Farm bauen (100$)");
        buildFarmBtn.addActionListener(e -> {
            String result = game.buildBuilding(new Farm());
            appendLog(result);
            updateUI();
        });

        JButton buildFactoryBtn = new JButton("Fabrik bauen (500$)");
        buildFactoryBtn.addActionListener(e -> {
            String result = game.buildBuilding(new Factory());
            appendLog(result);
            updateUI();
        });

        JButton buildBakeryBtn = new JButton("Bäckerei bauen (250$)");
        buildBakeryBtn.addActionListener(e -> {
            String result = game.buildBuilding(new Bakery());
            appendLog(result);
            updateUI();
        });

        JButton simulateBtn = new JButton("Nächster Tag");
        simulateBtn.addActionListener(e -> {
            List<String> events = game.simulateDayAndGetLog();
            events.forEach(this::appendLog);
            updateUI();
        });

        controlPanel.add(buildFarmBtn);
        controlPanel.add(buildFactoryBtn);
        controlPanel.add(buildBakeryBtn);
        controlPanel.add(simulateBtn);

        add(controlPanel, BorderLayout.SOUTH);

        updateUI();
    }

    private void appendLog(String message) {
        logArea.append(message + "\n");
    }

    private void updateUI() {
        moneyLabel.setText("Geld: " + game.getMoney());

        //Ressourcen anzeigen
        ResourceStorage storage = game.getResourceStorage();
        StringBuilder resourceText = new StringBuilder();
        storage.getAll().forEach((type, amount) ->
                resourceText.append(type).append(": ").append(amount).append("\n"));
        resourceArea.setText(resourceText.toString());

        //Gebäude anzeigen
        Map<ResourceType, Map<String, Integer>> grouped = game.getBuildingsGroupedByOutput();

        StringBuilder groupedText = new StringBuilder();
        for (Map.Entry<ResourceType, Map<String, Integer>> entry : grouped.entrySet()) {
            groupedText.append(entry.getKey()).append("-Produktion:\n");
            for (Map.Entry<String, Integer> inner : entry.getValue().entrySet()) {
                groupedText.append("  - ").append(inner.getKey())
                        .append(" (").append(inner.getValue()).append("x)\n");
            }
            groupedText.append("\n");
        }
        buildingArea.setText(groupedText.toString());
    }
}
