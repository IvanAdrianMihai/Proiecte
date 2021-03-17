package service;

import com.itextpdf.text.*;
import domain.*;
import domain.validators.*;
import util.events.Event;
import util.observer.Observable;
import util.observer.Observer;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static util.Util.*;


/**
 * Se realizeaza functionalitatil necesare pentru a retine utilizatorii si prieteniile dintre acestia
 */

public class SuperService implements Observable<Event> {
    private final UtilizatorService utilizatorService;
    private final PrietenieService prietenieService;
    private final CerereService cerereService;
    private final MesajService mesajService;
    private final ContService contService;
    private final UtilizatorPageService utilizatorPageService;
    private final EvenimentService evenimentService;
    List<Observer<Event>> observers;

    @Override
    public void addObserver(Observer<Event> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<Event> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(Event t) {
        for (Observer<Event> o : observers)
            o.update(t);
    }

    public void setLogat(Long id) {
        utilizatorPageService.setUtilizatorLogat(id);
    }

    public UtilizatorPage getLogat() {
        return utilizatorPageService.getUtilizatorLogat();
    }

    /**
     * Metoda sterge mesajele retinute de system, dar care nu mai sunt importante pentru ceilalti utilizatori din aplicatie
     */
    public void deleteMesjeSystem() {
        mesajService.deleteMesjeSystem();
    }

    /**
     * Metoda creaza un super server care se foloseste de serverul de utilizatori si de cel de prieteni
     *
     * @param utilizatorService de tip UtilizatorService
     * @param prietenieService  de tip PrietenieService
     */
    public SuperService(UtilizatorService utilizatorService, PrietenieService prietenieService,
                        CerereService cerereService, MesajService mesajService, ContService contService,
                        UtilizatorPageService utilizatorPageService, EvenimentService evenimentService) {
        this.utilizatorService = utilizatorService;
        this.prietenieService = prietenieService;
        this.cerereService = cerereService;
        this.mesajService = mesajService;
        this.contService = contService;
        this.utilizatorPageService = utilizatorPageService;
        this.evenimentService = evenimentService;
        observers = new ArrayList<>();
    }

    public Long getStartTimeToWait() {
        return contService.getStartTimeToWait();
    }

    public void setStartTimeToWait(Long startTimeToWait) {
        contService.setStartTimeToWait(startTimeToWait);
    }

    public void contLogIn(String mail, String password) throws ServiceException {
        Cont cont = new Cont(mail, password);
        Cont contNou = contService.tryToConnect(cont);
        setLogat(contNou.getId());
        utilizatorPageService.updateCont(contNou);
    }

    public void addCont(String mail, String password, String firstName, String lastName) throws ServiceException {
        Cont cont = new Cont(mail, password);
        Utilizator utilizator = new Utilizator(firstName, lastName);
        utilizator.setId(contService.addCont(cont).getId());
        utilizatorService.addUtilizator(utilizator);
        notifyObservers(Event.ADD_CONT);
    }

    public void updateCont(String mail, String password) throws ServiceException {
        Cont cont = new Cont(mail, password);
        contService.updateCont(mail, cont);
        notifyObservers(Event.UPDATE_CONT);
    }

    public void recuperareParola(String mail) {
        contService.recoverMailPassword(mail);
    }

    public boolean verifyCod(String mail, String cod) throws ServiceException {
        Cont cont = contService.findCont(mail);
        if (cont == null)
            throw new ServiceException("\tNu exista acest mail\n");
        if (cont.getCode() == null)
            throw new ServiceException("\tNu s-a generat niciun cod pentru contul dumneavoastra\n");
        if (cont.getCode().compareTo(cod) == 0) {
            LocalDateTime date = cont.getDate();
            LocalDateTime dateNow = LocalDateTime.now();
            if ((dateNow.isAfter(date) && dateNow.isBefore(date.plusMinutes(30))) || (dateNow.isEqual(date)) || (dateNow.isEqual(date.plusMinutes(30))))
                return true;
            else
                throw new ServiceException("\tS-a depasit timpul in care se putea modifica parola.\nIncercati din nou!\n");
        }
        return false;
    }

    /**
     * @param Mail      -  de tip String
     * @param Password  -  de tip String
     * @param firstName -  de tip String
     * @param lastName  -  de tip String
     * @param image     -  de tip byte[]
     * @throws ServiceException - daca contul si utilizatorul nu sunt valide,
     *                          daca nu este un utilizator logat,
     *                          daca exista un cont cu acest mail de cont
     */
    public void updateProfile(String Mail, String Password, String firstName, String lastName, byte[] image, String path) throws ServiceException {
        Cont c = new Cont(Mail, Password);
        Utilizator u = new Utilizator(firstName, lastName);
        u.setImagineProfil(image);
        u.setPath(path);
        utilizatorPageService.updateProfile(c, u);
        notifyObservers(Event.UPDATE_CONT);
    }

    /**
     * @return numarul de utilizatori din repository
     */
    public List<Utilizator> getUtilizatori() {
        return StreamSupport.stream(utilizatorService.getAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * @return toate mesajele cronologic
     */
    public Iterable<Message> getMesaje() {
        return mesajService.findAll();
    }

    /**
     * @return toate mesajele cronologic care sunt pentru utilizatorul logat
     */
    public Iterable<Message> getMessageFor() {
        return StreamSupport.stream(mesajService.findAll().spliterator(), false)
                .filter(x -> {
                    for (Utilizator util : x.getTo())
                        if (util.getId() == utilizatorPageService.getUtilizatorLogat().getId())
                            return true;
                    return false;
                })
                .collect(Collectors.toList());
    }

    /**
     * Metoda sterge acest cont din baza de date
     *
     * @throws ServiceException - daca nu exista un acest cont,
     *                          daca nu exista un utiliztor logat
     */
    public void deleteCont() throws ServiceException {
        utilizatorPageService.deleteCont();
        notifyObservers(Event.DELETE_CONT);
    }

    /**
     * Metoda adauga o cerere de la utilizatorul cu id-ul from la utilizatorul cu id-ul to
     *
     * @param to de tip Long
     * @throws ServiceException - daca noua cerere nu intruneste caracteristicile necesare,
     *                          daca cererea de prietenie nu isi are locul, utilizatorii fiind deja prieteni,
     *                          daca exista deja o cerere in asteptare intre cei doi utilizatori,
     *                          daca nu exista un utiliztor logat
     */
    public void addCerere(Long to) throws ServiceException {
        utilizatorPageService.addCerere(utilizatorService.findUtilizator(to));
        notifyObservers(Event.ADD_CERERE);
    }

    /**
     * @param u1 de tip Utilizator
     * @param u2 de tip Utilizator
     * @throws RepositoryException daca u1 sau u2 este null
     */
    private void existaDoiUtilizatori(Utilizator u1, Utilizator u2) throws RepositoryException {
        String eroare = "";
        if (u1 == null)
            eroare = eroare + "\tPrimul utilizator nu exista\n";
        if (u2 == null)
            eroare = eroare + "\tAl doilea utilizator nu exista\n";
        if (!eroare.isEmpty())
            throw new RepositoryException(eroare);
    }

    /**
     * @param u de tip Utilizator
     * @throws RepositoryException daca u este null
     */
    private void existaUtilizator(Utilizator u) throws RepositoryException {
        if (u == null)
            throw new RepositoryException("\tPrimul utilizator nu exista\n");
    }

    /**
     * Metoda nu aproba cererea de la utilizatorul from primita de utilizatorul to
     *
     * @param from de tip Long
     * @throws ServiceException daca cererea nu este valida,
     *                          daca tipul cererii pentru care se face update nu este corect,
     *                          daca se incearca update-ul de catre persoana care a trimis cererea,
     *                          daca nu exista nicio cerere in asteptare intre cei doi,
     *                          daca nu exista un utiliztor logat
     */
    public void rejectCerere(Long from) throws ServiceException {
        try {
            Utilizator F = utilizatorService.findUtilizator(from);
            existaUtilizator(F);
            utilizatorPageService.rejectCerere(F);
            notifyObservers(Event.REJECT_CERERE);
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    /**
     * Metoda aproba cererea de la utilizatorul from primita de utilizatorul to
     *
     * @param from de tip Long
     * @throws ServiceException daca cererea nu este valida,
     *                          daca tipul cererii pentru care se face update nu este corect,
     *                          daca se incearca update-ul de catre persoana care a trimis cererea,
     *                          daca nu exista nicio cerere in asteptare intre cei doi,
     *                          daca nu exista un utiliztor logat
     */
    public void approveCerere(Long from) throws ServiceException {
        try {
            Utilizator F = utilizatorService.findUtilizator(from);
            existaUtilizator(F);
            utilizatorPageService.approveCerere(F);
            notifyObservers(Event.APPROVE_CERERE);
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
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
        utilizatorPageService.deleteCerere(to);
        notifyObservers(Event.DELETE_CERERE);
    }

    /**
     * Metoda elimina prietenia dintre from si to, dar si cererea de prietenie aprobata dintre cei doi
     *
     * @param to de tip Long
     * @throws ServiceException daca cei doi utilizatori nu exista,
     *                          daca cei doi nu sunt prieteni,
     *                          daca nu exista un utiliztor logat
     */
    public void deletePrietenieCerere(Long to) throws ServiceException {
        try {
            Utilizator T = utilizatorService.findUtilizator(to);
            existaUtilizator(T);
            utilizatorPageService.deletePrietenie(T);
            notifyObservers(Event.DELETE_PRIETEN);
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
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
        utilizatorPageService.writeMessage(to, message);
        notifyObservers(Event.ADD_MESAJ);
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
        utilizatorPageService.replayToAll(idMesaj, message);
        notifyObservers(Event.ADD_MESAJ);
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
        utilizatorPageService.replayToOne(to, idMesaj, message);
        notifyObservers(Event.ADD_MESAJ);
    }

    /**
     * Metoda verifica daca exista utilizatorul e in list
     *
     * @param list de tip List<Utilizator>
     * @param e    de tip Utilizatoru
     * @return true daca exista/ false daca nu exista
     */
    private boolean existaUtilizator(List<Utilizator> list, Utilizator e) {
        for (Utilizator u : list)
            if (u.getId() == e.getId())
                return true;
        return false;
    }

    /**
     * @return lista de liste de mesaje care fac parte din conversatii diferite
     */
    //incearca imbunatatire cu gueue + interatori in continuare
    private List<List<Message>> getConversations() {
        List<List<Message>> conversatii = new ArrayList<>();
        List<Long> visitedMessage = new ArrayList<>();
        Iterable<Message> mesaje = getMesaje();
        int size = (int) mesajService.size();
        for (Message m : mesaje)
            if (!existaLong(visitedMessage, m.getId())) {
                Message[] conversatie = new Message[size];
                List<Message> conversatieColect = new ArrayList<>();
                int i = 0;
                List<Long> visitedInThisConv = new ArrayList<>();
                conversatie[i++] = m;
                conversatieColect.add(m);
                for (int j = 0; j < i; j++) {
                    visitedMessage.add(conversatie[j].getId());
                    visitedInThisConv.add(conversatie[j].getId());
                    for (Message m2 : mesaje)
                        if (m2.getClass().getName().equals("socialnetwork.domain.ReplyMessage") &&
                                !existaLong(visitedInThisConv, m2.getId())
                                && conversatie[j].getId() == ((ReplyMessage) m2).getReplayMessage().getId()) {
                            conversatie[i++] = m2;
                            conversatieColect.add(m2);
                        }
                }
                conversatii.add(conversatieColect);
            }
        return conversatii;
    }

    /**
     * @param id1 de tip Long
     * @param id2 de tip Long
     * @return Metoda returneaza lista de conversatii ale celor doi utilizatori in ordine cronologica a primului
     * mesaj trimis in conversatie
     * @throws ServiceException cei doi utilizatori nu exista
     */
    public List<List<Message>> getConversation(Long id1, Long id2) throws ServiceException {
        String eroare = "";
        List<List<Message>> conversatii = new ArrayList<>();
        Utilizator u1 = utilizatorService.findUtilizator(id1), u2 = utilizatorService.findUtilizator(id2);
        try {
            if (u1 == null)
                eroare = eroare + "\tPrimul utilizator nu exista\n";
            if (u2 == null)
                eroare = eroare + "\tAl doilea utilizator nu exista\n";
            if (!eroare.isEmpty())
                throw new RepositoryException(eroare);
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }

        List<List<Message>> conv = getConversations();
        for (List<Message> l : conv) {
            List<Message> selectate = new ArrayList<>();
            for (Message m : l)
                //incearca imbunatatire sa verifici primul mesaj, root-ul, si sa vezi daca cei doi fac parte din acea conversatie
                if ((m.getFrom().getId() == id1 && existaUtilizator(m.getTo(), u2))
                        || (m.getFrom().getId() == id2 && existaUtilizator(m.getTo(), u1)))
                    selectate.add(m);
            if (!selectate.isEmpty())
                conversatii.add(selectate);
        }
        return conversatii;
    }

    /**
     * @param id1 de tip Long
     * @param id2 de tip Long
     * @return returneaza conversatiile a doi utilizatori
     * @throws ServiceException daca cei doi utilizatori nu exista
     */
    public List<Message> getConversationMessages(Long id1, Long id2) throws ServiceException {
        try {
            Utilizator u1 = utilizatorService.findUtilizator(id1), u2 = utilizatorService.findUtilizator(id2);
            existaDoiUtilizatori(u1, u2);
            return StreamSupport.stream(mesajService.findAll().spliterator(), false)
                    .filter(x -> {
                        if (x.getFrom().getId() == u1.getId() && existaUtilizator(x.getTo(), u2))
                            return true;
                        if (x.getFrom().getId() == u2.getId() && existaUtilizator(x.getTo(), u1))
                            return true;
                        return false;
                    })
                    .collect(Collectors.toList());
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    /**
     * Se returneaza lista de prieteni ai utilizatorului cu id-ul ID
     *
     * @param ID de tip Long
     * @throws ServiceException nu exista utilizator cu acest ID
     */
    public Iterable<PrietenDTO> prieteniUtilizator(Long ID) throws ServiceException {
        try {
            if (utilizatorService.findUtilizator(ID) == null)
                throw new RepositoryException("\tNu exista utilizatorul cu acest ID\n");
            return StreamSupport.stream(prietenieService.getAll().spliterator(), false)
                    .filter(x -> x.getId().getRight() == ID || x.getId().getLeft() == ID)
                    .map(x -> {
                        Utilizator u;
                        if (x.getId().getLeft() == ID) {
                            u = utilizatorService.findUtilizator(x.getId().getRight());
                            return new PrietenDTO(u.getFirstName(), u.getLastName(), x.getDate());
                        }
                        u = utilizatorService.findUtilizator(x.getId().getLeft());
                        return new PrietenDTO(u.getFirstName(), u.getLastName(), x.getDate());
                    })
                    .collect(Collectors.toList());
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    /**
     * Se returneaza lista de prieteni ai utilizatorului cu id-ul ID din luna mounth
     *
     * @param ID de tip Long
     * @throws ServiceException nu exista utilizator cu acest ID
     */
    public Iterable<PrietenDTO> prieteniUtilizatorMounth(Long ID, int mounth) throws ServiceException {
        try {
            return StreamSupport.stream(prieteniUtilizator(ID).spliterator(), false)
                    .filter(x -> x.getDate().getMonth().getValue() == mounth)
                    .collect(Collectors.toList());
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    /**
     * Metoda va asigna fiecarui id al utilizatorilor un alt id astfel incat toate id-urile vor fi consecutive.
     * Mapul transformare va memora id-urile corespunzatoare transformarii id-urilor utilizatorilor.
     * Mapul original va memora id-urile originale corespunzatoare id-urilor transformate
     *
     * @param transformare de tip Map<Long,Long>
     * @param original     de tip Map<Long,Long>
     */
    private void codareIduriUtilizatori(Map<Long, Long> transformare, Map<Long, Long> original) {
        long c = 1;
        for (Utilizator u : getUtilizatori()) {
            transformare.put(u.getId(), c);
            original.put(c++, u.getId());
        }
    }

    /**
     * Metoda marcheaza muchiile pentru fiecare prietenie
     *
     * @param matrix       de tip boolean[][]
     * @param transformare de tip Map<Long,Long> ce transforma id-urile tuturor utilizatorilor in id-uri consecutive
     */
    private void initializareMatrice(boolean[][] matrix, Map<Long, Long> transformare) {
        for (Prietenie p : prietenieService.getAll()) {
            int x = Math.toIntExact(transformare.get(p.getId().getLeft())),
                    y = Math.toIntExact(transformare.get(p.getId().getRight()));
            matrix[x][y] = true;
            matrix[y][x] = true;
        }
    }

    /**
     * @return List<List < Long>> ce reprezinta componentele conexe din reteaua de prieteni
     */
    public List<List<Long>> componenteConexe() {
        int s = (int) utilizatorService.size();
        boolean[][] matrix = creareMatriceFalse(s + 1);
        boolean[] vizitat = creareVectorFalse(s + 1);
        List<List<Long>> l = new ArrayList<>();

        Map<Long, Long> transf = new HashMap<>(), orig = new HashMap<>();
        codareIduriUtilizatori(transf, orig);
        initializareMatrice(matrix, transf);

        //se repeta pana cand se gasesc toate componentele conexe
        int nod = isVizitat(vizitat);
        do {
            List<Long> p = new ArrayList<>();
            parcurgeRec(nod, vizitat, matrix, p, s);
            l.add(p);
            nod = isVizitat(vizitat);
        } while (nod != -1);

        //transformarea in id-urile originale
        for (List<Long> list : l)
            if (list != null)
                list.stream().forEach(x -> x = orig.get(x));

        return l;
    }

    /**
     * @return List<Long> ce reprezinta componenta coneza cu cel mai lung drum
     */
    public List<Long> componentaConexaCuCelMaiLungDrum() {
        List<List<Long>> l = componenteConexe();
        int s = (int) utilizatorService.size();
        boolean[][] matrix = creareMatriceFalse(s + 1);
        boolean[][] matrixViz = creareMatriceFalse(s + 1);

        Map<Long, Long> transf = new HashMap<>(), orig = new HashMap<>();
        codareIduriUtilizatori(transf, orig);
        initializareMatrice(matrix, transf);

        //se afla pentru fiecare componenta conexa lungimea drumului maxim
        List<Long> lisMax = null;
        int max = -1;
        for (List<Long> lis : l)
            for (Long lon : lis) {
                AtomicInteger maxim = new AtomicInteger(0);
                drumLungimeMaxima(Math.toIntExact(lon), matrixViz, matrix, s, 0, maxim);
                if (max < maxim.get()) {
                    max = maxim.get();
                    lisMax = lis;
                }
            }
        //transformarea in id-urile originale
        if (lisMax != null)
            lisMax = lisMax.stream()
                    .map(x -> orig.get(x))
                    .collect(Collectors.toList());
        return lisMax;
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
        utilizatorPageService.makeReport1PDF(URL, date1, date2);
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
    public void makeReport2PDF(String URL, LocalDateTime date1, LocalDateTime date2, Utilizator friend) throws ServiceException, FileNotFoundException, DocumentException {
        utilizatorPageService.makeReport2PDF(URL, date1, date2, friend);
    }

    public String prieteniRap1(LocalDateTime date1, LocalDateTime date2) {
        return utilizatorPageService.prieteniRap1(date1, date2);
    }

    public String mesajeRap1(LocalDateTime date1, LocalDateTime date2) {
        return utilizatorPageService.mesajeRap1(date1, date2);
    }

    public String mesajeRap2(LocalDateTime date1, LocalDateTime date2, Utilizator friend) {
        return utilizatorPageService.mesajeRap2(date1, date2, friend);
    }

    public List<String> conversationOfOneMessage(Message message) {
        Long firstId;
        List<String> conversatie = new ArrayList<>();
        Long[] visitedMessage = new Long[500];
        int poz = 0;
        Message copyMessage = message;
        while (copyMessage.getClass().getName().equals("socialnetwork.domain.ReplyMessage")) {
            copyMessage = ((ReplyMessage) copyMessage).getReplayMessage();
        }
        firstId = copyMessage.getId();
        conversatie.add(copyMessage.getForGuiFrom() + ": " + copyMessage.getMessage() + "\nSend: " + copyMessage.getForGuiDate() + "\n");
        visitedMessage[poz++] = copyMessage.getId();
        if (copyMessage.getId() == message.getId())
            return conversatie;

        Iterable<Message> messageIterable = getMesaje();
        boolean ok = true;
        for (int i = 0; i < poz && ok; i++)
            for (Message m : messageIterable)
                if (m.getClass().getName().equals("socialnetwork.domain.ReplyMessage") &&
                        ((ReplyMessage) m).getReplayMessage().getId() == visitedMessage[i]) {
                    visitedMessage[poz++] = m.getId();
                    ReplyMessage t = (ReplyMessage) m;
                    String msg;

                    if (t.getReplayMessage().getMessage().length() > 20)
                        msg = "Replay: " + t.getReplayMessage().getMessage().substring(0, 20) + "...\n\t";
                    else
                        msg = "Replay: " + t.getReplayMessage().getMessage() + "\n\t";
                    msg = msg + m.getForGuiFrom() + ": " + m.getMessage() + "\n\tSend: " + m.getForGuiDate() + "\n";
                    conversatie.add(msg);
                    if (m.getId() == message.getId()) {
                        ok = false;
                        break;
                    }
                }
        return conversatie;
    }

    public void adaugareEveniment(LocalDateTime dataEveniment, String denumire, String descriere) throws ServiceException {
        utilizatorPageService.adaugareEveniment(dataEveniment, denumire, descriere);
        notifyObservers(Event.ADD_EVENIMENT);
    }

    public void deleteEveniment(Long id) throws ServiceException {
        utilizatorPageService.deleteEveniment(id);
        notifyObservers(Event.DELETE_EVENIMENT);
    }

    public void abonareEveniment(Long id, NotificationType note) throws ServiceException {
        utilizatorPageService.abonareEveniment(id, note);
        notifyObservers(Event.ADD_PARTICIPANT);
    }

    public void dezabonareEveniment(Long id) throws ServiceException {
        utilizatorPageService.dezabonareEveniment(id);
        notifyObservers(Event.DELETE_PARTICIPANT);
    }

    public void notificatiOn(Long id) throws ServiceException {
        utilizatorPageService.notificatiOn(id);
    }

    public void notificatiOff(Long id) throws ServiceException {
        utilizatorPageService.notificatiOff(id);
    }

    public void trimiteNotificariEvenimente() {
        evenimentService.trimiteNotificari();
    }

    public List<Utilizator> notYetFriends(Long id, Integer limit, String last, String first) {
        return utilizatorService.notYetFriends(id, limit, last, first);
    }

    public List<Utilizator> otherThanMe(Long id_user, Integer limit, String last, String first) {
        return utilizatorService.otherThanMe(id_user, limit, last, first);
    }

    public void updateUtilizator(){
        utilizatorPageService.updateUtilizator(getLogat().getId());
    }
}
