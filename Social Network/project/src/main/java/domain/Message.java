package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static domain.Constants.DATE_TIME_FORMATTER;

public class Message extends Entity<Long> {
    private Utilizator from;
    private List<Utilizator> to;
    private String message;
    private LocalDateTime date;

    private String forGuiFrom = "";
    private String forGuiTo = "";
    private String forGuiDate = "";

    public String getForGuiFrom() {
        return forGuiFrom;
    }

    public String getForGuiTo() {
        return forGuiTo;
    }

    public String getForGuiDate() {
        return forGuiDate;
    }

    private void forGui() {
        forGuiFrom = from.getLastName() + " " + from.getFirstName();
        forGuiDate = date.format(DATE_TIME_FORMATTER);
    }

    public Message(Utilizator from, String message, LocalDateTime date) {
        this.from = from;
        this.to = new ArrayList<>();
        this.message = message;
        this.date = date;
        forGui();
    }

    public Message(Utilizator from, String message) {
        this.from = from;
        this.to = new ArrayList<>();
        this.message = message;
        this.date = LocalDateTime.now();
        forGui();
    }

    public void addUtilizator(Utilizator u) {
        to.add(u);
        if (forGuiTo.compareTo("") != 0)
            forGuiTo = forGuiTo + "; ";
        forGuiTo = forGuiTo + u.getLastName() + " " + u.getFirstName();
    }

    public Utilizator getFrom() {
        return from;
    }

    public List<Utilizator> getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        String mesage = "Message{" + "id=" + this.getId() + ", " +
                "from=" + from.getId() +
                ", to=[";
        int nr = 0;
        for (Utilizator u : to) {
            mesage = mesage + u.getId();
            nr++;
            if (nr != to.size())
                mesage = mesage + ",";
        }
        mesage = mesage + "], message='" + message + '\'' +
                ", date=" + date.format(DATE_TIME_FORMATTER) +
                '}';
        return mesage;
    }
}
