package domain;

import javafx.scene.image.ImageView;

import java.time.LocalDateTime;

import static domain.Constants.DATE_TIME_FORMATTER;

public class Cerere extends  Entity<Long>{
    private Utilizator from;
    private Utilizator to;
    private CerereType type;
    private LocalDateTime date;

    private ImageView imageFrom;
    private ImageView imageTo;
    private String forGuiFrom;
    private String forGuiTo;
    private String forGuiDate;

    public ImageView getImageFrom() {
        return imageFrom;
    }

    public ImageView getImageTo() {
        return imageTo;
    }

    public String getForGuiTo() {
        return forGuiTo;
    }

    public Cerere(Utilizator from, Utilizator to, CerereType type, LocalDateTime date) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.date = date;
        forGuiFrom=from.getLastName()+" "+from.getFirstName();
        forGuiTo=to.getLastName()+" "+to.getFirstName();
        forGuiDate=date.format(DATE_TIME_FORMATTER);
        imageFrom=from.getImage();
        imageTo=to.getImage();
    }

    public Cerere(Utilizator from, Utilizator to, CerereType type) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.date = LocalDateTime.now();
        forGuiFrom=from.getLastName()+" "+from.getFirstName();
        forGuiDate=date.format(DATE_TIME_FORMATTER);
    }

    public String getForGuiFrom() {
        return forGuiFrom;
    }

    public String getForGuiDate() {
        return forGuiDate;
    }

    public void setType(CerereType type) {
        this.type = type;
    }

    public Utilizator getFrom() {
        return from;
    }

    public Utilizator getTo() {
        return to;
    }

    public CerereType getType() {
        return type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getDateForGui() { return date.format(DATE_TIME_FORMATTER);
    }

    @Override
    public String toString() {
        return "Cerere{" +"id=" + this.getId() + ", " +
                "from=" + from.getId() +
                ", to=" + to.getId() +
                ", type='" + type + '\'' +
                ", date=" + date.format(DATE_TIME_FORMATTER) +
                '}';
    }
}
