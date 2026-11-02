package org.omaresz.omaradditions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static org.omaresz.omaradditions.OmarLifeSteal.HEART_KEY;

public class WithdrawalHeartsCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        int value;
        Player player = (Player) commandSourceStack.getExecutor();
        try {
            if (args[0] == null) value = 1;
            else try {
                value = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage(MiniMessage.miniMessage().deserialize(OmarLifeSteal.PREFIX + "<red> Please input an Integer"));
                return;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            value = 1;
        }

        if (player.getMaxHealth() <= value * 2) player.sendMessage(MiniMessage.miniMessage().deserialize(OmarLifeSteal.PREFIX + "<red>You don't have enough Hearts"));
        else {
            player.setMaxHealth(player.getMaxHealth() - (value * 2));
            ItemStack items = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, value);
            ItemMeta meta = items.getItemMeta();
            meta.displayName(MiniMessage.miniMessage().deserialize("<!italic><red><bold>HEART"));
            meta.getPersistentDataContainer().set(HEART_KEY, PersistentDataType.BOOLEAN, Boolean.TRUE);
            items.setItemMeta(meta);
            player.give(items);
            player.playSound(Sound.sound(Key.key("block.beacon.deactivate"), Sound.Source.PLAYER, 1.0f, 1.0f));
            OmarLifeSteal.BANNED_PLAYERLIST.addString("test");
            Bukkit.broadcast(MiniMessage.miniMessage().deserialize("" + OmarLifeSteal.BANNED_PLAYERLIST.getPlayerList()));
        }


    }
}
