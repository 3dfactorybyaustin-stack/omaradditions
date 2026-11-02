package org.omaresz.omaradditions;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;

public class BannedPlayerList {
    private final Plugin plugin;
    private final File file;
    private final YamlConfiguration yml;

    public BannedPlayerList(Plugin plugin) {
        this.plugin = (OmarLifeSteal) plugin;
        file = new File(plugin.getDataFolder(), "bannedPlayers.yml");
        yml = YamlConfiguration.loadConfiguration(file);
        plugin.getDataFolder().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                getLogger().severe("Could not create bannedPlayers.yml: " + e.getMessage());
            }
        }


    }
    public List<UUID> getPlayerList() {
        List<UUID> temp = new ArrayList<>();
        for (String s : yml.getStringList("playerList")) {
            temp.add(UUID.fromString(s));
        }
        return temp;
    }

    public void addPlayer(Player p) {
        yml.set("playerList", yml.getStringList("playerList").add(p.getUniqueId().toString()));
    }

    public void addString(String s) {
        yml.getStringList("playerList").add(s);

    }

    public void save() {
        try {
            yml.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


