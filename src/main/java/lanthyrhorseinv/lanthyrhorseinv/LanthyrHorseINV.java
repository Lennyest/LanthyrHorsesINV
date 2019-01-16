package lanthyrhorseinv.lanthyrhorseinv;
import com.github.zedd7.zhorse.ZHorse;
import lanthyrhorseinv.lanthyrhorseinv.commands.HorseInventory;
import lanthyrhorseinv.lanthyrhorseinv.events.PlayerInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public final class LanthyrHorseINV extends JavaPlugin {

    static LanthyrHorseINV plugin;
    private static final ZHorse zHorse = (ZHorse) Arrays.stream(Bukkit.getServer().getPluginManager().getPlugins()).filter(p -> p.getName().equalsIgnoreCase("ZHorse")).findFirst().orElse(null);
    public static ZHorse returnHorse() {
        return zHorse;
    }

    public LanthyrHorseINV() {
        plugin = this;
    }
    public static LanthyrHorseINV getPlugin() {
        return plugin;
    }



    public File inventoryFile = new File(getDataFolder() + File.separator + "inventory.yml");
    public FileConfiguration cfg = new YamlConfiguration();



    @Override
    public void onEnable() {
        createConfig();
        getCommand("HorseInventory").setExecutor(new HorseInventory());
        getServer().getPluginManager().registerEvents(new HorseInventory(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractEvent(), this);
    }

    private void createConfig() {

        try {
            cfg.load(inventoryFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        if (!inventoryFile.exists()) {
            inventoryFile.getParentFile().mkdirs();
            saveResource("inventory.yml", false);
        }

        try {
            cfg.load(inventoryFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
