package io.github.droppinganvil;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SeamlessUpgradeListeners implements Listener {
    SeamlessUpgrade keepInv = SeamlessUpgrades.upgrades.get("Keep_Inventory");
    SeamlessUpgrade keepExp = SeamlessUpgrades.upgrades.get("Keep_Exp");
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        //Keep Inventory
        if (keepInv.hasUpgrade(e.getEntity())) {
            e.setKeepInventory(true);
            e.getDrops().clear();
            keepInv.removeOne(e.getEntity());
        }
        if (keepExp.hasUpgrade(e.getEntity())) {
            e.setKeepLevel(true);
            e.setDroppedExp(0);
        }
    }
}
