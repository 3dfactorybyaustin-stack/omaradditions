package org.omaresz.omaradditions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;


public class LifeStealInventories implements BasicCommand, Listener {
    static int PLAYER_LIST_SLOT = 15;
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getExecutor();
        player.openInventory(Omaradditions.MAIN_INV.getInventory());
    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().equals(Omaradditions.MAIN_INV.getInventory())) {


            if (event.getClick().equals(ClickType.LEFT) || event.getClick().equals(ClickType.RIGHT)) {
                if (event.getSlot() == PLAYER_LIST_SLOT) event.getWhoClicked().openInventory(Omaradditions.PLAYER_LIST_INV.getInventory());


            }
        }
    }


}

