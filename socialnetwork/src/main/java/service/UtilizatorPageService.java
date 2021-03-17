package service;

import com.google.common.hash.Hashing;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import domain.*;
import domain.validators.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static domain.CerereType.*;
import static domain.Constants.DATE_TIME_FORMATTER;

public class UtilizatorPageService {
    private UtilizatorService utilizatorService;
    private PrietenieService prietenieService;
    private MesajService mesajService;
    private CerereService cerereService;
    private ContService contService;
    private UtilizatorPage utilizatorLogat;
    private Utilizator utilizator;
    private EvenimentService evenimentService;

    public UtilizatorPageService(UtilizatorService utilizatorService, PrietenieService prietenieService,
                                 MesajService mesajService, CerereService cerereService, ContService contService,
                                 EvenimentService evenimentService) {
        this.utilizatorService = utilizatorService;
        this.prietenieService = prietenieService;
        this.mesajService = mesajService;
        this.cerereService = cerereService;
        this.contService = contService;
        this.evenimentService = evenimentService;
    }

    public UtilizatorPage getUtilizatorLogat() {
        return utilizatorLogat;
    }

    public void setUtilizatorLogat(Long id) {
        utilizatorLogat = new UtilizatorPage();
        utilizatorLogat.setId(id);
        updateUtilizator(id);
        updateFriends();
        updateMesaje();
        updateCereri();
        updateEvenimente();
    }

    public void updateEvenimente() {
        utilizatorLogat.updatePageEvenimenteCreate(evenimentService);
        utilizatorLogat.updatePageEvenimenteParticipare(evenimentService);
        utilizatorLogat.updatePageEvenimentePublice(evenimentService);
    }

    public void updateMesaje() {
        utilizatorLogat.updatePageMesajePrimite(mesajService);
        utilizatorLogat.updatePageMesajeTrimise(mesajService);
    }

    public void updateCereri() {
        utilizatorLogat.updatePageCereriPrimite(cerereService);
        utilizatorLogat.updatePageCereriTrimise(cerereService);
    }

    public void updateFriends() {
        utilizatorLogat.updatePagePrieteni(utilizatorService);
    }

    public void updateCont(Cont c) {
        if (utilizatorLogat != null) {
            utilizatorLogat.setMail(c.getMail());
            utilizatorLogat.setPassword(c.getPassword());
        }
    }

    public void updateUtilizator(Long id) {
        if (utilizatorLogat != null) {
            Utilizator utilizator1 = utilizatorService.findUtilizator(id);
            utilizator = new Utilizator(utilizator1.getFirstName(), utilizator1.getLastName());
            utilizator.setId(id);

            utilizatorLogat.setFirstName(utilizator1.getFirstName());
            utilizatorLogat.setLastName(utilizator1.getLastName());
            utilizatorLogat.setImagineProfil(utilizator1.getImagineProfil());
        }
    }

