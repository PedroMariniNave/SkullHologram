package com.zpedroo.skullhologram.commands;

import com.zpedroo.skullhologram.utils.config.Item;
import com.zpedroo.skullhologram.utils.config.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SkullHologramCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        if (args.length <= 1) {
            player.sendMessage(Messages.COMMAND_USAGE);
            return true;
        }

        String playerSkullName = args[0];
        StringBuilder text = new StringBuilder(args.length);

        for (int i = 1; i < args.length; ++i) {
            if (text.length() > 0) text.append(" ");

            text.append(ChatColor.translateAlternateColorCodes('&', args[i]));
        }

        ItemStack item = Item.get(playerSkullName, text.toString());

        if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(item);
            return true;
        }

        player.getWorld().dropItemNaturally(player.getLocation(), item);
        return false;
    }
}