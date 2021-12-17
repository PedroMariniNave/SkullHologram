package com.zpedroo.skullhologram.managers;

import com.zpedroo.skullhologram.managers.cache.DataCache;
import com.zpedroo.skullhologram.mysql.DBConnection;
import com.zpedroo.skullhologram.objects.PlacedSkullHologram;
import org.bukkit.Location;

import java.util.HashSet;

public class DataManager {

    private static DataManager instance;
    public static DataManager getInstance() { return instance; }

    private DataCache dataCache;

    public DataManager() {
        instance = this;
        this.dataCache = new DataCache();
    }

    public void save(PlacedSkullHologram placedSkullHologram) {
        if (!placedSkullHologram.isQueueUpdate()) return;

        DBConnection.getInstance().getDBManager().saveSkullHologram(placedSkullHologram);
        placedSkullHologram.setUpdate(false);
    }

    public void saveAll() {
        new HashSet<>(dataCache.getDeletedSkullHolograms()).forEach(location -> {
            DBConnection.getInstance().getDBManager().deleteSkullHologram(location);
        });

        new HashSet<>(dataCache.getSkullHolograms().values()).forEach(this::save);
    }

    public PlacedSkullHologram getPlacedSkullHologram(Location location) {
        return dataCache.getSkullHolograms().get(location);
    }

    public DataCache getCache() {
        return dataCache;
    }
}