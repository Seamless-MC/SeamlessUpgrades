package io.github.droppinganvil;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class SeamlessUpgrades extends JavaPlugin {
    private static SeamlessUpgrades instance;
    public FileConfiguration data;
    private File dataFile;
    public static HashMap<String, SeamlessUpgrade> upgrades;
    public static SeamlessUpgrades getInstance() {
        return instance;
    }
    public SeamlessUpgrades() {
        if (instance == null) {
            instance = this;
            upgrades = new HashMap<>();
        }
    }
    @Override
    public void onEnable() {
        saveDefaultConfig();
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {e.printStackTrace();}
        }
        data = YamlConfiguration.loadConfiguration(dataFile);
        upgrades.put("Keep_Inventory", new SeamlessUpgrade("Keep_Inventory"));
        upgrades.put("Keep_Exp", new SeamlessUpgrade("Keep_Exp"));
        getServer().getPluginManager().registerEvents(new SeamlessUpgradeListeners(), this);
        getCommand("upgrades").setExecutor(new UpgradeCommandManager());
    }
    @Override
    public void onDisable() {
        for (SeamlessUpgrade su : upgrades.values()) {
            su.save();
        }
        try {
            data.save(dataFile);
        } catch (IOException e) {e.printStackTrace();}
    }
}
