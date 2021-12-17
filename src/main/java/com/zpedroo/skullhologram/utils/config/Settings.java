package com.zpedroo.skullhologram.utils.config;

import com.zpedroo.skullhologram.utils.FileUtils;
import org.bukkit.ChatColor;

import java.util.List;

public class Settings {

    public static final String COMMAND = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.command");

    public static final List<String> ALIASES = FileUtils.get().getStringList(FileUtils.Files.CONFIG, "Settings.aliases");

    public static final String PERMISSION = FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.permission");

    public static final String PERMISSION_MESSAGE = ChatColor.translateAlternateColorCodes('&',
            FileUtils.get().getString(FileUtils.Files.CONFIG, "Settings.permission-message"));
}