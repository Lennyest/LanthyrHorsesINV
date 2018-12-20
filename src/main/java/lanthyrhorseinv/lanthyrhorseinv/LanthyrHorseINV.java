package lanthyrhorseinv.lanthyrhorseinv;
import com.github.zedd7.zhorse.ZHorse;
import lanthyrhorseinv.lanthyrhorseinv.commands.HorseInventory;
import lanthyrhorseinv.lanthyrhorseinv.events.PlayerInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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

    @Override
    public void onEnable() {
        getCommand("HorseInventory").setExecutor(new HorseInventory());
        getServer().getPluginManager().registerEvents(new PlayerInteractEvent(), this);
    }

}
