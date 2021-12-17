package com.zpedroo.skullhologram.mysql;

import com.zpedroo.skullhologram.objects.PlacedSkullHologram;
import com.zpedroo.skullhologram.utils.serialization.LocationSerialization;
import org.bukkit.Location;

import java.sql.*;
import java.util.*;

public class DBManager {

    public void saveSkullHologram(PlacedSkullHologram skullHologram) {
        String serializedLocation = LocationSerialization.serializeLocation(skullHologram.getLocation());

        if (contains(serializedLocation, "location")) {
            String query = "UPDATE `" + DBConnection.TABLE + "` SET" +
                    "`location`='" + serializedLocation + "', " +
                    "`text`='" + skullHologram.getText() + "' " +
                    "WHERE `location`='" + serializedLocation + "';";
            executeUpdate(query);
            return;
        }

        String query = "INSERT INTO `" + DBConnection.TABLE + "` (`location`, `text`) VALUES " +
                "('" + serializedLocation + "', " +
                "'" + skullHologram.getText() + "');";
        executeUpdate(query);
    }

    public void deleteSkullHologram(Location location) {
        String query = "DELETE FROM `" + DBConnection.TABLE + "` WHERE `location`='" + LocationSerialization.serializeLocation(location) + "';";
        executeUpdate(query);
    }

    public Map<Location, PlacedSkullHologram> loadSkullHolograms() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String query = "SELECT * FROM `" + DBConnection.TABLE + "`;";

        Map<Location, PlacedSkullHologram> skullHolograms = new HashMap<>(4);

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();

            while (result.next()) {
                Location location = LocationSerialization.deserializeLocation(result.getString(1));
                String text = result.getString(2);

                skullHolograms.put(location, new PlacedSkullHologram(location, text));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection, result, preparedStatement, null);
        }

        return skullHolograms;
    }

    private boolean contains(String value, String column) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        String query = "SELECT `" + column + "` FROM `" + DBConnection.TABLE + "` WHERE `" + column + "`='" + value + "';";
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();
            return result.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection, result, preparedStatement, null);
        }

        return false;
    }

    private void executeUpdate(String query) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            closeConnection(connection, null, null, statement);
        }
    }

    private void closeConnection(Connection connection, ResultSet resultSet, PreparedStatement preparedStatement, Statement statement) {
        try {
            if (connection != null) connection.close();
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (statement != null) statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void createTable() {
        String query = "CREATE TABLE IF NOT EXISTS `" + DBConnection.TABLE + "` (`location` VARCHAR(255), `text` VARCHAR(100), PRIMARY KEY(`location`));";
        executeUpdate(query);
    }

    private Connection getConnection() throws SQLException {
        return DBConnection.getInstance().getConnection();
    }
}