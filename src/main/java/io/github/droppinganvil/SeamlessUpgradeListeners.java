package io.github.droppinganvil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SeamlessUpgradeListeners implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        //Keep Inventory
        SeamlessUpgrade keepInv = SeamlessUpgrades.upgrades.get("Keep_Inventory");
        if (keepInv.hasUpgrade(e.getEntity())) {
            e.setKeepInventory(true);
            keepInv.removeOne(e.getEntity());
        }
    }
}
