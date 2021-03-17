package repository.database;

import domain.Cont;

import java.sql.*;
import java.time.LocalDateTime;

import static domain.Constants.DATE_TIME_FORMATTER;

public class ContRepositoryBD {
    private String url;
    private String username;
    private String password;

    public ContRepositoryBD(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Cont findOne(String mail) {
        if (mail == null)
            throw new IllegalArgumentException("mail must be not null");

        Cont cont = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM accounts WHERE mail=?")) {
            statement.setString(1, mail);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long idul = resultSet.getLong("id");
                String thisMail = resultSet.getString("mail");
                String password = resultSet.getString("password");
                String code = resultSet.getString("code");
                LocalDateTime date;
                if (code == null)
                    cont = new Cont(thisMail, password);
                else {
                    date = resultSet.getTimestamp("date").toLocalDateTime();
                    cont = new Cont(thisMail, password, code, date);
                }
                cont.setId(idul);
                return cont;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cont;
    }

    public Cont save(Cont entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO accounts (mail, password)" +
                     "VALUES (?,?) RETURNING id")) {
            preparedStatement.setString(1, entity.getMail());
            preparedStatement.setString(2, entity.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            entity.setId(resultSet.getLong("id"));
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE messages SET from_who=0" +
                     " WHERE from_who=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM accounts WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Cont entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        if (entity.getCode() == null)
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement("UPDATE accounts SET mail=?, password=?," +
                         "code=NULL, date=NULL WHERE id=?")) {
                preparedStatement.setString(1, entity.getMail());
                preparedStatement.setString(2, entity.getPassword());
                preparedStatement.setLong(3, entity.getId());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        else
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement preparedStatement = connection.prepareStatement("UPDATE accounts SET mail=?, password=?, " +
                         "date=to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS'), code=? WHERE id=?")) {
                preparedStatement.setString(1, entity.getMail());
                preparedStatement.setString(2, entity.getPassword());
                preparedStatement.setString(3, entity.getDate().format(DATE_TIME_FORMATTER));
                preparedStatement.setString(4, entity.getCode().substring(0, 6));
                preparedStatement.setLong(5, entity.getId());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
