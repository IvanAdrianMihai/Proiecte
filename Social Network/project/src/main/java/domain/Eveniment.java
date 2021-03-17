package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static domain.Constants.DATE_TIME_FORMATTER;

public class Eveniment extends Entity<Long> {
    private Utilizator gazda;
    private LocalDateTime createDate;
    private LocalDateTime evenimentDate;
    private String denumire;
    private String descriere;
    private List<UtilizatorEveniment> participanti;

    private String createDateForGui;
    private String evenimentDateForGui;
    private String gazdaForGui;
    private String statusForGui;

    public String getStatusForGui() {
        return statusForGui;
    }

    public String getCreateDateForGui() {
        return createDateForGui;
    }

    public String getEvenimentDateForGui() {
        return evenimentDateForGui;
    }

    public String getGazdaForGui() {
        return gazdaForGui;
    }

    public Eveniment(Utilizator gazda, LocalDateTime evenimentDate, String denumire, String descriere) {
        this.gazda = gazda;
        this.evenimentDate = evenimentDate;
        this.denumire = denumire;
        this.descriere = descriere;
        createDate = LocalDateTime.now().toLocalDate().atStartOfDay();

        createDateForGui = createDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        evenimentDateForGui = evenimentDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        gazdaForGui = gazda.getFirstName() + " " + gazda.getLastName();
        if (LocalDateTime.now().isAfter(evenimentDate))
            statusForGui = "Ended";
        else
            statusForGui = "Ongoing";
    }

    public Eveniment(Utilizator gazda, LocalDateTime createDate,
                     LocalDateTime evenimentDate, String denumire, String descriere) {
        this.gazda = gazda;
        this.createDate = createDate;
        this.evenimentDate = evenimentDate;
        this.denumire = denumire;
        this.descriere = descriere;
        participanti = new ArrayList<>();

        createDateForGui = createDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        evenimentDateForGui = evenimentDate.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        gazdaForGui = gazda.getFirstName() + " " + gazda.getLastName();
        if (LocalDateTime.now().isAfter(evenimentDate))
            statusForGui = "Ended";
        else
            statusForGui = "Ongoing";
    }

    public Utilizator getGazda() {
        return gazda;
    }

    public void addParticipant(UtilizatorEveniment u) {
        participanti.add(u);
    }

    public void setGazda(Utilizator gazda) {
        this.gazda = gazda;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getEvenimentDate() {
        return evenimentDate;
    }

    public void setEvenimentDate(LocalDateTime evenimentDate) {
        this.evenimentDate = evenimentDate;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public List<UtilizatorEveniment> getParticipanti() {
        return participanti;
    }

    public void setParticipanti(List<UtilizatorEveniment> participanti) {
        this.participanti = participanti;
    }


    @Override
    public String toString() {
        return "Eveniment{" +
                "gazda=" + gazda +
                ", createDate=" + createDate +
                ", evenimentDate=" + evenimentDate +
                ", denumire='" + denumire + '\'' +
                ", descriere='" + descriere + '\'' +
                ", participanti=" + participanti +
                ", createDateForGui='" + createDateForGui + '\'' +
                ", evenimentDateForGui='" + evenimentDateForGui + '\'' +
                ", gazdaForGui='" + gazdaForGui + '\'' +
                ", statusForGui='" + statusForGui + '\'' +
                '}';
    }
}
