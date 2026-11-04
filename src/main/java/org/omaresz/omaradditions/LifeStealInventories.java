package org.omaresz.omaradditions;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.UUID;


public class LifeStealInventories implements BasicCommand, Listener {
    public static int BACK_BUTTON_LOC_ONLINE = 42;
    public static int BACK_BUTTON_LOC_BANNED = 33;
    public static ItemStack BACK_BUTTON_ITEM = OmarInventory.createItem(Material.REDSTONE_BLOCK, itemMeta -> itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><!italic>Back")));
    public static int NEXT_BUTTON_LOC_ONLINE = 43;
    public static int NEXT_BUTTON_LOC_BANNED = 34;
    public static ItemStack NEXT_BUTTON_ITEM = OmarInventory.createItem(Material.EMERALD_BLOCK, itemMeta -> itemMeta.displayName(MiniMessage.miniMessage().deserialize("<green><!italic>Next")));
    private static int playerListLength;
    private static int bannedListLength;


    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        Player player = (Player) commandSourceStack.getExecutor();
        player.openInventory(OmarLifeSteal.MAIN_INV.getInventory());
    }


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize("" ));
        int slot = event.getSlot();
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(" " + slot));
        if (event.getInventory().equals(OmarLifeSteal.MAIN_INV.getInventory())) {

            if (slot == OmarLifeSteal.BANNED_PLAYER_LIST_SLOT) event.getWhoClicked().openInventory(OmarLifeSteal.BANNED_PLAYERS_INVS[0].getInventory());
            else if (slot == OmarLifeSteal.PLAYER_LIST_SLOT) event.getWhoClicked().openInventory(OmarLifeSteal.PLAYER_LIST_INVS[0].getInventory());




        } else if (OmarInventory.checkInventoryArray(OmarLifeSteal.PLAYER_LIST_INVS, event.getInventory())) {
            OmarInventory temp = (OmarInventory) event.getInventory().getHolder();
            if (slot == BACK_BUTTON_LOC_ONLINE) {

                if (event.getInventory().equals(OmarLifeSteal.PLAYER_LIST_INVS[0].getInventory())) event.getWhoClicked().openInventory(OmarLifeSteal.MAIN_INV.getInventory());
                else event.getWhoClicked().openInventory(OmarLifeSteal.PLAYER_LIST_INVS[temp.getPageIndex() - 1].getInventory());

            } else if (slot == NEXT_BUTTON_LOC_ONLINE) {
                if (temp.getPageIndex() == playerListLength) return;
                event.getWhoClicked().openInventory(OmarLifeSteal.PLAYER_LIST_INVS[temp.getPageIndex() + 1].getInventory());
            }
        } else if (OmarInventory.checkInventoryArray(OmarLifeSteal.BANNED_PLAYERS_INVS, event.getInventory())) {
            OmarInventory temp = (OmarInventory) event.getInventory().getHolder();
            if (slot == BACK_BUTTON_LOC_BANNED) {

                if (event.getInventory().equals(OmarLifeSteal.BANNED_PLAYERS_INVS[0].getInventory())) event.getWhoClicked().openInventory(OmarLifeSteal.MAIN_INV.getInventory());
                else event.getWhoClicked().openInventory(OmarLifeSteal.BANNED_PLAYERS_INVS[temp.getPageIndex() - 1].getInventory());

            } else if (slot == NEXT_BUTTON_LOC_BANNED) {
                if (temp.getPageIndex() == bannedListLength) return;
                event.getWhoClicked().openInventory(OmarLifeSteal.BANNED_PLAYERS_INVS[temp.getPageIndex() + 1].getInventory());
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
                OmarLifeSteal.PLAYER_LIST_INVS[i].setSlot(BACK_BUTTON_LOC_ONLINE, BACK_BUTTON_ITEM);
                if (i != playerListLength) OmarLifeSteal.PLAYER_LIST_INVS[i].setSlot(NEXT_BUTTON_LOC_ONLINE, NEXT_BUTTON_ITEM);
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
                //Bukkit.broadcast(MiniMessage.miniMessage().deserialize("rowNum" + rowNum));new ItemStack(Material.REDSTONE)
                //adds all full rows
                //Bukkit.broadcast(MiniMessage.miniMessage().deserialize("index: " + index));
                for (int row = 1; row <= rowNum; row++) {
                    for (int x = 0; x < 7; x++) {
                        Player p = players[index];
                        OmarLifeSteal.PLAYER_LIST_INVS[i].setSlot((row * 9) + 1 + x, OmarInventory.createItem(Material.PAPER, itemMeta -> itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><!italic>" + p.getName() + "<grey>(" + p.getMaxHealth()/2 + "❤)"))));
                        index ++;
                        //Bukkit.broadcast(MiniMessage.miniMessage().deserialize("index++"));

                    }
                }
                //adds final row as trim
                for (int x = 0; x < trim; x++) {
                    Player p = players[index];
                    OmarLifeSteal.PLAYER_LIST_INVS[i].setSlot(((rowNum + 1) * 9) + 1 + x, OmarInventory.createItem(Material.PAPER, itemMeta -> itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><!italic>" + p.getName() + "<grey>(" + p.getMaxHealth()/2 + "❤)"))));
                    //Bukkit.broadcast(MiniMessage.miniMessage().deserialize("index++"));
                    index ++;
                }

            }
        //banned playerlist

            total = OmarLifeSteal.BANNED_PLAYERLIST.getPlayerList().size();
            inventoryNum = (int) Math.ceil(((double) total / 7));
            index = 0;
            List<String> uuidList = OmarLifeSteal.BANNED_PLAYERLIST.getPlayerList();
            bannedListLength = inventoryNum - 1;
            OfflinePlayer p;
            OmarLifeSteal.BANNED_PLAYERS_INVS[0].setSlot(BACK_BUTTON_LOC_BANNED, BACK_BUTTON_ITEM);
            OmarLifeSteal.BANNED_PLAYERS_INVS[0].setSlots(10, 16, new ItemStack(Material.AIR));
            OmarLifeSteal.BANNED_PLAYERS_INVS[0].setSlots(19, 25, new ItemStack(Material.AIR));
            for (int i = 0; i < inventoryNum; i++) {
                OmarLifeSteal.BANNED_PLAYERS_INVS[i].setSlots(10, 16, new ItemStack(Material.AIR));
                OmarLifeSteal.BANNED_PLAYERS_INVS[i].setSlots(19, 25, new ItemStack(Material.AIR));
                OmarLifeSteal.BANNED_PLAYERS_INVS[i].setSlot(BACK_BUTTON_LOC_BANNED, BACK_BUTTON_ITEM);
                if (OmarLifeSteal.BANNED_PLAYERS_INVS[i].getPageIndex() != bannedListLength) {
                    OmarLifeSteal.BANNED_PLAYERS_INVS[i].setSlot(NEXT_BUTTON_LOC_BANNED, NEXT_BUTTON_ITEM);
                }
                if (total - index >= 7) {
                    trim = 0;
                    rowNum =1;

                } else{
                    rowNum = 0;
                    trim = total - index;
                }
                if (rowNum == 1) {
                    for (int x = 0; x < 7; x++) {
                        int temp = index;
                        index++;
                        OmarLifeSteal.BANNED_PLAYERS_INVS[i].setSlot(x + 10, OmarInventory.createItem(Material.PAPER, itemMeta -> itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><!italic>" + Bukkit.getOfflinePlayer(UUID.fromString(uuidList.get(temp))).getName()))));
                        OmarLifeSteal.BANNED_PLAYERS_INVS[i].setSlot(x+ 19, OmarInventory.createItem(Material.RED_STAINED_GLASS_PANE, itemMeta -> {
                            itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><!italic>Click To Unban"));
                            itemMeta.lore(List.of(MiniMessage.miniMessage().deserialize("<red><!italic>Requires Revive Token")));
                        }));
                    }

                }else{
                    for (int x = 0; x < trim; x++) {
                        int temp = index;
                        index++;
                        OmarLifeSteal.BANNED_PLAYERS_INVS[i].setSlot(x + 10, OmarInventory.createItem(Material.PAPER, itemMeta -> itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><!italic>" + Bukkit.getOfflinePlayer(UUID.fromString(uuidList.get(temp))).getName()))));
                        OmarLifeSteal.BANNED_PLAYERS_INVS[i].setSlot(x+ 19, OmarInventory.createItem(Material.RED_STAINED_GLASS_PANE, itemMeta -> {
                            itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><!italic>Click To Unban"));
                            itemMeta.lore(List.of(MiniMessage.miniMessage().deserialize("<red><!italic>Requires Revive Token")));
                        }));
                    }
                }
            }
        }, 0L, 20L);
    }



    public static void broadcast(String s) {
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(" broadcast: " + s));

    }
    public static void broadcast(int s) {
        Bukkit.broadcast(MiniMessage.miniMessage().deserialize(" broadcast: " + s));

    }
}

