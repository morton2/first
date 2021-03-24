package com.codecool.database;


import java.sql.*;

public class RadioCharts {
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public RadioCharts(String dbUrl, String dbUser, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            System.err.println("Database not reachable.");
        }
        return connection;
    }

    public String getMostPlayedSong() {
        String name = "";
        String query = "SELECT song, artist, MAX(times_aired) FROM music_broadcast GROUP BY artist";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            //statement.setInt(1, competitorId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) name = resultSet.getString("song");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return name;
    }

    public String getMostActiveArtist() {
        String name = "";
        String query = "SELECT artist, count(artist) as occur FROM music_broadcast GROUP BY artist ORDER BY `occur` DESC LIMIT 1";
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            //statement.setInt(1, competitorId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) name = resultSet.getString("artist");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return name;
    }
}
