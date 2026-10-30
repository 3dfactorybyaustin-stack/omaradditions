package org.omaresz.omaradditions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import javax.swing.text.html.HTMLDocument;

public class coolCommand implements BasicCommand {


    @Override
    public void execute(final CommandSourceStack source, final String[] args) {
        Component broadcastMessage = MiniMessage.miniMessage().deserialize("<red>init");
        Location loc;
        if (args.length == 0) loc = source.getLocation();
        else try {
            loc = new Location(source.getExecutor().getWorld(), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
        } catch (NullPointerException e) {
            loc = new Location(Bukkit.getWorld("world"), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
        }
        nuker(loc);

    }
        public static void nuker (Location loc){
            loc = loc.subtract(5, 0 ,5);
            for (int k = 0; k < 10; k++){
                for (int i = 0; i < 10; i++) {
                    loc.add(1, 0, 0).getBlock()
                    .breakNaturally();
                    Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<yellow> Loc: " + loc));
                }
            loc.add(-10, 0, 1);
            }
    }}