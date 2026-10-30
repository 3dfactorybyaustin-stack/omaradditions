package org.omaresz.omaradditions;

import org.bukkit.plugin.java.JavaPlugin;

public final class Omaradditions extends JavaPlugin {
    public static final String PREFIX = "<dark_grey><bold>[<#a554f4>O<#9e55ef>M<#9856ea>A<#9157e5>R<#8a57df>A<#8358da>D<#7d59d5>D<#765ad0>I<#6f5bcb>T<#685cc6>I<#625cc0>O<#5b5dbb>N<#545eb6>S<dark_grey><bold>]<white> - <grey>";
    @Override
    public void onEnable() {
        // Plugin startup logic


        this.registerCommand("breakStuff", new coolCommand());
        this.registerCommand("debug", new DebugCommand());
        this.registerCommand("WithdrawalHearts", new WithdrawalHeartsCommand());
        this.getServer().getPluginManager().registerEvents(new LifeStealListener(), this);
    
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
