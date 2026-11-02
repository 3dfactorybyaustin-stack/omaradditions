package org.omaresz.omaradditions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;


public class LifeStealInventories implements BasicCommand, Listener {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getExecutor();
        player.openInventory(OmarLifeSteal.MAIN_INV.getInventory());
    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        int slot = event.getSlot();
        if (event.getInventory().equals(OmarLifeSteal.MAIN_INV.getInventory())) {

            if (slot == OmarLifeSteal.BANNED_PLAYER_LIST_SLOT) event.getWhoClicked().openInventory(OmarLifeSteal.BANNED_PLAYERS_INV.getInventory());
            if (slot == OmarLifeSteal.PLAYER_LIST_SLOT) event.getWhoClicked().openInventory(OmarLifeSteal.PLAYER_LIST_INVS[0].getInventory());




        } else if (Arrays.stream(OmarLifeSteal.PLAYER_LIST_INVS).toList().contains(event.getInventory())) {
            Bukkit.broadcast(MiniMessage.miniMessage().deserialize("test"));
        }
    }


    public static void updateGUILoop(Plugin plugin) {

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            int total = 11;//Bukkit.getOnlinePlayers().size();
            int inventoryNum = (int) Math.ceil(((double) total / 21));
            int rowNum;
            int trim;
            int index = 0;
            Player players[] = Bukkit.getOnlinePlayers().toArray(new Player[0]);

            //Bukkit.broadcast(MiniMessage.miniMessage().deserialize(" " + inventoryNum));
            for (int i = 0; i < inventoryNum; i++) {


                OmarLifeSteal.PLAYER_LIST_INVS[i].setSlots(10, 16, new ItemStack(Material.AIR));
                OmarLifeSteal.PLAYER_LIST_INVS[i].setSlots(19, 25, new ItemStack(Material.AIR));
                OmarLifeSteal.PLAYER_LIST_INVS[i].setSlots(28, 34, new ItemStack(Material.AIR));
            }

            //loops for each inventory
            for (int i = 0; i <= inventoryNum-1; i++) {
                if (total - index >= 21) {
                    trim = 0;
                    rowNum = 3;

                } else{
                    rowNum = ((int) total / 7);
                    trim = ((int) total % 7);

                }
                //adds all full rows
                for (int row = 1; row <= rowNum; row++) {
                    OmarLifeSteal.PLAYER_LIST_INVS[i].setSlots((row * 9) + 1, (row * 9) + 7, new ItemStack(Material.REDSTONE));
                }
                //adds final row as trim
                OmarLifeSteal.PLAYER_LIST_INVS[i].setSlots(((rowNum + 1) * 9) + 1, ((rowNum + 1) * 9) + trim, new ItemStack(Material.REDSTONE));
            }

        }, 0L, 20L);
    }


}

