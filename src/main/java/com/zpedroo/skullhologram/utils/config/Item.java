package com.zpedroo.skullhologram.utils.config;

import com.zpedroo.skullhologram.utils.FileUtils;
import com.zpedroo.skullhologram.utils.builder.ItemBuilder;
import de.tr7zw.nbtapi.NBTItem;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private static final ItemStack item = ItemBuilder.build(FileUtils.get().getFile(FileUtils.Files.CONFIG).get(), "Item").build();

    public static ItemStack get(String playerName, String text) {
        NBTItem nbt = new NBTItem(item.clone());
        nbt.setString("SkullHologram", text);

        ItemStack item = nbt.getItem();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String displayName = meta.hasDisplayName() ? meta.getDisplayName() : null;
            List<String> lore = meta.hasLore() ? meta.getLore() : null;

            if (displayName != null) meta.setDisplayName(StringUtils.replaceEach(displayName, new String[]{
                    "{player}",
                    "{text}"
            }, new String[]{
                    playerName,
                    text
            }));

            if (lore != null) {
                List<String> newLore = new ArrayList<>(lore.size());

                for (String str : lore) {
                    newLore.add(StringUtils.replaceEach(str, new String[]{
                            "{player}",
                            "{text}"
                    }, new String[]{
                            playerName,
                            text
                    }));
                }

                meta.setLore(newLore);
            }

            if (item.getType().equals(Material.SKULL_ITEM)) {
                ((SkullMeta) meta).setOwner(playerName);
            }

            item.setItemMeta(meta);
        }

        return item;
    }
}