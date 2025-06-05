package com.muteStone.gui;

import com.muteStone.buildings.Bakery;
import com.muteStone.buildings.Factory;
import com.muteStone.buildings.Farm;
import com.muteStone.buildings.Weaponsmith;
import com.muteStone.economy.ResourceStorage;
import com.muteStone.economy.ResourceType;
import com.muteStone.game.Game;
import com.muteStone.save.GameSaveManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GameGUI extends JFrame {
    private Game game;
    private final JTextArea logArea;
    private final JLabel moneyLabel;
    private final JTextArea resourceArea;
    private final JTextArea buildingArea;

    public GameGUI() {
        super("MiniCityTycoon");

        this.game = new Game(); //Spiel-Backend

        //Layout
        setSize(1280, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        GameGUI.this,
                        "Willst du das Spiel wirklich beenden?",
                        "Beenden bestätigen",
                        JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        File file = new File("autosave.json");
                        GameSaveManager.save(game, file);
                        System.out.println("Spiel automatisch gespeichert.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(GameGUI.this,
                                "Fehler beim automatischen Speichen:\n" + ex.getMessage(),
                                "Fehler", JOptionPane.ERROR_MESSAGE);
                    }

                    System.exit(0);
                }
            }
        });

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

        JButton buildWeaponsmithbtn = new JButton("Waffenschmied bauen (700$)");
        buildWeaponsmithbtn.addActionListener(e -> {
            String result = game.buildBuilding(new Weaponsmith());
            appendLog(result);
            updateUI();
        });

        JButton simulateBtn = new JButton("Nächster Tag");
        simulateBtn.addActionListener(e -> {
            List<String> events = game.simulateDayAndGetLog();
            events.forEach(this::appendLog);
            updateUI();
        });

        JButton saveButton = new JButton("Spiel speichern");
        saveButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    GameSaveManager.save(game, file);
                    appendLog("Spiel gespeichert: " + file.getName());
                } catch (IOException ex) {
                    appendLog("Fehler beim Speichern: " + ex.getMessage());
                }
            }
        });

        JButton loadButton = new JButton("Spiel laden");
        loadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    Game loadedGame = GameSaveManager.load(file);
                    this.replaceGame(loadedGame);
                    appendLog("Spiel geladen: " + file.getName());
                } catch (Exception ex) {
                    appendLog("Fehler beim laden: " + ex.getMessage());
                }
            }
        });

        JButton newGameBtn = new JButton("Neues Spiel");
        newGameBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Willst du wirklich ein neues Spiel starten?\nDer aktuelle Fortschritt geht verloren.", "Neues Spiel bestätigen", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                game.reset();
                appendLog("Neues Spiel gestartet.");
                updateUI();
            }
        });

        JButton quitBtn = new JButton("Spiel beenden");
        quitBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                    "Willst du das Spiel wirklich beenden?",
                    "Spiel beenden", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        controlPanel.add(buildFarmBtn);
        controlPanel.add(buildFactoryBtn);
        controlPanel.add(buildBakeryBtn);
        controlPanel.add(buildWeaponsmithbtn);
        controlPanel.add(simulateBtn);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
        controlPanel.add(newGameBtn);
        controlPanel.add(quitBtn);

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

    private void replaceGame(Game newGame) {
        this.game = newGame;
        updateUI();
    }
}
