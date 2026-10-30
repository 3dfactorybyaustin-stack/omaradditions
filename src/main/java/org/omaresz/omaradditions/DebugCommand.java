package org.omaresz.omaradditions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DebugCommand implements BasicCommand {


    @Override
    public void execute(CommandSourceStack source , String[] args) {

        if (args[0].equals("resetHealth")) {
            Player player = (Player) source.getSender();
            player.setMaxHealth(20);
            //Bukkit.broadcast(MiniMessage.miniMessage().deserialize(player.toString()));

        }
    }
}
