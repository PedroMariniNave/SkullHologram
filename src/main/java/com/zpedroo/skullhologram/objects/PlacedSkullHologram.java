package com.zpedroo.skullhologram.objects;

import com.zpedroo.skullhologram.managers.DataManager;
import org.bukkit.Location;

public class PlacedSkullHologram {

    private Location location;
    private String text;
    private Holographic holographic;
    private boolean update;

    public PlacedSkullHologram(Location location, String text) {
        this.location = location;
        this.text = text;
        this.holographic = new Holographic(this);
        this.update = false;
    }

    public Location getLocation() {
        return location;
    }

    public String getText() {
        return text;
    }

    public boolean isQueueUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public void cache() {
        DataManager.getInstance().getCache().getSkullHolograms().put(location, this);
    }

    public void delete() {
        DataManager.getInstance().getCache().getSkullHolograms().remove(location);
        DataManager.getInstance().getCache().getDeletedSkullHolograms().add(location);

        holographic.removeHologram();
    }
}