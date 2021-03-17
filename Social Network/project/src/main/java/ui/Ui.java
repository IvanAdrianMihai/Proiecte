/*
package socialnetwork.ui;

import socialnetwork.domain.Message;
import socialnetwork.domain.Utilizator;
import socialnetwork.domain.validators.*;
import socialnetwork.service.SuperService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static socialnetwork.domain.Constants.DATE_TIME_FORMATTER;

*/
/**
 * Realizeaza interactiunea cu utilizatorul
 *//*


public class Ui {
    */
/**
     * Metoda afisaza meniul
     *//*

    private static void printMeniu() {
        System.out.println("Meniu:");
        System.out.println("\t1.Add utilizator");
        System.out.println("\t2.Remove utilizator");
        System.out.println("\t3.Relatiile de prietenie ale unui utilizator");
        System.out.println("\t4.Relatiile de prietenie ale unui utilizator dintr-o luna");
        System.out.println("\t5.Print All Users");
        System.out.println("\t6.Print All Friendships");
        System.out.println("\t7.Print All Requests");
        System.out.println("\t8.Print All Messages");
        System.out.println("\t9.Conversatiile dintre doi utilizatori");
        System.out.println("\t10.Logare");
        System.out.println("\t11.Exit");
        System.out.print("\nIntroduceti optiunea dorita: ");
    }

    */
