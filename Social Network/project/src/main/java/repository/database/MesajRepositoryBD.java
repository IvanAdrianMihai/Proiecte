package repository.database;

import domain.*;
import domain.validators.ValidationException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static domain.Constants.DATE_TIME_FORMATTER;

public class MesajRepositoryBD {
    private final String url;
    private final String username;
    private final String password;
    private final UtilizatorRepositoryDB utilizatori;

    public MesajRepositoryBD(String url, String username, String password, UtilizatorRepositoryDB utilizatori) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.utilizatori = utilizatori;
    }

    /**
     * @param id de tip Long reprezinta id-ul Mesajului si nu trebuie sa fie null
     * @return mesajul cu id-ul id sau null daca mesajul cu acest id nu exista
     * @throws IllegalArgumentException daca id-ul este null
     */
    public Message findOne(Long id) throws IllegalArgumentException {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        Message message = null;
        //se gaseste mesajul cu id-ul cautat
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages WHERE id=? ORDER BY date")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                //daca exista se creaza mesajul
                Utilizator utilizator = utilizatori.findOne(resultSet.getLong("from_who"));
                String text = resultSet.getString("text_message");
                LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();
                Long idreplay = resultSet.getLong("replay_to");
                Message replayed = null;
                //daca replay_to din db este null inseamna ca este mesajul principal
                if (idreplay != 0)
                    //inseamna ca trebuie sa ajung la mesajul principal si caut mesajul cu id-ul la care face replay
                    replayed = findOne(idreplay);
                if (utilizator != null) {
                    //se creaza mesajul potrivit
                    if (idreplay == 0)
                        message = new Message(utilizator, text, dateTime);
                    else
                        message = new ReplyMessage(utilizator, text, dateTime, replayed);
                    message.setId(id);
                    //trebuie sa aflu lista de utilizatori la care se trimite acest mesaj
                    PreparedStatement statement1 = connection.prepareStatement("SELECT *  FROM sendings " +
                            "WHERE id_message=?");
                    statement1.setLong(1, id);
                    ResultSet resultSet1 = statement1.executeQuery();
                    while (resultSet1.next()) {
                        Long idTo = resultSet1.getLong("to_who");
                        Utilizator to = utilizatori.findOne(idTo);
                        if (to != null)
                            message.addUtilizator(to);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return message;
    }

    /**
     * @return toate mesajele ordonate dupa data
     */
    public Iterable<Message> findAll() {
        //ma folosesc de un map pentru a crea dependentele de mesaje mult mai usor
        Map<Long, Message> map = new HashMap<>();
        //ma sigur ca obtin mesajele in ordinea datei
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages ORDER BY date ASC");
             ResultSet resultSet = statement.executeQuery()) {

            //adaug fiecare mesaj in map
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Utilizator utilizator = utilizatori.findOne(resultSet.getLong("from_who"));
                String text = resultSet.getString("text_message");
                LocalDateTime dateTime = resultSet.getTimestamp("date").toLocalDateTime();
                Long idreplay = resultSet.getLong("replay_to");
                if (utilizator != null)
                    if (idreplay != 0) {
                        //stiind ca am obtinut mesajele in ordine cronologica inseamna ca mesajul actual
                        //daca depinde de un altul sigur acesta a fost adaugat deja in map
                        ReplyMessage replyMessage = new ReplyMessage(utilizator, text, dateTime, map.get(idreplay));
                        replyMessage.setId(id);
                        map.put(id, replyMessage);
                    } else {
                        Message message = new Message(utilizator, text, dateTime);
                        message.setId(id);
                        map.put(id, message);
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //trebuie sa aflu lista de utilizatori la care se trimite fiecare mesaj
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM sendings");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long idMesaj = resultSet.getLong("id_message");
                Long idTo = resultSet.getLong("to_who");
                Utilizator to = utilizatori.findOne(idTo);
                Message msg = map.get(idMesaj);
                if (msg != null && to != null)
                    msg.addUtilizator(to);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //sortez valorile din map dupa data si returnez intr-un iterator mesajele
        List<Message> l = map.values().stream().collect(Collectors.toList());
        Collections.sort(l, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        return l;
    }

    /**
     * @param entity este de tip mesaj si nu trebuie sa fie null
     * @throws IllegalArgumentException daca entity este null
     */
    public void save(Message entity) throws ValidationException, IllegalArgumentException {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            PreparedStatement preparedStatement;
            //se adauga mesajul (depinde daca este un mesaj care raspunde la un alt mesaj sau nu)
            if (entity.getClass().getName().equals("socialnetwork.domain.ReplyMessage")) {
                preparedStatement = connection.prepareStatement("INSERT INTO messages (from_who, text_message, date, replay_to)" +
                        "VALUES (?,?,to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS'),?) RETURNING id");
                preparedStatement.setLong(4, ((ReplyMessage) entity).getReplayMessage().getId());
            } else
                preparedStatement = connection.prepareStatement("INSERT INTO messages (from_who, text_message, date) " +
                        "VALUES (?,?,to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS')) RETURNING id");
            preparedStatement.setLong(1, entity.getFrom().getId());
            preparedStatement.setString(2, entity.getMessage());
            preparedStatement.setString(3, entity.getDate().format(DATE_TIME_FORMATTER));
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Long id = resultSet.getLong("id");
            entity.setId(id);

            //se adauga persoanele la care se trimite mesajul
            for (Utilizator u : entity.getTo()) {
                preparedStatement = connection.prepareStatement("INSERT INTO sendings(id_message, to_who) VALUES (?,?) ");
                preparedStatement.setLong(1, entity.getId());
                preparedStatement.setLong(2, u.getId());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda sterge mesajele retinute de system, dar care nu mai sunt importante pentru ceilalti utilizatori din aplicatie
     */
    public void deleteMesjeSystem() {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM messages where from_who=0");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                boolean exist = false;
                try (PreparedStatement statement1 = connection.prepareStatement("SELECT to_who FROM sendings where id_message=?")) {
                    statement1.setLong(1, id);
                    ResultSet resultSet1 = statement1.executeQuery();
                    if (resultSet1.next())
                        exist = true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (!exist)
                    try (PreparedStatement statement2 = connection.prepareStatement("DELETE FROM messages WHERE id=?")) {
                        statement2.setLong(1, id);
                        statement2.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return numarul de elemente din repository
     */
    public long size() {
        long count = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM messages");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Message> mesajeTrimise(Long id_user, Integer limit, Integer offset){
        List<Message> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM messages where from_who=?" +
                     " order by id desc LIMIT ? OFFSET ?")) {
            statement.setLong(1,id_user);
            statement.setInt(2,limit);
            statement.setInt(3,offset);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                list.add(findOne(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Message> mesajePrimite(Long id_user, Integer limit, Integer offset){
        List<Message> list = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT id_message FROM sendings where to_who=?" +
                     " order by id_message desc LIMIT ? OFFSET ?")) {
            statement.setLong(1,id_user);
            statement.setInt(2,limit);
            statement.setInt(3,offset);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id_message");
                list.add(findOne(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
