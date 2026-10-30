package org.omaresz.omaradditions;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.time.Duration;


public class LifeStealListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getPlayer();
        Player killer = event.getPlayer().getKiller();
        if (killer == null) {
            event.setCancelled(true);
            return;
        }
        killer.setMaxHealth(killer.getMaxHealth() + 2);
        killer.sendMessage(MiniMessage.miniMessage().deserialize("<green>You have gained one Heart from <bold>" + victim.getName()));
        if (victim.getMaxHealth() < 3) victim.ban("Max Health Too Low", Duration.ZERO, "omaradditions", true);
        else {
            victim.setMaxHealth(victim.getMaxHealth() - 2);
            victim.sendMessage(MiniMessage.miniMessage().deserialize("<red>You have lost one Heart to <bold>" + killer.getName()));
        }
    }
}