/**
     * Metoda afisaza meniul utilizatorului
     *//*

    private static void printMeniuUtilizator() {
        System.out.println("Meniu:");
        System.out.println("\t1.Add cerere");
        System.out.println("\t2.Replay cerere");
        System.out.println("\t3.Delete cerere");
        System.out.println("\t4.Delete prietenie");
        System.out.println("\t5.Cereri primite");
        System.out.println("\t6.Cereri trimise");
        System.out.println("\t7.Mesaj nou");
        System.out.println("\t8.Replay to all - message");
        System.out.println("\t9.Replay to one - message");
        System.out.println("\t10.Messages received");
        System.out.println("\t11.Messages sent");
        System.out.println("\t12.Delogare");
        System.out.print("\nIntroduceti optiunea dorita: ");
    }


    private static void addUt(SuperService service) throws ServiceException {
        String nume = "", prenume = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti numele utilizatorului: ");
        try {
            nume = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Introduceti prenumele utilizatorului: ");
        try {
            prenume = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //service.addUtilizator(nume, prenume);
    }


    private static void delUt(SuperService service) throws ServiceException, ImputException {
        String id = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul utilizatorului: ");
        try {
            id = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (!ImputValidator.isLong(id))
            throw new ImputException("\tId-ul trebuie sa fie un numar intreg\n");
        //service.deleteUtilizator(Long.parseLong(id));
    }


    private static void iduriIntregi(String ID1, String ID2) throws ImputException {
        String eroare = "";
        if (!ImputValidator.isLong(ID1))
            eroare = eroare + "\tId-ul utilizatorului trebuie sa fie un numar intreg\n";
        if (!ImputValidator.isLong(ID2))
            eroare = eroare + "\tId-ul prietenului trebuie sa fie un numar intreg\n";
        if (!eroare.isEmpty())
            throw new ImputException(eroare);
    }


    private static void addFr(SuperService service) throws ImputException, ServiceException {
        String id1 = "", id2 = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul utilizatorului: ");
        try {
            id1 = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Introduceti id-ul prietenului: ");
        try {
            id2 = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        iduriIntregi(id1, id2);
        //service.addPrietenie(Long.parseLong(id1), Long.parseLong(id2));
    }

    private static void delFr(SuperService service) throws ImputException, ServiceException {
        String id1 = "", id2 = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul utilizatorului: ");
        try {
            id1 = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Introduceti id-ul prietenului: ");
        try {
            id2 = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        iduriIntregi(id1, id2);
        //service.deletePrietenie(Long.parseLong(id1), Long.parseLong(id2));
    }

    private static void raport1(SuperService service) throws ImputException, ServiceException {
        String id = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul utilizatorului: ");
        try {
            id = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (!ImputValidator.isLong(id))
            throw new ImputException("\tId-ul utilizatorului trebuie sa fie un numar intreg\n");
        System.out.println("Lista prietenilor este:");
        service.prieteniUtilizator(Long.parseLong(id)).forEach(System.out::println);
        System.out.println();
    }

    private static void raport2(SuperService service) throws ImputException, ServiceException {
        String id = "", mounth = "", eroare = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul utilizatorului: ");
        try {
            id = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Introduceti luna dorita: ");
        try {
            mounth = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (!ImputValidator.isLong(id))
            eroare = eroare + "\tId-ul utilizatorului trebuie sa fie un numar intreg\n";
        if (!ImputValidator.isLong(mounth))
            eroare = eroare + "\tLuna trebuie sa fie un numar natural [1,12]\n";
        else if (Long.parseLong(mounth) < 1 || Long.parseLong(mounth) > 12)
            eroare = eroare + "\tLuna trebuie sa fie un numar natural [1,12]\n";
        if (!eroare.isEmpty())
            throw new ImputException(eroare);
        System.out.println("Lista prietenilor din luna " + Integer.parseInt(mounth) + " este:");
        service.prieteniUtilizatorMounth(Long.parseLong(id), Integer.parseInt(mounth)).forEach(System.out::println);
        System.out.println();
    }

    private static void logare(SuperService service) throws ImputException, ServiceException {
        String id = "", eroare = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul utilizatorului: ");
        try {
            id = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (!ImputValidator.isLong(id))
            throw new ImputException("\tId-ul utilizatorului trebuie sa fie un numar intreg\n");
        Utilizator u = service.getUtilizator(Long.parseLong(id));
        if (u == null)
            throw new ServiceException("\tNu exista acest utilizator\n");
        service.setLogat(u);
    }

    private static void conv(SuperService service) throws ImputException, ServiceException {
        String id1 = "", id2 = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul utilizatorului: ");
        try {
            id1 = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Introduceti id-ul prietenului: ");
        try {
            id2 = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        iduriIntregi(id1, id2);
        if (id1 == id2)
            throw new ImputException("\tNu exista conversatii ale persoanei cu ea insasi\n");
        List<List<Message>> lis = service.getConversation(Long.parseLong(id1), Long.parseLong(id2));
        int nr = 1;
        for (List<Message> liste : lis) {
            System.out.println("Conversatia " + nr + ":");
            nr++;
            for (Message mes : liste) {
                System.out.print("\t" + mes.getFrom().getFirstName() + " " + mes.getFrom().getLastName() + ": ");
                System.out.println(mes.getMessage());
                System.out.println("\t\tSend at " + mes.getDate().format(DATE_TIME_FORMATTER));
            }
            System.out.println();
        }
    }

    private static void conv1(SuperService service) throws ImputException, ServiceException {
        String id1 = "", id2 = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul utilizatorului: ");
        try {
            id1 = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Introduceti id-ul prietenului: ");
        try {
            id2 = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        iduriIntregi(id1, id2);
        if (id1 == id2)
            throw new ImputException("\tNu exista conversatii ale persoanei cu ea insasi\n");
        List<Message> lis = service.getConversationMessages(Long.parseLong(id1), Long.parseLong(id2));
        for (Message mes : lis) {
            System.out.print("\t" + mes.getFrom().getFirstName() + " " + mes.getFrom().getLastName() + ": ");
            System.out.println(mes.getMessage());
            System.out.println("\t\tSend at " + mes.getDate().format(DATE_TIME_FORMATTER));
        }
    }

    private static void addCerere(SuperService service) throws ImputException, ServiceException {
        String id = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul prietenului: ");
        try {
            id = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (!ImputValidator.isLong(id))
            throw new ImputException("\tId-ul prietenului trebuie sa fie un numar intreg\n");
        service.addCerere(service.getLogat().getId(), Long.parseLong(id));
    }

    private static void replayCerere(SuperService service) throws ImputException, ServiceException {
        String id = "", raspuns = "", eroare = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul prietenului la cererea caruia raspundeti: ");
        try {
            id = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Introduceti raspunsul[a/r]: ");
        try {
            raspuns = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (!ImputValidator.isLong(id))
            eroare = eroare + "\tId-ul prietenului trebuie sa fie un numar intreg\n";
        if (!raspuns.equals("a") && !raspuns.equals("r"))
            eroare = eroare + "\tRaspunsul este fie a-approved, fie r-rejected\n";
        if (!eroare.isEmpty())
            throw new ImputException(eroare);
        if (raspuns.equals("a"))
            service.approveCerere(service.getLogat().getId(), Long.parseLong(id));
        else
            service.rejectCerere(service.getLogat().getId(), Long.parseLong(id));
    }

    private static void deleteCerere(SuperService service) throws ImputException, ServiceException {
        String id = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul prietenului pentru care stergeti cererea: ");
        try {
            id = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (!ImputValidator.isLong(id))
            throw new ImputException("\tId-ul prietenului trebuie sa fie un numar intreg\n");
        service.deleteCerere(service.getLogat().getId(), Long.parseLong(id));
    }

    private static void deletePrietenie(SuperService service) throws ImputException, ServiceException {
        String id = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul prietenului: ");
        try {
            id = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (!ImputValidator.isLong(id))
            throw new ImputException("\tId-ul prietenului trebuie sa fie un numar intreg\n");
        service.deletePrietenieCerere(service.getLogat().getId(), Long.parseLong(id));
    }

    private static void mesajNou(SuperService service) throws ImputException, ServiceException {
        String idUri = "", mesaj = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-urile utilizatorilor: ");
        try {
            idUri = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Introduceti mesajului: ");
        try {
            mesaj = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        List<Long> id_uri = new ArrayList<>();
        String[] splituit = idUri.split(" ");
        for (String s : splituit)
            if (!ImputValidator.isLong(s))
                throw new ImputException("\tId-urile trebuie sa fie numere intregi\n");
            else
                id_uri.add(Long.parseLong(s));
        service.writeMessage(service.getLogat().getId(), id_uri, mesaj);
    }

    private static void all(SuperService service) throws ImputException, ServiceException {
        String id = "", mesaj = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul mesajului: ");
        try {
            id = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Introduceti mesajului: ");
        try {
            mesaj = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (!ImputValidator.isLong(id))
            throw new ImputException("\tId-ul trebuie sa fie numere intregi\n");
        service.replayToAll(service.getLogat().getId(), Long.parseLong(id), mesaj);
    }

    private static void one(SuperService service) throws ImputException, ServiceException {
        String id = "", idMesaj = "", mesaj = "", eroare = "";
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Introduceti id-ul mesajului: ");
        try {
            idMesaj = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Introduceti id-ul utilizatorului: ");
        try {
            id = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.print("Introduceti mesajului: ");
        try {
            mesaj = obj.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (!ImputValidator.isLong(idMesaj))
            throw new ImputException("\tId-ul mesajului trebuie sa fie numere intregi\n");
        if (!ImputValidator.isLong(id))
            throw new ImputException("\tId-ul utilizatorului trebuie sa fie numere intregi\n");
        service.replayToOne(service.getLogat().getId(), Long.parseLong(id), Long.parseLong(idMesaj), mesaj);
    }

    */
