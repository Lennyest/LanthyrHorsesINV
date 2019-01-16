package lanthyrhorseinv.lanthyrhorseinv.events;

import lanthyrhorseinv.lanthyrhorseinv.LanthyrHorseINV;
import lanthyrhorseinv.lanthyrhorseinv.Serialization.InvSerialization;
import lanthyrhorseinv.lanthyrhorseinv.Serialization.Serialization;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.thane.NMSUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

import static lanthyrhorseinv.lanthyrhorseinv.LanthyrHorseINV.returnHorse;

public class PlayerInteractEvent implements Listener {

    private static Inventory horseInventory = Bukkit.createInventory(null, 36, ChatColor.GREEN + "Horse Inventory");

    @EventHandler
    public void horseInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getRightClicked() instanceof AbstractHorse) {
            if (player.isSneaking() && event.getRightClicked().getType().equals(EntityType.HORSE)) {
                AbstractHorse horse = (AbstractHorse) event.getRightClicked();
                if (returnHorse().getDM().isHorseOwnedBy(player.getUniqueId(), horse.getUniqueId())) {
                    event.setCancelled(true);
                    player.openInventory(horseInventory);
                } else {
                    player.sendMessage(ChatColor.RED + "You do not own this horse.");
                    event.setCancelled(true);
                }
            }
        }
    }


    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {
        if (!event.getInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Horse Inventory")) {
            return;
        }

        Material clicked = event.getCurrentItem().getType();
        HumanEntity player = event.getWhoClicked();

        if (event.getClickedInventory() == null || !event.getCurrentItem().hasItemMeta()) {
            return;
        }

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        if ((event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "NOTICE!")) && (clicked.equals(Material.PAPER))
                ||
                (clicked.equals(Material.STAINED_GLASS_PANE)) && (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(" "))) {
            event.setCancelled(true);
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.RED + "NOTICE!")) {
            player.sendMessage(ChatColor.WHITE + "Discord: " + ChatColor.BLUE + "Lenny#5713");
            player.sendMessage(ChatColor.WHITE + "Plugin made by GreatThane and Laleem/Lenny");
        }
    }

    @EventHandler
    public void inventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Horse Inventory")) {
            Player player = (Player) event.getPlayer();

            ItemStack glasspane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
            ItemMeta glasspaneMeta = glasspane.getItemMeta();
            glasspaneMeta.setDisplayName(" ");
            glasspane.setItemMeta(glasspaneMeta);

            ItemStack note = new ItemStack(Material.PAPER, 1);
            ItemMeta noteM = note.getItemMeta();
            noteM.setDisplayName(ChatColor.RED + "NOTICE!");
            ArrayList lore = new ArrayList<>();
            lore.add(ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "-----------------------------------");
            lore.add(ChatColor.GREEN + "Do not add anything you don't mind losing!");
            lore.add(ChatColor.GREEN + "Shulker-boxes and potions have been blocked.");
            lore.add(ChatColor.GREEN + "This system is currently still being tested");
            lore.add(ChatColor.GREEN + "Found any bad bugs? Click me to get contact links.");
            lore.add(ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");
            noteM.setLore(lore);
            note.setItemMeta(noteM);

            ItemStack[] contents = InvSerialization.toInventory(LanthyrHorseINV.getPlugin().cfg, "inventory." + player.getName()).getContents();
            horseInventory.setContents(contents);

            horseInventory.setItem(0, glasspane);
            horseInventory.setItem(1, glasspane);
            horseInventory.setItem(2, glasspane);
            horseInventory.setItem(3, glasspane);
            horseInventory.setItem(4, glasspane);
            horseInventory.setItem(5, glasspane);
            horseInventory.setItem(6, glasspane);
            horseInventory.setItem(7, glasspane);
            horseInventory.setItem(8, glasspane);
            horseInventory.setItem(9, glasspane);
            horseInventory.setItem(17, glasspane);
            horseInventory.setItem(18, glasspane);
            horseInventory.setItem(26, glasspane);
            horseInventory.setItem(27, glasspane);
            horseInventory.setItem(28, glasspane);
            horseInventory.setItem(29, glasspane);
            horseInventory.setItem(30, glasspane);
            horseInventory.setItem(31, note);
            horseInventory.setItem(32, glasspane);
            horseInventory.setItem(33, glasspane);
            horseInventory.setItem(34, glasspane);
            horseInventory.setItem(35, glasspane);
        }
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Horse Inventory")) {
            Player player = (Player) event.getPlayer();
            InvSerialization.saveInventory(event.getInventory(), LanthyrHorseINV.getPlugin().cfg, "inventory." + player.getName());

            try {
                LanthyrHorseINV.getPlugin().cfg.save(LanthyrHorseINV.getPlugin().inventoryFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                LanthyrHorseINV.getPlugin().cfg.load(LanthyrHorseINV.getPlugin().inventoryFile);
            } catch (IOException | InvalidConfigurationException e1) {
                e1.printStackTrace();
            }
        }
    }
}
