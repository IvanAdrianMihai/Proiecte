package repository.database;


import domain.Utilizator;
import repository.Repository0;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UtilizatorRepositoryDB implements Repository0<Long, Utilizator> {
    private String url;
    private String username;
    private String password;

    public UtilizatorRepositoryDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Utilizator findOne(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        Utilizator utilizator = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id=?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long idul = resultSet.getLong("id");
                String firstName = resultSet.getString("prenume");
                String lastName = resultSet.getString("nume");
                byte[] imagine = resultSet.getBytes("image");

                utilizator = new Utilizator(firstName, lastName);
                utilizator.setImagineProfil(imagine);
                utilizator.setId(idul);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return utilizator;
    }

    @Override
    public Iterable<Utilizator> findAll() {
        Map<Long, Utilizator> entities = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
             PreparedStatement statement1 = connection.prepareStatement("SELECT idfriend1, idfriend2 FROM friendships");
             ResultSet resultSet = statement.executeQuery();
             ResultSet resultSet1 = statement1.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("prenume");
                String lastName = resultSet.getString("nume");
                byte[] imagine = resultSet.getBytes("image");

                Utilizator utilizator = new Utilizator(firstName, lastName);
                utilizator.setId(id);
                utilizator.setImagineProfil(imagine);

                if (id != 0)
                    entities.put(id, utilizator);
            }
            while (resultSet1.next()) {
                Long id1 = resultSet1.getLong("idfriend1"),
                        id2 = resultSet1.getLong("idfriend2");
                Utilizator u1 = entities.get(id1), u2 = entities.get(id2);
                u1.addFriend(u2);
                u2.addFriend(u1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities.values();
    }

    @Override
    public void save(Utilizator entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (id, nume, prenume)" +
                     "VALUES (?,?,?)")) {
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setString(3, entity.getFirstName());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Utilizator entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        if (entity.getPath() == null)
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET prenume=?, nume=? WHERE id=?");) {
                preparedStatement.setString(1, entity.getFirstName());
                preparedStatement.setString(2, entity.getLastName());
                preparedStatement.setLong(3, entity.getId());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        else
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET prenume=?, nume=?, image=?  WHERE id=?");) {
                File file = new File(entity.getPath());
                FileInputStream fis = new FileInputStream(file);
                preparedStatement.setString(1, entity.getFirstName());
                preparedStatement.setString(2, entity.getLastName());
                preparedStatement.setBinaryStream(3, fis, file.length());
                preparedStatement.setLong(4, entity.getId());
                preparedStatement.execute();
            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
            }
    }

    @Override
    public long size() {
        long count = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Utilizator> notYetFriends(Long id_user, Integer limit, String last, String first) {
        List<Utilizator> list = new ArrayList<>();
        List<Long> listPrieteni = new ArrayList<>();
        String string = ",?";
        if (last == null)
            last = "";
        if (first == null)
            first = "";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatementstatement = connection.prepareStatement("SELECT * FROM friendships where idfriend1=? or idfriend2=?")) {
            preparedStatementstatement.setLong(1, id_user);
            preparedStatementstatement.setLong(2, id_user);
            ResultSet resultSetFirst = preparedStatementstatement.executeQuery();

            while (resultSetFirst.next())
                if (resultSetFirst.getLong("idfriend1") != id_user)
                    listPrieteni.add(resultSetFirst.getLong("idfriend1"));
                else
                    listPrieteni.add(resultSetFirst.getLong("idfriend2"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Long l : listPrieteni)
            string = string + ",?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users where id not in(?" + string +
                     ") and ((nume like " + "'%" + last + "%'" + " and prenume like " + "'%" + first + "%'" +
                     ") or (nume like " + "'%" + first + "%'" + " and prenume like " + "'%" + last + "%'" + ")) order by id LIMIT ? OFFSET 0")) {
            statement.setLong(1, 0);
            statement.setLong(2, id_user);
            int i = 3;
            for (Long l : listPrieteni)
                statement.setLong(i++, l);
            statement.setLong(i, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("prenume");
                String lastName = resultSet.getString("nume");
                byte[] imagine = resultSet.getBytes("image");

                Utilizator utilizator = new Utilizator(firstName, lastName);
                utilizator.setId(id);
                utilizator.setImagineProfil(imagine);
                list.add(utilizator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Utilizator> otherThanMe(Long id_user, Integer limit, String last, String first) {
        List<Utilizator> list = new ArrayList<>();
        String string = ",?";
        if (last == null)
            last = "";
        if (first == null)
            first = "";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users where id not in(?,?)" +
                     " and ((nume like " + "'%" + last + "%'" + " and prenume like " + "'%" + first + "%'" +
                     ")or (nume like " + "'%" + first + "%'" + " and prenume like " + "'%" + last + "%'" + ")) order by id LIMIT ? OFFSET 0")) {
            statement.setLong(1, 0);
            statement.setLong(2, id_user);
            statement.setLong(3, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("prenume");
                String lastName = resultSet.getString("nume");
                byte[] imagine = resultSet.getBytes("image");

                Utilizator utilizator = new Utilizator(firstName, lastName);
                utilizator.setId(id);
                utilizator.setImagineProfil(imagine);
                list.add(utilizator);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Utilizator> getFriends(Long id_user, Integer limit, Integer offset, String last, String first) {
        List<Utilizator> list = new ArrayList<>();
        List<Long> listPrieteni = new ArrayList<>();
        if (last == null)
            last = "";
        if (first == null)
            first = "";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatementstatement = connection.prepareStatement("SELECT * FROM friendships " +
                     "where idfriend1=? or idfriend2=? ")) {
            preparedStatementstatement.setLong(1, id_user);
            preparedStatementstatement.setLong(2, id_user);

            ResultSet resultSetFirst = preparedStatementstatement.executeQuery();

            while (resultSetFirst.next())
                if (resultSetFirst.getLong("idfriend1") != id_user)
                    listPrieteni.add(resultSetFirst.getLong("idfriend1"));
                else
                    listPrieteni.add(resultSetFirst.getLong("idfriend2"));

            String string = "";
            for (int i = 1; i < listPrieteni.size(); i++)
                    string = string + ",?";

            if (listPrieteni.size() != 0)
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id in (?" + string +
                        ") and ((nume like " + "'%" + last + "%'" + " and prenume like " + "'%" + first + "%'" +
                        ") or (nume like " + "'%" + first + "%'" + " and prenume like " + "'%" + last + "%'" + ")) order by nume, prenume LIMIT ? OFFSET ?")) {

                    int i = 1;
                    for (Long l : listPrieteni)
                        statement.setLong(i++, l);
                    statement.setInt(i++, limit);
                    statement.setInt(i, offset);

                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        Long idul = resultSet.getLong("id");
                        String firstName = resultSet.getString("prenume");
                        String lastName = resultSet.getString("nume");
                        byte[] imagine = resultSet.getBytes("image");

                        Utilizator utilizator = new Utilizator(firstName, lastName);
                        utilizator.setImagineProfil(imagine);
                        utilizator.setId(idul);
                        list.add(utilizator);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