/**
     * Metoda realizeaza gestiunea meniului
     *
     * @param service de tip SuperService
     *//*

    public static void meniu(SuperService service) {
        BufferedReader obj = new BufferedReader(new InputStreamReader(System.in));
        String optiune = "-1";
        int opt;
        boolean stop = false;
        do {
            if (service.getLogat() == null && !stop) {
                printMeniu();
                try {
                    optiune = obj.readLine();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    opt = Integer.parseInt(optiune);
                } catch (IllegalArgumentException e) {
                    opt = -1;
                }
                if (opt < 1 || opt > 11)
                    System.out.println("\tComanda gresita!");
                switch (opt) {
                    case 1:
                        try {
                            addUt(service);
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            delUt(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            raport1(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            raport2(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 5:
                        System.out.println("Utilizatorii sunt:");
                        service.getUtilizatori().forEach(System.out::println);
                        System.out.println();
                        break;
                    case 6:
                        System.out.println("Prieteniile sunt:");
                        service.getPrietenii().forEach(System.out::println);
                        System.out.println();
                        break;
                    case 7:
                        System.out.println("Cererile sunt:");
                        service.getCereri().forEach(System.out::println);
                        System.out.println();
                        break;
                    case 8:
                        System.out.println("Mesajele sunt:");
                        service.getMesaje().forEach(System.out::println);
                        System.out.println();
                        break;
                    case 9:
                        try {
                            conv(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 10:
                        try {
                            logare(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 11:
                        stop = true;
                        break;
                }
            } else if (!stop) {

                printMeniuUtilizator();
                try {
                    optiune = obj.readLine();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    opt = Integer.parseInt(optiune);
                } catch (IllegalArgumentException e) {
                    opt = -1;
                }
                if (opt < 1 || opt > 12)
                    System.out.println("\tComanda gresita!");
                switch (opt) {
                    case 1:
                        try {
                            addCerere(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 2:
                        try {
                            replayCerere(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 3:
                        try {
                            deleteCerere(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            deletePrietenie(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 5:
                        System.out.println("Cererile primite sunt:");
                        service.getCereriPrimiteInAsteptare().forEach(System.out::println);
                        System.out.println();
                        break;
                    case 6:
                        System.out.println("Cererile trimise sunt:");
                        service.getCereriTrimiseInAsteptare().forEach(System.out::println);
                        System.out.println();
                        break;
                    case 7:
                        try {
                            mesajNou(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 8:
                        try {
                            all(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 9:
                        try {
                            one(service);
                        } catch (ImputException v) {
                            System.out.println("Exceptie la introducerea datelor:");
                            System.out.println(v.getMessage());
                        } catch (ServiceException s) {
                            System.out.println("Exceptie:");
                            System.out.println(s.getMessage());
                        }
                        break;
                    case 10:
                        System.out.println("Mesajele primite sunt:");
                        service.getMessageFor().forEach(System.out::println);
                        System.out.println();
                        break;
                    case 11:
                        System.out.println("Mesajele trimise sunt:");
                        service.getMessageSendBy().forEach(System.out::println);
                        System.out.println();
                        break;
                    case 12:
                        service.setLogat(null);
                        break;
                }
            }
        } while (!stop);
        System.out.println("La revedere!");
    }
}
*/
