package org.omaresz.omaradditions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;

public class DebugCommand implements BasicCommand {

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (args[0].equals("giveUnbanItem")) {
            Player p = (Player) commandSourceStack.getExecutor();
            p.give(OmarLifeSteal.UNBAN_ITEM);
        }
    }
}
