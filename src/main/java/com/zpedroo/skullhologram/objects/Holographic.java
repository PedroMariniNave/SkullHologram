package com.zpedroo.skullhologram.objects;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;
import com.zpedroo.skullhologram.SkullHologram;
import org.bukkit.scheduler.BukkitRunnable;

public class Holographic {

    private PlacedSkullHologram placedSkullHologram;

    private String[] hologramLines;
    private TextLine[] textLines;

    private Hologram hologram;

    public Holographic(PlacedSkullHologram placedSkullHologram) {
        this.placedSkullHologram = placedSkullHologram;
        this.hologramLines = new String[]{ placedSkullHologram.getText() };
        this.updateHologram();
    }

    public void updateHologram() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (hologram == null || hologram.isDeleted()) createHologram();

                for (int i = 0; i < hologramLines.length; i++) {
                    textLines[i].setText(hologramLines[i]);
                }
            }
        }.runTaskLater(SkullHologram.get(), 0L);
    }

    private void createHologram() {
        hologram = HologramsAPI.createHologram(SkullHologram.get(), placedSkullHologram.getLocation().clone().add(0.5D, 1, 0.5D));
        textLines = new TextLine[hologramLines.length];

        for (int i = 0; i < hologramLines.length; i++) {
            textLines[i] = hologram.insertTextLine(i, hologramLines[i]);
        }
    }

    public void removeHologram() {
        if (hologram == null || hologram.isDeleted()) return;

        hologram.delete();
        hologram = null;
    }
}