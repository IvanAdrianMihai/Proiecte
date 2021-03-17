package domain;

import java.time.LocalDateTime;

import static domain.Constants.DATE_TIME_FORMATTER;

public class PrietenDTO {
    private String nume;
    private String prenume;
    private LocalDateTime date;

    public PrietenDTO(String nume, String prenume, LocalDateTime date) {
        this.nume = nume;
        this.prenume = prenume;
        this.date = date;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return nume + " | " + prenume + " | " +date.format(DATE_TIME_FORMATTER);
    }
}
