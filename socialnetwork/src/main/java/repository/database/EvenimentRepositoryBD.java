package repository.database;

import domain.Eveniment;
import domain.NotificationType;
import domain.Utilizator;
import domain.UtilizatorEveniment;
import util.EmailSender;
import util.Util;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static domain.Constants.DATE_TIME_FORMATTER;
import static domain.NotificationType.OFF;
import static domain.NotificationType.ON;

public class EvenimentRepositoryBD {
    private String url;
    private String username;
    private String password;
    private final UtilizatorRepositoryDB utilizatori;

    public EvenimentRepositoryBD(String url, String username, String password, UtilizatorRepositoryDB utilizatori) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.utilizatori = utilizatori;
    }

    public Eveniment findOne(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        Eveniment eveniment = null;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM events WHERE id=?")) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Utilizator gazda = utilizatori.findOne(resultSet.getLong("id_user"));
                LocalDateTime dateCreate = resultSet.getTimestamp("date_create").toLocalDateTime();
                LocalDateTime dateEvent = resultSet.getTimestamp("date_event").toLocalDateTime();
                String denumire = resultSet.getString("denumire");
                String descriere = resultSet.getString("descriere");

                eveniment = new Eveniment(gazda, dateCreate, dateEvent, denumire, descriere);
                eveniment.setId(id);

                try (PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM records WHERE id_event=?")) {
                    statement1.setLong(1, id);
                    ResultSet resultSet1 = statement1.executeQuery();
                    while (resultSet1.next()) {
                        Utilizator participant = utilizatori.findOne(resultSet1.getLong("id_user"));
                        byte statusNotification = resultSet1.getByte("note");
                        UtilizatorEveniment uv = new UtilizatorEveniment(participant.getFirstName(), participant.getLastName(), OFF);
                        uv.setFriends(participant.getFriends());
                        uv.setImagineProfil(participant.getImagineProfil());
                        uv.setId(participant.getId());
                        if (statusNotification == 1)
                            uv.setNotificari(ON);

                        eveniment.addParticipant(uv);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return eveniment;
    }

    public List<Eveniment> findAll() {
        Map<Long, Eveniment> entities = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM events order by date_event asc");
             PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM records");
             ResultSet resultSet = statement.executeQuery();
             ResultSet resultSet1 = statement1.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Utilizator gazda = utilizatori.findOne(resultSet.getLong("id_user"));
                LocalDateTime dateCreate = resultSet.getTimestamp("date_create").toLocalDateTime();
                LocalDateTime dateEvent = resultSet.getTimestamp("date_event").toLocalDateTime();
                String denumire = resultSet.getString("denumire");
                String descriere = resultSet.getString("descriere");

                Eveniment eveniment = new Eveniment(gazda, dateCreate, dateEvent, denumire, descriere);
                eveniment.setId(id);

                entities.put(id, eveniment);
            }
            while (resultSet1.next()) {
                Long id = resultSet1.getLong("id_event");
                Utilizator participant = utilizatori.findOne(resultSet1.getLong("id_user"));
                byte statusNotification = resultSet1.getByte("note");
                UtilizatorEveniment uv = new UtilizatorEveniment(participant.getFirstName(), participant.getLastName(), OFF);
                uv.setFriends(participant.getFriends());
                uv.setImagineProfil(participant.getImagineProfil());
                uv.setId(participant.getId());
                if (statusNotification == 1)
                    uv.setNotificari(ON);

                Eveniment ev = entities.get(id);
                ev.addParticipant(uv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities.values().stream().collect(Collectors.toList());
    }

    public void save(Eveniment entity) {
        if (entity == null)
            throw new IllegalArgumentException("entity must be not null");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO events (id_user, date_create," +
                     " date_event, denumire, descriere) VALUES (?,to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS')," +
                     "to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS'),?,?)")) {
            preparedStatement.setLong(1, entity.getGazda().getId());
            preparedStatement.setString(2, entity.getCreateDate().format(DATE_TIME_FORMATTER));
            preparedStatement.setString(3, entity.getEvenimentDate().format(DATE_TIME_FORMATTER));
            preparedStatement.setString(4, entity.getDenumire());
            preparedStatement.setString(5, entity.getDescriere());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("id must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM events WHERE id=?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void abonare(Long id_event, Long id_user, NotificationType note) {
        if (id_event == null)
            throw new IllegalArgumentException("id_event must be not null");
        if (id_user == null)
            throw new IllegalArgumentException("id_user must be not null");
        if (note == null)
            throw new IllegalArgumentException("note must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO records (id_event, id_user," +
                     " note) VALUES (?,?,?::bit)")) {
            preparedStatement.setLong(1, id_event);
            preparedStatement.setLong(2, id_user);
            if (note == OFF)
                preparedStatement.setString(3, "0");
            else
                preparedStatement.setString(3, "1");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dezabonare(Long id_event, Long id_user) {
        if (id_event == null)
            throw new IllegalArgumentException("id_event must be not null");
        if (id_user == null)
            throw new IllegalArgumentException("id_user must be not null");
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM records WHERE id_event=? AND " +
                     "id_user=?")) {
            preparedStatement.setLong(1, id_event);
            preparedStatement.setLong(2, id_user);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void notificationOn(Long id_event, Long id_user) {
        if (id_event == null)
            throw new IllegalArgumentException("id_event must be not null");
        if (id_user == null)
            throw new IllegalArgumentException("id_user must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE records SET note=?::bit WHERE id_event=?" +
                     " AND id_user=?")) {
            preparedStatement.setString(1, "1");
            preparedStatement.setLong(2, id_event);
            preparedStatement.setLong(3, id_user);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void notificationOff(Long id_event, Long id_user) {
        if (id_event == null)
            throw new IllegalArgumentException("id_event must be not null");
        if (id_user == null)
            throw new IllegalArgumentException("id_user must be not null");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE records SET note=?::bit WHERE id_event=?" +
                     " AND id_user=?")) {
            preparedStatement.setString(1, "0");
            preparedStatement.setLong(2, id_event);
            preparedStatement.setLong(3, id_user);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void sendNotifications() {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM events order by date_event asc");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Utilizator gazda = utilizatori.findOne(resultSet.getLong("id_user"));
                LocalDateTime dateEvent = resultSet.getTimestamp("date_event").toLocalDateTime();
                String denumire = resultSet.getString("denumire");

                String text = Util.getIfNeedNotification(dateEvent.toLocalDate());
                if (LocalDateTime.now().isBefore(dateEvent) && !text.isEmpty()) {
                    try (PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM records where id_event=?")) {
                        statement1.setLong(1, id);
                        ResultSet resultSet1 = statement1.executeQuery();

                        while (resultSet1.next()) {
                            Utilizator participant = utilizatori.findOne(resultSet1.getLong("id_user"));
                            byte statusNotification = resultSet1.getByte("note");
                            LocalDate date = null;
                            if (resultSet1.getDate("notification_date") != null)
                                date = resultSet1.getDate("notification_date").toLocalDate();
                            if (statusNotification == 1 && (date == null || !LocalDate.now().isEqual(date))) {
                                String mail = "";
                                try (PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM accounts where id=?")) {
                                    statement2.setLong(1, participant.getId());
                                    ResultSet resultSet2 = statement2.executeQuery();
                                    System.out.println("search accounts");
                                    if (resultSet2.next()) {
                                        System.out.println("mail");
                                        mail = resultSet2.getString("mail");
                                        EmailSender.send(mail, "Notificare evenimen", "Buna, " +
                                                participant.getLastName() + " " + participant.getFirstName() + "!\nNu uita ca "
                                                + text + " va avea loc evenimentul '" + denumire + "' la care te-ai inscris.\n" +
                                                gazda.getLastName() + " " + gazda.getFirstName() +
                                                " isi doreste sa participi!\n\nNota: Daca doresti sa nu mai primesti" +
                                                " notificari in legatura cu acest eveniment intra in aplicatie si " +
                                                "dezactiveaza notificarile.");
                                        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE records " +
                                                "SET notification_date=to_date(?, 'DD/MM/YYYY') WHERE id_event=? AND id_user=?")) {
                                            preparedStatement.setString(1, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                                            preparedStatement.setLong(2, id);
                                            preparedStatement.setLong(3, participant.getId());
                                            preparedStatement.execute();
                                            System.out.println("update");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public long size() {
        long count = 0;
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM events");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Eveniment> getEvenimenteleCreateUtilizator(Long id_utilizator, boolean all, Integer limit, Integer offset) {
        Map<Long, Eveniment> entities = new HashMap<>();
        if (all)
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM events where id_user=? order by" +
                         " date_event desc LIMIT ? OFFSET ?");
                 PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM records");
                 ResultSet resultSet1 = statement1.executeQuery()) {

                statement.setLong(1, id_utilizator);
                statement.setInt(2, limit);
                statement.setInt(3, offset);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Utilizator gazda = utilizatori.findOne(resultSet.getLong("id_user"));
                    LocalDateTime dateCreate = resultSet.getTimestamp("date_create").toLocalDateTime();
                    LocalDateTime dateEvent = resultSet.getTimestamp("date_event").toLocalDateTime();
                    String denumire = resultSet.getString("denumire");
                    String descriere = resultSet.getString("descriere");

                    Eveniment eveniment = new Eveniment(gazda, dateCreate, dateEvent, denumire, descriere);
                    eveniment.setId(id);
                    entities.put(id, eveniment);
                }
                while (resultSet1.next()) {
                    Long id = resultSet1.getLong("id_event");
                    Utilizator participant = utilizatori.findOne(resultSet1.getLong("id_user"));
                    byte statusNotification = resultSet1.getByte("note");
                    UtilizatorEveniment uv = new UtilizatorEveniment(participant.getFirstName(), participant.getLastName(), OFF);
                    uv.setFriends(participant.getFriends());
                    uv.setImagineProfil(participant.getImagineProfil());
                    uv.setId(participant.getId());
                    if (statusNotification == 1)
                        uv.setNotificari(ON);

                    Eveniment ev = entities.get(id);
                    if (ev != null)
                        ev.addParticipant(uv);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        else
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM events where id_user=? and " +
                         "date_event>=to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS') order by" +
                         " date_event  LIMIT ? OFFSET ?");
                 PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM records");
                 ResultSet resultSet1 = statement1.executeQuery()) {

                statement.setLong(1, id_utilizator);
                statement.setString(2, LocalDate.now().atStartOfDay().format(DATE_TIME_FORMATTER));
                statement.setInt(3, limit);
                statement.setInt(4, offset);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Utilizator gazda = utilizatori.findOne(resultSet.getLong("id_user"));
                    LocalDateTime dateCreate = resultSet.getTimestamp("date_create").toLocalDateTime();
                    LocalDateTime dateEvent = resultSet.getTimestamp("date_event").toLocalDateTime();
                    String denumire = resultSet.getString("denumire");
                    String descriere = resultSet.getString("descriere");

                    Eveniment eveniment = new Eveniment(gazda, dateCreate, dateEvent, denumire, descriere);
                    eveniment.setId(id);
                    entities.put(id, eveniment);
                }
                while (resultSet1.next()) {
                    Long id = resultSet1.getLong("id_event");
                    Utilizator participant = utilizatori.findOne(resultSet1.getLong("id_user"));
                    byte statusNotification = resultSet1.getByte("note");
                    UtilizatorEveniment uv = new UtilizatorEveniment(participant.getFirstName(), participant.getLastName(), OFF);
                    uv.setFriends(participant.getFriends());
                    uv.setImagineProfil(participant.getImagineProfil());
                    uv.setId(participant.getId());
                    if (statusNotification == 1)
                        uv.setNotificari(ON);

                    Eveniment ev = entities.get(id);
                    if (ev != null)
                        ev.addParticipant(uv);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return entities.values().stream().collect(Collectors.toList());
    }

    public List<Eveniment> getEvenimenteleParticip(Long id_utilizator, Integer limit, Integer offset) {
        Map<Long, Eveniment> entities = new HashMap<>();
        List<Long> listEvent = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatementstatement = connection.prepareStatement("SELECT id_event FROM records where id_user=?" +
                     " order by id_event desc LIMIT ? OFFSET ?")) {
            preparedStatementstatement.setLong(1, id_utilizator);
            preparedStatementstatement.setInt(2, limit);
            preparedStatementstatement.setInt(3, offset);
            ResultSet resultSetFirst = preparedStatementstatement.executeQuery();

            while (resultSetFirst.next())
                listEvent.add(resultSetFirst.getLong("id_event"));

            String string = "";
            for (int i = 1; i < listEvent.size(); i++)
                string = string + ",?";

            if (listEvent.size() != 0)
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM events where id in (?" + string +
                        ") order by date_event desc");
                     PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM records");
                     ResultSet resultSet1 = statement1.executeQuery()) {

                    int i = 1;
                    for (Long l : listEvent)
                        statement.setLong(i++, l);

                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        Long id = resultSet.getLong("id");
                        Utilizator gazda = utilizatori.findOne(resultSet.getLong("id_user"));
                        LocalDateTime dateCreate = resultSet.getTimestamp("date_create").toLocalDateTime();
                        LocalDateTime dateEvent = resultSet.getTimestamp("date_event").toLocalDateTime();
                        String denumire = resultSet.getString("denumire");
                        String descriere = resultSet.getString("descriere");

                        Eveniment eveniment = new Eveniment(gazda, dateCreate, dateEvent, denumire, descriere);
                        eveniment.setId(id);
                        entities.put(id, eveniment);
                    }
                    while (resultSet1.next()) {
                        Long id = resultSet1.getLong("id_event");
                        Utilizator participant = utilizatori.findOne(resultSet1.getLong("id_user"));
                        byte statusNotification = resultSet1.getByte("note");
                        UtilizatorEveniment uv = new UtilizatorEveniment(participant.getFirstName(), participant.getLastName(), OFF);
                        uv.setFriends(participant.getFriends());
                        uv.setImagineProfil(participant.getImagineProfil());
                        uv.setId(participant.getId());
                        if (statusNotification == 1)
                            uv.setNotificari(ON);

                        Eveniment ev = entities.get(id);
                        if (ev != null)
                            ev.addParticipant(uv);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entities.values().stream().collect(Collectors.toList());
    }

    public List<Eveniment> getEvenimetePublice(Long id_utilizator, Integer limit, Integer offset) {
        Map<Long, Eveniment> entities = new HashMap<>();
        List<Long> listEvent = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatementstatement = connection.prepareStatement("SELECT id_event FROM records where id_user=?")) {
            preparedStatementstatement.setLong(1, id_utilizator);
            ResultSet resultSetFirst = preparedStatementstatement.executeQuery();

            while (resultSetFirst.next())
                listEvent.add(resultSetFirst.getLong("id_event"));

            try (PreparedStatement prepare = connection.prepareStatement("SELECT id from events where id_user=?")) {
                prepare.setLong(1, id_utilizator);
                ResultSet res = prepare.executeQuery();

                while (res.next())
                    listEvent.add(res.getLong("id"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String string = "";
            for (int i = 1; i < listEvent.size(); i++)
                string = string + ",?";
            if (listEvent.size() != 0) {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM events where id not in (?" + string +
                        ") and date_event>=to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS') order by date_event LIMIT ? OFFSET ?");
                     PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM records");
                     ResultSet resultSet1 = statement1.executeQuery()) {

                    int i = 1;
                    for (Long l : listEvent)
                        statement.setLong(i++, l);
                    statement.setString(i++, LocalDate.now().atStartOfDay().format(DATE_TIME_FORMATTER));
                    statement.setInt(i++, limit);
                    statement.setInt(i, offset);

                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        Long id = resultSet.getLong("id");
                        Utilizator gazda = utilizatori.findOne(resultSet.getLong("id_user"));
                        LocalDateTime dateCreate = resultSet.getTimestamp("date_create").toLocalDateTime();
                        LocalDateTime dateEvent = resultSet.getTimestamp("date_event").toLocalDateTime();
                        String denumire = resultSet.getString("denumire");
                        String descriere = resultSet.getString("descriere");

                        Eveniment eveniment = new Eveniment(gazda, dateCreate, dateEvent, denumire, descriere);
                        eveniment.setId(id);
                        entities.put(id, eveniment);
                    }
                    while (resultSet1.next()) {
                        Long id = resultSet1.getLong("id_event");
                        Utilizator participant = utilizatori.findOne(resultSet1.getLong("id_user"));
                        byte statusNotification = resultSet1.getByte("note");
                        UtilizatorEveniment uv = new UtilizatorEveniment(participant.getFirstName(), participant.getLastName(), OFF);
                        uv.setFriends(participant.getFriends());
                        uv.setImagineProfil(participant.getImagineProfil());
                        uv.setId(participant.getId());
                        if (statusNotification == 1)
                            uv.setNotificari(ON);

                        Eveniment ev = entities.get(id);
                        if (ev != null)
                            ev.addParticipant(uv);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM events where " +
                        "date_event>=to_timestamp(?, 'DD/MM/YYYY HH24:MI:SS') order by date_event LIMIT ? OFFSET ?");
                     PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM records");
                     ResultSet resultSet1 = statement1.executeQuery()) {

                    statement.setString(1, LocalDate.now().atStartOfDay().format(DATE_TIME_FORMATTER));
                    statement.setInt(2, limit);
                    statement.setInt(3, offset);

                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        Long id = resultSet.getLong("id");
                        Utilizator gazda = utilizatori.findOne(resultSet.getLong("id_user"));
                        LocalDateTime dateCreate = resultSet.getTimestamp("date_create").toLocalDateTime();
                        LocalDateTime dateEvent = resultSet.getTimestamp("date_event").toLocalDateTime();
                        String denumire = resultSet.getString("denumire");
                        String descriere = resultSet.getString("descriere");

                        Eveniment eveniment = new Eveniment(gazda, dateCreate, dateEvent, denumire, descriere);
                        eveniment.setId(id);
                        entities.put(id, eveniment);
                    }
                    while (resultSet1.next()) {
                        Long id = resultSet1.getLong("id_event");
                        Utilizator participant = utilizatori.findOne(resultSet1.getLong("id_user"));
                        byte statusNotification = resultSet1.getByte("note");
                        UtilizatorEveniment uv = new UtilizatorEveniment(participant.getFirstName(), participant.getLastName(), OFF);
                        uv.setFriends(participant.getFriends());
                        uv.setImagineProfil(participant.getImagineProfil());
                        uv.setId(participant.getId());
                        if (statusNotification == 1)
                            uv.setNotificari(ON);

                        Eveniment ev = entities.get(id);
                        if (ev != null)
                            ev.addParticipant(uv);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities.values().stream().collect(Collectors.toList());
    }
}
