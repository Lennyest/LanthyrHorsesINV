package lanthyrhorseinv.lanthyrhorseinv.commands;

import lanthyrhorseinv.lanthyrhorseinv.LanthyrHorseINV;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.thane.NMSUtils;

import java.io.*;
import java.nio.file.Files;

public class HorseInventory implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player target = Bukkit.getPlayerExact(args[1]);
        if (command.getName().equalsIgnoreCase("HorseInventory")) {
            Player player = (Player) sender;
            Inventory inv = Bukkit.createInventory(null, 36, ChatColor.GREEN + "Horse Inventory");

            switch (args.length) {
                case 0:
                    if (player.hasPermission("HorseInventory.help")) {
                        player.sendMessage(ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------------------------");
                        player.sendMessage(ChatColor.GREEN + ">" + ChatColor.GRAY + " /HorseInventory view <player>");
                        player.sendMessage(ChatColor.WHITE + "" + ChatColor.STRIKETHROUGH + "----------------------------------");
                    }
                    return true;
/*                case 1:
                    player.sendMessage(ChatColor.RED + "Wrong usage! type /HorseInventory to view usage.");
                    return true;*/
                case 2:
                    if (player.hasPermission("HorseInventory.view")) {
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
}
