package service;

import domain.Cerere;
import domain.CerereType;
import domain.validators.RepositoryException;
import domain.validators.ServiceException;
import domain.validators.ValidationException;
import domain.validators.Validator;
import repository.database.CerereRepositoryBD;

import java.util.List;

import static domain.CerereType.*;

public class CerereService {
    private CerereRepositoryBD repo;
    private Validator<Cerere> validator;
    private final UtilizatorService utilizatorService;

    public CerereService(CerereRepositoryBD repo, Validator<Cerere> validator, UtilizatorService utilizatorService) {
        this.repo = repo;
        this.validator = validator;
        this.utilizatorService = utilizatorService;
    }

    /**
     * Metoda adauga noua cerere in repo
     *
     * @param cerere det tip Cerere si nu trebuie sa fie null
     * @throws ServiceException daca noua cerere nu intruneste caracteristicile necesare,
     *                          daca cererea de prietenie nu isi are locul, utilizatorii fiind deja prieteni,
     *                          daca exista deja o cerere in asteptare intre cei doi utilizatori
     */
    public void addCerere(Cerere cerere) throws ServiceException {
        try {
            validator.validate(cerere);
            Cerere c = repo.findOne(cerere.getFrom().getId(), cerere.getTo().getId());

            if (c != null && c.getType() == APPROVED)
                throw new RepositoryException("\tSunteti deja prieteni\n");

            if (c != null && c.getType() == PENDING && c.getFrom().getId() == cerere.getFrom().getId()
                    && c.getTo().getId() == cerere.getTo().getId())
                throw new RepositoryException("\tCererea este deja trimisa\n");
            else if (c != null && c.getType() == PENDING)
                throw new RepositoryException("\tCererea este in asteptare. Trebuie sa raspundeti la ea!\n");

            cerere.setType(PENDING);
            repo.save(cerere);
        } catch (ValidationException v) {
            throw new ServiceException(v.getMessage());
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    /**
     * Metoda sterge o cerere de prietenie aflata in pending
     *
     * @param id1 - id-ul utilizatorului 1 care nu trebuie sa fie null
     * @param id2 - id-ul utilizatorului 2 care nu trebuie sa fie null
     * @return cererea care este stearsa
     * @throws ServiceException daca nu exista cei doi utilizatori,
     *                          daca nu exista nicio cerere in pending intre cei doi,
     *                          daca se doreste stergerea cererii in asteptare trimisa de catre altcineva
     */
    public Cerere deleteCererePending(Long id1, Long id2) throws ServiceException {
        try {
            String eroare = "";
            if (utilizatorService.findUtilizator(id1) == null)
                eroare = eroare + "\tPrimul utilizator nu exista\n";
            if (utilizatorService.findUtilizator(id2) == null)
                eroare = eroare + "\tAl doilea utilizator nu exista\n";
            if (!eroare.isEmpty())
                throw new RepositoryException(eroare);

            Cerere c = repo.findOne(id1, id2);
            if (c != null && c.getType() == PENDING && c.getFrom().getId() == id1 &&
                    c.getTo().getId() == id2) {
                repo.delete(c.getId());
                return c;
            } else if (c != null && c.getType() == PENDING)
                throw new RepositoryException("\tNu aveti dreptul sa stergeti aceasta cerere de prietenie\n");
            throw new RepositoryException("\tNu exista nicio cerere in pending intre cei doi utilizatori\n");
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }


    /**
     * Se poate face update doar pe cereri care sunt in asteptare -> se accepta sau nu
     *
     * @param cerere de tip Cerere nu trebuie sa fie null
     * @return cererea care a fost updated
     * @throws ServiceException daca cererea nu este valida,
     *                          daca tipul cererii pentru care se face update nu este corect,
     *                          daca se incearca update-ul de catre persoana care a trimis cererea,
     *                          daca nu exista nicio cerere in asteptare intre cei doi
     */
    public Cerere updateCererePending(Cerere cerere) throws ServiceException {
        try {
            validator.validate(cerere);

            Cerere c = repo.findOne(cerere.getFrom().getId(), cerere.getTo().getId());
            if (c != null && c.getType() == PENDING) {
                if (cerere.getType() != REJECTED && cerere.getType() != APPROVED)
                    throw new ValidationException("\tTipul cererii poate fi doar APPROVED/REJECTED\n");
                if (cerere.getFrom().getId() == c.getFrom().getId() && cerere.getTo().getId() == c.getTo().getId()) {
                    cerere.setId(c.getId());
                    repo.update(cerere);
                    return c;
                }
                throw new RepositoryException("\tTu ai trimis cererea, nu poti accepta sau respinge cererea\n");
            }
            throw new RepositoryException("\tNu exista nicio cerere in asteptare intre cei doi utilizatori\n");
        } catch (ValidationException v) {
            throw new ServiceException(v.getMessage());
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    /**
     * Se face update la o cerere care a fost acceptata, dar care se doreste sa fie stearsa in cazul in care
     * se sterge o prietenie, ea ramanand totusi in memorie, dar cu statutul noMoreFriends
     *
     * @param cerere de tip Cerere nu trebuie sa fie null
     * @return cererea care a fost updated
     * @throws ServiceException daca cererea nu este valida,
     *                          daca nu exista o cerere aprobata intre cei di
     */
    public Cerere updateCerereStergePrietenie(Cerere cerere) throws ServiceException {
        try {
            validator.validate(cerere);

            Cerere c = repo.findOne(cerere.getFrom().getId(), cerere.getTo().getId());
            if (c != null && c.getType() == APPROVED) {
                cerere.setId(c.getId());
                cerere.setType(NOMOREFRIENDS);
                repo.update(cerere);
                return c;
            }
            throw new RepositoryException("\tCei doi utilizatori nu sunt prieteni\n");
        } catch (ValidationException v) {
            throw new ServiceException(v.getMessage());
        } catch (RepositoryException r) {
            throw new ServiceException(r.getMessage());
        }
    }

    /**
     * @return toate cererile
     */
    public Iterable<Cerere> getAll() {
        return repo.findAll();
    }

    public List<Cerere> cereriPrimite(Long id_user,Integer limit, Integer offset){
        return repo.cereriPrimite(id_user,limit,offset);
    }

    public List<Cerere> cereriTrimise(Long id_user,Integer limit, Integer offset){
        return repo.cereriTrimise(id_user,limit,offset);
    }
}
