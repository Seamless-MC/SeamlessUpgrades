package io.github.droppinganvil;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SeamlessUpgrade {
    public static FileConfiguration data;
    public static FileConfiguration config;
    private String name;
    private String friendlyName;
    private HashMap<String, Integer> players;

    public SeamlessUpgrade(String name) {
        if (data == null) {
            data = SeamlessUpgrades.getInstance().data;
        }
        if (config == null) {
            data = SeamlessUpgrades.getInstance().getConfig();
        }
        this.name = name;
        this.friendlyName = name.replace('_', ' ');
        load();
    }

    public boolean hasUpgrade(Player p) {
        String uuid = p.getUniqueId().toString();
        if (players.containsKey(uuid) && players.get(uuid) != 0) {
            return true;
        }
        return false;
    }
    public void removeOne(Player p) {
        String uuid = p.getUniqueId().toString();
        players.replace(uuid, players.get(uuid), players.get(uuid) - 1);
        p.sendMessage(select(name, "-Remove")
                .replace("{UPGRADE}", friendlyName)
                .replace("{AMOUNT}", players.get(uuid)));
    }

    public void addOne(Player p) {
        String uuid = p.getUniqueId().toString();
        if (players.containsKey(uuid)) {
            players.replace(uuid, players.get(uuid), players.get(uuid) + 1);
        } else {
            players.put(uuid, 1);
        }
        p.sendMessage(select(name, "-Remove")
                .replace("{UPGRADE}", friendlyName)
                .replace("{AMOUNT}", players.get(uuid)));
    }

    public static String select(String name, String operation) {
        /* operation needs to be -Removed, -Added, or -Edited */
        int index = 0;
        String temp = config.getString("Messages." + name + operation);
        while (temp == null) {
            switch (index) {
                case 0:
                    temp = config.getString("Messages." + name + "-Edited");
                    break;
                case 1:
                    temp = config.getString("Messages.Generic" + operation);
                    break;
                case 2:
                    temp = config.getString("Messages.Generic-Edited");
                    break;
                case 3:
                    temp = "&8[&4&l&oSF&r&8]&r &8You now have {AMOUNT} {UPGRADE}s";
            }
            index++;
        }
        return ChatColor.translateAlternateColorCodes('&', temp);
    }

    private void load() {
        if (data.getConfigurationSection(name) == null) {
            data.createSection(name);
        }
        players = new HashMap<>();
        for (String uuid : data.getConfigurationSection(name).getKeys(false)) {
            players.put(uuid, data.getInt(name + "." + uuid));
        }
    }

    public void save() {
        for (String uuid : players.keySet()) {
            int i = players.get(uuid);
            data.set(name + "." + uuid, i);
        }
    }
}
