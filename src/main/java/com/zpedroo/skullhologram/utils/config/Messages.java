package com.zpedroo.skullhologram.utils.config;

import com.zpedroo.skullhologram.utils.FileUtils;
import org.bukkit.ChatColor;

public class Messages {

    public static final String COMMAND_USAGE = getColored(FileUtils.get().getString(FileUtils.Files.CONFIG, "Messages.command-usage"));

    private static String getColored(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}