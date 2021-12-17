package com.zpedroo.skullhologram.managers.cache;

import com.zpedroo.skullhologram.mysql.DBConnection;
import com.zpedroo.skullhologram.objects.PlacedSkullHologram;
import org.bukkit.Location;

import java.util.*;

public class DataCache {

    private Map<Location, PlacedSkullHologram> skullHolograms;
    private Set<Location> deletedSkullHolograms;

    public DataCache() {
        this.skullHolograms = DBConnection.getInstance().getDBManager().loadSkullHolograms();
        this.deletedSkullHolograms = new HashSet<>(4);
    }

    public Map<Location, PlacedSkullHologram> getSkullHolograms() {
        return skullHolograms;
    }

    public Set<Location> getDeletedSkullHolograms() {
        return deletedSkullHolograms;
    }
}