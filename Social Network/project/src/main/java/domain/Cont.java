package domain;

import java.time.LocalDateTime;

public class Cont extends Entity<Long> {
    private String mail;
    private String password;
    private String code;
    private LocalDateTime date;

    public Cont(String mail, String password, String code, LocalDateTime date) {
        this.mail = mail;
        this.password = password;
        this.code = code;
        this.date = date;
    }

    public Cont(String mail, String password) {
        this.mail = mail;
        this.password = password;
        this.code = null;
        this.date = null;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
