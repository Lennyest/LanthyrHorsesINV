package lanthyrhorseinv.lanthyrhorseinv.Serialization;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

public class InvSerialization {
    public static Inventory toInventory(FileConfiguration config, String path) {
        Inventory in = Bukkit.createInventory(null, 36);
        for (int i = 0; i<27;i++) {
            if (config.isItemStack(path + "." + i)) {
                in.setItem(i, config.getItemStack(path + "." + i));
            }
        }
        return in;
    }

    public static void saveInventory(Inventory inv, FileConfiguration config, String path) {
        for (int i = 0; i<inv.getSize();i++) {
            if (inv.getItem(i)!=null) {
                config.set(path + "." + i, inv.getItem(i));
            } else {
                if (config.isItemStack(path + "." + i)) {
                    config.set(path + "." + i, null);
                }
            }
        }
    }
}
