package lanthyrhorseinv.lanthyrhorseinv.commands;

import lanthyrhorseinv.lanthyrhorseinv.LanthyrHorseINV;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.thane.NMSUtils;

import java.io.*;
import java.nio.file.Files;

public class HorseInventory implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("HorseInventory")) {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayerExact(args[1]);

            switch (args.length) {
                case 0:
                    if (player.hasPermission("HorseInventory.help")) {
                        player.sendMessage(ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------------------------");
                        player.sendMessage(ChatColor.GREEN + ">" + ChatColor.GRAY + " /HorseInventory view <player>");
                        player.sendMessage(ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------------------------");
                    }
                    return true;
                case 2:
                    if (player.hasPermission("HorseInventory.view")) {
                        Inventory inv = Bukkit.createInventory(null, 36, ChatColor.GREEN + "Player Inventory");

                        File file = new File(LanthyrHorseINV.getPlugin().getDataFolder().getAbsolutePath() + File.separatorChar + target.getUniqueId() + ".json");
                            if (file.exists()) {
                                ItemStack[] contents = new ItemStack[0];
                                try {
                                    contents = NMSUtils.getGson().fromJson(new String(Files.readAllBytes(file.toPath())), ItemStack[].class);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                inv.setContents(contents);

                            player.openInventory(inv);
                        } else {
                            player.sendMessage(ChatColor.RED + "The targetted player does not have an inventory file.");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "You're missing the required permission node, couldn't execute command.");
                    }
                    return true;
                default:
                    player.sendMessage(ChatColor.RED + "Wrong usage! type /HorseInventory to view usage.");
                    return true;
            }
        }
        return false;
    }

    @EventHandler
    public void playerOpen(InventoryOpenEvent event) {
        if (event.getInventory().getTitle().equalsIgnoreCase(ChatColor.GREEN + "Player Inventory")) {
            event.setCancelled(true);
        }
    }
}
