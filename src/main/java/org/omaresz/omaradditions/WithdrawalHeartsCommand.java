package org.omaresz.omaradditions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

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
        if (player.getMaxHealth() <= value * 2) player.sendMessage(MiniMessage.miniMessage().deserialize(Omaradditions.PREFIX + "<You don't have enough Hearts"));
        else {

        }


    }
}
