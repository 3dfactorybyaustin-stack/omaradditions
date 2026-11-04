package org.omaresz.omaradditions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static java.util.UUID.fromString;

public class UnBanPlayer implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player p = (Player) commandSourceStack.getExecutor();
        if (p.getInventory().contains(OmarLifeSteal.UNBAN_ITEM)) {
            for (String s : OmarLifeSteal.BANNED_PLAYERLIST.getPlayerList()) {
                if (UUID.fromString(s).equals(Bukkit.getOfflinePlayer(args[0]).getUniqueId())) {
                    OmarLifeSteal.BANNED_PLAYERLIST.removePlayer(Bukkit.getOfflinePlayer(args[0]));
                    p.sendMessage(MiniMessage.miniMessage().deserialize(OmarLifeSteal.PREFIX + "<blue> " + Bukkit.getOfflinePlayer(args[0]).getName() + " Has been Unbanned"));
                    for (ItemStack item : p.getInventory()) {
                        if (item == null) continue;
                        if (item.equals(OmarLifeSteal.UNBAN_ITEM)) {
                            item.setAmount(0);
                            return;
                        }
                    }
                };
            }
            p.sendMessage(MiniMessage.miniMessage().deserialize(OmarLifeSteal.PREFIX + "<red> Player has not been Banned"));
        } else
            p.sendMessage(MiniMessage.miniMessage().deserialize(OmarLifeSteal.PREFIX + "<red> You do not have a Respawn Token"));


    }
}
