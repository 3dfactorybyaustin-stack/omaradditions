package org.omaresz.omaradditions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;


public class LifeStealInventories implements BasicCommand, Listener {
    public static int BACK_BUTTON_LOC = 42;
    public static ItemStack BACK_BUTTON_ITEM = OmarInventory.createItem(Material.REDSTONE_BLOCK, itemMeta -> itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><!italic>Back")));
    public static int NEXT_BUTTON_LOC = 43;
    public static ItemStack NEXT_BUTTON_ITEM = OmarInventory.createItem(Material.EMERALD_BLOCK, itemMeta -> itemMeta.displayName(MiniMessage.miniMessage().deserialize("<green><!italic>Next")));
    private static int playerListLength;


    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getExecutor();
        player.openInventory(OmarLifeSteal.MAIN_INV.getInventory());
    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        int slot = event.getSlot();
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(" " + slot));
        if (event.getInventory().equals(OmarLifeSteal.MAIN_INV.getInventory())) {

            if (slot == OmarLifeSteal.BANNED_PLAYER_LIST_SLOT) event.getWhoClicked().openInventory(OmarLifeSteal.BANNED_PLAYERS_INV.getInventory());
            else if (slot == OmarLifeSteal.PLAYER_LIST_SLOT) event.getWhoClicked().openInventory(OmarLifeSteal.PLAYER_LIST_INVS[0].getInventory());




        } else if (OmarInventory.checkInventoryArray(OmarLifeSteal.PLAYER_LIST_INVS, event.getInventory())) {
            OmarInventory temp = (OmarInventory) event.getInventory().getHolder();
            if (slot == BACK_BUTTON_LOC) {

                if (event.getInventory().equals(OmarLifeSteal.PLAYER_LIST_INVS[0].getInventory())) event.getWhoClicked().openInventory(OmarLifeSteal.MAIN_INV.getInventory());
                else event.getWhoClicked().openInventory(OmarLifeSteal.PLAYER_LIST_INVS[temp.getPageIndex() - 1].getInventory());

            } else if (slot == NEXT_BUTTON_LOC) {
                if (temp.getPageIndex() == playerListLength) return;
                event.getWhoClicked().openInventory(OmarLifeSteal.PLAYER_LIST_INVS[temp.getPageIndex() + 1].getInventory());
            }
        }
    }



    public static void updateGUILoop(Plugin plugin) {

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            int total = Bukkit.getOnlinePlayers().size();
            int inventoryNum = (int) Math.ceil(((double) total / 21));
            int rowNum;
            int trim;
            int index = 0;
            Player players[] = Bukkit.getOnlinePlayers().toArray(new Player[0]);

            //Bukkit.broadcast(MiniMessage.miniMessage().deserialize(" " + inventoryNum));
            playerListLength = inventoryNum - 1;
            for (int i = 0; i < inventoryNum; i++) {


                OmarLifeSteal.PLAYER_LIST_INVS[i].setSlots(10, 16, new ItemStack(Material.AIR));
                OmarLifeSteal.PLAYER_LIST_INVS[i].setSlots(19, 25, new ItemStack(Material.AIR));
                OmarLifeSteal.PLAYER_LIST_INVS[i].setSlots(28, 34, new ItemStack(Material.AIR));
                OmarLifeSteal.PLAYER_LIST_INVS[i].setSlot(BACK_BUTTON_LOC, BACK_BUTTON_ITEM);
                if (i != playerListLength) OmarLifeSteal.PLAYER_LIST_INVS[i].setSlot(NEXT_BUTTON_LOC, NEXT_BUTTON_ITEM);
            }

            //loops for each inventory
            //Bukkit.broadcast(MiniMessage.miniMessage().deserialize("--------"));

            for (int i = 0; i <= inventoryNum-1; i++) {
                if (total - index >= 21) {
                    trim = 0;
                    rowNum = 3;

                } else{
                    rowNum = ((int) (total - index) / 7);
                    trim = ((int) (total - index) % 7);

                }
                //Bukkit.broadcast(MiniMessage.miniMessage().deserialize("trim" + trim));
                //Bukkit.broadcast(MiniMessage.miniMessage().deserialize("rowNum" + rowNum));
                //adds all full rows
                Bukkit.broadcast(MiniMessage.miniMessage().deserialize("index: " + index));
                for (int row = 1; row <= rowNum; row++) {
                    for (int x = 0; x < 7; x++) {
                        Player p = players[index];
                        OmarLifeSteal.PLAYER_LIST_INVS[i].setSlot((row * 9) + 1 + x, OmarInventory.createItem(Material.PAPER, itemMeta -> itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><!italic>" + p.getName() + "<grey>(" + p.getMaxHealth()/2 + "❤)"))));
                        index ++;
                        Bukkit.broadcast(MiniMessage.miniMessage().deserialize("index++"));

                    }
                }
                //adds final row as trim
                for (int x = 0; x < trim; x++) {
                    Player p = players[index];
                    OmarLifeSteal.PLAYER_LIST_INVS[i].setSlot(((rowNum + 1) * 9) + 1 + x, OmarInventory.createItem(Material.PAPER, itemMeta -> itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><!italic>" + p.getName() + "<grey>(" + p.getMaxHealth()/2 + "❤)"))));
                    Bukkit.broadcast(MiniMessage.miniMessage().deserialize("index++"));
                    index ++;
                }

            }

        }, 0L, 20L);
    }


}

