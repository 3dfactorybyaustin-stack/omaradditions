package org.omaresz.omaradditions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static org.omaresz.omaradditions.Omaradditions.HEART_KEY;

public class WithdrawalHeartsCommand implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        int value;
        Player player = (Player) commandSourceStack.getExecutor();
        if (args[0] == null) value = 2;
        else try {
            value = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(Omaradditions.PREFIX + "<red> Please input an Integer"));
            return;
        }
        if (player.getMaxHealth() <= value * 2) player.sendMessage(MiniMessage.miniMessage().deserialize(Omaradditions.PREFIX + "<red>You don't have enough Hearts"));
        else {
            player.setMaxHealth(player.getMaxHealth() - (value * 2));
            ItemStack items = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, value);
            ItemMeta meta = items.getItemMeta();
            meta.displayName(MiniMessage.miniMessage().deserialize("<!italic><red><bold>HEART"));
            meta.getPersistentDataContainer().set(HEART_KEY, PersistentDataType.BOOLEAN, Boolean.TRUE);
            items.setItemMeta(meta);
            player.give(items);
        }


    }
}
