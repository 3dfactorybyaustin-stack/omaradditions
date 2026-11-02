package org.omaresz.omaradditions;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.function.Consumer;



public class OmarInventory implements InventoryHolder, Listener {
    private Inventory inventory;
    private int pageIndex = 0;

    public OmarInventory(String title) {
        inventory = Bukkit.createInventory(this, 27, MiniMessage.miniMessage().deserialize(title));
    }

    public OmarInventory(int num, String title) {
        inventory = Bukkit.createInventory(this, num * 9, MiniMessage.miniMessage().deserialize(title));
    }

    public OmarInventory(int num, String title, int page) {
        inventory = Bukkit.createInventory(this, num * 9, MiniMessage.miniMessage().deserialize(title));
        pageIndex = page;
    }


    // set methods
    public void setSlot(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }




    public void setSlots(int first, int second, ItemStack item) {
        for (int i = first; i <= second; i++) {
            this.setSlot(i, item);
        }
    }


    public void border(ItemStack itemStack) {
        this.setSlots(0, 8, OmarLifeSteal.BORDER_ITEM);
        this.setSlots(inventory.getSize()-9, inventory.getSize()-1, OmarLifeSteal.BORDER_ITEM);
        for (int i = 1; i <= (inventory.getSize() / 9) - 2; i++) {
            this.setSlot(i*9, OmarLifeSteal.BORDER_ITEM);
            this.setSlot(i * 9 + 8, OmarLifeSteal.BORDER_ITEM);
        }
    }

    //get methods
    public int getPageIndex() {
        return pageIndex;
    }


    //helper method for creating inventory items
    public static ItemStack createItem(Material item, Consumer<ItemMeta> meta) {
        ItemStack temp = new ItemStack(item);
        if (meta != null) temp.editMeta(meta);

        return temp;

    }
    //helper method for checking inventory arrays
    public static boolean checkInventoryArray(OmarInventory[] arrays, Inventory inventory) {
        for (OmarInventory inv : arrays) {
            if (inventory.equals(inv.getInventory())) return true;
        }
        return false;
    }


    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }


    //cancels all clicked inventories created by this class
    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if (!(event.getInventory().getHolder() instanceof OmarInventory)) return;
        if (!(event.getClick().equals(ClickType.LEFT) || event.getClick().equals(ClickType.RIGHT))) event.setCancelled(true);
        if (event.getClickedInventory() == null) return;
        if (!(event.getClickedInventory().getHolder() instanceof OmarInventory)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof OmarInventory) event.setCancelled(true);
    }



}
