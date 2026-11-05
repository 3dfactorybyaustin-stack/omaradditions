package org.omaresz.omaradditions;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;


public final class OmarLifeSteal extends JavaPlugin {
    //files
    public static BannedPlayerList BANNED_PLAYERLIST;
    //variables
    public static final String PREFIX = "<dark_grey><bold>[<#a554f4>O<#9e55ef>M<#9856ea>A<#9157e5>R<#8a57df>A<#8358da>D<#7d59d5>D<#765ad0>I<#6f5bcb>T<#685cc6>I<#625cc0>O<#5b5dbb>N<#545eb6>S<dark_grey><bold>]<white> - <grey>";
    public static final NamespacedKey HEART_KEY = new NamespacedKey("omaradditions", "heart");
    public static final NamespacedKey CANCEL_ClICK = new NamespacedKey("omaradditrions", "inventory");
    public static final ItemStack HEART_ITEM = OmarInventory.createItem(Material.ENCHANTED_GOLDEN_APPLE, itemMeta -> {
        itemMeta.displayName(MiniMessage.miniMessage().deserialize("<!italic><red><bold>HEART"));
        itemMeta.getPersistentDataContainer().set(HEART_KEY, PersistentDataType.BOOLEAN, Boolean.TRUE);
    });
    public static final ItemStack UNBAN_ITEM = OmarInventory.createItem(Material.DIAMOND, itemMeta -> {
        itemMeta.displayName(MiniMessage.miniMessage().deserialize("<!italic><blue><bold>RESPAWN TOKEN"));
        itemMeta.setMaxStackSize(1);
    });

    public ShapedRecipe UNBAN_ITEM_RECIPE = new ShapedRecipe(new NamespacedKey(this, "revive_token"), UNBAN_ITEM);

    //inventories
    public static final OmarInventory MAIN_INV = new OmarInventory("<#7E9398>O<#7B8F9B>M<#778A9D>A<#7486A0>R<#7081A2>L<#6D7DA5>I<#6979A7>F<#6674AA>E<#6270AC>S<#5F6BAF>T<#5B67B1>E<#5862B4>A<#545EB6>L");
    public static final OmarInventory BANNED_PLAYERS_INVS[] = new OmarInventory[11];
    public static OmarInventory PLAYER_LIST_INVS[] = new OmarInventory[11];


    //inventory variables
    public static final ItemStack BORDER_ITEM = OmarInventory.createItem(Material.BLACK_STAINED_GLASS_PANE, itemMeta -> {
        itemMeta.displayName(MiniMessage.miniMessage().deserialize(""));
    });



    public static final ItemStack BANNED_PLAYER_LIST_ITEM = OmarInventory.createItem(Material.PLAYER_HEAD, itemMeta -> {
        itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><bold><!italic>Banned Players"));
    });
    public static final int PLAYER_LIST_SLOT = 11;


    public static final ItemStack PLAYER_LIST_ITEM = OmarInventory.createItem(Material.PLAYER_HEAD, itemMeta -> {
        itemMeta.displayName(MiniMessage.miniMessage().deserialize("<red><bold><!italic>Player List"));
    });
    public static final int BANNED_PLAYER_LIST_SLOT = 15;








    @Override
    public void onEnable() {
    BANNED_PLAYERLIST = new BannedPlayerList(this);


        //recipes
        /*
        UNBAN_ITEM_RECIPE.addIngredient(2, new ItemStack(Material.DIAMOND_BLOCK));
        UNBAN_ITEM_RECIPE.addIngredient(1, new ItemStack(Material.NETHER_STAR));
        UNBAN_ITEM_RECIPE.addIngredient(1, HEAR_ITEM);
        */
        UNBAN_ITEM_RECIPE.shape(" A ","ABA", " A ");
        UNBAN_ITEM_RECIPE.setIngredient('A', new ItemStack(Material.DIAMOND_BLOCK));
        UNBAN_ITEM_RECIPE.setIngredient('B', new RecipeChoice.ExactChoice(HEART_ITEM));
        this.getServer().addRecipe(UNBAN_ITEM_RECIPE);


        //main inventory


        MAIN_INV.border(BORDER_ITEM);
        MAIN_INV.setSlot(BANNED_PLAYER_LIST_SLOT, BANNED_PLAYER_LIST_ITEM);
        MAIN_INV.setSlot(PLAYER_LIST_SLOT, PLAYER_LIST_ITEM);



        //playerlist inventory


        this.registerCommand("Debug", new DebugCommand());
        this.registerCommand("LifeStealGui", List.of("lifestealgui", "lsg"),  new LifeStealInventories());
        this.registerCommand("WithdrawalHeart", List.of("withdrawalheart"), new LifeStealWithdrawHeartsCommand());
        this.registerCommand("UnbanPlayer", List.of("unbanplayer", "revive"), new UnBanPlayer());
        this.getServer().getPluginManager().registerEvents(new LifeStealListener(), this);
        this.getServer().getPluginManager().registerEvents(new LifeStealInventories(), this);
        this.getServer().getPluginManager().registerEvents(new OmarInventory("none"), this);

        LifeStealInventories.updateGUILoop(this);
        //banned players inventory
        for (int i = 0; i < 11; i++){
            BANNED_PLAYERS_INVS[i] = new OmarInventory(4, "<#7E9398>O<#7B8F9A>N<#788B9D>L<#74879F>I<#7183A1>N<#6E7FA4>E <#6776A8>P<#6472AA>L<#616EAD>A<#5E6AAF>Y<#5A66B1>E<#5762B4>R<#545EB6>S<grey> (" + (i + 1) + ")", i);
            BANNED_PLAYERS_INVS[i].border(BORDER_ITEM);
        }

        //active playerlist inventpry
        for (int i = 0; i < 11; i++){
            PLAYER_LIST_INVS[i] = new OmarInventory(5, "<#7E9398>O<#7B8F9A>N<#788B9D>L<#74879F>I<#7183A1>N<#6E7FA4>E <#6776A8>P<#6472AA>L<#616EAD>A<#5E6AAF>Y<#5A66B1>E<#5762B4>R<#545EB6>S<grey> (" + (i + 1) + ")", i);
            PLAYER_LIST_INVS[i].border(BORDER_ITEM);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        BANNED_PLAYERLIST.save();
    }
}
