package repository.database;

import domain.Cerere;
import domain.CerereType;
import domain.validators.RepositoryException;
import domain.validators.ValidationException;
import domain.validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static domain.Constants.DATE_TIME_FORMATTER;

public class CerereRepositoryBD {
    private final String url;
    private final String username;
    private final String password;
    private final UtilizatorRepositoryDB utilizatori;

    public CerereRepositoryBD(String url, String username, String password, UtilizatorRepositoryDB utilizatori) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.utilizatori = utilizatori;
    }

    /**
     * @param idUt1 - id-ul utilizatorului 1 care nu trebuie sa fie null
     * @param idUt2 - id-ul utilizatorului 2 care nu trebuie sa fie null
     * @return ultima cerere dintre  utilizatorul cu idUt1 si utilizatorul cu idUt2 si care are tipul type
     * sau null - daca nu exista o astfel de cerere
     * @throws IllegalArgumentException daca idUt1 este null, idUt2 este null sau type nu este corect
     */
    public Cerere findOne(Long idUt1, Long idUt2) throws IllegalArgumentException {
        if (idUt1 == null || idUt2 == null)
            throw new IllegalArgumentException("id must not be null");

        Cerere cerere = null;
        //se cauta ultima cerere ce ii implica pe cei doi utilizatori
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM requests WHERE ((from_who=? AND to_who=?)" +
                     "OR (from_who=? AND to_who=?)) ORDER BY date DESC");) {
            statement.setLong(1, idUt1);
            statement.setLong(2, idUt2);
            statement.setLong(3, idUt2);
            statement.setLong(4, idUt1);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long idul = resultSet.getLong("id");
                Long from = resultSet.getLong("from_who");
                Long to = resultSet.getLong("to_who");
                String type = resultSet.getString("type");
                LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();

                cerere = new Cerere(utilizatori.findOne(from), utilizatori.findOne(to), CerereType.valueOf(type), dateTime);
                cerere.setId(idul);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cerere;
    }

    /**
     * @return toate cererile
     */
    public Iterable<Cerere> findAll() {
        List<Cerere> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM requests");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long from = resultSet.getLong("from_who");
                Long to = resultSet.getLong("to_who");
                String type = resultSet.getString("type");
                LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();

                Cerere cerere = new Cerere(utilizatori.findOne(from), utilizatori.findOne(to), CerereType.valueOf(type), dateTime);
                cerere.setId(id);
                list.add(cerere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param entity det tip Cerere si nu trebuie sa fie null
     * @throws IllegalArgumentException daca entity este null
     */
    public void save(Cerere entity) throws IllegalArgumentException, RepositoryException, ValidationException {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO requests (from_who, " +
                     "to_who, type, date) VALUES (?,?,?,to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS'))")) {
            preparedStatement.setLong(1, entity.getFrom().getId());
            preparedStatement.setLong(2, entity.getTo().getId());
            preparedStatement.setString(3, entity.getType().toString());
            preparedStatement.setString(4, entity.getDate().format(DATE_TIME_FORMATTER));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda sterge ultima cerere cu id-ul id
     *
     * @param id - de tip Long
     * @throws IllegalArgumentException daca id-ul este null
     */
    public void delete(Long id) throws RepositoryException {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM requests" +
                     " WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Se poate face update doar pe cereri care sunt in asteptare -> se accepta sau nu
     * Se actualizeaza tipul si data cererii
     *
     * @param entity de tip Cerere nu trebuie sa fie null
     * @throws IllegalArgumentException daca entity este null
     */
    public void update(Cerere entity) throws RepositoryException {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE requests " +
                     "SET type=?, date=to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS') WHERE id=?");) {
            preparedStatement.setString(1, entity.getType().toString());
            preparedStatement.setString(2, entity.getDate().format(DATE_TIME_FORMATTER));
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cerere> cereriPrimite(Long id_user,Integer limit, Integer offset){
        List<Cerere> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM requests where to_who=? and type!='NOMOREFRIENDS'" +
                     " order by id desc LIMIT ? OFFSET ?")) {
            statement.setLong(1,id_user);
            statement.setInt(2,limit);
            statement.setInt(3,offset);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long from = resultSet.getLong("from_who");
                Long to = resultSet.getLong("to_who");
                String type = resultSet.getString("type");
                LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();

                Cerere cerere = new Cerere(utilizatori.findOne(from), utilizatori.findOne(to), CerereType.valueOf(type), dateTime);
                cerere.setId(id);
                list.add(cerere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Cerere> cereriTrimise(Long id_user,Integer limit, Integer offset){
        List<Cerere> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM requests where from_who=? AND type='PENDING' " +
                     "order by id desc LIMIT ? OFFSET ?")) {
            statement.setLong(1,id_user);
            statement.setInt(2,limit);
            statement.setInt(3,offset);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long from = resultSet.getLong("from_who");
                Long to = resultSet.getLong("to_who");
                String type = resultSet.getString("type");
                LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();

                Cerere cerere = new Cerere(utilizatori.findOne(from), utilizatori.findOne(to), CerereType.valueOf(type), dateTime);
                cerere.setId(id);
                list.add(cerere);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
