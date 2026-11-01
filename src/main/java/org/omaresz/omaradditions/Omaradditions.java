package org.omaresz.omaradditions;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public final class Omaradditions extends JavaPlugin {
    //variables
    public static final String PREFIX = "<dark_grey><bold>[<#a554f4>O<#9e55ef>M<#9856ea>A<#9157e5>R<#8a57df>A<#8358da>D<#7d59d5>D<#765ad0>I<#6f5bcb>T<#685cc6>I<#625cc0>O<#5b5dbb>N<#545eb6>S<dark_grey><bold>]<white> - <grey>";
    public static final NamespacedKey HEART_KEY = new NamespacedKey("omaradditions", "heart");
    public static final NamespacedKey CANCEL_ClICK = new NamespacedKey("omaradditrions", "inventory");

    public static final OmarInventory MAIN_INV = new OmarInventory();
    public static final OmarInventory PLAYER_LIST_INV = new OmarInventory(5);







    //inventory variables
    public static final ItemStack BORDER_ITEM = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);








    @Override
    public void onEnable() {
        // Plugin startup logic

        //main inventory
        MAIN_INV.border(BORDER_ITEM);
        MAIN_INV.setSlot(26, new ItemStack(Material.REDSTONE_BLOCK));
        MAIN_INV.setSlot(LifeStealInventories.PLAYER_LIST_SLOT, new ItemStack(Material.PLAYER_HEAD));


        //player list inventory

        //border
        PLAYER_LIST_INV.border(BORDER_ITEM);



        this.registerCommand("LifeStealGui", new LifeStealInventories());
        this.registerCommand("breakStuff", new coolCommand());
        this.registerCommand("debug", new DebugCommand());
        this.registerCommand("WithdrawalHeart", new WithdrawalHeartsCommand());
        this.getServer().getPluginManager().registerEvents(new LifeStealListener(), this);
        this.getServer().getPluginManager().registerEvents(new LifeStealInventories(), this);
        this.getServer().getPluginManager().registerEvents(new OmarInventory(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
