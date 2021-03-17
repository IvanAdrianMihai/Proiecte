package repository.database;

import domain.Prietenie;
import domain.Tuple;
import repository.Repository0;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static domain.Constants.DATE_TIME_FORMATTER;

public class PrietenieRepositoryDB implements Repository0<Tuple<Long, Long>, Prietenie> {
    private final String url;
    private final String username;
    private final String password;

    public PrietenieRepositoryDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Prietenie findOne(Tuple<Long, Long> id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        Prietenie prietenie = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships WHERE " +
                     "(idfriend1=? AND idfriend2=?) OR (idfriend1=? AND idfriend2=?)")) {
            statement.setLong(1, id.getLeft());
            statement.setLong(2, id.getRight());
            statement.setLong(3, id.getRight());
            statement.setLong(4, id.getLeft());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id1 = resultSet.getLong("idfriend1");
                Long id2 = resultSet.getLong("idfriend2");
                LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();

                prietenie = new Prietenie(id1, id2, dateTime);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return prietenie;
    }

    @Override
    public Iterable<Prietenie> findAll() {
        Set<Prietenie> entities = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id1 = resultSet.getLong("idfriend1");
                Long id2 = resultSet.getLong("idfriend2");
                LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();

                Prietenie prietenie = new Prietenie(id1, id2, dateTime);
                entities.add(prietenie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    @Override
    public void save(Prietenie entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO friendships " +
                     "(idfriend1, idfriend2, date) VALUES (?,?,to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS'));")) {
            preparedStatement.setLong(1, entity.getId().getLeft());
            preparedStatement.setLong(2, entity.getId().getRight());
            preparedStatement.setString(3, entity.getDate().format(DATE_TIME_FORMATTER));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Tuple<Long, Long> id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM friendships" +
                     " WHERE idfriend1=? AND idfriend2=?")) {
            preparedStatement.setLong(1, id.getLeft());
            preparedStatement.setLong(2, id.getRight());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Prietenie entity) {
    }

    @Override
    public long size() {
        long count = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
