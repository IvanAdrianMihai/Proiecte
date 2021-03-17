package domain;

import repository.page.*;
import service.CerereService;
import service.EvenimentService;
import service.MesajService;
import service.UtilizatorService;

import java.util.ArrayList;
import java.util.List;

public class UtilizatorPage extends Entity<Long> {
    private String Mail;
    private String Password;
    private String firstName;
    private String lastName;
    private byte[] imagineProfil;

    private PageCereriPrimite pageCereriPrimite;
    private PageCereriTrimise pageCereriTrimise;
    private PageMesajePrimite pageMesajePrimite;
    private PageMesajeTrimise pageMesajeTrimise;
    private PageEvenimenteCreate pageEvenimenteCreate;
    private PageEvenimenteParticipare pageEvenimenteParticipare;
    private PageEvenimentePublice pageEvenimentePublice;
    private PagePrieteni pagePrieteni;

    public void updatePagePrieteni(UtilizatorService utilizatorService) {
        pagePrieteni = new PagePrieteni(5, utilizatorService, getId());
    }

    public void updatePageEvenimentePublice(EvenimentService evenimentService) {
        pageEvenimentePublice = new PageEvenimentePublice(5, evenimentService, getId());
    }

    public void updatePageEvenimenteCreate(EvenimentService evenimentService) {
        pageEvenimenteCreate = new PageEvenimenteCreate(5, evenimentService, getId());
    }

    public void updatePageEvenimenteParticipare(EvenimentService evenimentService) {
        pageEvenimenteParticipare = new PageEvenimenteParticipare(5, evenimentService, getId());
    }

    public void updatePageCereriPrimite(CerereService cerereService) {
        pageCereriPrimite = new PageCereriPrimite(5, cerereService, getId());
    }

    public void updatePageCereriTrimise(CerereService cerereService) {
        pageCereriTrimise = new PageCereriTrimise(5, cerereService, getId());
    }

    public void updatePageMesajePrimite(MesajService mesajService) {
        pageMesajePrimite = new PageMesajePrimite(5, mesajService, getId());
    }

    public void updatePageMesajeTrimise(MesajService mesajService) {
        pageMesajeTrimise = new PageMesajeTrimise(5, mesajService, getId());
    }

    public PagePrieteni getPagePrieteni() {
        return pagePrieteni;
    }

    public PageEvenimentePublice getPageEvenimentePublice() {
        return pageEvenimentePublice;
    }

    public PageEvenimenteCreate getPageEvenimenteCreate() {
        return pageEvenimenteCreate;
    }

    public PageEvenimenteParticipare getPageEvenimenteParticipare() {
        return pageEvenimenteParticipare;
    }

    public PageMesajePrimite getPageMesajePrimite() {
        return pageMesajePrimite;
    }

    public PageMesajeTrimise getPageMesajeTrimise() {
        return pageMesajeTrimise;
    }

    public PageCereriPrimite getPageCereriPrimite() {
        return pageCereriPrimite;
    }

    public PageCereriTrimise getPageCereriTrimise() {
        return pageCereriTrimise;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setImagineProfil(byte[] imagineProfil) {
        this.imagineProfil = imagineProfil;
    }

    public String getMail() {
        return Mail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public byte[] getImagineProfil() {
        return imagineProfil;
    }
}
