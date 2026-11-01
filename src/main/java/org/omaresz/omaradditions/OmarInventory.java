package org.omaresz.omaradditions;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class OmarInventory implements InventoryHolder, Listener {
    private final Inventory inventory;

    public OmarInventory() {
        inventory = Bukkit.createInventory(this, 27);
    }

    public OmarInventory(int num) {
        inventory = Bukkit.createInventory(this, num * 9);
    }

    public void setSlot(int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    public void setSlots(int first, int second, ItemStack item) {
        for (int i = first; i <= second; i++) {
            this.setSlot(i, item);
        }
    }

    public void border(ItemStack itemStack) {
        this.setSlots(0, 8, Omaradditions.BORDER_ITEM);
        this.setSlots(inventory.getSize()-9, inventory.getSize()-1, Omaradditions.BORDER_ITEM);
        for (int i = 1; i <= (inventory.getSize() / 9) - 2; i++) {
            this.setSlot(i*9, Omaradditions.BORDER_ITEM);
            this.setSlot(i * 9 + 8, Omaradditions.BORDER_ITEM);
        }
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
