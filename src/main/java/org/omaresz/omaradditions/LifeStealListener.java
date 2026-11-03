package org.omaresz.omaradditions;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Date;

import static org.omaresz.omaradditions.OmarLifeSteal.HEART_KEY;
import static org.omaresz.omaradditions.OmarLifeSteal.UNBAN_ITEM;


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
        if (victim.getMaxHealth() < 3) {
            victim.kick(MiniMessage.miniMessage().deserialize("Max Health Too Low"));
            OmarLifeSteal.BANNED_PLAYERLIST.addPlayer(victim);
        }
        else {
            victim.setMaxHealth(victim.getMaxHealth() - 2);
            victim.sendMessage(MiniMessage.miniMessage().deserialize("<red>You have lost one Heart to <bold>" + killer.getName()));
        }
    }

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getItemMeta().getPersistentDataContainer().has(HEART_KEY, PersistentDataType.BOOLEAN)) {
            event.setCancelled(true);
            ItemStack items = event.getPlayer().getActiveItem();
            items.setAmount(items.getAmount() - 1);
            event.getPlayer().setMaxHealth(event.getPlayer().getMaxHealth() + 2);
            event.getPlayer().playSound(Sound.sound(Key.key("block.beacon.activate"), Sound.Source.PLAYER, 1.0f, 1.0f));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (OmarLifeSteal.BANNED_PLAYERLIST.getPlayerList().contains(event.getPlayer().getUniqueId().toString())) event.getPlayer().kick(MiniMessage.miniMessage().deserialize("Max Health Too Low"));
    }
}