    /**
     * Metoda sterge acest cont din baza de date
     *
     * @throws ServiceException - daca nu exista un acest cont,
     *                          daca nu exista un utiliztor logat
     */
    public void deleteCont() throws ServiceException {
        if (utilizatorLogat != null) {
            contService.deleteCont(utilizatorLogat.getMail());
            utilizatorLogat = null;
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    /**
     * Metoda adauga o cerere de la utilizatorul cu id-ul from la utilizatorul cu id-ul to
     *
     * @param to de tip Utilizator
     * @throws ServiceException - daca noua cerere nu intruneste caracteristicile necesare,
     *                          daca cererea de prietenie nu isi are locul, utilizatorii fiind deja prieteni,
     *                          daca exista deja o cerere in asteptare intre cei doi utilizatori,
     *                          daca nu exista un utiliztor logat
     */
    public void addCerere(Utilizator to) throws ServiceException {
        if (utilizatorLogat != null) {
            Cerere cerere = new Cerere(utilizator, to, PENDING);
            cerereService.addCerere(cerere);
            updateCereri();
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    /**
     * Metoda nu aproba cererea de la utilizatorul from primita de utilizatorul to
     *
     * @param from de tip Utilizator
     * @throws ServiceException daca cererea nu este valida,
     *                          daca tipul cererii pentru care se face update nu este corect,
     *                          daca se incearca update-ul de catre persoana care a trimis cererea,
     *                          daca nu exista nicio cerere in asteptare intre cei doi,
     *                          daca nu exista un utiliztor logat
     */
    public void rejectCerere(Utilizator from) throws ServiceException {
        if (utilizatorLogat != null) {
            try {
                Cerere cerere = new Cerere(from, utilizator, REJECTED);
                cerereService.updateCererePending(cerere);
                updateCereri();
            } catch (RepositoryException r) {
                throw new ServiceException(r.getMessage());
            }
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    /**
     * Metoda aproba cererea de la utilizatorul from primita de utilizatorul to
     *
     * @param from de tip Utilizator
     * @throws ServiceException daca cererea nu este valida,
     *                          daca tipul cererii pentru care se face update nu este corect,
     *                          daca se incearca update-ul de catre persoana care a trimis cererea,
     *                          daca nu exista nicio cerere in asteptare intre cei doi,
     *                          daca nu exista un utiliztor logat
     */
    public void approveCerere(Utilizator from) throws ServiceException {
        if (utilizatorLogat != null) {
            try {
                Cerere cerere = new Cerere(from, utilizator, APPROVED);
                cerereService.updateCererePending(cerere);
                prietenieService.addPrietenie(new Prietenie(from.getId(), utilizator.getId()));
                updateFriends();
                updateCereri();
            } catch (RepositoryException r) {
                throw new ServiceException(r.getMessage());
            }
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    /**
     * Metoda sterge cererea de prietenie aflata in asteptare catre utilizatorul to de la utilizatorul from
     *
     * @param to de tip Long
     * @throws ServiceException daca nu exista cei doi utilizatori,
     *                          daca nu exista nicio cerere in pending intre cei doi,
     *                          daca se doreste stergerea cererii in asteptare trimisa de catre altcineva,
     *                          daca nu exista un utiliztor logat
     */
    public void deleteCerere(Long to) throws ServiceException {
        if (utilizatorLogat != null) {
            cerereService.deleteCererePending(utilizator.getId(), to);
            updateCereri();
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    /**
     * Metoda elimina prietenia dintre from si to, dar si cererea de prietenie aprobata dintre cei doi
     *
     * @param to de tip Utilizator
     * @throws ServiceException daca cei doi utilizatori nu exista,
     *                          daca cei doi nu sunt prieteni,
     *                          daca nu exista un utiliztor logat
     */
    public void deletePrietenie(Utilizator to) throws ServiceException {
        if (utilizatorLogat != null)
            try {
                Cerere cerere = new Cerere(utilizator, to, NOMOREFRIENDS);
                cerereService.updateCerereStergePrietenie(cerere);
                prietenieService.deletePrietenie(utilizator.getId(), to.getId());
                updateFriends();
                updateCereri();
            } catch (RepositoryException r) {
                throw new ServiceException(r.getMessage());
            }
        else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    /**
     * Metoda salveaza un mesaj nou de la from la toti cei din lista to
     *
     * @param to      de tip List<Long>
     * @param message de tip String
     * @throws ServiceException daca mesajul nu este valid
     *                          daca nu exista utilizatorul from,
     *                          daca nu exista utilizatorii din lista to,
     *                          daca nu exista un utiliztor logat
     */
    public void writeMessage(List<Long> to, String message) throws ServiceException {
        if (utilizatorLogat != null)
            try {
                Utilizator fr = utilizatorService.findUtilizator(utilizator.getId());
                if (fr == null)
                    throw new RepositoryException("\tNu exista acest utilizator\n");
                Message message1 = new Message(fr, message);
                String error = "";
                for (Long l : to) {
                    Utilizator u = utilizatorService.findUtilizator(l);
                    if (u == null)
                        error = error + "\tNu exista utilizatorul " + l + "\n";
                    else
                        message1.addUtilizator(u);
                }
                if (!error.isEmpty())
                    throw new RepositoryException(error);

                mesajService.addMesaj(message1);
                updateMesaje();
            } catch (RepositoryException r) {
                throw new ServiceException(r.getMessage());
            }
        else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    /**
     * Metoda trimite un mesaj la toti utilizatorii care vad mesajul cu id-ul idMesaj
     *
     * @param idMesaj de tip Long
     * @param message de tip String
     * @throws ServiceException daca mesajul nu este valid
     *                          daca nu exista utilizatorul from,
     *                          daca nu exista mesajul la care sa se raspunda,
     *                          daca utilizatorul care trimite nu se afla in lista de utilizatori la care s-a trimis mesajul cu id idMesaj,
     *                          daca nu exista un utiliztor logat
     */
    public void replayToAll(Long idMesaj, String message) throws ServiceException {
        if (utilizatorLogat != null)
            try {
                String eroare = "";
                Utilizator fr = utilizatorService.findUtilizator(utilizator.getId());
                if (fr == null)
                    eroare = eroare + "\tNu exista acest utilizator\n";
                Message forReplay = mesajService.findOne(idMesaj);
                if (forReplay == null)
                    eroare = eroare + "\tNu exista acest mesaj la care sa raspunzi\n";
                if (!eroare.isEmpty())
                    throw new RepositoryException(eroare);
                ReplyMessage message1 = new ReplyMessage(fr, message, forReplay);
                boolean ok = false;
                //in timp ce caut utilizatorul from in lista de utilizatori la care se trimite mesajul
                //compun lista de utilizatori la care se trimite mesajul
                for (Utilizator u : forReplay.getTo())
                    if (u.getId() != fr.getId())
                        message1.addUtilizator(u);
                    else
                        ok = true;
                if (forReplay.getFrom().getId() != fr.getId())
                    message1.addUtilizator(forReplay.getFrom());
                if (ok == false)
                    throw new RepositoryException("\tNu puteti raspunde la acest mesaj\n");
                mesajService.addMesaj(message1);
                updateMesaje();
            } catch (RepositoryException r) {
                throw new ServiceException(r.getMessage());
            }
        else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    /**
     * Metoda trimite un mesaj la un utilizator care vede mesajul cu id-ul idMesaj
     *
     * @param to      de tip Long
     * @param idMesaj de tip Long
     * @param message de tip String
     * @throws ServiceException daca mesajul nu este valid
     *                          daca nu exista tu si from,
     *                          daca nu exista mesajul la care sa se raspunda,
     *                          daca utilizatorul care trimite nu se afla in lista de utilizatori la care s-a trimis mesajul cu id idMesaj,
     *                          daca utilizatorul la care trimiti nu se afla in lista de prieteni sau nu este cel care trimite mesajul cu id idMesaj,
     *                          daca nu exista un utiliztor logat
     */
    public void replayToOne(Long to, Long idMesaj, String message) throws ServiceException {
        if (utilizatorLogat != null)
            try {
                String eroare = "";
                Utilizator fr = utilizatorService.findUtilizator(utilizator.getId()), t = utilizatorService.findUtilizator(to);
                if (fr == null)
                    eroare = eroare + "\tNu exista acest utilizator\n";
                if (t == null)
                    eroare = eroare + "\tNu exista acest utilizator la care doriti sa trimiteti\n";
                Message forReplay = mesajService.findOne(idMesaj);
                if (forReplay == null)
                    eroare = eroare + "\tNu exista acest mesaj la care sa raspunzi\n";
                if (!eroare.isEmpty())
                    throw new RepositoryException(eroare);
                ReplyMessage message1 = new ReplyMessage(fr, message, forReplay);
                boolean ok = false, ok1 = false;
                //in timp ce caut utilizatorul from si to in lista de utilizatori la care se trimite mesajul
                //compun lista de utilizatori la care se trimite mesajul
                for (Utilizator u : forReplay.getTo()) {
                    if (u.getId() == fr.getId())
                        ok = true;
                    if (u.getId() == t.getId()) {
                        message1.addUtilizator(u);
                        ok1 = true;
                    }
                }
                if (forReplay.getFrom().getId() == t.getId()) {
                    message1.addUtilizator(forReplay.getFrom());
                    ok1 = true;
                }
                if (ok == false)
                    throw new RepositoryException("\tNu puteti raspunde la acest mesaj\n");
                if (ok1 == false)
                    throw new RepositoryException("\tAceasta persoana nu se afla in conversatia aceasta\n");
                mesajService.addMesaj(message1);
                updateMesaje();
            } catch (RepositoryException r) {
                throw new ServiceException(r.getMessage());
            }
        else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    public String prieteniRap1(LocalDateTime date1, LocalDateTime date2) {
        String prieteni = "";
        for (Cerere c : cerereService.getAll())
            if (c.getFrom().getId() == utilizator.getId() && c.getType() == APPROVED
                    && c.getDate().compareTo(date1) >= 0 && c.getDate().compareTo(date2) <= 0)
                prieteni = prieteni + "    " + c.getTo().getLastName() + " " + c.getTo().getFirstName() + " - " + c.getDate().format(DATE_TIME_FORMATTER) + "\n";
            else if (c.getTo().getId() == utilizator.getId() && c.getType() == APPROVED
                    && c.getDate().compareTo(date1) >= 0 && c.getDate().compareTo(date2) <= 0)
                prieteni = prieteni + "    " + c.getFrom().getLastName() + " " + c.getFrom().getFirstName() + " - " + c.getDate().format(DATE_TIME_FORMATTER) + "\n";
        return prieteni;
    }

    public String mesajeRap1(LocalDateTime date1, LocalDateTime date2) {
        String mesaje = "";
        List<Message> list=StreamSupport.stream(mesajService.findAll().spliterator(), false)
                .filter(x -> {
                    for (Utilizator util : x.getTo())
                        if (util.getId() == getUtilizatorLogat().getId())
                            return true;
                    return false;
                })
                .collect(Collectors.toList());
        for (Message m : list)
            if (m.getDate().compareTo(date1) >= 0 && m.getDate().compareTo(date2) <= 0)
                mesaje = mesaje + "    " + m.getDate().format(DATE_TIME_FORMATTER) + "\n    " + m.getFrom().getLastName() + " " + m.getFrom().getFirstName() + ": " + m.getMessage() + "\n\n";
        return mesaje;
    }

    /**
     * Metoda creaza un fisier pdf la adresa URL si contine informatii despre prieteniile utilizatorului logat
     * si a mesajelor primite intre date1 si date2
     *
     * @param URL   - String
     * @param date1 - LocalDateTime
     * @param date2 - LocalDateTime
     * @throws ServiceException - daca nu exista un utiliztor logat
     */
    public void makeReport1PDF(String URL, LocalDateTime date1, LocalDateTime date2) throws ServiceException, FileNotFoundException, DocumentException {
        if (utilizatorLogat != null) {
            String prieteni = prieteniRap1(date1, date2);

            String mesaje = mesajeRap1(date1, date2);

            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, new FileOutputStream(URL + "\\Raport1.pdf"));
            document.open();
            Font bold = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD | Font.UNDERLINE);
            Paragraph paragraph = new Paragraph("Raport prietenii si mesaje", bold);
            paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(paragraph);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            Font bold1 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            paragraph = new Paragraph("Utilizator: " + utilizator.getLastName() + " " + utilizator.getFirstName(), bold1);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Data:" + date1.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + date2.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), bold1);
            document.add(paragraph);

            document.add(Chunk.NEWLINE);

            Font bold2 = new Font(Font.FontFamily.HELVETICA, 12, Font.UNDERLINE);
            Font bold3 = new Font(Font.FontFamily.HELVETICA, 12);

            paragraph = new Paragraph("Prieteni noi:", bold2);
            document.add(paragraph);
            paragraph = new Paragraph(prieteni, bold3);
            document.add(paragraph);

            document.add(Chunk.NEWLINE);

            paragraph = new Paragraph("Mesaje primite:", bold2);
            document.add(paragraph);
            paragraph = new Paragraph(mesaje, bold3);
            document.add(paragraph);
            document.close();
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    public String mesajeRap2(LocalDateTime date1, LocalDateTime date2, Utilizator friend) {
        String mesaje = "";
        List<Message> list=StreamSupport.stream(mesajService.findAll().spliterator(), false)
                .filter(x -> x.getFrom().getId() == friend.getId())
                .collect(Collectors.toList());
        for (Message m : list){
            boolean ok=false;
            for(Utilizator u:m.getTo())
                if(u.getId()==getUtilizatorLogat().getId())
                    ok=true;
            if (ok && m.getDate().compareTo(date1) >= 0 && m.getDate().compareTo(date2) <= 0)
                mesaje = mesaje + "    " + m.getDate().format(DATE_TIME_FORMATTER) + "\n    Mesaj: " + m.getMessage() + "\n\n";
        }
        return mesaje;
    }

    /**
     * Metoda creaza un fisier pdf la adresa URL si contine informatii despre mesajele primite de la prietenul
     * cu id-ul Priend-ului intre datele date1 si date2
     *
     * @param URL    - String
     * @param date1  - LocalDateTime
     * @param date2  - LocalDateTime
     * @param friend - Utilizator
     * @throws ServiceException - daca nu exista un utiliztor logat
     */
    public void makeReport2PDF(String URL, LocalDateTime date1, LocalDateTime date2, Utilizator friend) throws FileNotFoundException, DocumentException {
        if (utilizatorLogat != null) {
            String mesaje = mesajeRap2(date1, date2, friend);

            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, new FileOutputStream(URL + "\\Raport2.pdf"));
            document.open();
            Font bold = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD | Font.UNDERLINE);
            Paragraph paragraph = new Paragraph("Raport mesaje de la prieten", bold);
            paragraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
            document.add(paragraph);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            Font bold1 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            paragraph = new Paragraph("Utilizator: " + utilizatorLogat.getLastName() + " " + utilizatorLogat.getFirstName(), bold1);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);

            paragraph = new Paragraph("Data:" + date1.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + date2.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), bold1);
            document.add(paragraph);

            document.add(Chunk.NEWLINE);

            Font bold2 = new Font(Font.FontFamily.HELVETICA, 12, Font.UNDERLINE);
            Font bold3 = new Font(Font.FontFamily.HELVETICA, 12);

            paragraph = new Paragraph("Mesaje primite de la " + friend.getLastName() + " " + friend.getFirstName() + ":", bold2);
            document.add(paragraph);
            paragraph = new Paragraph(mesaje, bold3);
            document.add(paragraph);
            document.close();
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    /**
     * @param c - de tip Cont
     * @param u - de tip Utilizator
     * @throws ServiceException - daca contul si utilizatorul nu sunt valide,
     *                          daca nu este un utilizator logat,
     *                          daca exista un cont cu acest mail de cont
     */
    public void updateProfile(Cont c, Utilizator u) throws ServiceException {
        if (utilizatorLogat != null) {
            ContValidator cv = new ContValidator();
            UtilizatorValidator uv = new UtilizatorValidator();
            c.setId(utilizator.getId());
            u.setId(utilizator.getId());
            try {
                cv.validate(c);
                uv.validate(u);
                if (utilizatorLogat.getMail().equals(c.getMail()) || contService.findCont(c.getMail()) == null) {
                    utilizatorLogat.setPassword(c.getPassword());
                    utilizatorLogat.setFirstName(u.getFirstName());
                    utilizatorLogat.setLastName(u.getLastName());
                    utilizatorLogat.setImagineProfil(u.getImagineProfil());

                    contService.updateCont(utilizatorLogat.getMail(), c);
                    utilizatorService.update(u);

                    utilizatorLogat.setMail(c.getMail());
                } else
                    throw new ServiceException("\tExista deja un cont cu ce foloseste acest mail\n");
            } catch (ValidationException e) {
                throw new ServiceException(e.getMessage());
            }
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    public void adaugareEveniment(LocalDateTime dataEveniment, String denumire, String descriere) throws ServiceException {
        if (utilizatorLogat != null) {
            evenimentService.addEveniment(new Eveniment(utilizator, dataEveniment, denumire, descriere));
            updateEvenimente();
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    public void deleteEveniment(Long id) throws ServiceException {
        if (utilizatorLogat != null) {
            evenimentService.deleteEveniment(id, utilizator.getId());
            updateEvenimente();
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    public void abonareEveniment(Long id, NotificationType note) throws ServiceException {
        if (utilizatorLogat != null) {
            evenimentService.abonareEveniment(id, utilizator.getId(), note);
            updateEvenimente();
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    public void dezabonareEveniment(Long id) throws ServiceException {
        if (utilizatorLogat != null) {
            evenimentService.dezabonareEveniment(id, utilizator.getId());
            updateEvenimente();
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    public void notificatiOn(Long id) throws ServiceException {
        if (utilizatorLogat != null) {
            evenimentService.notificariOn(id, utilizator.getId());
            updateEvenimente();
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }

    public void notificatiOff(Long id) throws ServiceException {
        if (utilizatorLogat != null) {
            evenimentService.notificariOff(id, utilizator.getId());
            updateEvenimente();
        } else
            throw new ServiceException("\tNu este niciun utilizator logat\n");
    }
}
